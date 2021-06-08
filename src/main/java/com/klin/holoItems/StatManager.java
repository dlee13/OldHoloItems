package com.klin.holoItems;

import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class StatManager implements Listener {
    private static final Map<Material, Material> ageable = new HashMap<Material, Material>(){{
        put(Material.WHEAT, Material.WHEAT);
        put(Material.BEETROOTS, Material.BEETROOT);
        put(Material.CARROTS, Material.CARROT);
        put(Material.POTATOES, Material.POTATO);
        put(Material.BAMBOO, Material.BAMBOO);
        put(Material.COCOA, Material.COCOA_BEANS);
        put(Material.SWEET_BERRY_BUSH, Material.SWEET_BERRIES);
        put(Material.NETHER_WART, Material.NETHER_WART);
    }};

    @EventHandler
    public static void increment(BlockBreakEvent event){
        if(event.isCancelled())
            return;
        Material type = event.getBlock().getType();
        if(!ageable.containsKey(type))
            return;

        Ageable crop = (Ageable) event.getBlock().getBlockData();
        if(crop.getAge()==crop.getMaximumAge()){
            Player player = event.getPlayer();
            type = ageable.get(type);
            player.setStatistic(Statistic.BREAK_ITEM, type,
                    player.getStatistic(Statistic.BREAK_ITEM, type)+1);
        }
    }

    @EventHandler
    public static void incrementBerries(PlayerInteractEvent event){
        if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK) ||
                event.useInteractedBlock().equals(Event.Result.DENY))
            return;
        Block block = event.getClickedBlock();
        if(block==null)
            return;
        Material type = block.getType();
        if(!type.equals(Material.SWEET_BERRY_BUSH))
            return;

        Ageable crop = (Ageable) block.getBlockData();
        if(crop.getAge()==crop.getMaximumAge()){
            Player player = event.getPlayer();
            type = ageable.get(type);
            player.setStatistic(Statistic.BREAK_ITEM, type,
                    player.getStatistic(Statistic.BREAK_ITEM, type)+1);
        }
    }

    @EventHandler
    public static void creditPotion(BrewEvent event){
        if(event.isCancelled())
            return;
        BrewerInventory inv = event.getContents();
        ItemStack ingredient = inv.getIngredient();
        if (ingredient == null || ingredient.getType().equals(Material.NETHER_WART))
            return;
        String type = "§7"+ingredient.getType().toString().replace("_", " ").toLowerCase();

        new BukkitRunnable() {
            @Override
            public void run(){
                for(int i=0; i<3; i++) {
                    ItemStack potion = inv.getItem(i);
                    if(potion==null)
                        continue;
                    ItemMeta meta = potion.getItemMeta();
                    List<String> lore = Arrays.asList(type);
                    meta.setLore(lore);
                    potion.setItemMeta(meta);
                }
            }
        }.runTaskLater(HoloItems.getInstance(), 1);
    }

    @EventHandler
    public static void incrementIngredients(InventoryClickEvent event){
        if(event.isCancelled())
            return;
        ItemStack potion = event.getCurrentItem();
        if(potion==null || !potion.getType().toString().contains("POTION"))
            return;
        ItemMeta meta = potion.getItemMeta();
        if(!meta.hasLore())
            return;

        Player player = (Player) event.getWhoClicked();
        for(String ingredient : meta.getLore()){
            if(ingredient.isEmpty())
                continue;
            Material type = Material.getMaterial(ingredient.substring(2).toUpperCase().replace(" ", "_"));
            if(type==null)
                continue;
            player.setStatistic(Statistic.BREAK_ITEM, type,
                    player.getStatistic(Statistic.BREAK_ITEM, type)+1);
        }
        meta.setLore(new ArrayList<>());
        potion.setItemMeta(meta);
    }
}
