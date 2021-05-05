package com.klin.holoItems.collections.en.watsonCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.abstractClasses.Wiring;
import com.klin.holoItems.collections.en.inaCollection.InaCollection;
import com.klin.holoItems.collections.en.watsonCollection.WatsonCollection;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Levelled;
import org.bukkit.block.data.type.Dispenser;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.*;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

public class Filter extends Wiring {
    public static final String name = "filter";

    private static final Set<Material> dirt = new HashSet<Material>(){{
        add(Material.DIRT);
        add(Material.COARSE_DIRT);
        add(Material.GRASS_BLOCK);
        add(Material.PODZOL);
        add(Material.MYCELIUM);
    }};
    private static final ItemStack clay = new ItemStack(Material.CLAY_BALL);

    private static final Material material = Material.IRON_BARS;
    private static final String lore =
            "§6Ability" +"/n"+
                "Dispensers wired with this item can" +"/n"+
                "filter clay from soil using water" + "/n"+
                "Break the dispenser to retrieve";
    private static final boolean shiny = true;

    public static final int cost = 8360;
    public static final char key = '1';

    public Filter(){
        super(name, material, lore, shiny, cost, ""+WatsonCollection.key+key, key);
    }

    public void registerRecipes(){
        ShapedRecipe recipe =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"0"), item);
        recipe.shape("&&&","***","%%%");
        recipe.setIngredient('&', Material.HOPPER);
        recipe.setIngredient('*', Material.COBWEB);
        recipe.setIngredient('%', Material.BASALT);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(BlockDispenseEvent event){
        ItemStack item = event.getItem();
        if(!dirt.contains(item.getType()))
            return;

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
                if(inv.firstEmpty()>=0)
                    inv.addItem(clay);
                else{
                    for(ItemStack content : inv.getContents()){
                        if(content.getType()==Material.CLAY_BALL)
                            if(content.getAmount()<content.getMaxStackSize()){
                                inv.addItem(clay);
                                return;
                            }
                    }
                    place.getLocation().getWorld().
                            dropItemNaturally(place.getLocation(), clay);
                }
            }
        }.runTask(HoloItems.getInstance());
    }
}
