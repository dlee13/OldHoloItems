package com.klin.holoItems.collections.gamers.okayuCollection.items;

import com.klin.holoItems.Collections;
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
import org.bukkit.Statistic;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MoguMogu extends Enchant implements Hungerable {
    public static final String name = "basicAutoEat";
    private static final Material material = Material.BEEF;
    private static final String lore = "Auto-eat food.\n" +
            "You still need food on your hotbar, though.";
    private static final int durability = 0;
    private static final boolean shiny = true;
    public static final boolean stackable = true;
    public static final int cost = 64;

    // Accepted armor types this can be on.
    // Yes, both of these variables represent the same thing; it was written this way in DemonAura.
    public static final Set<String> acceptedIds = Stream.of(ReadingGlasses.name, DragonHorns.name, Backdash.name).collect(Collectors.toCollection(HashSet::new));
    public static final Set<Material> acceptedTypes = Utility.helmets;
    public static final int expCost = 25;

    public MoguMogu(){
        super(name, material, lore, durability, shiny, cost, acceptedIds, acceptedTypes, null, OkayuCollection.autoEatEnchs, expCost, stackable);

        // Incase I make an error like use BEETROOTS (block) insteaad of BEETROOT (item)
        for(Material key : Utility.nutritionValues.keySet()){
            if(!key.isEdible()){
                // I'll admit, I don't know what kind of exception to use.
                throw new IllegalStateException("AutoEat food is invalid!");
            }
        }
    }

    @Override
    public void registerRecipes() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("g g", "bhb", "ggg");
        // "G"od apple
        recipe.setIngredient('g', new RecipeChoice.MaterialChoice(Material.ENCHANTED_GOLDEN_APPLE));
        // "B"lock of netherite
        recipe.setIngredient('b', new RecipeChoice.MaterialChoice(Material.NETHERITE_BLOCK));
        // "H"elmet (netherite)
        recipe.setIngredient('h', new RecipeChoice.MaterialChoice(Material.NETHERITE_HELMET));

        Bukkit.getServer().addRecipe(recipe);
    }

    @Override
    public void ability(FoodLevelChangeEvent event){
        autoEat(event);
    }

    /**
     *
     * @param event - The FoodLevelChangeEvent
     * @return The ItemStack used to refill the food level.
     */
    public static ItemStack autoEat(FoodLevelChangeEvent event) {
        if(event.getFoodLevel() > 19){
            // Each drumstick is 2, and each half-drumstick is 1
            return null;
        }

        HumanEntity humanEntity = event.getEntity();
        PlayerInventory inv = humanEntity.getInventory();
        for(int i = 0; i <= 8; i++){
            ItemStack item = inv.getItem(i);
            if(item == null)
                continue;
            Material foodType = item.getType();
            if(!foodType.isEdible()){
                // Item isn't edible, so don't consider eating it.
                continue;
            }
            if(item.getItemMeta().hasDisplayName()){
                // Unnamed items have no display name (their display name is their default)
                // I don't want to eat an item with a special display name.
                // (Maybe it's a frostfall Tsuchigumo eye, who knows. Those are technically edible since they're spider eyes.)
                continue;
            }

            // We're eating this item.
            // First, find its nutrition information.
            float[] nutritionValue = Utility.nutritionValues.get(foodType);
            if(nutritionValue == null){
                // Cake, chorus fruits, etc.
                // Don't eat these!
                continue;
            }

            // We're 100% eating this so
            item.setAmount(item.getAmount() - 1);
            int hunger = Math.round(nutritionValue[0]);
            float saturation = nutritionValue[1];

            // Add that nutrition information to the entity.
            event.setFoodLevel(Math.min(event.getFoodLevel() + hunger, 20));
            humanEntity.setSaturation(humanEntity.getSaturation() + saturation);

            // Check for this item's potion effects.
            PotionEffect[] effects = Utility.foodEatEffects.get(foodType);
            if(effects != null){
                for(PotionEffect effect : effects){
                    humanEntity.addPotionEffect(effect);
                }
            }

            // Special effects for certain items.
            if(foodType == Material.HONEY_BOTTLE){
                humanEntity.removePotionEffect(PotionEffectType.POISON);
            }
            else if(foodType == Material.ROTTEN_FLESH){
                Random r = ThreadLocalRandom.current();
                if(r.nextDouble() < 0.8){
                    humanEntity.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 600, 0));
                }
            }
            else if(foodType == Material.CHICKEN){
                Random r = ThreadLocalRandom.current();
                if(r.nextDouble() < 0.3){
                    humanEntity.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 600, 0));
                }
            }

            // Finally, increment the statistic.
            Player player = Bukkit.getPlayer(humanEntity.getUniqueId());
            if(player != null){
                // weird but it's not "NotNull" so it's good to check
                player.setStatistic(Statistic.USE_ITEM, foodType, player.getStatistic(Statistic.USE_ITEM, foodType) + 1);
            }

            // And we've eaten something, so move on past this event.
            return item;
        }
        return null;
    }
}
