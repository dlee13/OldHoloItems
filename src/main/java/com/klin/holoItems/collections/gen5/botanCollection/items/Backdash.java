package com.klin.holoItems.collections.gen5.botanCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.abstractClasses.Armor;
import com.klin.holoItems.collections.gen5.botanCollection.BotanCollection;
import com.klin.holoItems.interfaces.Togglable;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Set;

public class Backdash extends Armor implements Togglable {
    public static final String name = "backdash";
    public static final Set<Enchantment> accepted = null;

    private static final Material material = Material.LEATHER_BOOTS;
    private static final String lore =
            "§6Ability" +"/n"+
            "Backstep attacks";
    private static final int durability = 65;
    public static final boolean stackable = false;

    private static final int armorPiece = 8;

    public static final int cost = 0;
    public static final char key = '0';

    public Backdash(){
        super(name, accepted, material, lore, durability, stackable, cost, armorPiece,
                ""+BotanCollection.key+key, key);
    }

    public void registerRecipes(){
        ShapedRecipe recipe =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("* *","* *");
        recipe.setIngredient('*', Material.PHANTOM_MEMBRANE);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(EntityDamageByEntityEvent event, boolean broken){
        Entity entity = event.getEntity();
        if(entity instanceof Player)
            return;
        Entity damager = event.getDamager();
        if(entity.equals(damager))
            return;
        boolean distant = true;
        if(damager instanceof Projectile) {
            damager = (LivingEntity) ((Projectile) damager).getShooter();
            distant = false;
        }
        if(damager==null)
            return;
        Location dodge = damager.getLocation();
        if(distant && dodge.distance(entity.getLocation())>=2.5 ||
                !distant && dodge.distance(entity.getLocation())<=2.5){
            event.setCancelled(true);
            entity.setVelocity(entity.getLocation().subtract(dodge).toVector().setY(0).normalize().multiply(2));
            ((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 8));
            if(damager instanceof Mob)
                ((Mob) entity).setTarget((LivingEntity) damager);
        }
    }

    public void ability(PlayerToggleSneakEvent event, ItemStack item){
        if(!event.isSneaking())
            return;
        Player player = event.getPlayer();
        Utility.addDurability(item, -1, player);
        player.setVelocity(player.getLocation().getDirection().setY(0).normalize().multiply(-1));
    }

    public void removeEffect(InventoryClickEvent event){}
    public void effect(PlayerInteractEvent event){}
    public void effect(PlayerDeathEvent event){}
}
