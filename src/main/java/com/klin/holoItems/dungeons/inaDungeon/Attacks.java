package com.klin.holoItems.dungeons.inaDungeon;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
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
}
