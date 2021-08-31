package com.klin.holoItems.collections.dungeons.inaDungeonCollection.items;

import com.klin.holoItems.Collections;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.dungeons.inaDungeonCollection.InaDungeonCollection;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.scheduler.BukkitRunnable;

public class BoneCrystal extends Item {
    public static final String name = "boneCrystal";

    private static final Material material = Material.BONE_BLOCK;
    private static final int quantity = 1;
    private static final String lore =
            "Souls call out for it";
    private static final int durability = 0;
    public static final boolean stackable = true;
    private static final boolean shiny = true;

    public static final int cost = 0;
    public static final char key = 'd';
    public static final String id = ""+InaDungeonCollection.key+key;

    public BoneCrystal(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost, id, key);
    }

    public void registerRecipes() {
        new BukkitRunnable(){
            public void run(){
                ShapedRecipe crystal = new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
                crystal.shape("aaa","aaa", "aaa");
                crystal.setIngredient('a', new RecipeChoice.ExactChoice(Collections.findItem(BoneFragment.id).item));
                crystal.setGroup(name);
                Bukkit.getServer().addRecipe(crystal);
            }
        }.runTask(HoloItems.getInstance());
    }
}
