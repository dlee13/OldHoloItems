package com.klin.holoItems.interfaces;

import org.bukkit.event.entity.EntityDamageByEntityEvent;

public interface Observable {
    //map check similar to Activatable but for damageEvent
    void ability(EntityDamageByEntityEvent event);
}
