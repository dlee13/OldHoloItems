package com.klin.holoItems.collections.gen3.flareCollection.items;

import com.klin.holoItems.interfaces.Interactable;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.gen3.flareCollection.FlareCollection;
import com.klin.holoItems.utility.Task;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.RecipeChoice;

import java.util.Set;

public class Flare extends Item implements Interactable {
    public static final String name = "flare";
    public static final Set<Enchantment> accepted = null;

    private static final Material material = Material.GOLDEN_AXE;
    private static final int quantity = 1;
    private static final String lore =
            "A fiery rocket that burns\n"+
            "everything around it";
    private static final int durability = 32;
    public static final boolean stackable = true;
    private static final boolean shiny = true;

    public static final int cost = 0;
    public static final char key = '2';

    public Flare(){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost,
                ""+FlareCollection.key+key, key);
    }

    public void registerRecipes(){
        FurnaceRecipe recipe =
                new FurnaceRecipe(new NamespacedKey(HoloItems.getInstance(), name+"2"), item,
                        new RecipeChoice.MaterialChoice(Material.LAVA_BUCKET), 1, 300);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(PlayerInteractEvent event, Action action){
        if(!(action==Action.RIGHT_CLICK_AIR || action==Action.RIGHT_CLICK_BLOCK) ||
                event.useItemInHand()==Event.Result.DENY)
            return;

        new Task(HoloItems.getInstance(), 1, 1){

            public void run(){

            }
        };
    }
}
