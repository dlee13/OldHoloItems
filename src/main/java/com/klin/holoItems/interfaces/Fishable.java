package com.klin.holoItems.interfaces;

import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

public interface Fishable {

    void ability(PlayerFishEvent event, ItemStack item);
}
