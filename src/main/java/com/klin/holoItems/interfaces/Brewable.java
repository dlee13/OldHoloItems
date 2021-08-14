package com.klin.holoItems.interfaces;

import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.inventory.ItemStack;

public interface Brewable {

    void ability(BrewEvent event, ItemStack item);
}
