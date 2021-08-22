package com.klin.holoItems.dungeons.inaDungeon.classes;

import org.bukkit.entity.Player;

public class Class {

    public Class(Player player){
        player.sendMessage("Class selected: "+this.getClass().getName());
    }
}
