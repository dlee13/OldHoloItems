package com.klin.holoItems.dungeons.inaDungeon.classes;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public abstract class Class {
    Player player;
    boolean cooldown;

    public Class(Player player){
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("Class selected: "+this.getClass().getSimpleName()));
        this.player = player;
        this.cooldown = false;
    }

    public abstract void ability(ItemStack item, Action action);
}
