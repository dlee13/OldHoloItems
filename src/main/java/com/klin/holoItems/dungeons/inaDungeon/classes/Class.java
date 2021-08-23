package com.klin.holoItems.dungeons.inaDungeon.classes;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class Class {
    Player player;
    static boolean cooldown;

    public Class(Player player){
        player.sendMessage("Class selected: "+this.getClass().getSimpleName());
        this.player = player;
        this.cooldown = false;
    }

    public abstract void ability(int type, ItemStack item);
}
