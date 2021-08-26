package com.klin.holoItems.collections.dungeons.inaDungeon.items;

import com.klin.holoItems.Item;
import com.klin.holoItems.collections.dungeons.inaDungeon.InaDungeon;
import org.bukkit.Material;

public class BoneShard extends Item {
    public static final String name = "boneShard";

    private static final Material material = Material.BONE;
    private static final int quantity = 1;
    private static final String lore =
            "2x2";
    private static final int durability = 0;
    public static final boolean stackable = true;
    private static final boolean shiny = true;

    public static final int cost = -1;
    public static final char key = '3';
    public static final String id = ""+InaDungeon.key+key;

    public BoneShard(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost, id, key);
    }

    public void registerRecipes() {}
}
