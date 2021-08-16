package com.klin.holoItems.collections.en.kiaraCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.en.kiaraCollection.KiaraCollection;
import com.klin.holoItems.interfaces.Holdable;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ShapedRecipe;

import java.util.Set;

public class Cushioning extends Item implements Holdable {
    public static final String name = "cushioning";
    public static final Set<Enchantment> accepted = null;

    private static final Material material = Material.WHITE_WOOL;
    private static final int quantity = 1;
    private static final String lore =
            "Negate fall damage with feathers\n" +
            "Bursts on death";
    private static final int durability = 0;
    private static final boolean stackable = false;
    private static final boolean shiny = false;

    public static final int cost = 0;
    public static final char key = '0';

    public Cushioning(){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost,
                ""+KiaraCollection.key+key, key);
    }

    public void registerRecipes(){
        ShapedRecipe recipe =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("#&#","*%*","#&#");
        recipe.setIngredient('*', Material.COBWEB);
        recipe.setIngredient('%', Material.TOTEM_OF_UNDYING);
        recipe.setIngredient('#', Material.STRING);
        recipe.setIngredient('&', Material.FEATHER);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(Event generic) {
        if(!(generic instanceof EntityDamageEvent))
            return;
        EntityDamageEvent event = (EntityDamageEvent) generic;

    }
}
