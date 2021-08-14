package com.klin.holoItems.interfaces;

import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public interface Retaliable {

    void ability(EntityDamageByEntityEvent event, Entity damager);
}
