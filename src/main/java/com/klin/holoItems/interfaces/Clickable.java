package com.klin.holoItems.interfaces;

import org.bukkit.event.inventory.InventoryClickEvent;

public interface Clickable {

    void ability(InventoryClickEvent event, boolean current);
}
