package com.klin.holoItems.collections.gen1.fubukiCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.abstractClasses.Enchant;
import com.klin.holoItems.interfaces.Extractable;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ShapedRecipe;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Plow extends Enchant implements Extractable {
    public static final String name = "plow";
    public static final Set<Enchantment> accepted = Stream.of(Enchantment.DIG_SPEED, Enchantment.SILK_TOUCH, Enchantment.MENDING).collect(Collectors.toCollection(HashSet::new));
    public static final Set<String> acceptedIds = null;
    public static final Set<Material> acceptedTypes = Utility.shovels;
    public static final int expCost = 32;
    private final Map<Player, Integer> held;

    private static final Material material = Material.IRON_SHOVEL;
    private static final String lore =
            "Shovel snow";
    private static final int durability = 250;
    private static final boolean shiny = false;
    public static final int cost = 0;

    public Plow(){
        super(name, accepted, material, lore, durability, shiny, cost, acceptedIds, acceptedTypes, expCost);
        held = new HashMap<>();
    }

    public void registerRecipes(){
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("aba","aca","aca");
        recipe.setIngredient('a', Material.TINTED_GLASS);
        recipe.setIngredient('b', Material.OBSIDIAN);
        recipe.setIngredient('c', Material.STICK);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(BlockBreakEvent event){
        Player player = event.getPlayer();
        Integer ticks = held.get(player);
        if(ticks==null) {
            if(event.getBlock().getType()!=Material.SNOW)
                return;
            held.put(player, 20);
        } else {
            if(event.getBlock().getType()==Material.SNOW)
                held.replace(player, 20);
            else
                event.setCancelled(true);
            return;
        }
        new Task(HoloItems.getInstance(), 0, 1){
            public void run(){
                if(held.get(player)<=0){
                    held.remove(player);
                    cancel();
                    return;
                }
                held.replace(player, held.get(player)-1);
            }
        };
    }
}