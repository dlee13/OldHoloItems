package com.klin.holoItems.collections.en.watsonCollection.items;

import com.klin.holoItems.abstractClasses.BatteryPack;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.collections.en.watsonCollection.WatsonCollection;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;

public class GemKnife extends BatteryPack {
    public static final String name = "gemKnife";

    private static final Map<Material, Material> ores = new HashMap<>() {{
        put(Material.COAL_ORE, Material.COAL);
        put(Material.IRON_ORE, Material.IRON_NUGGET);
        put(Material.GOLD_ORE, Material.GOLD_NUGGET);
        put(Material.NETHER_GOLD_ORE, Material.GOLD_NUGGET);
        put(Material.GILDED_BLACKSTONE, Material.GOLD_NUGGET);
        put(Material.REDSTONE_ORE, Material.REDSTONE);
        put(Material.LAPIS_ORE, Material.LAPIS_LAZULI);
        put(Material.NETHER_QUARTZ_ORE, Material.QUARTZ);
    }};

    private static final Material material = Material.EMERALD;
    private static final String lore =
            "Right click to consume a charge an\n"+
            "break a piece off any ore softer than\n"+
            "an emerald";
    private static final int durability = 0;
    private static final boolean shiny = true;

    public static final Material content = Material.EMERALD;
    public static final double perFuel = 2;
    public static final int cap = 1152;

    public static final int cost = 24000;
    public static final char key = '2';

    public GemKnife(){
        super(name, material, lore, durability, shiny, cost, content, perFuel, cap,
                ""+WatsonCollection.key+key, key);
    }

    public void registerRecipes(){
        ShapedRecipe recipe =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("a","b");
        recipe.setIngredient('a', Material.EMERALD_BLOCK);
        recipe.setIngredient('b', Material.STICK);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void effect(PlayerInteractEvent event){
        event.setCancelled(true);
        if(event.getClickedBlock()==null)
            return;
        Block ore = event.getClickedBlock();
        Material type = ores.get(ore.getType());
        if(type==null)
            return;
        ItemStack item = event.getItem();
        int charge = Utility.deplete(item);
        if(charge==-1)
            return;

        if(charge>=63){
            ItemMeta meta = item.getItemMeta();
            meta.getPersistentDataContainer().set(Utility.pack, PersistentDataType.INTEGER, charge-63);
            item.setItemMeta(meta);
            ore.getWorld().dropItemNaturally(ore.getLocation(), new ItemStack(type, 64));
            return;
        }
        ore.getWorld().dropItemNaturally(ore.getLocation(), new ItemStack(type));
        if(charge==192 || charge==64 || charge==0)
            event.getPlayer().sendMessage("§7" + charge + " remaining");
    }
}

