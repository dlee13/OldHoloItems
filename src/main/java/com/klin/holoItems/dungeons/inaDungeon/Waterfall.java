package com.klin.holoItems.dungeons.inaDungeon;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Set;

public class Waterfall implements Listener {
    private static final Set<Material> prohibited = Set.of(Material.PISTON, Material.STICKY_PISTON, Material.IRON_TRAPDOOR, Material.OAK_TRAPDOOR, Material.SPRUCE_TRAPDOOR, Material.BIRCH_TRAPDOOR, Material.JUNGLE_TRAPDOOR, Material.ACACIA_TRAPDOOR, Material.DARK_OAK_TRAPDOOR, Material.CRIMSON_TRAPDOOR, Material.WARPED_TRAPDOOR);

    @EventHandler
    public static void decay(BlockPlaceEvent event){
        if(!event.isCancelled() && prohibited.contains(event.getBlock().getType()))
            event.setCancelled(true);
    }
}
