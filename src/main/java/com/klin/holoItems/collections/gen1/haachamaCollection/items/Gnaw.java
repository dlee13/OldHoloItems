package com.klin.holoItems.collections.gen1.haachamaCollection.items;

import com.klin.holoItems.Collections;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.gen1.haachamaCollection.HaachamaCollection;
import com.klin.holoItems.interfaces.Consumable;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Gnaw extends Item implements Consumable {
    public static final String name = "gnaw";
    public static final Set<Enchantment> accepted = Stream.of(
            Enchantment.DURABILITY,
            Enchantment.IMPALING,
            Enchantment.LURE,
            Enchantment.KNOCKBACK,
            Enchantment.MENDING,
            Enchantment.THORNS
    ).collect(Collectors.toCollection(HashSet::new));

    private static final Material material = Material.DRIED_KELP;
    private static final int quantity = 1;
    private static final String lore =
            "§6Ability" + "/n" +
                "Sate your hunger";
    private static final int durability = 13;
    public static final boolean stackable = false;
    private static final boolean shiny = false;

    public static final int cost = 0;
    public static final char key = '1';

    public Gnaw() {
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost,
                ""+HaachamaCollection.key + key, key);
    }

    public void registerRecipes() {
        ShapedRecipe recipe =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("a a", "bab", " b ");
        recipe.setIngredient('a', Material.WITHER_SKELETON_SKULL);
        recipe.setIngredient('b', new RecipeChoice.ExactChoice(Collections.findItem("01").item));
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(PlayerItemConsumeEvent event, ItemStack item) {
        event.setCancelled(true);
        Player player = event.getPlayer();

        Location loc = player.getLocation().add(0, 1, 0);
        loc = loc.add(loc.getDirection());
        ItemMeta meta = item.getItemMeta();
        double range = 0.5*(meta.getEnchantLevel(Enchantment.IMPALING)+1);
        Collection<Entity> entities = loc.getWorld().getNearbyEntities(loc, range, 1, range, entity -> (entity!=player && entity instanceof LivingEntity));
        if(entities.isEmpty())
            return;

        int lure = meta.getEnchantLevel(Enchantment.LURE);
        int knockback = meta.getEnchantLevel(Enchantment.KNOCKBACK);
        int thorns = meta.getEnchantLevel(Enchantment.THORNS);
        for(Entity entity : entities){
            LivingEntity food = (LivingEntity) entity;
            int damage = 9 + thorns;
            if(thorns!=0) {
                player.damage(((double)thorns)/2);
                player.setNoDamageTicks(0);
            }
            Utility.damage(item, damage, food.getVelocity().getY()<0, player, food, false, false);
            if(food instanceof Player && ((Player) food).isBlocking()){
                food.setNoDamageTicks(0);
                Utility.damage(item, damage, food.getVelocity().getY()<0, player, food, false, false);
            }
            if(lure!=0)
                entity.setVelocity(loc.subtract(entity.getLocation()).toVector().normalize().setY(0).multiply(lure/3));
            else if(knockback!=0)
                entity.setVelocity(entity.getLocation().subtract(loc).toVector().normalize().multiply(knockback));
        }
    }
}