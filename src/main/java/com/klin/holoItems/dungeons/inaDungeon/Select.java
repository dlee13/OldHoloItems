package com.klin.holoItems.dungeons.inaDungeon;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.dungeons.Resetable;
import com.klin.holoItems.dungeons.inaDungeon.members.*;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

import static org.bukkit.Bukkit.getServer;

public class Select implements Listener, Resetable {
    //select buildteam 885 110 -189
    //select world 78 120 -231
    private final Map<Player, Member> classes;
    private final Location center;
    private final List<Block> source;
    private boolean ina;
    private final List<Block> chain;
    private Map<Location, BlockData> cage;

    public Select(World world, int x, int y, int z){
        classes = new HashMap<>();
        center = new Location(world, x, y, z);
        source = Utility.vacuum(world.getBlockAt(center), Material.WATER, 500, false);
        ina = false;
        chain = new ArrayList<>();
        cage = null;
        getServer().getPluginManager().registerEvents(this, HoloItems.getInstance());
    }

    @EventHandler
    public void select(EntityDamageEvent event){
        if(event.getCause()!=EntityDamageEvent.DamageCause.VOID)
            return;
        Entity entity = event.getEntity();
        if(!(entity instanceof Player))
            return;
        Player player = (Player) entity;
        Location loc = player.getLocation();
        Vector select = loc.clone().subtract(center).toVector().setY(0).normalize();
        loc.setY(center.getY());
        if(ina && center.distance(loc)<4.25)
            classes.put(player, new Ina(player));
        else {
            double side = select.getX();
            double choice = select.getZ();
            if (side > 0.8) {
                if (choice < -0.2)
                    classes.put(player, new Gura(player));
                else if (choice < 0.2)
                    classes.put(player, new Watson(player));
                else
                    classes.put(player, new Kiara(player));
            } else if (side < -0.8) {
                if (choice < -0.2)
                    classes.put(player, new Calli(player));
                else if (choice < 0.2)
                    classes.put(player, new Enma(player));
                else
                    classes.put(player, new Irys(player));
            }
        }
        event.setCancelled(true);
        player.setFallDistance(0);
        player.teleport(center);
    }

    @EventHandler
    public void select(EntityBlockFormEvent event){
        Block ice = event.getBlock();
        if(source.contains(ice)) {
            new BukkitRunnable() {
                public void run() {
                    if(ice.getType()!=Material.FROSTED_ICE)
                        return;
                    boolean melt = false;
                    for (Block block : source) {
                        if (block.getType()!=Material.FROSTED_ICE) {
                            melt = true;
                            break;
                        }
                    }
                    if(melt){
                        new BukkitRunnable(){
                            public void run(){
                                ice.setType(Material.WATER);
                            }
                        }.runTaskLater(HoloItems.getInstance(), 120);
                        return;
                    }
                    List<Block> temp = new ArrayList<>(source);
                    new Task(HoloItems.getInstance(), 1, 1){
                        public void run(){
                            for(int i=0; i<4; i++){
                                Block block = temp.remove(0);
                                block.setType(Material.ICE);
                                if(temp.isEmpty()){
                                    cancel();
                                    return;
                                }
                            }
                        }
                    };
                    String[][] strings = new String[][]{
                            {"minecraft:stone_brick_stairs[facing=east,half=bottom,shape=straight,waterlogged=false]", "minecraft:stone_bricks", "minecraft:cracked_stone_bricks", "minecraft:stone_brick_stairs[facing=east,half=top,shape=straight,waterlogged=false]"},
                            {"minecraft:stone_bricks", "minecraft:air", "minecraft:air", "minecraft:stone_bricks"},
                            {"minecraft:stone_brick_stairs[facing=west,half=bottom,shape=straight,waterlogged=false]", "minecraft:cracked_stone_bricks", "minecraft:stone_bricks", "minecraft:stone_brick_stairs[facing=west,half=top,shape=straight,waterlogged=false]"}
                    };
                    BlockData[][] link = new BlockData[3][4];
                    BlockData[][] klin = new BlockData[3][4];
                    for(int i=0; i<3; i++){
                        for(int j=0; j<4; j++) {
                            BlockData data = Bukkit.createBlockData(strings[i][j]);
                            link[i][j] = data;
                            if(data instanceof Directional){
                                Directional directional = (Directional) data.clone();
                                directional.setFacing(Utility.opposites.get(Utility.left.get(directional.getFacing())));
                                klin[i][j] = directional;
                            }
                            else
                                klin[i][j] = data;
                        }
                    }
                    Location clone = center.clone().add(-1, 0, -1);
                    clone.setY(256);
                    new Task(HoloItems.getInstance(), 20, 1){
                        int increment = 0;
                        int pull = 84;
                        int slow = 1;
                        int down = 0;
                        public void run(){
                            if(pull<increment+10){
                                down++;
                                if(slow<down){
                                    slow++;
                                    down = 0;
                                }
                                else{
                                    increment++;
                                    return;
                                }
                            }
                            Location location = clone.clone();
                            boolean orientation = true;
                            for(Block block : chain)
                                block.setType(Material.AIR);
                            for(int k=0; k<increment; k++) {
                                for (int i = 0; i < 3; i++) {
                                    for (int j = 0; j < 4; j++) {
                                        Block block = location.clone().add(orientation?i:1, -j, orientation?1:i).getBlock();
                                        block.setBlockData(orientation?link[i][j]:klin[i][j]);
                                        chain.add(block);
                                    }
                                }
                                location.add(0, -3, 0);
                                if(pull>increment+10 && location.getY()<center.getY()){
                                    Location temp = location.clone();
                                    Utility.explode(center, 12, Map.of(Material.ICE, Material.BLUE_ICE));
                                    cage = new HashMap<>();
                                    Location loc = temp.clone().add(-9, 1, -10);
                                    new Task(HoloItems.getInstance(), 10, 1){
                                        int increment = 0;
                                        int frame = 0;
                                        public void run(){
                                            increment++;
                                            if(frame==0 && increment>=4 || frame==1 && increment>=2 || frame==2 && increment>=1) {
                                                cage = InaDungeon.build("cage"+frame, loc, cage, Set.of(), BlockFace.SOUTH);
                                                frame++;
                                                increment = 0;
                                            }
                                            if(frame>2){
                                                temp.add(1, 0, 1);
                                                boolean dig = true;
                                                while(dig){
                                                    dig = false;
                                                    for(int i=-3; i<=3; i++){
                                                        for(int j=-3; j<=3; j++){
                                                            Block block = temp.clone().add(i, 0, j).getBlock();
                                                            if(!block.getType().isAir()) {
                                                                cage.put(block.getLocation(), block.getBlockData());
                                                                block.setType(Material.AIR);
                                                                dig = true;
                                                            }
                                                        }
                                                    }
                                                    temp.add(0, -1, 0);
                                                }
                                                Location tip = center.clone().add(0, 18, 0);
                                                World world = tip.getWorld();
                                                for(int l=0; l<24; l++) {
                                                    for (int i=-2; i<=2; i++) {
                                                        for (int j=-1; j<=1; j++) {
                                                            Location loc = tip.clone().add(i, l, j);
                                                            Block block = loc.getBlock();
                                                            if(block.getType().isAir())
                                                                continue;
                                                            FallingBlock fallingBlock = world.spawnFallingBlock(loc, block.getBlockData());
                                                            fallingBlock.setVelocity(new Vector((Math.random()-0.5)*2, (Math.random()-0.5)*2, (Math.random()-0.5)*2));
                                                            fallingBlock.setDropItem(false);
                                                            block.setType(Material.AIR);
                                                        }
                                                    }
                                                }
                                                InaDungeon.build("tip", tip.clone().add(-1, 0, -1), null, null, BlockFace.SOUTH);
                                                List<Block> shatter = new ArrayList<>(chain);
                                                Set<Location> ignore = cage.keySet();
                                                new Task(HoloItems.getInstance(), 1, 1){
                                                    public void run(){
                                                        for(int i=0; i<20; i++){
                                                            if(shatter.isEmpty()){
                                                                cancel();
                                                                return;
                                                            }
                                                            Block block;
                                                            if(!ignore.contains((block = shatter.remove(shatter.size()-1)).getLocation())) {
                                                                block.setType(Material.AIR);
                                                                chain.remove(block);
                                                            }
                                                        }
                                                    }
                                                };
                                                cancel();
                                            }
                                        }
                                    };
                                    pull = increment+10;
                                }
                                orientation = !orientation;
                            }
                            clone.add(0, -1, 0);
                            increment++;
                            if(increment>=pull)
                                cancel();
                        }
                    };
                    ina = true;
                }
            }.runTask(HoloItems.getInstance());
        }
    }

    @EventHandler
    public void melt(BlockFadeEvent event){
        if(!event.isCancelled() && event.getNewState().getType()==Material.WATER)
            event.setCancelled(true);
    }

    @EventHandler
    public void shatter(EntityChangeBlockEvent event){
        if(!event.isCancelled() && event.getEntity() instanceof FallingBlock)
            event.setCancelled(true);
    }

    public void freeze(){
        for(Block block : source)
            block.setType(Material.FROSTED_ICE);
    }

    public void reset(){
        EntityDamageEvent.getHandlerList().unregister(this);
        EntityBlockFormEvent.getHandlerList().unregister(this);
        BlockFadeEvent.getHandlerList().unregister(this);
        EntityChangeBlockEvent.getHandlerList().unregister(this);
        if(!classes.isEmpty())
            ((Maintenance) InaDungeon.presets.computeIfAbsent("maintenance", k -> new Maintenance())).members = classes;
        if(cage!=null){
            for(Location loc : cage.keySet())
                loc.getBlock().setBlockData(cage.get(loc));
        }
        for(Block block : chain)
            block.setType(Material.AIR);
        for(Block block : source)
            block.setType(Material.WATER);
    }
}
