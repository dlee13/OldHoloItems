package com.klin.holoItems.interfaces;

import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Set;

public interface Activatable {

    void ability(CreatureSpawnEvent event);

    <E> Set<E> survey();
}
