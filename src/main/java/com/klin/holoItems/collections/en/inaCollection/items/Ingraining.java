package com.klin.holoItems.collections.en.inaCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.abstractClasses.Enchant;
import com.klin.holoItems.collections.en.inaCollection.InaCollection;
import com.klin.holoItems.interfaces.Extractable;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

public class Ingraining extends Enchant implements Extractable {
    public static final String name = "ingraining";
    public static final Set<Enchantment> accepted = new HashSet<Enchantment>(){{
        add(Enchantment.DURABILITY);
        add(Enchantment.MENDING);
        add(Enchantment.LOOT_BONUS_BLOCKS);
    }};
    public static final Set<String> acceptedIds = null;
    public static final Set<Material> acceptedTypes = Utility.hoes;
    public static final int expCost = 32;

    private static final Material material = Material.FLINT;
    private static final String lore =
            "§6Ability" +"/n"+
                "Replant the crops you break";
    private static final int durability = 256;
    private static final boolean shiny = false;

    public static final int cost = 0;
    public static final char key = '4';

    public Ingraining(){
        super(name, accepted, material, lore, durability, shiny, cost,
                ""+InaCollection.key+key, key, acceptedIds, acceptedTypes, expCost);
    }

    public void registerRecipes(){
        ShapedRecipe recipe0 =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"0"), item);
        recipe0.shape("*  ","*  ","%  ");
        recipe0.setIngredient('*', Material.NAUTILUS_SHELL);
        recipe0.setIngredient('%', Material.STICK);
        recipe0.setGroup(name);
        Bukkit.getServer().addRecipe(recipe0);

        ShapedRecipe recipe1 =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"1"), item);
        recipe1.shape(" * "," * "," % ");
        recipe1.setIngredient('*', Material.NAUTILUS_SHELL);
        recipe1.setIngredient('%', Material.STICK);
        recipe1.setGroup(name);
        Bukkit.getServer().addRecipe(recipe1);

        ShapedRecipe recipe2 =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"2"), item);
        recipe2.shape("  *","  *","  %");
        recipe2.setIngredient('*', Material.NAUTILUS_SHELL);
        recipe2.setIngredient('%', Material.STICK);
        recipe2.setGroup(name);
        Bukkit.getServer().addRecipe(recipe2);
    }

    public void ability(BlockBreakEvent event){
        Material type = event.getBlock().getType();
        Inventory inv = event.getPlayer().getInventory();
        if(!Utility.sowable.containsKey(type) || !inv.contains(Utility.sowable.get(type)))
            return;

        inv.removeItem(new ItemStack(Utility.sowable.get(type), 1));
        new BukkitRunnable(){
            public void run(){
                event.getBlock().setType(type);
            }
        }.runTask(HoloItems.getInstance());
    }
}
