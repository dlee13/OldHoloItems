package com.klin.holoItems.abstractClasses;

import com.klin.holoItems.Item;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import java.util.Set;

public abstract class Enchant extends Item {
    private static final int quantity = 1;
    public static final boolean stackable = false;

    public final Set<String> acceptedIds;
    public final Set<Material> acceptedTypes;
    public final int expCost;

    public Enchant(String name, Set<Enchantment> accepted, Material material, String lore, int durability, boolean shiny,
                   int cost, String id, char key, Set<String> acceptedIds, Set<Material> acceptedTypes, int expCost){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost, id, key);

        this.acceptedIds = acceptedIds;
        this.acceptedTypes = acceptedTypes;
        this.expCost = expCost;
    }
}