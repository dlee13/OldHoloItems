package com.klin.holoItems.collections.dungeons.inaDungeonCollection.items;

import com.klin.holoItems.Item;
import com.klin.holoItems.collections.dungeons.inaDungeonCollection.InaDungeonCollection;
import org.bukkit.Material;

public class TemperedGlass extends Item {
    public static final String name = "temperedGlass";

    private static final Material material = Material.GLASS;
    private static final int quantity = 1;
    private static final String lore =
            "Reinforced through heating";
    private static final int durability = 0;
    public static final boolean stackable = true;
    private static final boolean shiny = true;

    public static final int cost = -1;
    public static final char key = 'h';
    public static final String id = ""+InaDungeonCollection.key+key;

    public TemperedGlass(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost, id, key);
    }

    public void registerRecipes() {}
}
