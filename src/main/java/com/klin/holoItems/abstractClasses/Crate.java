package com.klin.holoItems.abstractClasses;

import com.klin.holoItems.Item;
import com.klin.holoItems.collections.gen0.robocoCollection.items.Magnet;
import com.klin.holoItems.interfaces.Breakable;
import com.klin.holoItems.interfaces.Placeable;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public abstract class Crate extends Item implements Breakable, Placeable {
    public Crate(String name, Material material, int quantity, String lore, int durability, boolean stackable, boolean shiny, int cost) {
        super(name, material, quantity, lore, durability, stackable, shiny, cost);
    }

    public void ability(BlockBreakEvent event) {
        if(Utility.findItem(event.getPlayer().getInventory().getItemInMainHand(), Magnet.class)!=null)
            return;
        event.setDropItems(false);
        Block block = event.getBlock();
        ItemStack drop = item.clone();
        drop.setAmount(1);
        block.getWorld().dropItemNaturally(block.getLocation(), drop);
    }

    public void ability(BlockPlaceEvent event){
        event.setCancelled(false);
        TileState state = (TileState) event.getBlockPlaced().getState();
        state.getPersistentDataContainer().set(Utility.key, PersistentDataType.STRING, name);
        state.update();
    }
}
