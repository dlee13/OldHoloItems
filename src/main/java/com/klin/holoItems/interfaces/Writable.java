package com.klin.holoItems.interfaces;

import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.meta.BookMeta;

public interface Writable {

    void ability(PlayerEditBookEvent event, BookMeta meta);
}
