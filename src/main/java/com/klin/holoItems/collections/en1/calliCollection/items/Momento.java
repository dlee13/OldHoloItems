package com.klin.holoItems.collections.en1.calliCollection.items;

import com.klin.holoItems.interfaces.Retainable;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class Momento extends Item implements Retainable {
    public static final String name = "momento";

    private static final Material material = Material.ENDER_CHEST;
    private static final int quantity = 1;
    private static final String lore =
            "Item is automatically consumed to\n" +
            "keep inventory upon death";
    private static final int durability = 0;
    public static final boolean stackable = false;
    private static final boolean shiny = false;
    public static final int cost = 2700;

    public Momento(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost);
    }

    public void registerRecipes(){
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("aaa","aba","aaa");
        recipe.setIngredient('a', Material.OBSIDIAN);
        recipe.setIngredient('b', Material.END_CRYSTAL);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public boolean ability(PlayerDeathEvent event, ItemStack item){
        event.getDrops().clear();
        event.setKeepInventory(true);
        event.setKeepLevel(true);
        return true;
    }
}
