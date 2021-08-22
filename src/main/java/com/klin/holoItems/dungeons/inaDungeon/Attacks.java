package com.klin.holoItems.dungeons.inaDungeon;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.collections.misc.utilityCollection.items.NoDrop;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.entity.*;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Attacks {

    public static void spreadFire(BlockFace burn, Block block, Set<BlockFace> spread, Set<Block> checked, Set<BlockFace> traversed, boolean lop){
        MultipleFacing flame = (MultipleFacing) Bukkit.createBlockData(Material.FIRE);
        if(burn!=BlockFace.DOWN)
            flame.setFace(burn, true);
        new Task(HoloItems.getInstance(), 2, 2){
            Set<Block> fire = Stream.of(block).collect(Collectors.toCollection(HashSet::new));
            int increment = 0;
            boolean loop = lop;
            public void run(){
                Set<Block> next = new HashSet<>();
                for(Block block : fire){
                    block.setBlockData(flame);
                    for(BlockFace face : spread){
                        Block relative = block.getRelative(face);
                        if(relative.isEmpty()) {
                            if(!checked.contains(relative)) {
                                checked.add(relative);
                                next.add(relative);
                            }
                        }
                        else if(!relative.getType().equals(Material.FIRE) && !traversed.contains(face)){
                            traversed.add(face);
                            if(loop && traversed.size()>=6){
                                loop = false;
                                traversed.clear();
                                checked.clear();
                            }
                            Set<BlockFace> recur;
                            if(spread.contains(BlockFace.UP))
                                recur = Utility.cardinal.keySet();
                            else {
                                BlockFace left = Utility.left.get(face);
                                recur = Stream.of(BlockFace.UP, BlockFace.DOWN, left, Utility.opposites.get(left)).collect(Collectors.toCollection(HashSet::new));
                            }
                            spreadFire(face, block, recur, checked, traversed, loop);
                        }
                    }
                }
                new BukkitRunnable(){
                    final Set<Block> air = new HashSet<>(fire);
                    public void run(){
                        for(Block block : air)
                            block.setType(Material.AIR);
                    }
                }.runTaskLater(HoloItems.getInstance(), 3);

                fire = next;
                if(fire.isEmpty() || increment>=400)
                    cancel();
                increment++;
            }
        };
    }

    public static void groundPound(Entity entity){
        Vector dir = entity.getLocation().getDirection().multiply(0.3);
        Vector axis = new Vector(0, 0.5, 0);
        new Task(HoloItems.getInstance(), 1, 1){
            Squid squid = null;
            public void run(){
                if(!entity.isValid()){
                    cancel();
                    return;
                }
                if(entity.isOnGround()){
                    World world = entity.getWorld();
                    Location center = entity.getLocation().add(0, -1, 0);
                    Block block = world.getBlockAt(center);
                    Set<Block> blocks = new HashSet<>();
                    Set<Block> checked = Stream.of(block).collect(Collectors.toCollection(HashSet::new));
                    for(BlockFace face : Utility.cardinal.keySet()){
                        Block relative = block.getRelative(face);
                        blocks.add(relative);
                        checked.add(relative);
                    }
                    Map<Block, Material> replace = new HashMap<>();
                    new Task(HoloItems.getInstance(), 1, 1){
                        int increment = 0;
                        Set<Block> ground = blocks;
                        int radius = 0;
                        int pound = 0;
                        public void run(){
                            if(increment>40 || radius>=8){
                                for(Block block : replace.keySet())
                                    block.setType(replace.get(block));
                                if(squid!=null){
                                    entity.setVelocity(squid.getLocation().subtract(entity.getLocation()).toVector());
                                    new Task(HoloItems.getInstance(), 1, 1){
                                        int increment = 0;
                                        public void run(){
                                            if(increment>=20 || entity.getLocation().distance(squid.getLocation())<=1) {
                                                entity.setVelocity(entity.getVelocity().normalize());
                                                entity.addPassenger(squid);
                                                cancel();
                                                return;
                                            }
                                            increment++;
                                        }
                                    };
                                }
                                cancel();
                                return;
                            }
                            if(pound>=5){
                                Set<Block> surrounding = new HashSet<>();
                                for(Block block : ground) {
                                    for (BlockFace face : Utility.cardinal.keySet()) {
                                        Block relative = block.getRelative(face);
                                        if(checked.contains(relative))
                                            continue;
                                        surrounding.add(relative);
                                        checked.add(relative);
                                    }

                                    Block empty = block;
                                    while(!empty.getRelative(BlockFace.UP).isPassable())
                                        empty = empty.getRelative(BlockFace.UP);
                                    Material type = empty.getType();
                                    for(int i=0; i<5; i++){
                                        empty.setType(Material.AIR);
                                        FallingBlock fallingBlock = world.spawnFallingBlock(empty.getLocation().add(0.5, 0, 0.5), Bukkit.createBlockData(type));
                                        fallingBlock.getPersistentDataContainer().set(Utility.key, PersistentDataType.STRING, NoDrop.id);
                                        fallingBlock.setVelocity(axis);
                                        empty = empty.getRelative(BlockFace.DOWN);
                                    }
                                    Material /*slab = Material.getMaterial(type+"_SLAB");
                                    if(slab==null)*/
                                        slab = Material.STONE_SLAB;
                                    empty.setType(slab);
                                    Block replace = empty;
                                    new BukkitRunnable(){
                                        public void run(){
                                            replace.setType(type);
                                        }
                                    }.runTaskLater(HoloItems.getInstance(), 30);
                                }
                                ground = surrounding;
                                radius++;
                                pound = 0;
                                return;
                            }
                            double distance = 0;
                            for(Block block : ground){
                                BlockFace face;
                                boolean passable;
                                if(block.isPassable()){
                                    face = BlockFace.DOWN;
                                    passable = true;
                                    if(!block.isEmpty())
                                        replace.put(block, block.getType());
                                }
                                else{
                                    face = BlockFace.UP;
                                    passable = false;
                                }
                                while(block.getRelative(face).isPassable() == passable)
                                    block = block.getRelative(face);
                                Material type;
                                if(passable)
                                    type = block.getRelative(BlockFace.DOWN).getType();
                                else {
                                    type = block.getType();
                                    block = block.getRelative(BlockFace.UP);
                                    if(!block.isEmpty())
                                        replace.put(block, block.getType());
                                }
                                block.setType(type);
                                distance = Math.max(distance, center.distance(block.getLocation()));
                            }
                            Vector axis = new Vector(0, 1.5, 0);
                            for(Entity livingEntity : entity.getNearbyEntities(distance, distance ,distance)){
                                if(livingEntity instanceof LivingEntity && livingEntity.isOnGround() && center.distance(livingEntity.getLocation())<=distance) {
                                    livingEntity.setVelocity(axis);
                                    if(squid==null && livingEntity instanceof Squid) {
                                        squid = (Squid) livingEntity;
                                        squid.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 60, 1));
                                        squid.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60, 3));
                                    }
                                }
                            }
                            increment++;
                            pound++;
                        }
                    };
                    cancel();
                    return;
                }
                dir.rotateAroundAxis(axis, Math.PI/4).add(new Vector(0, -0.5, 0));
                entity.teleport(entity.getLocation().setDirection(dir));
                entity.setVelocity(dir);
            }
        };
    }

    private static void burst(Entity entity){
        if(!(entity instanceof LivingEntity))
            return;
        entity.setGravity(false);
        Location loc = ((LivingEntity) entity).getEyeLocation();
        Vector direction = loc.getDirection();
        Vector perpendicular = new Vector(direction.getZ(), 0, -1*direction.getX());
        List<Entity> passengers = entity.getPassengers();
        for (Entity passenger : passengers) {
            entity.removePassenger(passenger);
            passenger.setGravity(false);
            passenger.teleport(loc.clone().add(perpendicular.rotateAroundAxis(direction, Math.PI / 4)));
        }
        new Task(HoloItems.getInstance(), 1, 1){
            int increment = 0;
            public void run(){
                if(increment>=120){
                    cancel();
                    return;
                }
                for(Entity passenger : passengers) {
                    Vector velocity = passenger.getLocation().subtract(entity.getLocation()).toVector().normalize();
                    passenger.setVelocity(new Vector(velocity.getZ(), 0, -1*velocity.getX()).add(velocity.multiply(-1)));
                }
                increment++;
            }
        };
    }

    public static void chainShot(){

    }
}
