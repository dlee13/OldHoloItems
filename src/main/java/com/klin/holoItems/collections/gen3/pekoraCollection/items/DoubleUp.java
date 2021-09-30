package com.klin.holoItems.collections.gen3.pekoraCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.abstractClasses.Wiring;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Dispenser;
import org.bukkit.block.TileState;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class DoubleUp extends Wiring {
    public static final String name = "doubleUp";
    private static Map<Integer, ItemStack> upgrades;

    private static final Material material = Material.DISPENSER;
    private static final String lore =
            "Dispensers wired with this item will\n"+
            "dispense multiple times when powered\n"+
            "Breaking the dispenser returns Double Up";
    private static final boolean shiny = true;
    public static final int cost = 500;

    public DoubleUp(){
        super(name, material, lore, shiny, cost);
    }

    public void registerRecipes(){
        ItemMeta doubleMeta = item.getItemMeta();
        doubleMeta.getPersistentDataContainer().set(Utility.pack, PersistentDataType.INTEGER, 2);
        item.setItemMeta(doubleMeta);

        ShapedRecipe recipe =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("&&&","&*&","%%%");
        recipe.setIngredient('&', Material.IRON_BLOCK);
        recipe.setIngredient('*', Material.DISPENSER);
        recipe.setIngredient('%', Material.SMOOTH_STONE);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);

        Map<Integer, String> sequence = new LinkedHashMap<>(){{
            put(3,"tripleUp");
            put(4,"quadrupleUp");
            put(5,"quintupleUp");
            put(6,"sextupleUp");
            put(7,"septupleUp");
            put(8,"octupleUp");
        }};
        ItemStack prior = item;
        upgrades = new HashMap<>();
        for(Integer i : sequence.keySet()){
            ItemStack copy = item.clone();
            doubleMeta.getPersistentDataContainer().set(Utility.pack, PersistentDataType.INTEGER, i);
            doubleMeta.setDisplayName("§6"+Utility.formatName(sequence.get(i)));
            copy.setItemMeta(doubleMeta);
            upgrades.put(i, copy);

            ShapelessRecipe shapelessRecipe = new ShapelessRecipe(new NamespacedKey(HoloItems.getInstance(), sequence.get(i)), copy);
            shapelessRecipe.addIngredient(new RecipeChoice.ExactChoice(prior));
            shapelessRecipe.addIngredient(new RecipeChoice.ExactChoice(prior));
            shapelessRecipe.setGroup(name);
            Bukkit.getServer().addRecipe(shapelessRecipe);
            prior = copy;
        }
    }

    public void ability(BlockDispenseEvent event) {
        TileState state = (TileState) event.getBlock().getState();
        PersistentDataContainer container = state.getPersistentDataContainer();
        Integer integer = container.get(Utility.pack, PersistentDataType.INTEGER);
        if(integer==null)
            return;

        Integer countdown = container.get(Utility.cooldown, PersistentDataType.INTEGER);
        if(countdown==null)
            container.set(Utility.cooldown, PersistentDataType.INTEGER, integer-1);
        else if(countdown<=1) {
            container.remove(Utility.cooldown);
            state.update();
            return;
        }
        else
            container.set(Utility.cooldown, PersistentDataType.INTEGER, countdown-1);
        state.update();

        new BukkitRunnable(){
            public void run(){
                if(!((Dispenser) state).dispense()) {
                    container.set(Utility.cooldown, PersistentDataType.INTEGER, 1);
                    state.update();
                }
            }
        }.runTask(HoloItems.getInstance());
    }

    protected void additional(PersistentDataContainer container, ItemStack item) {
        if(item==null || item.getItemMeta()==null)
            return;
        Integer integer = item.getItemMeta().getPersistentDataContainer().
                get(Utility.pack, PersistentDataType.INTEGER);
        if(integer==null)
            return;
        container.set(Utility.pack, PersistentDataType.INTEGER, integer);
    }

    @Override
    public void ability(BlockBreakEvent event) {
        Block block = event.getBlock();
        BlockState state = block.getState();
        if(state instanceof TileState){
            Integer upgrade = ((TileState) state).getPersistentDataContainer().get(Utility.pack, PersistentDataType.INTEGER);
            if(upgrade!=null && upgrade>=3 && upgrade<=8){
                block.getWorld().dropItemNaturally(block.getLocation(), upgrades.get(upgrade));
                return;
            }
        }
        block.getWorld().dropItemNaturally(block.getLocation(), item.clone());
    }
}
