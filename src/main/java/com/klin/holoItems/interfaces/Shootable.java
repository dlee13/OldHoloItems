package com.klin.holoItems.interfaces;

import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

public interface Shootable{

    void cause(ProjectileLaunchEvent event, ItemStack item);

    void effect(ProjectileHitEvent event);
}
