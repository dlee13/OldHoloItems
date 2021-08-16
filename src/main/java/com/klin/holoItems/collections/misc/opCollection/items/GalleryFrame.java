package com.klin.holoItems.collections.misc.opCollection.items;

import com.klin.holoItems.interfaces.Hangable;
import com.klin.holoItems.interfaces.Reactable;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.misc.opCollection.OpCollection;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.enchantments.Enchantment;
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
import java.util.Set;

public class GalleryFrame extends Item implements Hangable, Reactable {
    public static final String name = "galleryFrame";

    private static Map<Player, Hanging> reference = new HashMap<>();
    private static final ItemStack buy = new ItemStack(Material.SPRUCE_TRAPDOOR);
    private static final ItemStack pay = new ItemStack(Material.MAP);

    public static final Set<Enchantment> accepted = null;

    private static final Material material = Material.ITEM_FRAME;
    private static final int quantity = 1;
    private static final String lore =
            "Display map art";
    private static final int durability = 0;
    private static final boolean stackable = true;
    private static final boolean shiny = true;

    public static final int cost = -1;
    public static final char key = '2';

    public GalleryFrame(){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost,
                "" + OpCollection.key + key, key);
        ItemMeta meta = buy.getItemMeta();
        meta.setDisplayName("§6Buy");
        buy.setItemMeta(meta);
    }

    public void registerRecipes() {}

    public void ability(HangingPlaceEvent event){
        new BukkitRunnable() {
            public void run() {
                event.getEntity().getPersistentDataContainer().set(Utility.key, PersistentDataType.STRING, id);
            }
        }.runTask(HoloItems.getInstance());
    }

    public void ability(PlayerInteractEntityEvent event){
        if(!(event.getRightClicked() instanceof ItemFrame))
            return;

        ItemFrame itemFrame = (ItemFrame) event.getRightClicked();
        ItemStack framedItem = itemFrame.getItem();
        Player player = event.getPlayer();
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
            BlockFace[] surrounding = new BlockFace[]{BlockFace.UP, BlockFace.DOWN, null, null};
            switch(itemFrame.getFacing()){
                case NORTH:
                case SOUTH:
                    surrounding[2] = BlockFace.EAST;
                    surrounding[3] = BlockFace.WEST;
                    break;
                case EAST:
                case WEST:
                    surrounding[2] = BlockFace.NORTH;
                    surrounding[3] = BlockFace.SOUTH;
                    break;
                default:
                    break;
            }
            if(surrounding[2]!=null){
                Block frame = itemFrame.getLocation().getBlock();
                for(BlockFace face : surrounding){
                    Block block = frame.getRelative(face);
                    if(!(block.getType().toString().contains("WALL_SIGN")))
                        continue;

                    Sign sign = (Sign) block.getState();
                    if(!meta.getDisplayName().isEmpty()) {
                        String line = "§6" + meta.getDisplayName();
                        line = line.substring(0, Math.min(15, line.length()));
                        sign.setLine(1, line);
                    }
                    else
                        sign.setLine(1, "by");
                    sign.setLine(2, player.getName());
                    sign.update();
                }
            }

            if(!meta.getDisplayName().isEmpty()) {
                meta.setDisplayName("");
                item.setItemMeta(meta);
            }
            itemFrame.setItem(item);
        }
        else if(framedItem.getType()==Material.FILLED_MAP){
            Inventory inv = Bukkit.createInventory(null, 9, "Price");
            inv.setItem(0, pay);
            inv.setItem(inv.getSize()-1, buy);

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
        if(!inv.containsAtLeast(pay, 1)) {
            reference.remove(player);
            new BukkitRunnable() {
                public void run() {
                    view.close();
                }
            }.runTask(HoloItems.getInstance());

            player.sendMessage("Please come back when you can afford this");
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

//        player.sendMessage("§7Please come again");
        reference.remove(player);
    }
}