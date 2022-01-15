package com.klin.holoItems.collections.misc.ingredientsCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.BlastingRecipe;
import org.bukkit.inventory.FurnaceRecipe;

public class EmeraldLeaf extends Item {
    public static final String name = "emeraldLeaf";

    private static final Material material = Material.KELP;
    private static final int quantity = 1;
    private static final int durability = 0;
    public static final boolean stackable = true;
    private static final boolean shiny = true;
    public static final int cost = 0;

    public EmeraldLeaf(){
        super(name, material, quantity, durability, stackable, shiny, cost);
    }

    public void registerRecipes(){
        FurnaceRecipe recipe =
                new FurnaceRecipe(new NamespacedKey(HoloItems.getInstance(), name), item,
                        Material.EMERALD_ORE, 1, 1600);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);

        BlastingRecipe blastingRecipe =
                new BlastingRecipe(new NamespacedKey(HoloItems.getInstance(), name + "Blast"), item,
                        Material.EMERALD_ORE, 1, 800);
        blastingRecipe.setGroup(name);
        Bukkit.getServer().addRecipe(blastingRecipe);
    }
}