package com.klin.holoItems.collections.gen0.suiseiCollection.items;

import com.klin.holoItems.interfaces.Responsible;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.gen0.suiseiCollection.SuiseiCollection;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RolledNewspaper extends Item implements Responsible {
    public static final String name = "rolledNewspaper";
    public static final Set<Enchantment> accepted = Stream.of(Enchantment.DURABILITY, Enchantment.MENDING).collect(Collectors.toCollection(HashSet::new));

    private static final Material material = Material.PAPER;
    private static final int quantity = 1;
    private static final String lore =
            "Cause a mild annoyance at will";
    private static final int durability = 8;
    public static final boolean stackable = false;
    private static final boolean shiny = false;

    public static final int cost = 0;
    public static final char key = '3';
    public static final String id = ""+SuiseiCollection.key+key;

    public RolledNewspaper(){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost, id, key);
    }

    public void registerRecipes(){
        ShapelessRecipe recipe =
                new ShapelessRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.addIngredient(8, Material.MAP);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public boolean ability(PlayerInteractEntityEvent event, ItemStack item){
        Entity player = event.getRightClicked();
        if(!(player instanceof Player))
            return false;

        ((Player) player).damage(0.1);
        return true;
    }
}
