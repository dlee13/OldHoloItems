package com.klin.holoItems.interfaces;

import org.bukkit.event.entity.PlayerDeathEvent;

public interface Retainable {

     //boolean consume item
    boolean ability(PlayerDeathEvent event);
}
