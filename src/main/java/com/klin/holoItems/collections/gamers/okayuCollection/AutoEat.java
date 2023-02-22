package com.klin.holoItems.collections.gamers.okayuCollection;

import com.klin.holoItems.abstractClasses.Enchant;
import com.klin.holoItems.collections.gen1.melCollection.items.ReadingGlasses;
import com.klin.holoItems.collections.gen4.cocoCollection.items.DragonHorns;
import com.klin.holoItems.collections.gen5.botanCollection.items.Backdash;
import com.klin.holoItems.interfaces.Hungerable;
import com.klin.holoItems.utility.Utility;
import it.unimi.dsi.fastutil.objects.ObjectObjectMutablePair;
import org.bukkit.Material;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AutoEat extends Enchant implements Hungerable {
    public static final String name = "autoEat";
    private static final Material material = Material.BREAD;
    private static final String lore = """
            Auto-eat food.
            You need the food on your hotbar.
            It'll auto-refill from your inventory.""";
    private static final int durability = 0;
    private static final boolean shiny = true;
    public static final boolean stackable = false;
    public static final int cost = 0;

    // Accepted armor types this can be on.
    // Yes, both of these variables represent the same thing; it was written this way in DemonAura.
    public static final Set<String> acceptedIds = Stream.of(ReadingGlasses.name, DragonHorns.name, Backdash.name).collect(Collectors.toCollection(HashSet::new));
    public static final Set<Material> acceptedTypes = Utility.helmets;
    public static final int expCost = 30;

    public AutoEat(){
        super(name, material, lore, durability, shiny, cost, acceptedIds, acceptedTypes, expCost);
    }

    @Override
    public void registerRecipes() {

    }

    @Override
    public void ability(FoodLevelChangeEvent event) {
        ItemStack consumedStack = BasicAutoEat.autoEat(event);
        if(consumedStack == null){
            // Didn't eat anything, move on.
            return;
        }

        recursiveRefillSlot(consumedStack, event.getEntity().getInventory(), 0);
    }

    public static void recursiveRefillSlot(ItemStack toRefill, PlayerInventory inv, int recursion){
        // Search the non-hotbar slots for an item we can refill from.
        ObjectObjectMutablePair<ItemStack, Runnable> searchPair =
                Utility.recursiveSearchForItem(inv.getStorageContents(), recursion, 9, Integer.MAX_VALUE,
                        i -> i != null && toRefill.isSimilar(i) && toRefill.hashCode() != i.hashCode());

        // If we found something to refill from...
        if(searchPair != null){
            // Get that item.
            ItemStack replacementStack = searchPair.left();
            Material mat = toRefill.getType();

            // Between BOTH slots, how many copies do we get?
            // EX If we have 63 and 35 gapples, we have 98 gapples total.
            int totalAmount = toRefill.getAmount() + replacementStack.getAmount();
            if(totalAmount <= mat.getMaxStackSize()){
                // If the main stack can be overfilled then fill it and put the rest back
                toRefill.setAmount(totalAmount);
                replacementStack.setAmount(0);
                replacementStack.setType(Material.AIR);
            }
            else{
                // If the main stack can't be overfilled then fill it as much as you can and delete the old slot.
                toRefill.setAmount(mat.getMaxStackSize());
                replacementStack.setAmount(totalAmount - mat.getMaxStackSize());
            }
            searchPair.right().run();
        }
    }
}
