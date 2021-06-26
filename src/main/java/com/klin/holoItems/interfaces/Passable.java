package com.klin.holoItems.interfaces;

import org.bukkit.block.Block;
import org.bukkit.event.player.PlayerInteractEvent;

public interface Passable {

    void ability(PlayerInteractEvent event, Block block);
}
