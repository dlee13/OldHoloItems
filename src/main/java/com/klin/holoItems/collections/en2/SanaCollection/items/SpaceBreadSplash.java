package com.klin.holoItems.collections.en2.SanaCollection.items;

import com.klin.holoItems.abstractClasses.Enchant;
import com.klin.holoItems.collections.gen0.suiseiCollection.items.Comet;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SpaceBreadSplash extends Enchant{
    public static final String name = "spaceBreadSplash";
    public static final Set<Enchantment> accepted = null;

    private static final Material material = Material.BREAD;
    private static final String lore =
            "BEEG damage";
    private static final int durability = 0;
    private static final boolean shiny = true;

    public static final Set<String> acceptedIds = Stream.of(Comet.name).collect(Collectors.toCollection(HashSet::new));
    public static final Set<Material> acceptedTypes = null;
    public static final Set<Enchantment> exclusive = Stream.of(Enchantment.DAMAGE_ALL, Enchantment.DAMAGE_UNDEAD, Enchantment.DAMAGE_ARTHROPODS).collect(Collectors.toCollection(HashSet::new));
    public static final int expCost = 40;
    public static final int cost = -1;

    public SpaceBreadSplash(){
        super(name, accepted, material, lore, durability, shiny, cost, acceptedIds, acceptedTypes, exclusive, expCost);
    }

    public void registerRecipes(){}
}
