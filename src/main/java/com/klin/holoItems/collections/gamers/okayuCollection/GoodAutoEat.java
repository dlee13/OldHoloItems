package com.klin.holoItems.collections.gamers.okayuCollection;

import com.klin.holoItems.Collections;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.abstractClasses.Enchant;
import com.klin.holoItems.collections.gen1.melCollection.items.ReadingGlasses;
import com.klin.holoItems.collections.gen4.cocoCollection.items.DragonHorns;
import com.klin.holoItems.collections.gen5.botanCollection.items.Backdash;
import com.klin.holoItems.interfaces.Hungerable;
import com.klin.holoItems.utility.Utility;
import it.unimi.dsi.fastutil.objects.ObjectObjectMutablePair;
import jdk.jshell.execution.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GoodAutoEat extends Enchant implements Hungerable {
    public static final String name = "goodAutoEat";
    private static final Material material = Material.COOKED_BEEF;
    private static final String lore = """
            Auto-eat food. You need the food on your hotbar.
            It'll auto-refill from your inventory and its shulker boxes.""";
    private static final int durability = 0;
    private static final boolean shiny = true;
    public static final boolean stackable = false;
    public static final int cost = 0;

    // Accepted armor types this can be on.
    // Yes, both of these variables represent the same thing; it was written this way in DemonAura.
    public static final Set<String> acceptedIds = Stream.of(ReadingGlasses.name, DragonHorns.name, Backdash.name).collect(Collectors.toCollection(HashSet::new));
    public static final Set<Material> acceptedTypes = Utility.helmets;
    public static final int expCost = 30;

    public GoodAutoEat(){
        super(name, material, lore, durability, shiny, cost, acceptedIds, acceptedTypes, expCost);
    }

    @Override
    public void registerRecipes() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("gsg", "aea", "gsg");
        // Good and Super foods
        recipe.setIngredient('g', new RecipeChoice.MaterialChoice(Utility.goodFoods));
        recipe.setIngredient('s', new RecipeChoice.MaterialChoice(Utility.superFoods));
        // "E"nchanted golden apple
        recipe.setIngredient('e', Material.ENCHANTED_GOLDEN_APPLE);
        // "A"uto eat
        recipe.setIngredient('a', new RecipeChoice.ExactChoice(Collections.items.get(AutoEat.name).item));

        Bukkit.getServer().addRecipe(recipe);
    }

    @Override
    public void ability(FoodLevelChangeEvent event) {
        ItemStack consumedStack = BasicAutoEat.autoEat(event);
        if(consumedStack == null){
            // Didn't eat anything, move on.
            return;
        }

        AutoEat.recursiveRefillSlot(consumedStack, event.getEntity().getInventory(), 1);
    }
}
