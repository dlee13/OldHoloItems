package com.klin.holoItems.collections.gen0.suiseiCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.collections.gen0.suiseiCollection.SuiseiCollection;
import com.klin.holoItems.abstractClasses.PowerUp;
import com.klin.holoItems.interfaces.Interactable;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;

import java.util.HashSet;
import java.util.Set;

public class SuperStar extends PowerUp {
    public static final String name = "superStar";
    public static final Set<Enchantment> accepted = null;

    private static final Material material = Material.NETHER_STAR;
    private static final int quantity = 1;
    private static final String lore =
            "§6Ability" +"/n"+
            "While riding in a boat," +"/n"+
            "Right click to gain a speed" +"/n"+
            "boost for 5 seconds";
    private static final boolean shiny = true;

    private static final int interval = 5;
    private static final int increments = 20;

    public static final int cost = 1000;
    public static final char key = '0';

    public SuperStar(){
        super(name, accepted, material, quantity, lore, shiny, cost, interval, increments,
                ""+SuiseiCollection.key+key, key);
    }

    public void registerRecipes(){
        ShapedRecipe recipe =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape(" * ","*%*"," * ");
        recipe.setIngredient('*', Material.GOLD_NUGGET);
        recipe.setIngredient('%', Material.GOLD_INGOT);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    protected void effect(Player player, Entity boat){
        boat.setVelocity(boat.getVelocity().add(player.getLocation().getDirection().setY(0).normalize()));
    }

    protected boolean endCondition(){
        return false;
    }
}
