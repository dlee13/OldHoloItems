package com.klin.holoItems.collections.gamers.okayuCollection;

import com.klin.holoItems.abstractClasses.Enchant;
import com.klin.holoItems.collections.gen1.melCollection.items.ReadingGlasses;
import com.klin.holoItems.collections.gen4.cocoCollection.items.DragonHorns;
import com.klin.holoItems.collections.gen5.botanCollection.items.Backdash;
import com.klin.holoItems.interfaces.Hungerable;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BasicAutoEat extends Enchant implements Hungerable {
    public static final String name = "basicAutoEat";
    private static final Material material = Material.BEEF;
    private static final String lore = "Auto-eat food.\n" +
            "You still need food on your hotbar, though.";
    private static final int durability = 0;
    private static final boolean shiny = true;
    public static final boolean stackable = false;
    public static final int cost = 0;

    // Accepted armor types this can be on.
    // Yes, both of these variables represent the same thing; it was written this way in DemonAura.
    public static final Set<String> acceptedIds = Stream.of(ReadingGlasses.name, DragonHorns.name, Backdash.name).collect(Collectors.toCollection(HashSet::new));
    public static final Set<Material> acceptedTypes = Utility.helmets;
    public static final int expCost = 25;

    private static final Map<Material, float[]> nutritionValues = new HashMap<>(){{
        // 38 food items, because no chorus fruit and no cake.
        // **GUESS WHO FOUND OUT THERE ISN'T A TABLE OF FOOD ITEMS?**
        // That, or I couldn't find it, so I get to make this instead.
        put(Material.APPLE, new float[]{4, 2.4F});
        put(Material.BAKED_POTATO, new float[]{5, 6F});
        put(Material.BEETROOT, new float[]{1, 1.2F});
        put(Material.BEETROOT_SOUP, new float[]{6, 7.2F});
        put(Material.BREAD, new float[]{5, 6F});
        put(Material.CARROT, new float[]{3, 3.6F});
        put(Material.COOKED_CHICKEN, new float[]{6, 7.2F});
        put(Material.COOKED_COD, new float[]{5, 6F});
        put(Material.COOKED_MUTTON, new float[]{6, 9.6F});
        put(Material.COOKED_PORKCHOP, new float[]{8, 12.8F});
        put(Material.COOKED_RABBIT, new float[]{5, 6F});
        put(Material.COOKED_SALMON, new float[]{6, 9.6F});
        put(Material.COOKIE, new float[]{2, 0.4F});
        put(Material.DRIED_KELP, new float[]{1, 0.6F});
        put(Material.ENCHANTED_GOLDEN_APPLE, new float[]{4, 9.6F});
        put(Material.GOLDEN_APPLE, new float[]{4, 9.6F});
        put(Material.GLOW_BERRIES, new float[]{2, 0.4F});
        put(Material.GOLDEN_CARROT, new float[]{6, 14.4F});
        put(Material.HONEY_BOTTLE, new float[]{6, 1.2F});
        put(Material.MELON_SLICE, new float[]{2, 1.2F});
        put(Material.MUSHROOM_STEW, new float[]{6, 7.2F});
        put(Material.POISONOUS_POTATO, new float[]{2, 1.2F});
        put(Material.POTATO, new float[]{1, 0.6F});
        put(Material.PUFFERFISH, new float[]{1, 0.2F});
        put(Material.PUMPKIN_PIE, new float[]{8, 4.8F});
        put(Material.RABBIT_STEW, new float[]{10, 12F});
        put(Material.BEEF, new float[]{3, 1.8F});
        put(Material.CHICKEN, new float[]{2, 1.2F});
        put(Material.COD, new float[]{2, 0.4F});
        put(Material.MUTTON, new float[]{2, 1.2F});
        put(Material.PORKCHOP, new float[]{3, 1.8F});
        put(Material.RABBIT, new float[]{3, 1.8F});
        put(Material.SALMON, new float[]{2, 0.4F});
        put(Material.ROTTEN_FLESH, new float[]{4, 0.8F});
        put(Material.SPIDER_EYE, new float[]{2, 3.2F});
        put(Material.COOKED_BEEF, new float[]{8, 12.8F});
        put(Material.SWEET_BERRIES, new float[]{2, 0.4F});
        put(Material.TROPICAL_FISH, new float[]{1, 0.2F});
    }};

    // Rotten flesh and raw chicken aren't on this list because I give them a special exception in the code later.
    // If mojang adds like a dozen foods with % potion effects I'll update this to be better.
    private static final Map<Material, PotionEffect[]> potionEffects = new HashMap<>(){{
        put(Material.PUFFERFISH, new PotionEffect[]{
                new PotionEffect(PotionEffectType.HUNGER, 300, 2),
                new PotionEffect(PotionEffectType.CONFUSION, 300, 0),
                new PotionEffect(PotionEffectType.POISON, 1200, 1)
        });
        put(Material.GOLDEN_APPLE, new PotionEffect[]{
                new PotionEffect(PotionEffectType.REGENERATION, 100, 1),
                new PotionEffect(PotionEffectType.ABSORPTION, 2400, 0)
        });
        put(Material.ENCHANTED_GOLDEN_APPLE, new PotionEffect[]{
                new PotionEffect(PotionEffectType.REGENERATION, 400, 1),
                new PotionEffect(PotionEffectType.ABSORPTION, 2400, 3),
                new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 2000, 0),
                new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 2000, 0)
        });
    }};

    public BasicAutoEat(){
        super(name, material, lore, durability, shiny, cost, acceptedIds, acceptedTypes, expCost);

        // Incase I make an error like use BEETROOTS (block) insteaad of BEETROOT (item)
        for(Material key : nutritionValues.keySet()){
            if(!key.isEdible()){
                // I'll admit, I don't know what kind of exception to use.
                throw new IllegalStateException("AutoEat food is invalid!");
            }
        }
    }

    @Override
    public void registerRecipes() {

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
            float[] nutritionValue = nutritionValues.get(foodType);
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
            PotionEffect[] effects = potionEffects.get(foodType);
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
