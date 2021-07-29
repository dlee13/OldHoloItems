package com.klin.holoItems.dungeons.inaDungeon;

import org.bukkit.Bukkit;
import org.bukkit.block.data.BlockData;

import java.util.HashMap;
import java.util.Map;

public class Builds {
    public static Map<Integer, BlockData[][][]> builds = new HashMap<>();

    String[][][][] storage = new String[][][][]{
            {{{}}}
    };

    public void build() {
        for (int i=0; i<storage.length; i++) {
            BlockData[][][] build = new BlockData[storage[i].length][storage[i][0].length][storage[i][0][0].length];
            for (int j=0; j<storage[i].length; j++) {
                for (int k=0; k<storage[i][j].length; k++) {
                    for (int l=0; l<storage[i][j][k].length; l++) {
                        String block = storage[i][j][k][l];
                        BlockData blockData = Bukkit.createBlockData(block);
                        build[j][k][l] = blockData;
                    }
                }
            }
            builds.put(i, build);
        }
    }
}
