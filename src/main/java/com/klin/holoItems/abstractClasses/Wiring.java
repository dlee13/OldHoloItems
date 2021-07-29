package com.klin.holoItems.abstractClasses;

import com.klin.holoItems.Collections;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.interfaces.Clickable;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class Wiring extends Crate implements Clickable {
    private static final int quantity = 1;
    private static final int durability = 0;
    private static final boolean stackable = true;

    public Wiring(String name, Material material, String lore, boolean shiny, int cost,
                  String id, char key) {
        super(name, material, quantity, lore, durability, stackable, shiny, cost, id, key);
    }

    public void ability(InventoryClickEvent event, boolean current){
        if(event.getAction()!=InventoryAction.PLACE_ALL)
            return;
        Inventory inv = event.getInventory();
        if(event.getRawSlot()>=inv.getSize() || !(inv.getHolder() instanceof BlockInventoryHolder))
            return;
        Block block = ((BlockInventoryHolder) inv.getHolder()).getBlock();
        if(!(block.getState() instanceof Dispenser))
            return;

        Dispenser dispenser = (Dispenser) block.getState();
        if(!current) {
            ItemStack wiring = event.getCursor();
            if(wiring==null)
                return;
            String check = dispenser.getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
            if(check!=null) {
                Item generic = Collections.findItem(check);
                if(generic!=null)
                    event.getWhoClicked().sendMessage(
                            "This dispenser is already wired with " + Utility.formatName(generic.name));
                else
                    event.getWhoClicked().sendMessage("This dispenser needs replacing");
                return;
            }

            PersistentDataContainer container = dispenser.getPersistentDataContainer();
            container.set(Utility.key, PersistentDataType.STRING, id);
            additional(container, wiring);
            dispenser.update();

            new BukkitRunnable(){
                public void run(){
                    ItemStack item = inv.getItem(event.getRawSlot());
                    if(item!=null)
                        item.setAmount(item.getAmount()-1);
                }
            }.runTask(HoloItems.getInstance());
        }
    }

    public abstract void ability(BlockDispenseEvent event);

    protected void additional(PersistentDataContainer container, ItemStack item){}
}
