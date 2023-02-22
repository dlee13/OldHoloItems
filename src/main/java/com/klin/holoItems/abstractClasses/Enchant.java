package com.klin.holoItems.abstractClasses;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class Enchant extends Item {
    private static final int quantity = 1;
    public static final boolean stackable = false;

    public final Set<String> acceptedIds;
    public final Set<Material> acceptedTypes;
    public final Set<Enchantment> exclusive;
    public final List<String> exclusiveHoloEnchs;
    public final int expCost;

    public Enchant(String name, Set<Enchantment> accepted, Material material, String lore, int durability, boolean shiny, int cost, Set<String> acceptedIds, Set<Material> acceptedTypes, int expCost){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost);
        this.acceptedIds = acceptedIds;
        this.acceptedTypes = acceptedTypes;
        this.exclusive = null;
        this.exclusiveHoloEnchs = null;
        this.expCost = expCost;
    }

    public Enchant(String name, Set<Enchantment> accepted, Material material, String lore, int durability, boolean shiny, int cost, Set<String> acceptedIds, Set<Material> acceptedTypes, Set<Enchantment> exclusive, int expCost){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost);
        this.acceptedIds = acceptedIds;
        this.acceptedTypes = acceptedTypes;
        this.exclusive = exclusive;
        this.exclusiveHoloEnchs = null;
        this.expCost = expCost;
    }

    public Enchant(String name, Material material, String lore, int durability, boolean shiny, int cost, Set<String> acceptedIds, Set<Material> acceptedTypes, int expCost){
        super(name, null, material, quantity, lore, durability, stackable, shiny, cost);
        this.acceptedIds = acceptedIds;
        this.acceptedTypes = acceptedTypes;
        this.exclusive = null;
        this.exclusiveHoloEnchs = null;
        this.expCost = expCost;
    }

    public Enchant(String name, Material material, String lore, int durability, boolean shiny, int cost, Set<String> acceptedIds, Set<Material> acceptedTypes, Set<Enchantment> exclusive, List<String> exclusiveHolo, int expCost){
        super(name, null, material, quantity, lore, durability, stackable, shiny, cost);
        this.acceptedIds = acceptedIds;
        this.acceptedTypes = acceptedTypes;
        this.exclusive = exclusive;
        this.expCost = expCost;

        // TLDR OkayuCollection fix so that when BadAutoEat removes itself that wont be true for the others
        exclusiveHolo = new ArrayList<>(exclusiveHolo);
        exclusiveHolo.remove(name);
        this.exclusiveHoloEnchs = exclusiveHolo;
    }
}