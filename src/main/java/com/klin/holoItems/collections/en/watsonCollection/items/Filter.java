package com.klin.holoItems.collections.en.watsonCollection.items;

import com.klin.holoItems.abstractClasses.Wiring;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.collections.en.watsonCollection.WatsonCollection;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Levelled;
import org.bukkit.block.data.type.Dispenser;
import org.bukkit.entity.*;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.*;
import org.bukkit.material.Colorable;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Filter extends Wiring {
    public static final String name = "filter";
    private static final Set<Material> dirt = Stream.of(Material.DIRT, Material.COARSE_DIRT, Material.GRASS_BLOCK, Material.PODZOL, Material.MYCELIUM).collect(Collectors.toCollection(HashSet::new));
    private static final Set<Material> dyes = Stream.of(Material.BLACK_DYE, Material.BLUE_DYE, Material.BROWN_DYE, Material.CYAN_DYE, Material.GRAY_DYE, Material.GREEN_DYE, Material.LIGHT_BLUE_DYE, Material.LIGHT_GRAY_DYE, Material.LIME_DYE, Material.MAGENTA_DYE, Material.ORANGE_DYE, Material.PINK_DYE, Material.PURPLE_DYE, Material.RED_DYE, Material.WHITE_DYE, Material.YELLOW_DYE).collect(Collectors.toCollection(HashSet::new));
    private static final ItemStack clay = new ItemStack(Material.CLAY_BALL);

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
            if(place.getType()!=Material.CAULDRON)
                return;
            Levelled cauldron = (Levelled) place.getBlockData();
            if(cauldron.getLevel()==0)
                return;
            cauldron.setLevel(cauldron.getLevel()-1);
            place.setBlockData(cauldron);

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
