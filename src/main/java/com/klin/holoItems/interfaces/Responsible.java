package com.klin.holoItems.interfaces;

import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public interface Responsible {

    boolean ability(PlayerInteractEntityEvent event, ItemStack item);
}
