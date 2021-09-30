package com.klin.holoItems.abstractClasses;

import com.klin.holoItems.Item;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public abstract class Crate extends Item {
    public Crate(String name, Material material, int quantity, String lore, int durability, boolean stackable, boolean shiny, int cost) {
        super(name, material, quantity, lore, durability, stackable, shiny, cost);
    }

    public void ability(BlockBreakEvent event) {
        Block block = event.getBlock();
        ItemStack drop = item.clone();
        drop.setAmount(1);
        block.getWorld().dropItemNaturally(block.getLocation(), drop);
    }
}
