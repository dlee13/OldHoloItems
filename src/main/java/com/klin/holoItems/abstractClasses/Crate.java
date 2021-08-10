package com.klin.holoItems.abstractClasses;

import com.klin.holoItems.Item;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public abstract class Crate extends Item {
    public Crate(String name, Material material, int quantity, String lore, int durability, boolean stackable,
                 boolean shiny, int cost, String id, char key) {
        super(name, material, quantity, lore, durability, stackable, shiny, cost, id, key);
    }

    public void ability(BlockBreakEvent event) {
        Block block = event.getBlock();
        ItemStack drop = item.clone();
        drop.setAmount(1);
        block.getWorld().dropItemNaturally(block.getLocation(), drop);
    }
}
