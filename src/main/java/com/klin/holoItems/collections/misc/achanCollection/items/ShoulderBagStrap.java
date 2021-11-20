package com.klin.holoItems.collections.misc.achanCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.interfaces.Closeable;
import com.klin.holoItems.interfaces.Holdable;
import com.klin.holoItems.interfaces.Placeable;
import com.klin.holoItems.utility.Utility;
import jdk.jshell.execution.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.ShulkerBox;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
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
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape(" ab","ab "," ab");
        recipe.setIngredient('a', Material.IRON_INGOT);
        recipe.setIngredient('b', Material.LEATHER);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);

        Utility.mirror(recipe, name, item);
    }

    public void ability(BlockPlaceEvent event){
        BlockState state = event.getBlockPlaced().getState();
        if(state instanceof ShulkerBox)
            event.getPlayer().openInventory(((ShulkerBox) state).getInventory());
    }

    public void ability(InventoryCloseEvent event){
        InventoryHolder holder = event.getInventory().getHolder();
        if(holder instanceof ShulkerBox) {
            Block block = ((ShulkerBox) holder).getBlock();
            block.setType(Material.AIR);
            Location loc = block.getLocation();
            for(ItemStack item : block.getDrops()) {
                if (!event.getPlayer().getInventory().addItem(item).isEmpty())
                    loc.getWorld().dropItemNaturally(loc, item);
            }
        }
    }
}
