package com.klin.holoItems.collections.id1.moonaCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.id1.moonaCollection.MoonaCollection;
import com.klin.holoItems.interfaces.Dispensable;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.block.data.type.Dispenser;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.*;

import static org.bukkit.Material.*;

public class Partitioner extends Item implements Dispensable {
    public static final String name = "partitioner";
    public static final HashSet<Enchantment> accepted = null;

    private static final Material material = Material.CYAN_BANNER;
    private static final int quantity = 1;
    private static final String lore =
            "§6Ability" +"/n"+
                    "Dispense to organize the target" +"/n"+
                    "container";
    private static final int durability = 0;
    public static final boolean stackable = false;
    private static final boolean shiny = false;

    public static final int cost = 0;
    public static final char key = '0';

    public Partitioner(){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost,
                ""+MoonaCollection.key+key, key);
    }

    public void registerRecipes(){
        ShapedRecipe recipe =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("& &","&*&","& &");
        recipe.setIngredient('&', DIAMOND);
        recipe.setIngredient('*', STICK);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(BlockDispenseEvent event){
        event.setCancelled(true);

        Block block = event.getBlock();
        BlockState container = block.getRelative(((Dispenser) block.getBlockData()).getFacing()).getState();
        if(!(container instanceof Container))
            return;
        Inventory inv = ((Container) container).getInventory();
        Map<ItemStack, List<Integer>> contents = new HashMap<>();
        for(ItemStack item : inv.getStorageContents()){
            if(item==null)
                continue;
            ItemStack key = new ItemStack(item);
            key.setAmount(1);
            List<Integer> quantities = contents.computeIfAbsent(key, k -> new ArrayList<>());
            quantities.add(item.getAmount());
        }
        for(ItemStack item : inv.getStorageContents()) {
            if(item==null)
                continue;
            ItemStack key = new ItemStack(item);
            key.setAmount(1);
            List<Integer> quantities = contents.get(key);
            int amount = quantities.get(0);
            int size = quantities.size();
            if(size>1){
                int mod = quantities.get(1);
                if(mod<0){
                    amount++;
                    if(mod==-1)
                        quantities.remove(1);
                    else
                        quantities.set(1, mod+1);
                }
                else {
                    int total = 0;
                    for (Integer i : quantities)
                        total += i;
                    amount = total / size;
                    quantities.clear();
                    quantities.add(amount);
                    int remainder = total % size;
                    if (remainder>0) {
                        amount++;
                        if(remainder>1)
                            quantities.add(-1 * (remainder-1));
                    }
                }
            }
            item.setAmount(amount);
        }
    }
}
