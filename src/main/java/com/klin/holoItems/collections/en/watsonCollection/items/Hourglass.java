package com.klin.holoItems.collections.en.watsonCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.abstractClasses.BatteryPack;
import com.klin.holoItems.interfaces.Hitable;
import com.klin.holoItems.utility.Utility;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;

public class Hourglass extends BatteryPack implements Hitable {
    public static final String name = "hourglass";

    private static final Material material = Material.SPLASH_POTION;
    private static final String lore =
            "Shatter to start a timer with duration\n"+
            "equal to the quantity of sand in the\n"+
            "hourglass, return *here* when it ends";
    private static final int durability = 0;
    private static final boolean shiny = true;

    public static final Material content = Material.SAND;
    public static final double perFuel = 1;
    public static final int cap = 15;
    public static final int cost = 0;

    public Hourglass(){
        super(name, material, lore, durability, shiny, cost, content, perFuel, cap);
    }

    public void registerRecipes(){
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        meta.setColor(Color.YELLOW);
        item.setItemMeta(meta);

        ShapedRecipe recipe =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("a","b","c");
        recipe.setIngredient('a', Material.IRON_NUGGET);
        recipe.setIngredient('b', Material.GLASS_BOTTLE);
        recipe.setIngredient('c', Material.GOLD_NUGGET);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(ProjectileHitEvent event) {
        ThrownPotion projectile = (ThrownPotion) event.getEntity();
        int charge = Utility.deplete(projectile.getItem(), null, cap);
        if(charge==-1)
            return;
        charge += 1;

        ProjectileSource shooter = projectile.getShooter();
        if(!(shooter instanceof Player))
            return;
        Player player = (Player) shooter;
        player.sendMessage("Returning in "+charge+" seconds");

        Location loc = player.getLocation();
        new BukkitRunnable(){
            public void run(){
                player.teleport(loc);
            }
        }.runTaskLater(HoloItems.getInstance(), charge*20L);
    }

    protected void effect(PlayerInteractEvent event) {}
}

