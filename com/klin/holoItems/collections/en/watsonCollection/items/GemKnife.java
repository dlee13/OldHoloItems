package com.klin.holoItems.collections.en.watsonCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.abstractClasses.BatteryPack;
import com.klin.holoItems.collections.en.watsonCollection.WatsonCollection;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.HashMap;
import java.util.Map;

public class GemKnife extends BatteryPack {
    public static final String name = "gemKnife";

    private static final Map<Material, Material> ores = new HashMap<Material, Material>(){{
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
            "§6Ability" +"/n"+
                "Right click to consume a charge and" +"/n"+
                "break a piece off any ore softer than" +"/n"+
                "an emerald";
    private static final int durability = 0;
    private static final boolean shiny = false;

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
        ShapedRecipe recipe0 =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"0"), item);
        recipe0.shape("*  ","%  ","   ");
        recipe0.setIngredient('*', Material.EMERALD_BLOCK);
        recipe0.setIngredient('%', Material.STICK);
        recipe0.setGroup(name);
        Bukkit.getServer().addRecipe(recipe0);

        ShapedRecipe recipe1 =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"1"), item);
        recipe1.shape(" * "," % ","   ");
        recipe1.setIngredient('*', Material.EMERALD_BLOCK);
        recipe1.setIngredient('%', Material.STICK);
        recipe1.setGroup(name);
        Bukkit.getServer().addRecipe(recipe1);

        ShapedRecipe recipe2 =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"2"), item);
        recipe2.shape("  *","  %","   ");
        recipe2.setIngredient('*', Material.EMERALD_BLOCK);
        recipe2.setIngredient('%', Material.STICK);
        recipe2.setGroup(name);
        Bukkit.getServer().addRecipe(recipe2);

        ShapedRecipe recipe3 =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"3"), item);
        recipe3.shape("   ","*  ","%  ");
        recipe3.setIngredient('*', Material.EMERALD_BLOCK);
        recipe3.setIngredient('%', Material.STICK);
        recipe3.setGroup(name);
        Bukkit.getServer().addRecipe(recipe3);

        ShapedRecipe recipe4 =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"4"), item);
        recipe4.shape("   "," * "," % ");
        recipe4.setIngredient('*', Material.EMERALD_BLOCK);
        recipe4.setIngredient('%', Material.STICK);
        recipe4.setGroup(name);
        Bukkit.getServer().addRecipe(recipe4);

        ShapedRecipe recipe5 =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"5"), item);
        recipe5.shape("   ","  *","  %");
        recipe5.setIngredient('*', Material.EMERALD_BLOCK);
        recipe5.setIngredient('%', Material.STICK);
        recipe5.setGroup(name);
        Bukkit.getServer().addRecipe(recipe5);
    }

    public void effect(PlayerInteractEvent event){
        event.setCancelled(true);
        if(event.getClickedBlock()==null)
            return;
        Block ore = event.getClickedBlock();
        Material type = ores.get(ore.getType());
        if(type==null)
            return;
        int charge = Utility.deplete(event.getItem());
        if(charge==-1)
            return;

        ore.getWorld().dropItemNaturally(ore.getLocation(), new ItemStack(type));
        if(charge==192 || charge==64 || charge==0)
            event.getPlayer().sendMessage("§7" + charge + " remaining");
    }
}

