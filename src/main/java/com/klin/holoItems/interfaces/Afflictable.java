package com.klin.holoItems.interfaces;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public interface Afflictable {

    void ability(EntityDamageByEntityEvent event, ItemStack item);
}
