package com.klin.holoItems.dungeons.inaDungeon.members;

import org.bukkit.boss.BarColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Enma extends Member {

    public Enma(Player player){
        super(player, BarColor.RED);
    }

    public void ability(double angle, PlayerInteractEvent event) {

    }

    public void attack(EntityDamageByEntityEvent event, Entity entity) {

    }

    public void defend(EntityDamageByEntityEvent event, Entity damager) {

    }

    public void burst(int charge) {

    }
}
