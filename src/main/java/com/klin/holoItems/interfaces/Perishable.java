package com.klin.holoItems.interfaces;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public interface Perishable {

    void ability(EntityDeathEvent event, ItemStack item, int slot, Player player);
}
