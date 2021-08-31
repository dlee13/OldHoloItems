package com.klin.holoItems.collections.dungeons.inaDungeonCollection.items;

import com.klin.holoItems.Item;
import com.klin.holoItems.collections.dungeons.inaDungeonCollection.InaDungeonCollection;
import org.bukkit.Material;

public class CoarseSand extends Item {
    public static final String name = "fineGrainSand";

    private static final Material material = Material.SAND;
    private static final int quantity = 1;
    private static final String lore =
            "A palatable delicacy";
    private static final int durability = 0;
    public static final boolean stackable = true;
    private static final boolean shiny = true;

    public static final int cost = -1;
    public static final char key = 'e';
    public static final String id = ""+InaDungeonCollection.key+key;

    public CoarseSand(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost, id, key);
    }

    public void registerRecipes() {}
}
