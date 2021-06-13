package com.klin.holoItems.collections.gen0.mikoCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.gen0.mikoCollection.MikoCollection;
import com.klin.holoItems.collections.misc.klinCollection.KlinCollection;
import com.klin.holoItems.interfaces.Dispensable;
import com.klin.holoItems.interfaces.Interactable;
import com.klin.holoItems.utility.Task;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Record extends Item implements Dispensable, Interactable {
    public static final String name = "record";
    public static final Set<Enchantment> accepted = null;

    private static final Material material = Material.PAPER;
    private static final int quantity = 1;
    private static final String lore =
            "§6Ability" +"/n"+
                "Dispense once to start, and"+"/n"+
                "again to save the final time";
    private static final int durability = -1;
    private static final boolean stackable = false;
    private static final boolean shiny = true;

    public static final int cost = 0;
    public static final char key = '0';

    public Record(){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost,
                ""+MikoCollection.key+key, key);
    }

    public void registerRecipes(){}

    public void ability(BlockDispenseEvent event){
        event.setCancelled(true);

        ItemStack check = event.getItem();
        Dispenser dispenser = (Dispenser) event.getBlock().getBlockData();
        for(ItemStack content : dispenser.getInventory().getStorageContents()){
            if(content.equals(check))
                check = content;
        }

        ItemMeta meta = item.getItemMeta();
        String time = meta.getDisplayName();

        Location loc = event.getBlock().getLocation();
        new Task(HoloItems.getInstance(), 0, 1){
            int increment = 0;

            public void run(){
                double seconds = Integer.parseInt(time.substring(2))/20;
                int minutes = (int) seconds/60;
                meta.setDisplayName(("§60"+minutes+":"+seconds).substring(0, 11));
                item.setItemMeta(meta);
                increment++;
            }
        };
    }

    public void ability(PlayerInteractEvent event, Action action){
        if(action!=Action.RIGHT_CLICK_BLOCK)
            return;
        BlockState state = event.getClickedBlock().getState();
        if(!(state instanceof Sign))
            return;
        String time = event.getItem().getItemMeta().getDisplayName();
        if(!time.startsWith("§6") || time.equals("§6Record"))
            return;

        Sign sign = (Sign) state;
        boolean empty = true;
        for(String line : sign.getLines()){
            if(!line.isEmpty()) {
                empty = false;
                break;
            }
        }
        if(empty){
            sign.setLine(1, event.getPlayer().getName());
            sign.setLine(2, time);
        }
    }
}