package com.klin.holoItems.interfaces;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface Sealable {

    int ability(Inventory inv, ItemStack item, Player player);
}
