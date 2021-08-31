package com.klin.holoItems.collections.dungeons.inaDungeonCollection.items;

import com.klin.holoItems.Collections;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.dungeons.inaDungeonCollection.InaDungeonCollection;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.scheduler.BukkitRunnable;

public class DepthCharge extends Item {
    public static final String name = "depthCharge";

    private static final Material material = Material.TNT;
    private static final int quantity = 1;
    private static final String lore =
            "Locked and loaded";
    private static final int durability = 0;
    public static final boolean stackable = true;
    private static final boolean shiny = true;

    public static final int cost = 0;
    public static final char key = 'f';
    public static final String id = ""+InaDungeonCollection.key+key;

    public DepthCharge(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost, id, key);
    }

    public void registerRecipes() {
        new BukkitRunnable(){
            public void run(){
                ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
                recipe.shape("aba","bab", "aba");
                recipe.setIngredient('a', new RecipeChoice.ExactChoice(Collections.findItem(BlackPowder.id).item));
                recipe.setIngredient('a', new RecipeChoice.ExactChoice(Collections.findItem(CoarseSand.id).item));
                recipe.setGroup(name);
                Bukkit.getServer().addRecipe(recipe);
            }
        }.runTask(HoloItems.getInstance());
    }
}