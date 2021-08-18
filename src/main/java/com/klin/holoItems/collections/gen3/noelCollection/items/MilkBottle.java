package com.klin.holoItems.collections.gen3.noelCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.gen3.noelCollection.NoelCollection;
import com.klin.holoItems.interfaces.Consumable;
import com.klin.holoItems.interfaces.Hitable;
import com.klin.holoItems.interfaces.Mixable;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;

public class MilkBottle extends Item implements Consumable, Hitable, Mixable {
    public static final String name = "milkBottle";

    private static final Material material = Material.POTION;
    private static final int quantity = 1;
    private static final String lore =
            "Remove potion effects";
    private static final int durability = 0;
    public static final boolean stackable = false;
    private static final boolean shiny = false;

    public static final int cost = 0;
    public static final char key = '0';
    public static final String id = ""+NoelCollection.key+key;

    public MilkBottle(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost, id, key);
    }

    public void registerRecipes() {
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        meta.setBasePotionData(new PotionData(PotionType.THICK));
        meta.setColor(Color.WHITE);
        item.setItemMeta(meta);

        ShapelessRecipe recipe =
                new ShapelessRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.addIngredient(Material.MILK_BUCKET);
        recipe.addIngredient(Material.GLASS_BOTTLE);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(ProjectileHitEvent event) {
        Projectile projectile = event.getEntity();
        ProjectileSource shooter = projectile.getShooter();
        if(!(shooter instanceof Player))
            return;
        Player player = (Player) shooter;
        for(Entity hit : projectile.getNearbyEntities(5, 2, 5)) {
            if (Utility.fireBlank(player, hit) && hit instanceof LivingEntity) {
                LivingEntity entity = (LivingEntity) hit;
                for (PotionEffect effect : entity.getActivePotionEffects())
                    entity.removePotionEffect(effect.getType());
            }
        }
    }

    public void ability(PlayerItemConsumeEvent event, ItemStack item) {
        Player player = event.getPlayer();
        for(PotionEffect effect : player.getActivePotionEffects())
            player.removePotionEffect(effect.getType());
    }

    public void ability(BrewEvent event, ItemStack item, ItemStack ingredient, BrewerInventory inv, int slot) {
        ItemMeta meta = item.getItemMeta();
        Material type;
        if (ingredient.getType() == Material.GUNPOWDER)
            type = Material.SPLASH_POTION;
        else
            type = item.getType();
        new BukkitRunnable() {
            @Override
            public void run(){
                ItemStack item = inv.getItem(slot);
                item.setItemMeta(meta);
                item.setType(type);
            }
        }.runTask(HoloItems.getInstance());
    }
}
