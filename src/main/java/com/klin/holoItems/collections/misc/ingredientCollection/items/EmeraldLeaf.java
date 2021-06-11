package com.klin.holoItems.collections.misc.ingredientCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.misc.hiddenCollection.HiddenCollection;
import com.klin.holoItems.collections.misc.ingredientCollection.IngredientCollection;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.BlastingRecipe;
import org.bukkit.inventory.FurnaceRecipe;

import java.util.Set;

public class EmeraldLeaf extends Item {
    public static final String name = "emeraldLeaf";
    public static final Set<Enchantment> accepted = null;

    private static final Material material = Material.KELP;
    private static final int quantity = 1;
    private static final String lore =
            "§7crafting ingredient";
    private static final int durability = 0;
    public static final boolean stackable = true;
    private static final boolean shiny = true;

    public static final int cost = 0;
    public static final char key = '0';
    public static final String id = ""+IngredientCollection.key+key;

    public EmeraldLeaf(){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost, id, key);
    }

    public void registerRecipes(){
        FurnaceRecipe recipe =
                new FurnaceRecipe(new NamespacedKey(HoloItems.getInstance(), name), item,
                        Material.EMERALD_ORE, 1, 1600);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);

        BlastingRecipe blastingRecipe =
                new BlastingRecipe(new NamespacedKey(HoloItems.getInstance(), name), item,
                        Material.EMERALD_ORE, 1, 800);
        blastingRecipe.setGroup(name);
        Bukkit.getServer().addRecipe(blastingRecipe);
    }
}