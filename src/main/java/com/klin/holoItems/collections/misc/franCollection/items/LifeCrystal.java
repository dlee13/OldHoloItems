package com.klin.holoItems.collections.misc.franCollection.items;

import com.klin.holoItems.Item;
import com.klin.holoItems.collections.misc.franCollection.FranCollection;
import com.klin.holoItems.interfaces.customMobs.Combinable;
import com.klin.holoItems.interfaces.customMobs.Spawnable;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class LifeCrystal extends Item implements Combinable, Spawnable {
    public static final String name = "lifeCrystal";
    public static final Set<Enchantment> accepted = null;

    private static final Material material = Material.RED_DYE;
    private static final int quantity = 1;
    private static final String lore =
            "Rename to set HP";
    private static final int durability = 0;
    public static final boolean stackable = true;
    private static final boolean shiny = true;

    public static final int cost = -1;
    public static final char key = '2';

    public LifeCrystal(){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost,
                ""+FranCollection.key+key, key);
    }

    public void registerRecipes(){}

    public String processInfo(ItemStack item) {
        return ":"+item.getItemMeta().getDisplayName();
    }

    public void ability(LivingEntity entity, String info) {
        try{
            entity.setMaxHealth(Math.max(0.1, Integer.parseInt(info)-0.1));
        } catch(NumberFormatException ignored){}
    }
}
