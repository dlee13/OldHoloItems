package com.klin.holoItems.collections.id1.moonaCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.id1.moonaCollection.MoonaCollection;
import com.klin.holoItems.interfaces.Placeable;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ShapelessRecipe;

import java.util.Set;

public class Lightbulb extends Item implements Placeable {
    public static final String name = "lightbulb";
    public static final Set<Enchantment> accepted = null;

    private static final Material material = Material.REDSTONE_TORCH;
    private static final int quantity = 1;
    private static final String lore =
            "§6Ability" +"/n"+
                "Places a lamp on top of the torch";
    private static final int durability = 0;
    public static final boolean stackable = true;
    private static final boolean shiny = true;

    public static final int cost = 0;
    public static final char key = '0';

    public Lightbulb(){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost,
                ""+MoonaCollection.key+key, key);
    }

    public void registerRecipes(){
        ShapelessRecipe recipe =
                new ShapelessRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.addIngredient(Material.REDSTONE_LAMP);
        recipe.addIngredient(Material.REDSTONE_TORCH);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(BlockPlaceEvent event){
        Block relative = event.getBlockPlaced().getRelative(BlockFace.UP);
        if (relative.isEmpty()) {
            relative.setType(Material.REDSTONE_LAMP);
            event.setCancelled(false);
        }
    }
}
