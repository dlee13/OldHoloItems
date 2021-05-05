package com.klin.holoItems.abstractClasses;

import com.klin.holoItems.Item;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Set;

public abstract class Crate extends Item {
    public static final Set<Enchantment> accepted = null;

    private static final int quantity = 1;
    public static final boolean stackable = false;

    public Crate(String name, Material material, String lore, int durability, boolean shiny, int cost,
                  String id, char key) {
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost, id, key);
    }

    public void ability(BlockBreakEvent event) {
        Block block = event.getBlock();
        block.getWorld().dropItemNaturally(block.getLocation(), item);
    }
}
