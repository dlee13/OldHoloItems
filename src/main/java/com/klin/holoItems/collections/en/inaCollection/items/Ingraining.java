package com.klin.holoItems.collections.en.inaCollection.items;

import com.klin.holoItems.abstractClasses.Enchant;
import com.klin.holoItems.interfaces.Extractable;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.collections.en.inaCollection.InaCollection;
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
    public static final Set<Enchantment> accepted = Set.of(Enchantment.DURABILITY, Enchantment.MENDING, Enchantment.LOOT_BONUS_BLOCKS);

    private static final Material material = Material.FLINT;
    private static final String lore =
            "Replant the crops you break";
    private static final int durability = 256;
    private static final boolean shiny = false;

    public static final Set<String> acceptedIds = null;
    public static final Set<Material> acceptedTypes = Utility.hoes;
    public static final int expCost = 32;

    public static final int cost = 0;
    public static final char key = '4';
    public static final String id = ""+InaCollection.key+key;

    public Ingraining(){
        super(name, accepted, material, lore, durability, shiny, cost, id, key, acceptedIds, acceptedTypes, expCost);
    }

    public void registerRecipes(){
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("a","a","b");
        recipe.setIngredient('a', Material.NAUTILUS_SHELL);
        recipe.setIngredient('b', Material.STICK);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
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
