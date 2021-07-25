package com.klin.holoItems.interfaces;

import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDropItemEvent;

public interface Collectable {

    void ability(EntityDropItemEvent event, Entity entity);
}
