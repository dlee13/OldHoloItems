package com.klin.holoItems.collections.hidden.utilityCollection.items;

import com.klin.holoItems.Item;
import com.klin.holoItems.interfaces.Placeable;
import org.bukkit.Material;
import org.bukkit.event.block.BlockPlaceEvent;

public class Enchanted extends Item implements Placeable {
    public static final String name = "enchanted";

    private static final Material material = Material.STICK;
    private static final int quantity = 1;
    private static final String lore =
            "Enchanted non-HoloItem";
    private static final int durability = 0;
    public static final boolean stackable = true;
    private static final boolean shiny = false;
    public static final int cost = -1;

    public Enchanted(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost);
    }

    public void registerRecipes(){}

    public void ability(BlockPlaceEvent event) {
        event.setCancelled(false);
    }
}
