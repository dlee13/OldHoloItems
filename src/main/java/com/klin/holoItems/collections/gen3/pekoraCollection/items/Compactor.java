package com.klin.holoItems.collections.gen3.pekoraCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.abstractClasses.Wiring;
import com.klin.holoItems.collections.gen3.pekoraCollection.PekoraCollection;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.block.data.type.Dispenser;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.*;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

import static org.bukkit.Material.*;

public class Compactor extends Wiring {
    public static final String name = "compactor";
    
    //    private static final Set<Material> exceptions = Stream.of().collect(Collectors.toCollection(HashSet::new));

    private static final Material material = BLAST_FURNACE;
    private static final String lore =
            "Dispensers wired with this item will\n"+
            "auto craft using its inventory as the grid\n"+
            "Break the dispenser to retrieve";
    private static final boolean shiny = true;
    public static final int cost = 0;

    public Compactor() {
        super(name, material, lore, shiny, cost);
    }

    public void registerRecipes() {
        ShapedRecipe recipe =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("&*&","*%*","&*&");
        recipe.setIngredient('&', COBBLESTONE);
        recipe.setIngredient('*', CRAFTING_TABLE);
        recipe.setIngredient('%', LODESTONE);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(BlockDispenseEvent event) {
        event.setCancelled(true);

        new BukkitRunnable() {
            public void run() {
                Block block = event.getBlock();
                Block relative = block.getRelative(((Dispenser) block.getBlockData()).getFacing());
                Location location = relative.getLocation();
                World world = location.getWorld();
                if (world == null) {
                    return;
                }
                
                ItemStack[] input = ((InventoryHolder) event.getBlock().getState()).getInventory().getStorageContents();
                Recipe recipe = Bukkit.getCraftingRecipe(input, world);
                if (recipe == null) {
                    return;
                }

                for (ItemStack itemStack : input) {
                    if (itemStack == null) {
                        continue;
                    }
                    itemStack.setAmount(itemStack.getAmount() - 1);
                }

                ItemStack result = recipe.getResult().clone();
                ItemStack remaining = getCraftingRemainingItem(result.getType());

                BlockState container = relative.getState();
                if (container instanceof Container) {
                    Inventory output = ((Container) container).getInventory();
                    Map<Integer, ItemStack> excess = remaining == null ?
                            output.addItem(result) : output.addItem(result, remaining);
                    for (ItemStack item : excess.values()) {
                        world.dropItemNaturally(location, item);
                    }
                } else {
                    world.dropItemNaturally(location, result);
                    if (remaining != null) {
                        world.dropItemNaturally(location, remaining);
                    }
                }
            }
        }.runTask(HoloItems.getInstance());
    }

    // return what would remain in the crafting grid after crafting the given item
    private static ItemStack getCraftingRemainingItem(Material type) {
        switch (type) {
            case CAKE:
                return new ItemStack(BUCKET, 3);

            case HONEY_BLOCK:
                return new ItemStack(GLASS_BOTTLE, 4);

            default:
                return null;
        }
    }
}
