package com.klin.holoItems.dungeons.inaDungeon;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.dungeons.Resetable;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Rail;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.util.Vector;

import java.util.*;

public class Payload implements Resetable {
    //payload world -138 59 -380 -294
    public Map<Location, BlockData> payload;
    public List<Block> spikes;
    private boolean shop;

    public Payload(Location loc, int z, boolean shop) {
        payload = InaDungeon.build("payload", loc.clone().add(0, 0, -6), null, null, BlockFace.SOUTH);
        if(payload==null){
            reset();
            return;
        }
        Block block = loc.clone().add(2, -1, 0).getBlock();
        spikes = new ArrayList<>();
        while(block.getZ()<=z){
            Block relative;
            while(!(relative = block.getRelative(BlockFace.UP)).isPassable())
                block = relative;
            spikes.add(block);
            block = block.getRelative(BlockFace.SOUTH);
        }
        this.shop = shop;
    }

    public void push(int z){
        Map<Integer, Map<Location, BlockData>> train = new HashMap<>();
        for(Location loc : payload.keySet()){
            Map<Location, BlockData> car = train.computeIfAbsent(loc.getBlockZ(), k -> new HashMap<>());
            Block block = loc.getBlock();
            car.put(loc, block.getBlockData());
            block.setBlockData(payload.get(loc));
        }
        Map<Integer, Set<Player>> riders = new HashMap<>();
        for(Player player : Bukkit.getOnlinePlayers()){
            Location loc = player.getLocation().getBlock().getLocation();
            if(payload.containsKey(loc.clone().add(0, -1, 0)))
                riders.computeIfAbsent(loc.getBlockZ(), k -> new HashSet<>()).add(player);
            else if(payload.containsKey(loc)){
                player.setNoDamageTicks(0);
                player.damage(10);
                player.setVelocity(new Vector(0, 0.45, 0));
            }
        }
        payload = new HashMap<>();
        Map<Location, BlockData> car;
        while((car = train.get(z))!=null){
            boolean up = false;
            for(Location loc : car.keySet()){
                if(!loc.getBlock().getRelative(BlockFace.SOUTH).isPassable()){
                    up = true;
                    break;
                }
            }
            for(Location loc : car.keySet()){
                Block block = loc.getBlock();
                block = block.getRelative(BlockFace.SOUTH);
                if(up)
                    block = block.getRelative(BlockFace.UP);
                payload.put(block.getLocation(), block.getBlockData());
                block.setBlockData(car.get(loc));
            }
            Set<Player> players = riders.get(z);
            if(players!=null){
                for(Player player : players)
                    player.teleport(player.getLocation().add(0, up?1:0, 1));
            }
            z--;
        }
    }

    public void fire(Location location){
        World world = Utility.getRandom(payload.keySet()).get().getWorld();
        List<Location> charges = new ArrayList<>();
        Map<Location, BlockData> frame = new HashMap<>();
        Map<Location, FallingBlock> mirage = new HashMap<>();
        for(Location loc : payload.keySet()){
            Block block = loc.getBlock();
            BlockData data = block.getBlockData();
            if(data.getMaterial()==Material.TNT || payload.containsKey(block.getRelative(BlockFace.DOWN).getLocation())){
                if(data.getMaterial()==Material.TNT)
                    charges.add(loc);
                else
                    frame.put(loc, data);
                block.setBlockData(payload.get(loc));
                FallingBlock fallingBlock = world.spawnFallingBlock(loc.clone().add(0.5, 0, 0.5), data);
                fallingBlock.setGravity(false);
                mirage.put(loc, fallingBlock);
            }
        }
        world.playSound(location.add(0, 1, 0), Sound.EVENT_RAID_HORN, 10, 1);
        Location aim = location.clone().add(0, 10, 16);
        new Task(HoloItems.getInstance(), 40, 5){
            public void run(){
                if(charges.isEmpty()){
                    for(FallingBlock fallingBlock : mirage.values())
                        fallingBlock.remove();
                    for(Location loc : frame.keySet())
                        loc.getBlock().setBlockData(frame.get(loc));
                    if(shop) {
                        AoShop aoShop = (AoShop) InaDungeon.presets.get("aoshop");
                        if (aoShop == null)
                            InaDungeon.presets.put("aoshop", new AoShop(world, location.add(0.5, -5.9, 30.5)));
                    }
                    cancel();
                    return;
                }
                Location loc = charges.remove(0);
                mirage.get(loc).remove();
                TNTPrimed tnt = world.spawn(loc.clone().add(0.5, 0.5, 0.5), TNTPrimed.class);
                tnt.setVelocity(aim.clone().subtract(loc).toVector().multiply(0.1));
                new Task(HoloItems.getInstance(), 10, 1){
                    int increment = 0;
                    public void run(){
                        Location loc = tnt.getLocation();
                        if(increment>60 || tnt.isOnGround() || tnt.getVelocity().getZ()<0.1){
                            world.spawnParticle(Particle.EXPLOSION_HUGE, loc, 1);
                            world.playSound(loc, Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 4, 1);
                            tnt.remove();
                            cancel();
                            return;
                        }
                        world.spawnParticle(Particle.REDSTONE, loc, 1, new Particle.DustOptions(Color.RED, 1));
                        increment++;
                    }
                };
            }
        };
    }

    public void guide(){
        Iterator<Block> guide = spikes.iterator();
        Rail rail = (Rail) Material.RAIL.createBlockData();
        rail.setShape(Rail.Shape.NORTH_SOUTH);
        new Task(HoloItems.getInstance(), 0, 2){
            Block prev;
            Block current = null;
            public void run(){
                if(current!=null)
                    (prev = current).setType(Material.AIR);
                if(!guide.hasNext())
                    cancel();
                else
                    (current = guide.next().getRelative(BlockFace.UP)).setBlockData(rail);
            }
        };
    }

    public void reset(){
        for (Location loc : payload.keySet())
            loc.getBlock().setBlockData(payload.get(loc));
    }
}
