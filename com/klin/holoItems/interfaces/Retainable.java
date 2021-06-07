package com.klin.holoItems.interfaces;

import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public interface Retainable {

    //boolean consume item
    boolean ability(PlayerDeathEvent event, ItemStack item);
}
