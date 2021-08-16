package com.klin.holoItems.collections.misc.klinCollection.items;

import com.klin.holoItems.abstractClasses.Wiring;
import com.klin.holoItems.collections.misc.klinCollection.KlinCollection;
import com.klin.holoItems.interfaces.Interactable;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashSet;
import java.util.Set;

public class SecondHand extends Wiring implements Interactable{
    public static final String name = "synchronizedDispenser";
    public static final Set<Enchantment> accepted = null;
    private static Set<Block> dispensers = new HashSet<>();

    private static final Material material = Material.STICK;
    private static final String lore =
            "Sync dispensers";
    private static final boolean shiny = false;

    public static final int cost = -1;
    public static final char key = '5';

    public SecondHand(){
        super(name, material, lore, shiny, cost, ""+KlinCollection.key+key, key);
    }

    public void registerRecipes(){}

    public void ability(BlockDispenseEvent event) {
        Block block = event.getBlock();
        ItemStack item = event.getItem();
        Set<Block> states = new HashSet<>();
        for(Block state : dispensers){
            if(state!=null && state.getType()==Material.DISPENSER){
                if(block.equals(state))
                    continue;
                Dispenser dispenser = ((Dispenser) state.getState());
                Inventory inv = dispenser.getInventory();
                inv.addItem(item);
                dispenser.dispense();
                inv.clear();
            }
            else
                states.add(state);
        }
        for(Block state : states)
            dispensers.remove(state);
    }

    public void ability(PlayerInteractEvent event, Action action){
        if(action!=Action.RIGHT_CLICK_BLOCK)
            return;
        Block block = event.getClickedBlock();
        if(block.getType()==Material.DISPENSER && !id.equals(((Dispenser) block.getState()).
                getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING))) {
            if(dispensers.add(block))
                event.getPlayer().sendMessage("Dispenser added");
            else
                event.getPlayer().sendMessage("Dispenser already synced");
        }
    }
}
