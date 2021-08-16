package com.klin.holoItems.collections.misc.archiveCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.misc.opCollection.OpCollection;
import com.klin.holoItems.interfaces.Hangable;
import com.klin.holoItems.interfaces.Reactable;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.ShulkerBox;
import org.bukkit.block.Sign;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class MoneyLaundering extends Item implements Hangable, Reactable {
    public static final String name = "moneyLaundering";

    private static Map<Player, Location> reference = new HashMap<>();

    public static final Set<Enchantment> accepted = null;

    private static final Material material = Material.ITEM_FRAME;
    private static final int quantity = 1;
    private static final String lore =
            "§6Ability" +"/n"+
                "Evade taxes";
    private static final int durability = 0;
    private static final boolean stackable = true;
    private static final boolean shiny = true;

    public static final int cost = -1;
    public static final char key = '2';

    private static final ItemStack buy = new ItemStack(Material.SUNFLOWER);

    public MoneyLaundering(){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost,
                ""+ OpCollection.key + key, key);
        ItemMeta meta = buy.getItemMeta();
        meta.setDisplayName("§6Buy");
        buy.setItemMeta(meta);
    }

    public void registerRecipes() {}

    public void ability(HangingPlaceEvent event){
        new BukkitRunnable() {
            public void run() {
                Location loc = event.getBlock().getLocation().add(0.5, 0.5, 0.5);
                ItemFrame itemFrame = (ItemFrame) (loc.getWorld().getNearbyEntities(
                        loc, 1, 1, 1, entity -> (entity instanceof ItemFrame))).iterator().next();
                itemFrame.getPersistentDataContainer().set(Utility.key, PersistentDataType.STRING, id);
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
            boolean empty = true;
            for(ItemStack content : playerInv.getStorageContents()){
                if(content!=null) {
                    empty = false;
                    break;
                }
            }
            if (empty) {
                player.sendMessage("You need items to be able to set a price");
                return;
            }
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
            }
            if(surrounding[2]!=null){
                Block frame = itemFrame.getLocation().getBlock();
                for(BlockFace face : surrounding){
                    Block block = frame.getRelative(face);
                    if(!(block.getType().toString().contains("WALL_SIGN")))
                        continue;

                    Sign sign = (Sign) block.getState();
                    if(!meta.getDisplayName().isEmpty())
                        sign.setLine(1, "§6"+meta.getDisplayName());
                    sign.setLine(2, "by "+player.getName());
                    sign.update();
                }
            }

            meta.setDisplayName("");
            meta.setLore(Arrays.asList(player.getUniqueId().toString()));
            item.setItemMeta(meta);
            itemFrame.setItem(item);

            new BukkitRunnable() {
                public void run() {
                    player.openInventory(Bukkit.createInventory(null, 9, "Pricing. . ."));
                }
            }.runTask(HoloItems.getInstance());
        }
        else if(framedItem.getType()==Material.FILLED_MAP){
            String prices = itemFrame.getPersistentDataContainer().get(Utility.pack, PersistentDataType.STRING);
            if(prices==null)
                return;
            String[] price = prices.split(" ");
            Inventory inv = Bukkit.createInventory(null, (price.length/18+1)*9, "Price");
            if(!prices.isEmpty()) {
                for (int i = 0; i < price.length; i += 2) {
                    Material type = Material.getMaterial(price[i + 1]);
                    if (type == null)
                        continue;
                    try {
                        inv.setItem(i / 2, new ItemStack(type, Integer.parseInt(price[i])));
                    } catch (NumberFormatException e) {
                    }
                }
            }
            inv.setItem(inv.getSize()-1, buy);

            new BukkitRunnable() {
                public void run() {
                    player.openInventory(inv);
                }
            }.runTask(HoloItems.getInstance());
        }
        else{
            if(framedItem.getItemMeta()!=null && framedItem.getItemMeta().getLore()!=null){
                String owner = framedItem.getItemMeta().getLore().get(0);
                if(player.equals(Bukkit.getServer().getPlayer(UUID.fromString(owner)))){
                    String prices = itemFrame.getPersistentDataContainer().get(Utility.pack, PersistentDataType.STRING);
                    if(prices!=null) {
                        String[] price = prices.split(" ");
                        PlayerInventory inv = player.getInventory();
                        for (int i = 0; i < price.length; i += 2) {
                            Material type = Material.getMaterial(price[i + 1]);
                            if (type == null)
                                continue;
                            try {
                                inv.addItem(new ItemStack(type, Integer.parseInt(price[i])));
                            } catch (NumberFormatException e) {}
                        }
                    }
                    itemFrame.setItem(null);
                    itemFrame.getPersistentDataContainer().remove(Utility.pack);

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
                    }
                    if(surrounding[2]!=null){
                        Block frame = itemFrame.getLocation().getBlock();
                        for(BlockFace face : surrounding){
                            Block block = frame.getRelative(face);
                            if(!(block.getType().toString().contains("WALL_SIGN")))
                                continue;

                            Sign sign = (Sign) block.getState();
                            sign.setLine(1, "");
                            sign.setLine(2, "");
                            sign.update();
                        }
                    }

                    player.sendMessage("Returns claimed");
                }
            }
            else
                player.sendMessage("§7This item frame is off limits");
            return;
        }
        reference.put(player, itemFrame.getLocation());
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
        Location loc = reference.get(player);
        ItemFrame itemFrame = (ItemFrame) (loc.getWorld().getNearbyEntities(
                loc, 1, 1, 1, entity -> (entity instanceof ItemFrame))).iterator().next();

        ItemStack curr = event.getCurrentItem();
        switch (view.getTitle()){
            case "Pricing. . .":
                String price = "";
                if(curr!=null) {
                    if (curr.getType().toString().contains("SHULKER_BOX")) {
                        ShulkerBox shulker = (ShulkerBox) ((BlockStateMeta) curr.getItemMeta()).getBlockState();
                        for (ItemStack content : shulker.getInventory().getContents()) {
                            if (content == null)
                                continue;
                            price += content.getAmount() + " " + content.getType() + " ";
                        }
                        if (!price.isEmpty())
                            price = price.substring(0, price.length() - 1);
                        else
                            price += curr.getAmount() + " " + curr.getType();
                    } else
                        price += curr.getAmount() + " " + curr.getType();
                }
                else if(event.getRawSlot()<0)
                    return;

                if(price.isEmpty()){
                    ItemStack item = itemFrame.getItem();
                    ItemMeta meta = item.getItemMeta();
                    meta.setLore(null);
                    item.setItemMeta(meta);
                    itemFrame.setItem(item);
                }

                itemFrame.getPersistentDataContainer().set(Utility.pack, PersistentDataType.STRING, price);
                player.sendMessage("§7Price set to: " + price.toLowerCase().replace("_", " "));
                break;

            case "Price":
                if(curr==null)
                    return;
                if(!curr.equals(buy))
                    break;

                Map<Material, Integer> totalCost = new HashMap<>();
                for(ItemStack item : view.getTopInventory().getStorageContents()){
                    if(item==null || item.equals(buy))
                        continue;
                    Material type = item.getType();
                    if(totalCost.containsKey(type))
                        totalCost.put(type, totalCost.get(type)+item.getAmount());
                    else
                        totalCost.put(type, item.getAmount());
                }
                Inventory inv = player.getInventory();
                Set<ItemStack> cost = new HashSet<>();
                for(Material type : totalCost.keySet()){
                    ItemStack item = new ItemStack(type, totalCost.get(type));
                    if(!inv.containsAtLeast(item, totalCost.get(type))){
                        player.sendMessage("Please come back when you can afford this");

                        reference.remove(player);
                        new BukkitRunnable(){
                            public void run(){
                                view.close();
                            }
                        }.runTask(HoloItems.getInstance());
                        return;
                    }
                    cost.add(item);
                }
                for(ItemStack item : cost)
                    inv.removeItem(item);

                ItemStack item = itemFrame.getItem();
                ItemMeta meta = item.getItemMeta();

                ItemStack claim = null;
                if(meta.getLore()!=null) {
                    claim = new ItemStack(Material.SUNFLOWER);
                    ItemMeta claimMeta = claim.getItemMeta();
                    claimMeta.setLore(meta.getLore());
                    claim.setItemMeta(claimMeta);
                }
                else{
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
                    }
                    if(surrounding[2]!=null){
                        Block frame = itemFrame.getLocation().getBlock();
                        for(BlockFace face : surrounding){
                            Block block = frame.getRelative(face);
                            if(!(block.getType().toString().contains("WALL_SIGN")))
                                continue;

                            Sign sign = (Sign) block.getState();
                            sign.setLine(1, "");
                            sign.setLine(2, "");
                            sign.update();
                        }
                    }
                }
                itemFrame.setItem(claim);

                meta.setLore(null);
                meta.getPersistentDataContainer().set(Utility.key, PersistentDataType.STRING, id);
                item.setItemMeta(meta);
                inv.addItem(item);

                player.sendMessage("Thank you for your purchase");
        }

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

        switch (event.getView().getTitle()) {
            case "Pricing. . .":
                Location loc = reference.get(player);
                ItemFrame itemFrame = (ItemFrame) (loc.getWorld().getNearbyEntities(
                        loc, 1, 1, 1, entity -> (entity instanceof ItemFrame))).iterator().next();
                player.getInventory().addItem(itemFrame.getItem());
                itemFrame.setItem(null);

            case "Price":
                player.sendMessage("§7Please come again");
                reference.remove(player);
        }
    }
}