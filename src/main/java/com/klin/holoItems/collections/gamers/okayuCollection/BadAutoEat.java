package com.klin.holoItems.collections.gamers.okayuCollection;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.abstractClasses.Enchant;
import com.klin.holoItems.collections.gen1.melCollection.items.ReadingGlasses;
import com.klin.holoItems.collections.gen4.cocoCollection.items.DragonHorns;
import com.klin.holoItems.collections.gen5.botanCollection.items.Backdash;
import com.klin.holoItems.interfaces.Hungerable;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Material;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BadAutoEat extends Enchant implements Hungerable {
    public static final String name = "badAutoEat";
    private static final Material material = Material.COOKIE;
    private static final String lore = "Auto-eat food.\n" +
            "Only activates at 3 drumsticks or less.";
    private static final int durability = 0;
    private static final boolean shiny = true;
    public static final boolean stackable = false;
    public static final int cost = 0;

    // Accepted armor types this can be on.
    // Yes, both of these variables represent the same thing; it was written this way in DemonAura.
    public static final Set<String> acceptedIds = Stream.of(ReadingGlasses.name, DragonHorns.name, Backdash.name).collect(Collectors.toCollection(HashSet::new));
    public static final Set<Material> acceptedTypes = Utility.helmets;
    public static final int expCost = 20;

    public BadAutoEat(){
        super(name, material, lore, durability, shiny, cost, acceptedIds, acceptedTypes, expCost);
    }

    @Override
    public void registerRecipes() {

    }

    @Override
    public void ability(FoodLevelChangeEvent event) {
        if(event.getFoodLevel() > 6){
            // Each drumstick is 2, and each half-drumstick is 1
            return;
        }
        BasicAutoEat.autoEat(event);
    }
}