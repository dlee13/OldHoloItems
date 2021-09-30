package com.klin.holoItems.collections.dungeons.inaDungeonCollection.items;

import com.klin.holoItems.Item;
import com.klin.holoItems.collections.dungeons.inaDungeonCollection.InaDungeonCollection;
import org.bukkit.Material;

public class BoneFragment extends Item {
    public static final String name = "boneFragment";

    private static final Material material = Material.BONE_MEAL;
    private static final int quantity = 1;
    private static final String lore =
            "Parts of a larger whole";
    private static final int durability = 0;
    public static final boolean stackable = true;
    private static final boolean shiny = true;
    public static final int cost = -1;

    public BoneFragment(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost);
    }

    public void registerRecipes() {}
}
