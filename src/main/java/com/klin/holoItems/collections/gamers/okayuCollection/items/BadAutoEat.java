package com.klin.holoItems.collections.gamers.okayuCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.abstractClasses.Enchant;
import com.klin.holoItems.collections.gamers.okayuCollection.OkayuCollection;
import com.klin.holoItems.collections.gen1.melCollection.items.ReadingGlasses;
import com.klin.holoItems.collections.gen4.cocoCollection.items.DragonHorns;
import com.klin.holoItems.collections.gen5.botanCollection.items.Backdash;
import com.klin.holoItems.interfaces.Hungerable;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

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
    public static final boolean stackable = true;
    public static final int cost = 0;

    // Accepted armor types this can be on.
    // Yes, both of these variables represent the same thing; it was written this way in DemonAura.
    public static final Set<String> acceptedIds = Stream.of(ReadingGlasses.name, DragonHorns.name, Backdash.name).collect(Collectors.toCollection(HashSet::new));
    public static final Set<Material> acceptedTypes = Utility.helmets;
    public static final int expCost = 20;

    public BadAutoEat(){
        super(name, material, lore, durability, shiny, cost, acceptedIds, acceptedTypes, null, OkayuCollection.autoEatEnchs, expCost, stackable);
    }

    @Override
    public void registerRecipes() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("lnl", "lbl", "lnl");
        // "Poor" and "Low" foods
        recipe.setIngredient('l', new RecipeChoice.MaterialChoice(Utility.lowFoods));
        recipe.setIngredient('n', new RecipeChoice.MaterialChoice(Utility.normalFoods));
        // "B"ook
        recipe.setIngredient('b', Material.BOOK);

        Bukkit.getServer().addRecipe(recipe);
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
