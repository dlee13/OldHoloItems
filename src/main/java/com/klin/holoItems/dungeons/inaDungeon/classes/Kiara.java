package com.klin.holoItems.dungeons.inaDungeon.classes;

import com.klin.holoItems.HoloItems;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Kiara extends Member {
    public int taskId;

    public Kiara(Player player){
        super(player);
        taskId = -1;
    }

    public void ability(double angle, PlayerInteractEvent event) {
        double abs = Math.abs(angle);
        if(abs<Math.PI*0.4)
            return;
        cooldown = true;
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("Command: Reversal"));
        taskId = new BukkitRunnable(){
            public void run(){
                cooldown = false;
                taskId = -1;
            }
        }.runTaskLater(HoloItems.getInstance(), 6).getTaskId();
    }

    public void reversal(Entity entity){
        if(entity instanceof LivingEntity){
            entity.setFireTicks(160);
        }
    }
}
