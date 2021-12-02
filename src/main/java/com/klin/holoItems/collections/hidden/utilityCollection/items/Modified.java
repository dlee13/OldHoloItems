package com.klin.holoItems.collections.hidden.utilityCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.misc.achanCollection.items.DebugStick;
import com.klin.holoItems.interfaces.Placeable;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.block.data.Openable;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public class Modified extends Item implements Placeable {
    public static final String name = "modified";

    private static final Material material = Material.STICK;
    private static final int quantity = 1;
    private static final String lore =
            "Modified placement";
    private static final int durability = 0;
    public static final boolean stackable = true;
    private static final boolean shiny = false;
    public static final int cost = -1;

    public Modified(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost);
    }

    public void registerRecipes(){}

    public void ability(BlockPlaceEvent event){
        int[] modifier = event.getItemInHand().getItemMeta().getPersistentDataContainer().get(Utility.pack, PersistentDataType.INTEGER_ARRAY);
        if(modifier==null)
            return;
        Block block = event.getBlockPlaced();
        BlockData data = block.getBlockData();
        Material type = data.getMaterial();
        if(type==Material.IRON_TRAPDOOR)
            ((Openable) data).setOpen(true);
        else if(Utility.fences.contains(type)){
            MultipleFacing multipleFacing = (MultipleFacing) data;
            for(int i=0; i<4; i++)
                multipleFacing.setFace(DebugStick.connections.get(i), modifier[i]==1);
        }
        else return;
        event.setCancelled(false);
        new BukkitRunnable(){
            public void run(){
                block.setBlockData(data);
            }
        }.runTask(HoloItems.getInstance());
    }
}
