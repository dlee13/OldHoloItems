package com.klin.holoItems.dungeons.inaDungeon;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.util.Vector;

import java.util.*;

public class Payload{
    //payload buildteam 599 56 307 597 69 416 597 70 417 SOUTH

    public static void payload(String[] args) {
        World world = Bukkit.getWorld(args[0]);
        if (world == null) {
            System.out.println("Invalid world name");
            return;
        }
        int[] start;
        int[] coords;
        BlockFace direction;
        try {
            start = new int[]{Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3])};
            coords = new int[]{Integer.parseInt(args[4]), Integer.parseInt(args[5]), Integer.parseInt(args[6]),
                    Integer.parseInt(args[7]), Integer.parseInt(args[8]), Integer.parseInt(args[9])};
            direction = BlockFace.valueOf(args[10]);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid argument(s)");
            return;
        }
        AbstractMap.SimpleEntry<Set<FallingBlock>, FallingBlock> payload = spawn(0, world, start[0], start[1], start[2], direction);
        FallingBlock center = payload.getValue();
        start[0] = center.getLocation().getBlockX();
        start[2] = center.getLocation().getBlockZ();

        int x = 0;
        int z = 0;
        int[] finish;
        switch (direction) {
            case NORTH:
                z = -1;
            case SOUTH:
                if (z == 0)
                    z = 1;
                finish = new int[]{start[0], (coords[1] + coords[4]) / 2, (Math.abs(coords[2] - start[2]) > Math.abs(coords[5] - start[2]) ? coords[5] : coords[2]) - 2 * z};
                break;
            case EAST:
                x = 1;
            case WEST:
                if (x == 0)
                    x = -1;
                finish = new int[]{(Math.abs(coords[0] - start[0]) > Math.abs(coords[3] - start[0]) ? coords[3] : coords[0]) - 2 * x, (coords[1] + coords[4]) / 2, start[2]};
                break;
            default:
                return;
        }

        Queue<Double> upcoming = new LinkedList<>();
        Location loc = new Location(world, start[0], start[1], start[2]);
        Block current = world.getBlockAt(loc);
        boolean found = false;
        for (int i = 0; i < 327; i++) {
            if (current.isEmpty()) {
                if (!Utility.slabs.contains(current.getRelative(BlockFace.DOWN).getType()) &&
                        Utility.slabs.contains(current.getRelative(Utility.opposites.get(direction)).getRelative(BlockFace.DOWN).getType()))
                    upcoming.add(0.5);
                else
                    upcoming.add(0d);
            } else {
                double y = Utility.slabs.contains(current.getType()) ? 0.5 : 1;
                current = current.getRelative(BlockFace.UP);
                if (current.isEmpty())
                    upcoming.add(y);
                else {
                    System.out.println("Error: the path rises more than a single block at once");
                    return;
                }
            }
            if (current.getX() == finish[0] && current.getZ() == finish[2]) {
                start[1] = current.getY();
                found = true;
                break;
            }
            current = current.getRelative(direction);
        }
        if (!found) {
            System.out.println("Error: no valid path");
            return;
        }

        //map path, including where to throw tnt at end
        int above = Integer.signum(finish[1] - start[1]);
        for (int i = 0; i <= Math.abs(finish[1] - start[1]); i += 1) {
            for (int j = 0; j <= 1; j++) {
                if (!world.getBlockAt(finish[0] + x * j, start[1] + i * above, finish[2] + z * j).isEmpty()) {
                    System.out.println("Error: firing area isn't clear");
                    return;
                }
            }
        }

        //adjust index to build corresponding to payload
        Set<FallingBlock> blocks = payload.getKey();
        Block explode = world.getBlockAt(finish[0] + 2 * x, finish[1], finish[2] + 2 * z);
        int[] copy = coords.clone();
        final double xInterval = ((double) x) / 20;
        final double zInterval = ((double) z) / 20;
        //test
        upcoming.remove();
        upcoming.remove();
        upcoming.remove();
        //
        new Task(HoloItems.getInstance(), 1, 1) {
            //length of payload*20
            int increment = 0;
            Double y;

            public void run() {
                //end reached
                if (upcoming.isEmpty() && increment % 20 == 0) {
                    for (FallingBlock block : blocks) {
                        if (block.getBlockData().getMaterial() == Material.TNT)
                            block.remove();
                    }
                    cancel();

                    TNTPrimed tnt = world.spawn(center.getLocation(), TNTPrimed.class);
                    tnt.setFuseTicks(64);
                    Vector aim;
                    if (center.getLocation().getY() > finish[1])
                        aim = new Vector(xInterval, 0.25, zInterval);
                    else
                        aim = new Vector(xInterval / 2, ((float) (finish[1] - start[1])) / 2.5, zInterval / 2);
                    tnt.setVelocity(aim);
                    new Task(HoloItems.getInstance(), 1, 1) {
                        int increment = 0;

                        public void run() {
                            Location loc = tnt.getLocation();
                            if (tnt.getVelocity().getY() < 0 && Math.abs(loc.getY() - finish[1]) < 3 || increment >= 60) {
                                world.spawnParticle(Particle.EXPLOSION_HUGE, loc, 1);
                                world.playSound(loc, Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 0.4f, 1f);
                                for (FallingBlock block : blocks)
                                    block.remove();
                                tnt.remove();
                                cancel();

                                Queue<Block> toExplode = new LinkedList<>();
                                Set<Block> checked = new HashSet<>();
                                toExplode.add(explode);
                                new Task(HoloItems.getInstance(), 2, 2) {
                                    public void run() {
                                        if (toExplode.isEmpty()) {
                                            cancel();
                                            return;
                                        }

                                        for (int i = 0; i < 2; i++) {
                                            Block center = toExplode.poll();
                                            if (center == null)
                                                break;
                                            checked.add(center);

                                            center.setType(Material.AIR);
                                            world.spawnParticle(Particle.EXPLOSION_LARGE, center.getLocation().add(0.5, 0, 0.5), 1);
                                            world.playSound(loc, Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 0.1f, 1f);
                                            for (Block block : new Block[]{
                                                    center.getRelative(BlockFace.UP),
                                                    center.getRelative(BlockFace.NORTH),
                                                    center.getRelative(BlockFace.SOUTH),
                                                    center.getRelative(BlockFace.EAST),
                                                    center.getRelative(BlockFace.WEST),
                                                    center.getRelative(BlockFace.DOWN)}) {
                                                if (!block.isEmpty() && (copy[0] - block.getX()) * (copy[3] - block.getX()) <= 0 && (copy[1] - block.getY()) * (copy[4] - block.getY()) <= 0 && (copy[2] - block.getZ()) * (copy[5] - block.getZ()) <= 0 && !checked.contains(block) && !toExplode.contains(block))
                                                    toExplode.add(block);
                                            }
                                        }
                                    }
                                };
                                return;
                            }
                            increment++;
                        }
                    };
                    return;
                }

                //game over
                if (increment >= 1200) {
                    Location loc = center.getLocation();
                    world.spawnParticle(Particle.EXPLOSION_HUGE, loc, 1);
                    world.playSound(loc, Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1f, 1f);
                    for (FallingBlock block : blocks)
                        block.remove();

                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.damage(100);
                        world.spawnParticle(Particle.EXPLOSION_LARGE, player.getLocation(), 1);
                    }
                    cancel();
                    return;
                }

                //upcoming elevation increase, toDo: wave effect
                if (increment % 20 == 0) {
                    y = upcoming.poll();
                    for (FallingBlock block : blocks)
                        block.setVelocity(new Vector(xInterval, y, zInterval));
                } else {
                    for (FallingBlock block : blocks)
                        block.setVelocity(new Vector(xInterval, 0, zInterval));
                }
                increment++;
            }
        };
    }

    public static AbstractMap.SimpleEntry<Set<FallingBlock>, FallingBlock> spawn(int index, World world, int x, int y, int z, BlockFace direction){
        Set<FallingBlock> blocks = new HashSet<>();
        FallingBlock center = null;
        Location loc = new Location(world, x, y, z);
        int iI = InaDungeon.builds[index].length;
        int jJ = InaDungeon.builds[index][0].length;
        int kK = InaDungeon.builds[index][0][0].length;
        for(int i=0; i<iI; i++){
            for(int j=0; j<jJ; j++){
                for(int k=0; k<kK; k++){
                    FallingBlock block = world.spawnFallingBlock(loc.clone().add(i+0.5, j, k+0.5), Bukkit.createBlockData(InaDungeon.builds[index][i][j][k]));
                    block.setGravity(false);
                    blocks.add(block);
                    switch(direction){
                        case NORTH:
                            if(i==0 && j==jJ/2 && k==kK/2)
                                center = block;
                            break;
                        case SOUTH:
                            if(i==iI-1 && j==jJ/2 && k==kK/2)
                                center = block;
                            break;
                        case EAST:
                            if(i==iI/2 && j==jJ/2 && k==kK-1)
                                center = block;
                            break;
                        case WEST:
                            if(i==iI/2 && j==jJ/2 && k==0)
                                center = block;
                            break;
                    }
                }
            }
        }
        return new AbstractMap.SimpleEntry<>(blocks, center);
    }

    public static void add(){

    }
}
