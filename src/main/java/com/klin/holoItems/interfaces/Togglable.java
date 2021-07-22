package com.klin.holoItems.interfaces;

import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;

public interface Togglable {

    void ability(PlayerToggleSneakEvent event, ItemStack item);
}
