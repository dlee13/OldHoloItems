package com.klin.holoItems.collections.gamers.okayuCollection;

import com.klin.holoItems.abstractClasses.Enchant;
import com.klin.holoItems.collections.gen1.melCollection.items.ReadingGlasses;
import com.klin.holoItems.collections.gen4.cocoCollection.items.DragonHorns;
import com.klin.holoItems.collections.gen5.botanCollection.items.Backdash;
import com.klin.holoItems.interfaces.Hungerable;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Material;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.inventory.ItemStack;

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

    }

    @Override
    public void ability(FoodLevelChangeEvent event) {
        ItemStack consumedStack = BasicAutoEat.autoEat(event);
        if(consumedStack == null){
            // Didn't eat anything, move on.
            return;
        }

        ItemStack replacementStack = Utility.recursiveSearchForItem(event.getEntity().getInventory(), 1, i ->
                i != null && consumedStack.isSimilar(i) && consumedStack.hashCode() != i.hashCode());
        if(replacementStack != null){
            Material mat = consumedStack.getType();

            int totalAmount = consumedStack.getAmount() + replacementStack.getAmount();
            if(totalAmount <= mat.getMaxStackSize()){
                consumedStack.setAmount(totalAmount);
                replacementStack.setAmount(0);
                replacementStack.setType(Material.AIR);
            }
            else{
                consumedStack.setAmount(mat.getMaxStackSize());
                replacementStack.setAmount(totalAmount - mat.getMaxStackSize());
            }
        }
    }
}
