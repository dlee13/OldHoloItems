package com.klin.holoItems.interfaces;

import org.bukkit.inventory.ItemStack;

public interface Combinable {

    default String processInfo(ItemStack item){
        return "";
    }
}
