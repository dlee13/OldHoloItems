package com.klin.holoItems.collections.hidden.franCollection.items;

import com.klin.holoItems.Item;
import com.klin.holoItems.interfaces.customMobs.Combinable;
import com.klin.holoItems.interfaces.customMobs.Retaliable;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class SharpenedFangs extends Item implements Combinable, Retaliable {
    public static final String name = "sharpenedFangs";

    private static final Material material = Material.GHAST_TEAR;
    private static final int quantity = 1;
    private static final String lore =
            "Rename to set attack";
    private static final int durability = 0;
    public static final boolean stackable = true;
    private static final boolean shiny = true;
    public static final int cost = -1;

    public SharpenedFangs(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost);
    }

    public void registerRecipes() {}

    public String processInfo(ItemStack item) {
        return ":"+item.getItemMeta().getDisplayName();
    }

    public void ability(EntityDamageByEntityEvent event, Entity damager, String info) {
        try{
            event.setDamage(Integer.parseInt(info));
        } catch(NumberFormatException ignored){}
    }
}
