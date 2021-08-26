package com.klin.holoItems.collections.dungeons.inaDungeon.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.dungeons.inaDungeon.InaDungeon;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

public class BoneCrystal extends Item {
    public static final String name = "boneCrystal";

    private static final Material material = Material.BONE_BLOCK;
    private static final int quantity = 1;
    private static final String lore =
            "A peculiar fertilizer";
    private static final int durability = 0;
    public static final boolean stackable = true;
    private static final boolean shiny = true;

    public static final int cost = 0;
    public static final char key = '4';
    public static final String id = ""+InaDungeon.key+key;

    public BoneCrystal(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost, id, key);
    }

    public void registerRecipes() {
        ItemStack fragment = Utility.process("boneFragment", Material.BONE_MEAL, 1, "2x2", 0, true, "!2");
        ItemStack shard = Utility.process("boneShard", Material.BONE, 1, "2x2", 0, true, "!3");
        ShapedRecipe shards = new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), "boneShard"), shard);
        shards.shape("aa","aa");
        shards.setIngredient('a', new RecipeChoice.ExactChoice(fragment));
        shards.setGroup(name);
        Bukkit.getServer().addRecipe(shards);
        ShapedRecipe crystal = new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        crystal.shape("aa","aa");
        crystal.setIngredient('a', new RecipeChoice.ExactChoice(shard));
        crystal.setGroup(name);
        Bukkit.getServer().addRecipe(crystal);
    }
}
