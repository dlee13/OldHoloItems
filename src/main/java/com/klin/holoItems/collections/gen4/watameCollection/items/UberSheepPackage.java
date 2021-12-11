package com.klin.holoItems.collections.gen4.watameCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.abstractClasses.Crate;
import com.klin.holoItems.collections.gen0.robocoCollection.items.Magnet;
import com.klin.holoItems.interfaces.Collectable;
import com.klin.holoItems.utility.Utility;
import org.bukkit.*;
import org.bukkit.block.Barrel;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashSet;

public class UberSheepPackage extends Crate implements Collectable {
    public static final String name = "uberSheepPackage";
    public static final HashSet<Enchantment> accepted = null;

    private static final Material material = Material.BARREL;
    private static final int quantity = 1;
    private static final String lore =
            "Contains goods to be delivered";
    private static final int durability = 0;
    public static final boolean stackable = true;
    private static final boolean shiny = false;
    public static final int cost = 0;

    public UberSheepPackage(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost);
    }

    public void registerRecipes(){
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6UberSheep Package");
        item.setItemMeta(meta);

        ShapedRecipe recipe =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("aaa","bcb","aaa");
        recipe.setIngredient('a', new RecipeChoice.MaterialChoice(Material.ACACIA_SLAB, Material.BIRCH_SLAB,
                Material.DARK_OAK_SLAB, Material.JUNGLE_SLAB, Material.OAK_SLAB, Material.SPRUCE_SLAB, Material.CRIMSON_SLAB, Material.WARPED_SLAB));
        recipe.setIngredient('b', Material.CHAIN);
        recipe.setIngredient('c', Material.BARREL);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(BlockBreakEvent event) {
        if(Utility.findItem(event.getPlayer().getInventory().getItemInMainHand(), Magnet.class)!=null)
            return;
        event.setDropItems(false);
        Block block = event.getBlock();
        Location loc = block.getLocation();
        World world = loc.getWorld();
        ItemStack drop = item.clone();
        drop.setAmount(1);
        world.dropItemNaturally(loc, drop);
        for(ItemStack content : ((Barrel) block.getState()).getInventory().getContents()) {
            if(content!=null && content.getType()!=Material.AIR)
                world.dropItemNaturally(loc, content);
        }
    }

    public void ability(EntityDropItemEvent event, Entity entity) {
        event.setCancelled(true);
        Location loc = entity.getLocation();
//        ItemStack drop = item.clone();
//        ItemMeta meta = drop.getItemMeta();
//        meta.setDisplayName(entity.getPersistentDataContainer().get(Utility.pack, PersistentDataType.STRING));
//        drop.setItemMeta(meta);
        loc.getWorld().dropItemNaturally(loc, item);
    }
}