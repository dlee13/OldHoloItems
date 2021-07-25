package com.klin.holoItems.collections.gen4.watameCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.abstractClasses.Crate;
import com.klin.holoItems.collections.gen4.watameCollection.WatameCollection;
import com.klin.holoItems.interfaces.Collectable;
import com.klin.holoItems.interfaces.Placeable;
import com.klin.holoItems.utility.Utility;
import org.bukkit.*;
import org.bukkit.block.Barrel;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashSet;

public class UberSheepPackage extends Crate implements Placeable, Collectable {
    public static final String name = "uberSheepPackage";
    public static final HashSet<Enchantment> accepted = null;

    private static final Material material = Material.BARREL;
    private static final int quantity = 1;
    private static final String lore =
            "§6Ability" +"/n"+
                "Contains goods to be delivered";
    private static final int durability = 0;
    public static final boolean stackable = false;
    private static final boolean shiny = false;

    public static final int cost = 0;
    public static final char key = '0';
    public static final String id = ""+WatameCollection.key+key;

    public UberSheepPackage(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost, id, key);
    }

    public void registerRecipes(){
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6UberSheep Package");
        item.setItemMeta(meta);

        ShapedRecipe recipe =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("aaa","bcb","aaa");
        recipe.setIngredient('a', Material.SMOOTH_STONE_SLAB);
        recipe.setIngredient('b', Material.SHIELD);
        recipe.setIngredient('c', Material.BARREL);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(BlockPlaceEvent event){
        event.setCancelled(false);
        TileState state = (TileState) event.getBlockPlaced().getState();
        state.getPersistentDataContainer().set(Utility.key, PersistentDataType.STRING, id);
        state.update();
    }

    public void ability(BlockBreakEvent event) {
        event.setDropItems(false);
        super.ability(event);

        Block block = event.getBlock();
        Location loc = block.getLocation();
        World world = loc.getWorld();
        for(ItemStack content : ((Barrel) block.getState()).getInventory().getContents()) {
            if(content!=null && content.getType()!=Material.AIR)
                world.dropItemNaturally(loc, content);
        }
    }

    public void ability(EntityDropItemEvent event, Entity entity) {
        event.setCancelled(true);
        Location loc = entity.getLocation();
        ItemStack drop = item.clone();
        ItemMeta meta = drop.getItemMeta();
        meta.setDisplayName(entity.getPersistentDataContainer().get(Utility.pack, PersistentDataType.STRING));
        meta.getPersistentDataContainer().set(Utility.stack, PersistentDataType.DOUBLE, Math.random());
        drop.setItemMeta(meta);
        loc.getWorld().dropItemNaturally(loc, drop);
    }
}