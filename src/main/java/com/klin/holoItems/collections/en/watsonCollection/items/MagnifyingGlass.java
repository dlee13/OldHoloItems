package com.klin.holoItems.collections.en.watsonCollection.items;

import com.klin.holoItems.Collections;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.en.watsonCollection.WatsonCollection;
import com.klin.holoItems.interfaces.Interactable;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockState;
import org.bukkit.block.TileState;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.persistence.PersistentDataType;

import java.util.Set;

public class MagnifyingGlass extends Item implements Interactable {
    public static final String name = "magnifyingGlass";
    public static final Set<Enchantment> accepted = null;

    private static final Material material = Material.GLASS;
    private static final int quantity = 1;
    private static final String lore =
            "§6Ability" +"/n"+
                "Check out a dispenser's wiring";
    private static final int durability = 0;
    private static final boolean stackable = false;
    private static final boolean shiny = false;

    public static final int cost = 0;
    public static final char key = '4';

    public MagnifyingGlass(){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost,
                ""+WatsonCollection.key+key, key);
    }

    public void registerRecipes(){
        ShapedRecipe recipe =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("*","&");
        recipe.setIngredient('*', Material.GLASS);
        recipe.setIngredient('&', Material.STICK);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(PlayerInteractEvent event, Action action){
        if(action!=Action.RIGHT_CLICK_BLOCK)
            return;
        BlockState state = event.getClickedBlock().getState();
        if(state instanceof TileState) {
            String check = ((TileState) state).getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
            if (check != null) {
                Item generic = Collections.findItem(check);
                if (generic != null)
                    event.getPlayer().sendMessage(Utility.formatName(generic.name));
            }
            else
                event.getPlayer().sendMessage("Nothing");
        }
    }
}