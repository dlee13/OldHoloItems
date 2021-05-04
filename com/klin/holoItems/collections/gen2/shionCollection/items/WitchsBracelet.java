package com.klin.holoItems.collections.gen2.shionCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.gen2.shionCollection.ShionCollection;
import com.klin.holoItems.interfaces.Holdable;
import com.klin.holoItems.utility.Task;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.Event;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.Set;

public class WitchsBracelet extends Item implements Holdable {
    public static final String name = "witchsBracelet";
    public static final Set<Enchantment> accepted = null;

    private static final Material material = Material.MUSIC_DISC_MELLOHI;
    private static final int quantity = 1;
    private static final String lore =
            "§6Ability" +"/n"+
            "Freely control the path your magic takes" +"/n"+
            "this is held in your offhand";
    private static final int durability = 0;
    private static final boolean stackable = false;
    private static final boolean shiny = true;

    public static final int cost = 145;
    public static final char key = '0';

    public WitchsBracelet(){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost,
                ""+ShionCollection.key+key, key);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6Witch's Bracelet");
        item.setItemMeta(meta);
    }

    public void registerRecipes(){
        ShapedRecipe recipe =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape(" * ","*%*"," * ");
        recipe.setIngredient('*', Material.CHORUS_FLOWER);
        recipe.setIngredient('%', Material.DRAGON_BREATH);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(Event generic) {
        ProjectileLaunchEvent event = (ProjectileLaunchEvent) generic;
        Projectile proj = event.getEntity();
        if(!(proj instanceof ThrownPotion))
            return;

        Player player = (Player) proj.getShooter();
        ThrownPotion potion = (ThrownPotion) proj;
        new Task(HoloItems.getInstance(), 0, 1){
            int increment = 0;

            @Override
            public void run(){
                if(!potion.isValid() || player==null || !player.isValid() || increment>=100){
                    cancel();
                    return;
                }

                Vector vel = potion.getVelocity();
                Location potionPos = potion.getLocation().clone();
                double y = potionPos.getY();
                potionPos.setY(0);
                Location playerPos = player.getLocation().clone();
                playerPos.setY(0);
                double dist = potionPos.distance(playerPos);
                playerPos.add(playerPos.getDirection().setY(0).normalize().multiply(dist)).setY(y);
                potion.teleport(playerPos);
                potion.setVelocity(vel);
//                potion.setVelocity((
//                        player.getLocation().getDirection().multiply(2).subtract(
//                                potion.getVelocity())).normalize().multiply(
//                                        potion.getVelocity().setY(0).length()).setY(
//                                                potion.getVelocity().getY()));
                increment++;
            }
        };
    }
}
