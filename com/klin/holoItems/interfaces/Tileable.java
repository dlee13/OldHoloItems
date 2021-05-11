package com.klin.holoItems.interfaces;

import org.bukkit.event.player.PlayerInteractEvent;

public interface Tileable {

    void access(PlayerInteractEvent event, String action);
}
