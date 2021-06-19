package com.klin.holoItems.collections.gen3.flareCollection.items;

import com.klin.holoItems.abstractClasses.Wiring;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.collections.gen3.flareCollection.FlareCollection;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Dispenser;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public class MoltenCore extends Wiring {
    public static final String name = "moltenCore";

    private static final Material material = Material.DARK_OAK_WOOD;
    private static final String lore =
            "§6Ability" +"/n"+
                "Just a rock. Maybe something will" +"/n"+
                "happen if you heat it up";
    private static final boolean shiny = false;

    public static final int cost = 400;
    public static final char key = '0';
    public static final String id = ""+FlareCollection.key+key;

    public MoltenCore(){
        super(name, material, lore, shiny, cost, id, key);
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().remove(Utility.key);
        meta.setDisplayName("§6Hardened Core");
        item.setItemMeta(meta);

        ItemStack activated = item.clone();
        ItemMeta activatedMeta = activated.getItemMeta();
        activatedMeta.getPersistentDataContainer().set(Utility.key, PersistentDataType.STRING, id);
        activatedMeta.setDisplayName("§6Molten Core");
        activatedMeta.setLore(Utility.processStr(
                "§6Ability" +"/n"+
                    "Dispensers wired with this item can" +"/n"+
                    "melt obsidian using fire charges" + "/n"+
                    "Break the dispenser to retrieve"));
        activatedMeta.addEnchant(Enchantment.LUCK, 1, false);
        activatedMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        activated.setItemMeta(activatedMeta);

        FurnaceRecipe recipe =
                new FurnaceRecipe(new NamespacedKey(HoloItems.getInstance(), name+"2"), activated,
                        new RecipeChoice.ExactChoice(item), 1, 1600);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void registerRecipes(){
        ShapedRecipe recipe =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"0"), item);
        recipe.shape("***","%%%","***");
        recipe.setIngredient('*', Material.COAL_BLOCK);
        recipe.setIngredient('%', Material.DARK_OAK_WOOD);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(BlockDispenseEvent event){
        ItemStack item = event.getItem();
        if(item.getType()!=Material.FIRE_CHARGE)
            return;

        event.setCancelled(true);
        Block block = event.getBlock();
        BlockFace face = ((Dispenser) block.getBlockData()).getFacing();
        Block place = block.getRelative(face);
        if(place.getType()!=Material.OBSIDIAN)
            return;
        Inventory inv = ((InventoryHolder) block.getState()).getInventory();

        place.setType(Material.LAVA);
        new BukkitRunnable(){
            public void run(){
                inv.removeItem(item);
            }
        }.runTask(HoloItems.getInstance());
    }
}

