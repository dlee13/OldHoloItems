package com.klin.holoItems.collections.en.inaCollection.items;

import com.klin.holoItems.abstractClasses.Wiring;
import com.klin.holoItems.HoloItems;
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
            "§6Ability" +"/n"+
            "Dispensers wired with this item will" +"/n"+
            "plant crops & flowers from its inventory" + "/n"+
            "Break the dispenser to retrieve";
    private static final boolean shiny = true;

    public static final int cost = 16720;
    public static final char key = '1';

    public EarthenSpoon(){
        super(name, material, lore, shiny, cost, ""+InaCollection.key+key, key);
    }

    public void registerRecipes(){
        ShapedRecipe recipe0 =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"0"), item);
        recipe0.shape("*  ","%  ","%  ");
        recipe0.setIngredient('*', Material.ANCIENT_DEBRIS);
        recipe0.setIngredient('%', Material.STICK);
        recipe0.setGroup(name);
        Bukkit.getServer().addRecipe(recipe0);

        ShapedRecipe recipe1 =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"1"), item);
        recipe1.shape(" * "," % "," % ");
        recipe1.setIngredient('*', Material.ANCIENT_DEBRIS);
        recipe1.setIngredient('%', Material.STICK);
        recipe1.setGroup(name);
        Bukkit.getServer().addRecipe(recipe1);

        ShapedRecipe recipe2 =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"2"), item);
        recipe2.shape("  *","  %","  %");
        recipe2.setIngredient('*', Material.ANCIENT_DEBRIS);
        recipe2.setIngredient('%', Material.STICK);
        recipe2.setGroup(name);
        Bukkit.getServer().addRecipe(recipe2);

        ItemStack ceramicLadle = Utility.process(
                "ceramicLadle", Material.STONE_SHOVEL, 1,
                "§6Ability" +"/n"+
                        "Dispensers wired with this item will" +"/n"+
                        "plant saplings from its inventory" + "/n"+
                        "Break the dispenser to retrieve",
                0, true, "L2");
        BlastingRecipe recipe =
                new BlastingRecipe(new NamespacedKey(HoloItems.getInstance(), "ceramicLadle"),
                        ceramicLadle, new RecipeChoice.ExactChoice(item), 1, 900);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(BlockDispenseEvent event){
        ItemStack item = event.getItem();
        Material crop = Utility.ageable.get(item.getType());
        if(crop==null)
            return;

        event.setCancelled(true);
//        Block block = event.getBlock();
//        BlockFace face = ((Dispenser) block.getBlockData()).getFacing();
//        Block place = block.getRelative(face);
//        Material soil;
//        if(crop==Material.NETHER_WART)
//            soil = Material.SOUL_SAND;
//        else
//            soil = Material.FARMLAND;
//        if(face==BlockFace.UP &&
//                !place.isEmpty() && place.getType()==soil){
//            Block air = place.getRelative(BlockFace.UP);
//            if(!air.isEmpty())
//                return;
//            air.setType(crop);
//        }
//        else {
//            if (!place.isEmpty() || place.getRelative(BlockFace.DOWN).getType()!=soil)
//                return;
//            place.setType(crop);
//        }

        Block block = event.getBlock();
        BlockFace face = ((Dispenser) block.getBlockData()).getFacing();
        Block place = block.getRelative(face);
        Material soil;
        if(crop==Material.NETHER_WART)
            soil = Material.SOUL_SAND;
        else
            soil = Material.FARMLAND;
        if(!place.isEmpty()) {
            if (face == BlockFace.UP && place.getType() == soil) {
                Block air = place.getRelative(BlockFace.UP);
                if (!air.isEmpty())
                    return;
                air.setType(crop);
            }
            else
                return;
        }
        int charge = 1;
        if(item.getItemMeta()!=null) {
            Integer battery = item.getItemMeta().getPersistentDataContainer().get(Utility.pack, PersistentDataType.INTEGER);
            if(battery!=null)
                charge = battery+1;
        }
        plant(face, place, crop, soil, charge);

        Inventory inv = ((InventoryHolder) block.getState()).getInventory();
        new BukkitRunnable(){
            public void run(){
                inv.removeItem(item);
            }
        }.runTask(HoloItems.getInstance());
    }

    private static void plant(BlockFace face, Block place, Material crop, Material soil, int charge){
        if(charge<=0 || !place.getType().isAir() || place.getRelative(BlockFace.DOWN).getType()!=soil)
            return;
        place.setType(crop);
        plant(face, place.getRelative(face), crop, soil, charge-1);
    }
}
