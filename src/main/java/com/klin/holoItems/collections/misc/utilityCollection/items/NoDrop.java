package com.klin.holoItems.collections.misc.utilityCollection.items;

import com.klin.holoItems.Item;
import com.klin.holoItems.collections.misc.utilityCollection.UtilityCollection;
import com.klin.holoItems.interfaces.Collectable;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDropItemEvent;

public class NoDrop extends Item implements Collectable {
    public static final String name = "noDrop";

    private static final Material material = Material.GRAY_STAINED_GLASS_PANE;
    private static final int quantity = 1;
    private static final String lore =
            "No drop falling block";
    private static final int durability = 0;
    public static final boolean stackable = true;
    private static final boolean shiny = false;

    public static final int cost = -1;
    public static final char key = '0';
    public static final String id = ""+ UtilityCollection.key+key;

    public NoDrop(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost, id, key);
    }

    public void registerRecipes(){}

    public void ability(EntityDropItemEvent event, Entity entity) {
        event.setCancelled(true);
    }
}