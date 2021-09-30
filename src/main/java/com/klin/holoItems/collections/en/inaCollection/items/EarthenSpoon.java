package com.klin.holoItems.collections.en.inaCollection.items;

import com.klin.holoItems.Collections;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.abstractClasses.Wiring;
import com.klin.holoItems.collections.en.inaCollection.InaCollection;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Dispenser;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.*;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public class EarthenSpoon extends Wiring {
    public static final String name = "earthenSpoon";

    private static final Material material = Material.WOODEN_SHOVEL;
    private static final String lore =
            "Dispensers wired with this item will\n" +
            "plant crops from its inventory\n" +
            "Break the dispenser to retrieve";
    private static final boolean shiny = true;
    public static final int cost = 16720;

    public EarthenSpoon(){
        super(name, material, lore, shiny, cost);
    }

    public void registerRecipes(){
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("a","b","b");
        recipe.setIngredient('a', Material.ANCIENT_DEBRIS);
        recipe.setIngredient('b', Material.STICK);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(BlockDispenseEvent event){
        ItemStack item = event.getItem();
        Material crop = Utility.ageable.get(item.getType());
        if(crop==null)
            return;

        event.setCancelled(true);
        Block block = event.getBlock();
        BlockFace face = ((Dispenser) block.getBlockData()).getFacing();
        Block place = block.getRelative(face);
        Material soil;
        if(crop==Material.NETHER_WART)
            soil = Material.SOUL_SAND;
        else if(crop==Material.CHORUS_FLOWER)
            soil = Material.END_STONE;
        else
            soil = Material.FARMLAND;
        if(!place.isEmpty()) {
            if (face == BlockFace.UP && place.getType() == soil) {
                Block air = place.getRelative(BlockFace.UP);
                if (!air.isEmpty())
                    return;
                if(item.getItemMeta()!=null){
                    Integer battery = item.getItemMeta().getPersistentDataContainer().get(Utility.pack, PersistentDataType.INTEGER);
                    if(battery!=null)
                        CorruptedWheat.spread(place, item.getType(), battery+1, air.getLocation());
                    else
                        air.setType(crop);
                }
                else
                    air.setType(crop);

                Inventory inv = ((InventoryHolder) block.getState()).getInventory();
                new BukkitRunnable(){
                    public void run(){
                        inv.removeItem(item);
                    }
                }.runTask(HoloItems.getInstance());
            }
            return;
        }
        int charge = 1;
        if(item.getItemMeta()!=null) {
            Integer battery = item.getItemMeta().getPersistentDataContainer().get(Utility.pack, PersistentDataType.INTEGER);
            if(battery!=null)
                charge = battery+1;
        }
        if(plant(face, place, crop, soil, charge))
            return;

        Inventory inv = ((InventoryHolder) block.getState()).getInventory();
        new BukkitRunnable(){
            public void run(){
                inv.removeItem(item);
            }
        }.runTask(HoloItems.getInstance());
    }

    private static boolean plant(BlockFace face, Block place, Material crop, Material soil, int charge){
        if(charge<=0 || !place.getType().isAir() || place.getRelative(BlockFace.DOWN).getType()!=soil)
            return true;
        place.setType(crop);
        plant(face, place.getRelative(face), crop, soil, charge-1);
        return false;
    }
}
