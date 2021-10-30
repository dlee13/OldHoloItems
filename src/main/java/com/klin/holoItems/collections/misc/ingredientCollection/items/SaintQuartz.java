package com.klin.holoItems.collections.misc.ingredientCollection.items;

import com.klin.holoItems.Collections;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.scheduler.BukkitRunnable;

public class SaintQuartz extends Item {
    public static final String name = "saintQuartz";

    private static final Material material = Material.SEA_LANTERN;
    private static final int quantity = 1;
    private static final int durability = 0;
    public static final boolean stackable = true;
    private static final boolean shiny = true;
    public static final int cost = 0;

    public SaintQuartz(){
        super(name, material, quantity, durability, stackable, shiny, cost);
    }

    public void registerRecipes() {
        new BukkitRunnable(){
            public void run(){
                ShapedRecipe crystal = new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
                crystal.shape("aa","aa");
                crystal.setIngredient('a', new RecipeChoice.ExactChoice(Collections.items.get(QuartzFragment.name).item));
                crystal.setGroup(name);
                Bukkit.getServer().addRecipe(crystal);
            }
        }.runTask(HoloItems.getInstance());
    }
}
