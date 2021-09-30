package com.klin.holoItems.collections.en.calliCollection.items;

import com.klin.holoItems.interfaces.Retainable;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.en.calliCollection.CalliCollection;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.HashSet;

public class MomentoMori extends Item implements Retainable {
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

    public MomentoMori(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost);
    }

    public void registerRecipes(){
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("aaa","aba","aaa");
        recipe.setIngredient('a', Material.ENDER_CHEST);
        recipe.setIngredient('b', Material.END_CRYSTAL);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public boolean ability(PlayerDeathEvent event, ItemStack item){
        event.getDrops().clear();
        event.setKeepInventory(true);
        return true;
    }
}
