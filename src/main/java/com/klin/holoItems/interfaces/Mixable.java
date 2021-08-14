package com.klin.holoItems.interfaces;

import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.ItemStack;

public interface Mixable {

    void ability(BrewEvent event, ItemStack item, ItemStack ingredient, BrewerInventory inv, int slot);
}
