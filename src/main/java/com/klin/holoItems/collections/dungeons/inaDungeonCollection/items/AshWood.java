package com.klin.holoItems.collections.dungeons.inaDungeonCollection.items;

import com.klin.holoItems.Item;
import com.klin.holoItems.collections.dungeons.inaDungeonCollection.InaDungeonCollection;
import org.bukkit.Material;

public class AshWood extends Item{
    public static final String name = "ashWood";

    private static final Material material = Material.SPRUCE_PLANKS;
    private static final int quantity = 1;
    private static final String lore =
            "Durable hardwood";
    private static final int durability = 0;
    public static final boolean stackable = true;
    private static final boolean shiny = false;

    public static final int cost = -1;
    public static final char key = '2';
    public static final String id = ""+InaDungeonCollection.key+key;

    public AshWood(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost, id, key);
    }

    public void registerRecipes() {}
}
