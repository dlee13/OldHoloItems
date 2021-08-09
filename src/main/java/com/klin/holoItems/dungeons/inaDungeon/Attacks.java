package com.klin.holoItems.dungeons.inaDungeon;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Squid;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        Vector direction = entity.getLocation().getDirection().multiply(2);
        Vector axis = new Vector(0, 2, 0);
        new Task(HoloItems.getInstance(), 1, 1){
            public void run(){
                if(!entity.isValid() || entity.isOnGround()){
                    List<Entity> nearby = entity.getNearbyEntities(5, 5, 5);
                    for(Entity target : nearby)
                        target.setVelocity(axis.clone().add(new Vector(Math.random()/3, Math.random(), Math.random()/3)));
                    new Task(HoloItems.getInstance(), 0, 15){
                        int limit = 0;
                        int increment = 0;
                        public void run(){
                            if(limit>=120 || increment>=nearby.size()){
                                entity.setVelocity(new Vector());
                                Player player = null;
                                for(Entity nearby : entity.getNearbyEntities(20, 20, 20)){
                                    if(nearby instanceof Player)
                                        player = (Player) nearby;
                                }
//                                Vector axis;
//                                if(player==null)
//                                    axis = new Vector(1, 0, 0);
//                                else
//                                    axis = player.getLocation().subtract(entity.getLocation()).toVector().normalize();
                                burst(entity);
                                cancel();
                                return;
                            }
                            boolean searching = true;
                            while(searching && increment<nearby.size()){
                                Entity target = nearby.get(increment);
                                if(target instanceof Squid) {
                                    new Task(HoloItems.getInstance(), 5, 1){
                                        int increment = 0;
                                        public void run(){
                                            if(increment>=10 || target.getLocation().distance(entity.getLocation())<1) {
                                                entity.addPassenger(target);
                                                cancel();
                                                return;
                                            }
                                            entity.setVelocity(target.getLocation().subtract(entity.getLocation()).toVector().normalize().multiply(2));
                                            increment++;
                                        }
                                    };
                                    searching = false;
                                }
                                increment++;
                            }
                            limit++;
                        }
                    };
                    cancel();
                    return;
                }
                direction.rotateAroundAxis(axis, Math.PI/4).add(new Vector(0, -1, 0));
                entity.teleport(entity.getLocation().setDirection(direction));
                entity.setVelocity(direction);
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
}
