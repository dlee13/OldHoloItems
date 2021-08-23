package com.klin.holoItems.collections.dungeons.inaDungeon.items;

import com.klin.holoItems.Item;
import com.klin.holoItems.collections.dungeons.inaDungeon.InaDungeon;
import com.klin.holoItems.interfaces.Dropable;
import com.klin.holoItems.interfaces.Hitable;
import com.klin.holoItems.interfaces.Launchable;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class Torrent extends Item implements Launchable, Hitable, Dropable {
    public static final String name = "torrent";

    private static final Material material = Material.BOW;
    private static final int quantity = 1;
    private static final String lore =
            "Splash";
    private static final int durability = 0;
    public static final boolean stackable = false;
    private static final boolean shiny = false;

    public static final int cost = -1;
    public static final char key = '1';
    public static final String id = ""+ InaDungeon.key+key;

    public Torrent(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost, id, key);
    }

    public void registerRecipes() {}

    public void ability(ProjectileLaunchEvent event, ItemStack item){
        event.getEntity().getPersistentDataContainer().set(Utility.key, PersistentDataType.STRING, id);
    }

    public void ability(ProjectileHitEvent event) {
        Projectile projectile = event.getEntity();
        projectile.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, projectile.getLocation(), 1);
    }

    public void ability(PlayerDropItemEvent event){
        event.setCancelled(true);
    }
}
