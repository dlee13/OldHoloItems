package com.klin.holoItems.dungeons.inaDungeon;

import com.klin.holoItems.HoloItems;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.bukkit.Bukkit.getServer;

public class GettingWood implements Listener {
    private static GettingWood instance = null;
    private static Map<Block, BlockData> tree = null;
    private static Set<Block> leaves = null;
    private static Set<Block> stem = null;

    public static void plant(int index, World world, Location loc){
        tree = InaDungeon.build(index, world, loc);
        stem = new HashSet<>();
        leaves = new HashSet<>();
        for(Block block : tree.keySet()){
            if(block.getType()== Material.WHITE_WOOL)
                leaves.add(block);
            else
                stem.add(block);
        }

        instance = new GettingWood();
        getServer().getPluginManager().registerEvents(instance, HoloItems.getInstance());
    }

    @EventHandler
    public static void gettingWood(BlockBreakEvent event){
        Block block = event.getBlock();
        if(!stem.contains(block)) {
            event.setCancelled(true);
            return;
        }
        event.setDropItems(false);
        Player player = event.getPlayer();
        World world = player.getWorld();
        world.dropItemNaturally(block.getLocation().add(0.5, 0.5, 0.5), new ItemStack(Material.SPRUCE_PLANKS));
        //if stem is broken, chance for a random collection of leaves to ignite and fall
    }

    public static void reset(){
        if(instance!=null) {
            BlockBreakEvent.getHandlerList().unregister(instance);
            instance = null;
        }
        for(Block block : tree.keySet())
            block.setBlockData(tree.get(block));
        tree = null;
        leaves = null;
        stem = null;
    }
}
