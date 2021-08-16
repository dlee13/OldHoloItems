package com.klin.holoItems.collections.gen3.pekoraCollection.items;

import com.klin.holoItems.abstractClasses.Wiring;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.collections.gen3.pekoraCollection.PekoraCollection;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Dispenser;
import org.bukkit.block.TileState;
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
import java.util.Map;

public class DoubleUp extends Wiring {
    public static final String name = "doubleUp";

    private static final Map<Integer, String> upgrades = new HashMap<>(){{
        put(3, "tripleUp");
        put(4, "quadrupleUp");
        put(5, "quintupleUp");
        put(6, "sextupleUp");
        put(7, "septupleUp");
        put(8, "octupleUp");
    }};

    private static final Material material = Material.DISPENSER;
    private static final String lore =
            "Dispensers wired with this item will\n"+
            "dispense multiple times when powered\n"+
            "Breaking the dispenser returns Double Up";
    private static final boolean shiny = true;

    public static final int cost = 500;
    public static final char key = '0';
    public static final String id = ""+PekoraCollection.key+key;

    public DoubleUp(){
        super(name, material, lore, shiny, cost, id, key);
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

        ItemStack prior = item;
        for(Integer i : upgrades.keySet()){
            ItemStack copy = item.clone();
            doubleMeta.getPersistentDataContainer().set(Utility.pack, PersistentDataType.INTEGER, i);
            doubleMeta.setDisplayName("§6"+Utility.formatName(upgrades.get(i)));
            copy.setItemMeta(doubleMeta);

            ShapelessRecipe shapelessRecipe =
                    new ShapelessRecipe(new NamespacedKey(HoloItems.getInstance(), upgrades.get(i)), copy);
            shapelessRecipe.addIngredient(new RecipeChoice.ExactChoice(prior));
            shapelessRecipe.addIngredient(new RecipeChoice.ExactChoice(prior));
            shapelessRecipe.setGroup(name);
            Bukkit.getServer().addRecipe(shapelessRecipe);

            prior = copy;
        }
    }

    public void ability(BlockDispenseEvent event) {
//        ItemStack item = event.getItem();
//        if(item.getItemMeta()!=null && item.getItemMeta().getPersistentDataContainer().
//                get(Utility.key, PersistentDataType.STRING)!=null)
//            return;
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
}
