package com.klin.holoItems.collections.misc.utilityCollection.items;

import com.klin.holoItems.Item;
import com.klin.holoItems.interfaces.Responsible;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class Enchanted extends Item {
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
}
