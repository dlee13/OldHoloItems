package com.klin.holoItems.collections.gamers.okayuCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.abstractClasses.Enchant;
import com.klin.holoItems.collections.gen1.melCollection.items.ReadingGlasses;
import com.klin.holoItems.collections.gen4.cocoCollection.items.DragonHorns;
import com.klin.holoItems.collections.gen5.botanCollection.items.Backdash;
import com.klin.holoItems.interfaces.Consumable;
import com.klin.holoItems.interfaces.Hungerable;
import com.klin.holoItems.utility.ReflectionUtils;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Statistic;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.sql.Ref;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MoguMogu extends Enchant implements Hungerable {
    public static final String name = "moguMogu";
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
        super(name, material, lore, durability, shiny, cost, acceptedIds, acceptedTypes, expCost);

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
        Player player = Bukkit.getPlayer(humanEntity.getUniqueId());
        PlayerInventory inv = humanEntity.getInventory();
        List<Integer> slots = Arrays.asList(0,1,2,3,4,5,6,7,8,40);
        Collections.shuffle(slots);
        for(int slot : slots){
            ItemStack itemStack = inv.getItem(slot);
            if(itemStack == null)
                continue;

            // Next, check if we can eat it and if we want to eat it.
            Item holoItem = Utility.findItem(itemStack, Item.class);

            if(holoItem != null) {
                // HoloItem!
                // The HoloItem can handle its own shit.
                if (holoItem instanceof Consumable) {
                    // Well now I don't know what to do.
                    // The holoItem requires a playerItemConsumeEvent
                    // and playerItemConsumeEvent requires a player, an ItemStack, and an EquipmentSlot.
                    // The problem is that if it's in a Shulker box it's not in an EquipmentSlot.
                    if(player != null) {
                        if(!tryConsumeItem(player, itemStack)){
                            // do not consume the item
                            continue;
                        }
                    }
                }
                // else: holoItem not instance of consumable
                // holoitem can be "consumable" like spacebread
                // I don't know how
                continue;
            }

            // Not a HoloItem. Run the normal items check.
            // First check if it's even possible:
            if(!tryConsumeItem(player, itemStack)) {
                // do not consume the item
                // could be an FF item
                continue;
            }

            // Then check if it's edible:
            Material foodType = itemStack.getType();
            if (!foodType.isEdible()) {
                // Item isn't edible, so don't consider eating it.
                continue;
            }

            // We're eating this item.
            ReflectionUtils.finishUsingItem(itemStack, humanEntity.getWorld(), humanEntity);

            // And we've eaten something, so move on past this event.
            return itemStack;
        }
        return null;
    }

    /**
     * Attempts to "consume" the item. Perhaps this could have a better desc.
     * @param player The player consuming the item
     * @param itemStack The ItemStack to be consumed
     * @return Whether to consume the itemStack or not.
     */
    private static boolean tryConsumeItem(Player player, ItemStack itemStack){
        PlayerItemConsumeEvent pice = new PlayerItemConsumeEvent(player, itemStack);
        Bukkit.getPluginManager().callEvent(pice);
        // if it's cancelled, don't consume
        // so if it's not cancelled, consume
        // I'm aware every usage of tryConsumeItem !'s it anyway but this makes more sense, I think.
        return !pice.isCancelled();
    }
}
