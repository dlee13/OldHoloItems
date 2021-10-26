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
    public final Set<Enchantment> exclusive;
    public final int expCost;

    public Enchant(String name, Set<Enchantment> accepted, Material material, String lore, int durability, boolean shiny, int cost, Set<String> acceptedIds, Set<Material> acceptedTypes, int expCost){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost);
        this.acceptedIds = acceptedIds;
        this.acceptedTypes = acceptedTypes;
        this.exclusive = null;
        this.expCost = expCost;
    }

    public Enchant(String name, Set<Enchantment> accepted, Material material, String lore, int durability, boolean shiny, int cost, Set<String> acceptedIds, Set<Material> acceptedTypes, Set<Enchantment> exclusive, int expCost){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost);
        this.acceptedIds = acceptedIds;
        this.acceptedTypes = acceptedTypes;
        this.exclusive = exclusive;
        this.expCost = expCost;
    }

    public Enchant(String name, Material material, String lore, int durability, boolean shiny, int cost, Set<String> acceptedIds, Set<Material> acceptedTypes, int expCost){
        super(name, null, material, quantity, lore, durability, stackable, shiny, cost);
        this.acceptedIds = acceptedIds;
        this.acceptedTypes = acceptedTypes;
        this.exclusive = null;
        this.expCost = expCost;
    }
}