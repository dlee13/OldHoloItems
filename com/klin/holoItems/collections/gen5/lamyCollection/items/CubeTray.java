package com.klin.holoItems.collections.gen5.lamyCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.abstractClasses.Wiring;
import com.klin.holoItems.collections.gen5.lamyCollection.LamyCollection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Dispenser;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.scheduler.BukkitRunnable;

public class CubeTray extends Wiring {
    public static final String name = "cubeTray";

    private static final ItemStack obsidian = new ItemStack(Material.OBSIDIAN, 2);

    private static final Material material = Material.ICE;
    private static final String lore =
            "§6Ability" +"/n"+
                "Freeze lava into obsidian using packed" +"/n"+
                "ice and shatter it, into halves";
    private static final boolean shiny = true;

    public static final int cost = -1;
    public static final char key = '0';

    public CubeTray(){
        super(name, material, lore, shiny, cost, ""+ LamyCollection.key+key, key);
    }

    public void registerRecipes(){
        ShapedRecipe recipe0 =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"0"), item);
        recipe0.shape("***","   ","   ");
        recipe0.setIngredient('*', Material.MINECART);
        recipe0.setGroup(name);
        Bukkit.getServer().addRecipe(recipe0);

        ShapedRecipe recipe1 =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"1"), item);
        recipe1.shape("   ","***","   ");
        recipe1.setIngredient('*', Material.MINECART);
        recipe1.setGroup(name);
        Bukkit.getServer().addRecipe(recipe1);

        ShapedRecipe recipe2 =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"2"), item);
        recipe2.shape("   ","   ","***");
        recipe2.setIngredient('*', Material.MINECART);
        recipe2.setGroup(name);
        Bukkit.getServer().addRecipe(recipe2);
    }

    public void ability(BlockDispenseEvent event){
        ItemStack item = event.getItem();
        if(item.getType()!=Material.PACKED_ICE)
            return;

        event.setCancelled(true);
        Block block = event.getBlock();
        BlockFace face = ((Dispenser) block.getBlockData()).getFacing();
        Block place = block.getRelative(face);
        if(place.getType()!=Material.LAVA)
            return;

        place.setType(Material.AIR);
        Location loc = place.getLocation();
        loc.getWorld().dropItemNaturally(loc, obsidian);
        new BukkitRunnable(){
            public void run(){
                ((InventoryHolder) block.getState()).getInventory().removeItem(item);
            }
        }.runTask(HoloItems.getInstance());
    }
}
