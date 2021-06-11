package com.klin.holoItems.collections.gen4.cocoCollection.items;

import com.klin.holoItems.interfaces.Flauntable;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.gen4.cocoCollection.CocoCollection;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ShapedRecipe;

import java.util.HashSet;
import java.util.Set;

public class DragonHorns extends Item implements Flauntable {
    public static final String name = "dragonHorns";
    public static final Set<Enchantment> accepted = new HashSet<Enchantment>(){{
        add(Enchantment.BINDING_CURSE);
        add(Enchantment.VANISHING_CURSE);
    }};

    private static final Material material = Material.DRAGON_HEAD;
    private static final int quantity = 1;
    private static final String lore =
            "§6Ability" +"/n"+ 
                    "Good morning-";
    private static final int durability = 0;
    public static final boolean stackable = false;
    private static final boolean shiny = false;

    public static final int cost = 0;
    public static final char key = '0';

    public DragonHorns(){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost,
                ""+CocoCollection.key+key, key);
    }

    public void registerRecipes(){
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape(" * ","*%*"," * ");
        recipe.setIngredient('*', Material.END_CRYSTAL);
        recipe.setIngredient('%', Material.DRAGON_HEAD);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(AsyncPlayerChatEvent event){
        event.setMessage(event.getMessage()+", motherfucker");
    }
}