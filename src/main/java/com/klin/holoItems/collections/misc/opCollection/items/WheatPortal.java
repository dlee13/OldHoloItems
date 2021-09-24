package com.klin.holoItems.collections.misc.opCollection.items;

import com.klin.holoItems.interfaces.Dispensable;
import com.klin.holoItems.interfaces.Placeable;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.misc.opCollection.OpCollection;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Dispenser;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

public class WheatPortal extends Item implements Dispensable, Placeable {
    public static final String name = "wheatPortal";
    public static final HashSet<Enchantment> accepted = null;

    private static final Map<Material, Material> ageable = new LinkedHashMap<Material, Material>(){{
        put(Material.BEETROOT_SEEDS, Material.BEETROOTS);
        put(Material.CARROT, Material.CARROTS);
        put(Material.POTATO, Material.POTATOES);
        put(Material.NETHER_WART, Material.NETHER_WART);
        put(Material.WHEAT_SEEDS, Material.WHEAT);
    }};

    private static final Material material = Material.WHEAT_SEEDS;
    private static final int quantity = 1;
    private static final String lore =
            "Dispense to create a physical crop\n"+
            "Can also be placed infinitely";
    private static final int durability = 0;
    public static final boolean stackable = true;
    private static final boolean shiny = false;
    public static final int cost = -1;

    public WheatPortal(){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost);
    }

    public void registerRecipes(){
        for(Material type : ageable.keySet()) {
            item.setType(type);
            String key = "";
            String[] strings = ageable.get(type).toString().toLowerCase().split("_");
            for(String str : strings)
                key += str.substring(0, 1).toUpperCase()+str.substring(1)+" ";
            key += "Portal";
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§6"+key);
            item.setItemMeta(meta);
            key = key.substring(0, 1).toLowerCase()+
                    key.substring(1).replace(" ", "");

            ShapelessRecipe recipe = new ShapelessRecipe(
                    new NamespacedKey(HoloItems.getInstance(), key), item);
            recipe.addIngredient(Material.NETHERITE_BLOCK);
            recipe.addIngredient(type);
            recipe.setGroup(name);
            Bukkit.getServer().addRecipe(recipe);
        }
    }

    public void ability(BlockDispenseEvent event){
        event.setCancelled(true);

        Block block = event.getBlock();
        BlockFace face = ((Dispenser) block.getBlockData()).getFacing();
        Block place = block.getRelative(face);
        Material crop = ageable.get(event.getItem().getType());
        Material soil;
        if(crop==Material.NETHER_WART)
            soil = Material.SOUL_SAND;
        else
            soil = Material.FARMLAND;
        plant(face, place, crop, soil);
    }

    private static void plant(BlockFace face, Block place, Material crop, Material soil){
        if(!place.getType().isAir() || place.getRelative(BlockFace.DOWN).getType()!=soil)
            return;
        place.setType(crop);
        plant(face, place.getRelative(face), crop, soil);
    }

    public void ability(BlockPlaceEvent event){
        Block block = event.getBlockPlaced();
        Material type = block.getType();
        new BukkitRunnable() {
            @Override
            public void run() {
                block.setType(type);
            }
        }.runTask(HoloItems.getInstance());
    }
}
