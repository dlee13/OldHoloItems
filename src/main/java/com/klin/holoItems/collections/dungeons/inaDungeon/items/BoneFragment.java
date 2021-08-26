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

public class BoneFragment extends Item {
    public static final String name = "boneFragment";

    private static final Material material = Material.BONE_MEAL;
    private static final int quantity = 1;
    private static final String lore =
            "2x2";
    private static final int durability = 0;
    public static final boolean stackable = true;
    private static final boolean shiny = true;

    public static final int cost = -1;
    public static final char key = '2';
    public static final String id = ""+InaDungeon.key+key;

    public BoneFragment(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost, id, key);
    }

    public void registerRecipes() {}
}
