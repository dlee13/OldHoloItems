package com.klin.holoItems.interfaces;

import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public interface Punchable {

    void ability(PlayerInteractEvent event, Action action);
}
