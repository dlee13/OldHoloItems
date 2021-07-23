package com.klin.holoItems.collections.holostars.temmaCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.holostars.temmaCollection.TemmaCollection;
import com.klin.holoItems.interfaces.Afflictable;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SSKSword extends Item implements Afflictable {
    public static final String name = "sskSword";
    public static final Set<Enchantment> accepted = Stream.of(Enchantment.FIRE_ASPECT, Enchantment.DAMAGE_ALL, Enchantment.DAMAGE_ARTHROPODS, Enchantment.DAMAGE_UNDEAD, Enchantment.DURABILITY, Enchantment.LOOT_BONUS_MOBS, Enchantment.MENDING, Enchantment.KNOCKBACK, Enchantment.SWEEPING_EDGE).collect(Collectors.toCollection(HashSet::new));

    private static final Material material = Material.DIAMOND_SWORD;
    private static final int quantity = 1;
    private static final String lore =
            "§6Ability" +"/n"+
                "Attacks heal when full hearts, and"+"/n"+
                "damages when not";
    private static final int durability = 251;
    public static final boolean stackable = false;
    private static final boolean shiny = false;

    public static final int cost = 0;
    public static final char key = '0';

    public SSKSword(){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost,
                ""+TemmaCollection.key+key, key);
    }

    public void registerRecipes(){
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6SSK Sword");
        item.setItemMeta(meta);

        ShapedRecipe recipe0 =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"0"), item);
        recipe0.shape("*&#","*&#","*/#");
        recipe0.setIngredient('*', Material.BLAZE_POWDER);
        recipe0.setIngredient('&', Material.GHAST_TEAR);
        recipe0.setIngredient('#', Material.SUGAR);
        recipe0.setIngredient('/', Material.NETHER_STAR);
        recipe0.setGroup(name);
        Bukkit.getServer().addRecipe(recipe0);

        ShapedRecipe recipe1 =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"1"), item);
        recipe1.shape("*&#","*&#","*/#");
        recipe1.setIngredient('*', Material.SUGAR);
        recipe1.setIngredient('&', Material.GHAST_TEAR);
        recipe1.setIngredient('#', Material.BLAZE_POWDER);
        recipe1.setIngredient('/', Material.NETHER_STAR);
        recipe1.setGroup(name);
        Bukkit.getServer().addRecipe(recipe1);
    }

    public void ability(EntityDamageByEntityEvent event, ItemStack item){
        Entity damager = event.getDamager();
        if(((Player) damager).getHealth()<20)
            return;
        Entity entity = event.getEntity();
        if(entity instanceof LivingEntity){
            LivingEntity target = (LivingEntity) entity;
            target.setHealth(Math.min(target.getMaxHealth(), target.getHealth()+event.getDamage()));
            event.setDamage(0);
            int level = item.getEnchantmentLevel(Enchantment.FIRE_ASPECT);
            if(level>0) {
                target.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 80 * level, 1));
                target.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80*level, 1));
                target.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80*level, 1));
            }
            Location loc = entity.getLocation().add(0, target.getHeight()/2, 0);
            World world = entity.getWorld();
            world.spawnParticle(Particle.HEART, loc, 3, 0.5, 0.5, 0.5);
            world.playSound(loc, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.1f, 1f);
        }
    }
}
