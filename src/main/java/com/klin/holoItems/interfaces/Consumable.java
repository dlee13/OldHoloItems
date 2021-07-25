package com.klin.holoItems.interfaces;

import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public interface Consumable {

    void ability(PlayerItemConsumeEvent event, ItemStack item);
}
