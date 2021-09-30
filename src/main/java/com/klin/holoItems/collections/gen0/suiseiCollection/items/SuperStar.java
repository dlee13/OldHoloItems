package com.klin.holoItems.collections.gen0.suiseiCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.abstractClasses.PowerUp;
import com.klin.holoItems.collections.gen0.suiseiCollection.SuiseiCollection;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;

import java.util.Set;

public class SuperStar extends PowerUp {
    public static final String name = "superStar";
    public static final Set<Enchantment> accepted = null;

    private static final Material material = Material.NETHER_STAR;
    private static final int quantity = 1;
    private static final String lore =
            "Drift";
    private static final boolean shiny = true;

    private static final int interval = 1;
    private static final int increments = 100;
    public static final int cost = -1;

    public SuperStar(){
        super(name, accepted, material, quantity, lore, shiny, cost, interval, increments);
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

    protected void effect(Player player, Entity cart){
        cart.setVelocity(cart.getVelocity().add(player.getLocation().getDirection()));
        //test
        System.out.println("star");
        //
    }

    protected boolean endCondition(){
        return false;
    }
}
