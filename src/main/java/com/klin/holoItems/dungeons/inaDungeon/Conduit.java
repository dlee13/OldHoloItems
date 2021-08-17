package com.klin.holoItems.dungeons.inaDungeon;

import com.klin.holoItems.utility.Utility;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Conduit {
    //rotate world -65 -63 -205 -203 72
    //19x19: y+=1
    private static final Map<Material, Set<Block[]>> buttons = new HashMap<>();
    private static final Map<Block[], BlockFace> joints = new HashMap<>();

    public static void setUp(World world, int x1, int y1, int z1, int x2, int y2, int z2){

    }

    public static void rotate(Block[] torches, BlockFace flip){
        if(flip==null){
            for(Block block : torches) {
                if(block.getType()!=Material.STONE_BUTTON)
                    block.setType(block.isEmpty() ? Material.REDSTONE_TORCH:Material.AIR);
            }
        }
        else{
            Block block = torches[2];
            block.getRelative(flip).setType(Material.REDSTONE_TORCH);
            block.getRelative(Utility.opposites.get(flip)).setType(Material.AIR);
            joints.replace(torches, Utility.left.get(flip));
        }
    }

    public static Map<Block[], BlockFace> joint(World world, int x1, int x2, int z1, int z2, int y, Map<Block[], BlockFace> joints){
        Block[] torches = new Block[5];
        int increment = 0;
        int check = 0;
        int index = -1;
        BlockFace flip = null;
        for(int x=x1; x<=x2; x++){
            for(int z=z1; z<=z2; z++){
                Block block = world.getBlockAt(x, y, z);
                if(!block.isPassable())
                    continue;
                try {
                    torches[increment] = block;
                }catch (IndexOutOfBoundsException e){return null;}
                if(check>-1 && index>-1 && block.isEmpty()){
                    switch (check){
                        case 1:
                            if(index==0)
                                flip = BlockFace.NORTH;
                            break;
                        case 3:
                            if(index==0)
                                flip = BlockFace.WEST;
                            break;
                        case 4:
                            if(index==1)
                                flip = BlockFace.EAST;
                            else if(index==3)
                                flip = BlockFace.SOUTH;
                    }
                    check = -1;
                }
                else if(block.isEmpty())
                    index = increment;
                check++;
                increment++;
            }
        }
        if(torches[4]==null)
            return null;
        if(joints==null)
            joints = new HashMap<>(Collections.singletonMap(torches, flip));
        else
            joints.put(torches, flip);
        return joints;
    }
}

