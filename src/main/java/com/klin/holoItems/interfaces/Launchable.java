package com.klin.holoItems.interfaces;

import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

public interface Launchable {

    void ability(ProjectileLaunchEvent event, ItemStack item);
}
