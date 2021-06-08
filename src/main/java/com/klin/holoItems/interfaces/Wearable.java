package com.klin.holoItems.interfaces;

import org.bukkit.event.entity.EntityDamageByEntityEvent;

public interface Wearable extends Clickable, Interactable, Retainable{

    void ability(EntityDamageByEntityEvent event, boolean broken);
}
