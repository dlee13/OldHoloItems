package com.klin.holoItems.collections.misc.achanCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.interfaces.Closeable;
import com.klin.holoItems.interfaces.Holdable;
import com.klin.holoItems.interfaces.Placeable;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockState;
import org.bukkit.block.ShulkerBox;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ShapedRecipe;

public class ShoulderBagStrap extends Item implements Placeable, Holdable, Closeable {
    public static final String name = "shoulderBagStrap";

    private static final Material material = Material.CHAIN;
    private static final int quantity = 1;
    private static final String lore =
            "Easy carry shulkers";
    private static final int durability = 0;
    public static final boolean stackable = false;
    private static final boolean shiny = false;
    public static final int cost = 0;

    public ShoulderBagStrap(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost);
    }

    public void registerRecipes(){
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"0"), item);
        recipe.shape(" ab","ab "," ab");
        recipe.setIngredient('a', Material.IRON_INGOT);
        recipe.setIngredient('b', Material.LEATHER);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);

        recipe = new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"1"), item);
        recipe.shape("ba "," ba","ba ");
        recipe.setIngredient('a', Material.IRON_INGOT);
        recipe.setIngredient('b', Material.LEATHER);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(BlockPlaceEvent event){
        BlockState state = event.getBlockPlaced().getState();
        if(state instanceof ShulkerBox)
            event.getPlayer().openInventory(((ShulkerBox) state).getInventory());
    }

    public void ability(InventoryCloseEvent event){
        InventoryHolder holder = event.getInventory().getHolder();
        if(holder instanceof ShulkerBox)
            ((ShulkerBox) holder).getBlock().breakNaturally();
    }
}
