package com.klin.holoItems.collections.misc.archiveCollection;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.abstractClasses.Crate;
import com.klin.holoItems.collections.gen3.marineCollection.MarineCollection;
import com.klin.holoItems.interfaces.Powerable;
import com.klin.holoItems.interfaces.Placeable;
import com.klin.holoItems.interfaces.Punchable;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import static org.bukkit.Material.*;

public class DetonationWire extends Crate implements Powerable, Placeable, Punchable {
    public static final String name = "detonationWire";

    private static final Material material = Material.COMPARATOR;
    private static final int quantity = 3;
    private static final String lore =
            "Burns when lit with a flint\n"+
            "and steel or when powered";
    private static final int durability = 0;
    private static final boolean stackable = true;
    private static final boolean shiny = false;
    public static final int cost = 0;

    public DetonationWire(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost);
    }

    public void registerRecipes(){
        ShapedRecipe recipe =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape(" * ","*%*","&&&");
        recipe.setIngredient('&', STONE);
        recipe.setIngredient('*', GUNPOWDER);
        recipe.setIngredient('%', STRING);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(BlockRedstoneEvent event) {
        Block block = event.getBlock();
//        Comparator comparator = (Comparator) block.getBlockData();
//        Block tnt = block.getRelative(comparator.getFacing());
//        if(tnt.getType()==TNT && comparator.getMode()==Comparator.Mode.SUBTRACT)
//            ((TNT) tnt.getBlockData()).setUnstable(true);
        new BukkitRunnable(){
            public void run(){
                block.setType(FIRE);
                new BukkitRunnable(){
                    public void run(){
                        if(block.getType()==FIRE)
                            block.setType(AIR);
                    }
                }.runTaskLater(HoloItems.getInstance(), 8);
            }
        }.runTaskLater(HoloItems.getInstance(), 4);
    }

    public void ability(BlockPlaceEvent event){
        event.setCancelled(false);
        TileState state = ((TileState) event.getBlockPlaced().getState());
        state.getPersistentDataContainer().set(Utility.key, PersistentDataType.STRING, name);
        state.update();
    }

    public void ability(PlayerInteractEvent event, Action action){
        if(action!=Action.RIGHT_CLICK_BLOCK)
            return;
        event.setCancelled(true);
        ItemStack fns = event.getItem();
        if(fns==null || fns.getType()!=FLINT_AND_STEEL)
            return;
        Block block = event.getClickedBlock();
//        Comparator comparator = (Comparator) block.getBlockData();
        block.setType(REDSTONE_TORCH);
        new BukkitRunnable(){
            public void run(){
                block.setType(FIRE);
                new BukkitRunnable(){
                    public void run(){
                        if(block.getType()==FIRE)
                            block.setType(AIR);
                    }
                }.runTaskLater(HoloItems.getInstance(), 8);
            }
        }.runTaskLater(HoloItems.getInstance(), 4);
    }

    public void ability(BlockBreakEvent event) {
        event.setDropItems(false);
        super.ability(event);
    }
}