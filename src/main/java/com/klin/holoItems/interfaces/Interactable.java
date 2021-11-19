package com.klin.holoItems.interfaces;

import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public interface Interactable{

    void ability(PlayerInteractEvent event, Action action);

    default void ability(PlayerInteractEvent event, ItemStack item){}
}
