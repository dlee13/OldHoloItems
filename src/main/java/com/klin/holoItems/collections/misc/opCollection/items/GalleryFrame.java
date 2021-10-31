package com.klin.holoItems.collections.misc.opCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.gen1.fubukiCollection.items.VerificationSeal;
import com.klin.holoItems.interfaces.Hangable;
import com.klin.holoItems.interfaces.Reactable;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Hanging;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class GalleryFrame extends Item implements Hangable, Reactable {
    public static final String name = "galleryFrame";
    private static final Map<Player, Hanging> reference = new HashMap<>();
    private final ItemStack buy;

    private static final Material material = Material.GLOW_ITEM_FRAME;
    private static final int quantity = 1;
    private static final String lore =
            "Display map art";
    private static final int durability = 0;
    private static final boolean stackable = true;
    private static final boolean shiny = true;
    public static final int cost = -1;

    public GalleryFrame(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost);
        buy = new ItemStack(Material.MAP);
        ItemMeta meta = buy.getItemMeta();
        meta.setDisplayName("§fBuy");
        buy.setItemMeta(meta);
    }

    public void registerRecipes() {}

    public void ability(HangingPlaceEvent event){
        new BukkitRunnable() {
            public void run() {
                event.getEntity().getPersistentDataContainer().set(Utility.key, PersistentDataType.STRING, name);
            }
        }.runTask(HoloItems.getInstance());
    }

    public void ability(PlayerInteractEntityEvent event){
        if(!(event.getRightClicked() instanceof ItemFrame))
            return;
        Player player = event.getPlayer();
        if(!player.hasPermission("holoItems.op"))
            event.setCancelled(true);
        ItemFrame itemFrame = (ItemFrame) event.getRightClicked();
        ItemStack framedItem = itemFrame.getItem();
        if(framedItem.getType()==Material.AIR) {
            ItemStack item;
            PlayerInventory playerInv = player.getInventory();
            if (event.getHand() == EquipmentSlot.HAND)
                item = playerInv.getItemInMainHand();
            else
                item = playerInv.getItemInOffHand();
            if (item.getType() != Material.FILLED_MAP) {
                if(item.getType()!=Material.AIR)
                    player.sendMessage("This gallery only accepts map art");
                return;
            }
            item = item.clone();
            item.setAmount(1);
            playerInv.removeItem(item);
            ItemMeta meta = item.getItemMeta();
            if(!meta.getDisplayName().isEmpty()) {
                meta.setDisplayName("");
                item.setItemMeta(meta);
            }
            itemFrame.setItem(item);
        } else if(framedItem.getType()==Material.FILLED_MAP){
            if(Utility.findItem(framedItem, VerificationSeal.class)!=null){
                player.sendMessage("Not for sale");
                return;
            }
            Inventory inv = Bukkit.createInventory(null, 9, "Price");
            inv.setItem(4, buy);
            new BukkitRunnable() {
                public void run() {
                    player.openInventory(inv);
                }
            }.runTask(HoloItems.getInstance());
        }
        reference.put(player, itemFrame);
    }

    public void ability(InventoryClickEvent event){
        event.setCancelled(true);
        InventoryView view = event.getView();
        Player player = (Player) event.getWhoClicked();
        if(!reference.containsKey(player)) {
            new BukkitRunnable(){
                public void run(){
                    view.close();
                }
            }.runTask(HoloItems.getInstance());
            return;
        }
        ItemFrame itemFrame = (ItemFrame) reference.get(player);
        ItemStack curr = event.getCurrentItem();
        if(!buy.equals(curr))
            return;
        Inventory inv = player.getInventory();
        ItemStack pay = new ItemStack(Material.MAP);
        if(!inv.containsAtLeast(pay, 1)) {
            reference.remove(player);
            new BukkitRunnable() {
                public void run() {
                    view.close();
                }
            }.runTask(HoloItems.getInstance());
            player.sendMessage("Please come back when you can afford to");
            return;
        }
        inv.removeItem(pay);
        inv.addItem(itemFrame.getItem());
        player.sendMessage("Thank you for your purchase");
        reference.remove(player);
        new BukkitRunnable(){
            public void run(){
                view.close();
            }
        }.runTask(HoloItems.getInstance());
    }

    public void ability(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();
        if(!reference.containsKey(player))
            return;
        reference.remove(player);
    }
}