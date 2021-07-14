package com.klin.holoItems.interfaces;

import org.bukkit.event.entity.EntityDamageByEntityEvent;

//for when an attacker has a modifier attached to them
public interface Confrontable {

    void ability(EntityDamageByEntityEvent event, String info);
}
