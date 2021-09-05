package com.klin.holoItems.dungeons.inaDungeon.members;

import com.klin.holoItems.HoloItems;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class Member {
    public Player player;
    public boolean cooldown;
    public int burst;

    public Member(Player player){
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("Class selected: "+this.getClass().getSimpleName()));
        this.player = player;
        this.cooldown = false;
        this.burst = -1;
    }

    public abstract void ability(double angle, PlayerInteractEvent event);

    public abstract void attack(EntityDamageByEntityEvent event, Entity damager, Entity entity);
    public abstract void defend(EntityDamageByEntityEvent event, Entity damager, Entity entity);

    public void burst(PlayerToggleSneakEvent event){
        if(event.isSneaking() || burst!=-1) //let go -> burst
            return;
        burst = 1;
        new BukkitRunnable(){
            public void run(){
                burst = 0;
                new BukkitRunnable(){
                    public void run(){
                        burst = -1;
                    }
                }.runTaskLater(HoloItems.getInstance(), 12).getTaskId();
            }
        }.runTaskLater(HoloItems.getInstance(), 6).getTaskId();
    }
}