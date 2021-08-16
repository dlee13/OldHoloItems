package com.klin.holoItems.interfaces;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

public interface Swappable {

    void ability(PlayerSwapHandItemsEvent event, Player player, ItemStack item, boolean main);
}
