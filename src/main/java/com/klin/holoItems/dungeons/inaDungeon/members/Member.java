package com.klin.holoItems.dungeons.inaDungeon.members;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public abstract class Member {
    public Player player;
    public boolean cooldown;
    protected final BarColor barColor;

    public Member(Player player, BarColor barColor){
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("Class selected: "+this.getClass().getSimpleName()));
        this.player = player;
        this.cooldown = false;
        this.barColor = barColor;
    }

    public abstract void ability(double angle, PlayerInteractEvent event);

    public abstract void attack(EntityDamageByEntityEvent event, Entity entity);
    public abstract void defend(EntityDamageByEntityEvent event, Entity damager);

    public void burst(PlayerToggleSneakEvent event){
        new Task(HoloItems.getInstance(), 0, 1) {
            int increment = 0;
            BossBar bossBar = null;
            PotionEffect speed;
            @Override
            public void run() {
                if (!player.isSneaking() || !player.isSneaking() && increment>=20) {
                    if(bossBar!=null) {
                        bossBar.removePlayer(player);
                        Bukkit.removeBossBar(Utility.key);
                        burst(increment);
                    }
                    cancel();
                    return;
                }
                if(increment>=5) {
                    if(increment==5) {
                        bossBar = Bukkit.createBossBar(Utility.key, "charging. . .", barColor, BarStyle.SEGMENTED_20);
                        bossBar.addPlayer(player);
                        speed = new PotionEffect(PotionEffectType.SPEED, 2, 2);
                    }
                    bossBar.setProgress(increment * 0.05);
                    player.addPotionEffect(speed);
                }
                increment = Math.min(20, increment + 1);
            }
        };
    }
    public abstract void burst(int charge);
}