package com.klin.holoItems.collections.misc.ingredientCollection.items;

import com.klin.holoItems.Item;
import org.bukkit.Material;

public class QuartzFragment extends Item {
    public static final String name = "quartzFragment";

    private static final Material material = Material.PRISMARINE_CRYSTALS;
    private static final int quantity = 1;
    private static final int durability = 0;
    public static final boolean stackable = true;
    private static final boolean shiny = true;
    public static final int cost = -1;

    public QuartzFragment(){
        super(name, material, quantity, durability, stackable, shiny, cost);
    }

    public void registerRecipes() {}
}
