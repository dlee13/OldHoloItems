package com.klin.holoItems.collections.en.inaCollection.items;

import com.klin.holoItems.abstractClasses.Wiring;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.collections.en.inaCollection.InaCollection;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Dispenser;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;

public class CeramicLadle  extends Wiring {
    public static final String name = "ceramicLadle";
    private static final Material material = Material.STONE_SHOVEL;
    private static final String lore =
            "§6Ability" +"/n"+
                "Dispensers wired with this item will" +"/n"+
                "plant saplings, flowers, and mushrooms" + "/n"+
                "Break the dispenser to retrieve";
    private static final boolean shiny = true;

    public static final int cost = 16720;
    public static final char key = '2';

    public CeramicLadle(){
        super(name, material, lore, shiny, cost, ""+ InaCollection.key+key, key);
    }

    public void registerRecipes(){}

    public void ability(BlockDispenseEvent event){
        ItemStack item = event.getItem();
        Material sapling = item.getType();
        String type = sapling.toString();
        if(Utility.flowers.contains(sapling) || type.contains("SAPLING"))
            type = "SAPLING";
        else if(type.contains("FUNGUS"))
            type = "FUNGUS";
        else
            return;

        event.setCancelled(true);
        Block block = event.getBlock();
        BlockFace face = ((Dispenser) block.getBlockData()).getFacing();
        Block place = block.getRelative(face);
        Set<Material> dirt = Utility.dirt.get(type);
        if(face==BlockFace.UP &&
                !place.isEmpty() && dirt.contains(place.getType())){
            Block air = place.getRelative(BlockFace.UP);
            if(!air.isEmpty())
                return;
            air.setType(sapling);
        }
        else {
            if (!place.isEmpty() || !dirt.contains(place.getRelative(BlockFace.DOWN).getType()))
                return;
            place.setType(sapling);
        }

        Inventory inv = ((InventoryHolder) block.getState()).getInventory();
        new BukkitRunnable(){
            public void run(){
                inv.removeItem(item);
            }
        }.runTask(HoloItems.getInstance());
    }
}