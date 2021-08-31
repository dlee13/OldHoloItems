package com.klin.holoItems.collections.dungeons.inaDungeonCollection.items;

import com.klin.holoItems.Item;
import com.klin.holoItems.collections.dungeons.inaDungeonCollection.InaDungeonCollection;
import org.bukkit.Material;

public class PieceOfEight extends Item {
    public static final String name = "pieceOfEight";

    private static final Material material = Material.GOLD_NUGGET;
    private static final int quantity = 1;
    private static final String lore =
            "Currency of the Ancient One";
    private static final int durability = 0;
    public static final boolean stackable = true;
    private static final boolean shiny = true;

    public static final int cost = -1;
    public static final char key = 'g';
    public static final String id = ""+InaDungeonCollection.key+key;

    public PieceOfEight(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost, id, key);
    }

    public void registerRecipes() {}
}
