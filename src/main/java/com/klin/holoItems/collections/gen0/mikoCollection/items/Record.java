package com.klin.holoItems.collections.gen0.mikoCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.gen0.mikoCollection.MikoCollection;
import com.klin.holoItems.interfaces.Clickable;
import com.klin.holoItems.interfaces.Dispensable;
import com.klin.holoItems.interfaces.Interactable;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class Record extends Item implements Dispensable, Interactable, Clickable {
    public static final String name = "record";
    public static final Set<Enchantment> accepted = null;
    private static Map<String, List<ItemStack>> tracker = new HashMap<>();

    private static final Material material = Material.PAPER;
    private static final int quantity = 1;
    private static final String lore =
            "Dispense once to start, and\n"+
            "again to save the final time";
    private static final int durability = 0;
    private static final boolean stackable = false;
    private static final boolean shiny = true;
    public static final int cost = 0;

    public Record(){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost);
    }

    public void registerRecipes(){
        ShapedRecipe recipe =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("%%%","%*%","%%%");
        recipe.setIngredient('*', Material.CLOCK);
        recipe.setIngredient('%', Material.PAPER);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(BlockDispenseEvent event) {
        ItemStack item = event.getItem();
        ItemMeta itemMeta = item.getItemMeta();
        String itemName = itemMeta.getDisplayName();
        Block block = event.getBlock();
        Dispenser dispenser = (Dispenser) block.getState();
        Location loc = event.getBlock().getLocation();

        if(!(itemName.startsWith("§6") && !itemName.equals("§6Record"))) {
            event.setCancelled(true);
            new BukkitRunnable() {
                public void run() {
                    Inventory inv = dispenser.getInventory();
                    List<ItemStack> records = new ArrayList<>();
                    for (ItemStack content : inv.getStorageContents()) {
                        if (content!=null && content.getItemMeta()!=null && content.getAmount()==1) {
                            ItemMeta meta = content.getItemMeta();
                            if (name.equals(meta.getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING))){
                                String time = meta.getDisplayName();
                                if(!(time.startsWith("§6") && !time.equals("§6Record")))
                                    records.add(content);
                            }
                        }
                    }

                    new Task(HoloItems.getInstance(), 1, 1) {
                        int increment = 0;

                        public void run() {
                            if (!loc.getChunk().isLoaded() || loc.getBlock().getType() != Material.DISPENSER || increment >= 2400 || records.isEmpty()){
                                if(records.isEmpty())
                                    tracker.remove(""+getTaskId());
                                cancel();
                                return;
                            }
                            if(increment==0){
                                tracker.put(""+getTaskId(), records);
                                for (ItemStack content : records) {
                                    ItemMeta meta = content.getItemMeta();
                                    meta.setLore(Collections.singletonList("§7" + getTaskId()));
                                    meta.getPersistentDataContainer().set(Utility.stack, PersistentDataType.DOUBLE, Math.random());
                                    content.setItemMeta(meta);
                                    increment++;
                                }
                            }
                            increment++;

                            String best = null;
                            for (ItemStack content : records) {
                                ItemMeta meta = content.getItemMeta();
                                if (best == null) {
                                    double seconds = ((double) increment) / 20 + 0.001;
                                    int minutes = (int) seconds / 60;
                                    seconds = seconds - minutes * 60;
                                    best = ("§60" + minutes + ":" + (seconds<10?"0":"") + seconds).substring(0, 10);
                                }
                                meta.setDisplayName(best);
                                content.setItemMeta(meta);
                            }
                        }
                    };
                }
            }.runTask(HoloItems.getInstance());
            return;
        }

        String key = itemMeta.getLore().get(0).substring(2);
        List<ItemStack> records = tracker.get(key);
        if(records!=null) {
            new BukkitRunnable(){
                public void run(){
                    records.remove(item);
                    if(records.isEmpty())
                        tracker.remove(key);
                }
            }.runTask(HoloItems.getInstance());
        }
        BlockState state = block.getRelative(((org.bukkit.block.data.type.Dispenser) block.getBlockData()).getFacing()).getState();
        if(state instanceof Container){
            ItemStack clone = item.clone();
            if(!((Container) state).getInventory().addItem(clone).isEmpty())
                loc.getWorld().dropItemNaturally(loc, clone);
            event.setCancelled(true);
            new BukkitRunnable(){
                public void run(){
                    dispenser.getInventory().removeItem(clone);
                }
            }.runTask(HoloItems.getInstance());
        }
    }

    public void ability(PlayerInteractEvent event, Action action){
        if(action!=Action.RIGHT_CLICK_BLOCK)
            return;
        BlockState state = event.getClickedBlock().getState();
        if(!(state instanceof Sign))
            return;
        ItemStack item = event.getItem();
        ItemMeta meta = item.getItemMeta();
        String time = meta.getDisplayName();
        if(!time.startsWith("§6") || time.equals("§6Record"))
            return;
        Sign sign = (Sign) state;
        if(sign.getLine(0).equals(meta.getLore().get(0).substring(2))){
            item.setAmount(item.getAmount()-1);
            sign.setLine(0, "");
            sign.setLine(1, event.getPlayer().getName());
            sign.setLine(2, time);
            sign.update();
        }
    }

    public void ability(InventoryClickEvent event, boolean current){
        if(!current || event.getInventory().getType()!=InventoryType.DISPENSER)
            return;
        ItemStack item = event.getCurrentItem();
        ItemMeta meta = item.getItemMeta();
        String time = meta.getDisplayName();
        if(time.startsWith("§6") && !time.equals("§6Record")) {
//            String key = meta.getLore().get(0).substring(2);
//            List<ItemStack> records = tracker.get(key);
//            if(records!=null) {
//                new BukkitRunnable(){
//                    public void run(){
//                        records.remove(item);
//                        if(records.isEmpty())
//                            tracker.remove(key);
//                    }
//                }.runTask(HoloItems.getInstance());
//            }
            event.setCancelled(true);
        }
    }
}