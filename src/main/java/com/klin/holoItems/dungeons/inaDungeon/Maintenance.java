package com.klin.holoItems.dungeons.inaDungeon;

import com.klin.holoItems.HoloItems;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;

public class Maintenance implements Listener {
    private static Maintenance instance;

    @EventHandler
    public static void prevent(BlockBreakEvent event){
        if(!event.isCancelled())
            event.setCancelled(true);
    }

    @EventHandler
    public static void decay(BlockPlaceEvent event){
        if(event.isCancelled())
            return;
        Block block = event.getBlock();
        new BukkitRunnable(){
            public void run(){
                block.setType(Material.AIR);
            }
        }.runTaskLater(HoloItems.getInstance(), 120);
    }
}
