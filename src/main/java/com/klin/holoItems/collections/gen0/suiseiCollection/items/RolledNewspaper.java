package com.klin.holoItems.collections.gen0.suiseiCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.gen0.suiseiCollection.SuiseiCollection;
import com.klin.holoItems.interfaces.Responsible;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ShapelessRecipe;
import java.util.HashSet;
import java.util.Set;

public class RolledNewspaper extends Item implements Responsible {
    public static final String name = "rolledNewspaper";
    public static final Set<Enchantment> accepted = new HashSet<Enchantment>(){{
        add(Enchantment.DURABILITY);
        add(Enchantment.MENDING);
    }};

    private static final Material material = Material.PAPER;
    private static final int quantity = 1;
    private static final String lore =
            "§6Ability" +"/n"+
            "Cause a mild annoyance at will";
    private static final int durability = 8;
    public static final boolean stackable = false;
    private static final boolean shiny = true;

    public static final int cost = 0;
    public static final char key = '3';

    public RolledNewspaper(){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost,
                ""+SuiseiCollection.key+key, key);
    }

    public void registerRecipes(){
        ShapelessRecipe recipe =
                new ShapelessRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.addIngredient(8, Material.MAP);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public boolean ability(PlayerInteractEntityEvent event){
        Entity player = event.getRightClicked();
        if(!(player instanceof Player))
            return false;

        ((Player) player).damage(0.1);
        return true;
    }
}
