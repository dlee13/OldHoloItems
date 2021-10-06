package com.klin.holoItems.collections.misc.opCollection.items;

import com.klin.holoItems.Item;
import com.klin.holoItems.interfaces.Responsible;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class NameTag extends Item implements Responsible {
    public static final String name = "nameTag";

    private static final Material material = Material.PAPER;
    private static final int quantity = 1;
    private static final String lore =
            "Hand a npc a name tag";
    private static final int durability = 0;
    public static final boolean stackable = false;
    private static final boolean shiny = false;
    public static final int cost = -1;

    public NameTag(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost);
    }

    public void registerRecipes(){}

    public boolean ability(PlayerInteractEntityEvent event, ItemStack item){
        event.getRightClicked().getPersistentDataContainer().set(Utility.key, PersistentDataType.STRING, item.getItemMeta().getDisplayName());
        return true;
    }
}
