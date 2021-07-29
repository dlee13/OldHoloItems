package com.klin.holoItems.collections.gen5.botanCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.abstractClasses.Enchant;
import com.klin.holoItems.collections.gen5.botanCollection.BotanCollection;
import com.klin.holoItems.interfaces.Togglable;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.util.Vector;

import java.util.Set;

public class AirStall extends Enchant implements Togglable {
    public static final String name = "airStall";
    public static final Set<Enchantment> accepted = Utility.enchantedBoots;
    public static final Set<String> acceptedIds = null;
    public static final Set<Material> acceptedTypes = Utility.boots;
    public static final int expCost = 20;

    private static final Material material = Material.LEATHER_BOOTS;
    private static final String lore =
            "§6Ability" +"/n"+
                "Sneak while airborne to slow descent";
    private static final int durability = 65;
    private static final boolean shiny = false;
    public static final boolean stackable = false;

    public static final int cost = -1;
    public static final char key = '1';

    public AirStall(){
        super(name, accepted, material, lore, durability, shiny, cost,
                ""+BotanCollection.key+key, key, acceptedIds, acceptedTypes, expCost);
    }

    public void registerRecipes(){
        ShapedRecipe recipe =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("a a","b b");
        recipe.setIngredient('a', Material.PHANTOM_MEMBRANE);
        recipe.setIngredient('b', Material.PISTON);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(PlayerToggleSneakEvent event, ItemStack item){
        if(!event.isSneaking())
            return;
        Player player = event.getPlayer();
        if(((LivingEntity) player).isOnGround())
            return;
        Utility.addDurability(item, -1, player);
        double velocity = player.getVelocity().getY();
        player.setVelocity(new Vector(0, velocity<0?-1*velocity:0, 0));
    }
}