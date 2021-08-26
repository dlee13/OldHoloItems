package com.klin.holoItems.collections.en.watsonCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.abstractClasses.Wiring;
import com.klin.holoItems.collections.en.watsonCollection.WatsonCollection;
import com.klin.holoItems.utility.Utility;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Levelled;
import org.bukkit.block.data.type.Dispenser;
import org.bukkit.entity.Entity;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.Colorable;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.Set;

public class Filter extends Wiring {
    public static final String name = "filter";
    private final Set<Material> dirt;
    private final Set<Material> dyes;

    private static final Material material = Material.IRON_BARS;
    private static final String lore =
                "Dispensers wired with this item can\n"+
                "filter clay from soil using water\n"+
                "Break the dispenser to retrieve";
    private static final boolean shiny = true;

    public static final int cost = 8360;
    public static final char key = '1';
    public static final String id = ""+WatsonCollection.key+key;

    public Filter(){
        super(name, material, lore, shiny, cost, id, key);
        dirt = Utility.fertile;
        dyes = Set.of(Material.BLACK_DYE, Material.BLUE_DYE, Material.BROWN_DYE, Material.CYAN_DYE, Material.GRAY_DYE, Material.GREEN_DYE, Material.LIGHT_BLUE_DYE, Material.LIGHT_GRAY_DYE, Material.LIME_DYE, Material.MAGENTA_DYE, Material.ORANGE_DYE, Material.PINK_DYE, Material.PURPLE_DYE, Material.RED_DYE, Material.WHITE_DYE, Material.YELLOW_DYE);
    }

    public void registerRecipes(){
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("aaa","bbb","ccc");
        recipe.setIngredient('a', Material.HOPPER);
        recipe.setIngredient('b', Material.COBWEB);
        recipe.setIngredient('c', Material.BASALT);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(BlockDispenseEvent event){
        ItemStack item = event.getItem();
        Material type = item.getType();

        if(dirt.contains(type)) {
            event.setCancelled(true);
            Block block = event.getBlock();
            BlockFace face = ((Dispenser) block.getBlockData()).getFacing();
            Block place = block.getRelative(face);
            if(place.getType()!=Material.WATER_CAULDRON)
                return;
            Levelled cauldron = (Levelled) place.getBlockData();
            if(cauldron.getLevel()==1) {
                place.setType(Material.CAULDRON);
            } else {
                cauldron.setLevel(cauldron.getLevel()-1);
                place.setBlockData(cauldron);
            }

            ItemStack clay = new ItemStack(Material.CLAY_BALL);
            new BukkitRunnable(){
                public void run(){
                    Inventory inv = ((InventoryHolder) block.getState()).getInventory();
                    inv.removeItem(item);
                    if(!inv.addItem(clay).isEmpty()) {
                        Location location = place.getLocation();
                        location.getWorld().dropItemNaturally(location, clay);
                    }
                }
            }.runTask(HoloItems.getInstance());
            return;
        }

        if(dyes.contains(type)) {
            event.setCancelled(true);
            Block block = event.getBlock();
            Location location = block.getRelative(((Dispenser) block.getBlockData()).getFacing()).getLocation().add(0.5, 0.5, 0.5);
            Collection<Entity> nearbyColorableEntities = location.getWorld().getNearbyEntities(location, 0.5, 0.5, 0.5, entity -> (entity instanceof Colorable));
            for(Entity entity : nearbyColorableEntities) {
                ((Colorable) entity).setColor(DyeColor.valueOf(type.toString().substring(0, type.toString().indexOf("_DYE"))));
                new BukkitRunnable(){
                    public void run(){
                        Inventory inv = ((InventoryHolder) block.getState()).getInventory();
                        inv.removeItem(item);
                    }
                }.runTask(HoloItems.getInstance());
                return;
            }
            return;
        }
    }
}
