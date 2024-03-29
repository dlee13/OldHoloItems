package com.klin.holoItems.collections.en1.watsonCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.interfaces.Dispensable;
import com.klin.holoItems.interfaces.Placeable;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.block.data.type.Dispenser;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class SandPortal extends Item implements Dispensable, Placeable {
    public static final String name = "sandPortal";
    private static final String[] types = new String[]{"RED_SAND", "GRAVEL",
            "WHITE_CONCRETE_POWDER", "ORANGE_CONCRETE_POWDER", "MAGENTA_CONCRETE_POWDER",
            "LIGHT_BLUE_CONCRETE_POWDER", "YELLOW_CONCRETE_POWDER", "LIME_CONCRETE_POWDER",
            "PINK_CONCRETE_POWDER", "GRAY_CONCRETE_POWDER", "LIGHT_GRAY_CONCRETE_POWDER",
            "CYAN_CONCRETE_POWDER", "PURPLE_CONCRETE_POWDER", "BLUE_CONCRETE_POWDER",
            "BROWN_CONCRETE_POWDER", "GREEN_CONCRETE_POWDER", "RED_CONCRETE_POWDER",
            "BLACK_CONCRETE_POWDER", "SAND"};

    private static final Material material = Material.SAND;
    private static final int quantity = 1;
    private static final String lore =
            "Dispense to create a physical block\n"+
            "Can also be placed infinitely";
    private static final int durability = 0;
    public static final boolean stackable = true;
    private static final boolean shiny = false;
    public static final int cost = 19200;

    public SandPortal(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost);
    }

    public void registerRecipes(){
        for(String type : types) {
            Material material = Material.getMaterial(type);
            item.setType(material);
            String key = "";
            String[] strings = type.toLowerCase().split("_");
            for(String str : strings)
                key += str.substring(0, 1).toUpperCase()+str.substring(1)+" ";
            key += "Portal";
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§6"+key);
            item.setItemMeta(meta);
            key = key.substring(0, 1).toLowerCase()+
                    key.substring(1).replace(" ", "");

            ShapelessRecipe recipe = new ShapelessRecipe(
                    new NamespacedKey(HoloItems.getInstance(), key), item);
            recipe.addIngredient(Material.NETHERITE_BLOCK);
            recipe.addIngredient(material);
            recipe.setGroup(name);
            Bukkit.getServer().addRecipe(recipe);
        }
    }

    public void ability(BlockDispenseEvent event){
        Block block = event.getBlock();
        BlockFace face = ((Dispenser) block.getBlockData()).getFacing();
        Block place = block.getRelative(face);
        event.setCancelled(true);
        Material type = event.getItem().getType();
        if(!place.getType().isAir()) {
            BlockState container = place.getState();
            if(container instanceof Container)
                ((Container) container).getInventory().addItem(new ItemStack(type));
            return;
        }
        place.setType(type);
    }

    public void ability(BlockPlaceEvent event){
        Block block = event.getBlockPlaced();
        Material type = block.getType();
        new BukkitRunnable() {
            @Override
            public void run() {
                block.setType(type);
            }
        }.runTask(HoloItems.getInstance());
    }
}
