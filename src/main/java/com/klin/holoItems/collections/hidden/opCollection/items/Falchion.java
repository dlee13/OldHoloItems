package com.klin.holoItems.collections.hidden.opCollection.items;

import com.klin.holoItems.abstractClasses.Armor;
import com.klin.holoItems.interfaces.Afflictable;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import java.util.Set;

public class Falchion extends Armor implements Afflictable {
    public static final String name = "falchion";
    public static final Set<Enchantment> accepted = null;
    private static final ItemStack pot = new ItemStack(Material.SPLASH_POTION);
    private static final Vector down = new Vector(0, -1, 0);

    private static final Material material = Material.IRON_SWORD;
    private static final String lore =
            "Splash harming pot every 6 strikes";
    private static final int durability = -1;
    public static final boolean stackable = false;
    public static final int cost = -1;

    private static final int armorPiece = 4;

    public Falchion(){
        super(name, accepted, material, lore, durability, stackable, cost, armorPiece);

        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.DAMAGE_ALL, 10, true);
        meta.addEnchant(Enchantment.KNOCKBACK, 1, false);
        item.setItemMeta(meta);

        PotionMeta potMeta = (PotionMeta) pot.getItemMeta();
        potMeta.setBasePotionData(new PotionData(PotionType.INSTANT_DAMAGE, false, true));
        pot.setItemMeta(potMeta);
    }

    public void ability(EntityDamageByEntityEvent event, ItemStack item){
        ItemMeta meta = item.getItemMeta();
        Integer integer = meta.getPersistentDataContainer().get(Utility.pack, PersistentDataType.INTEGER);
        if(integer==null)
            integer = 1;
        Entity damager = event.getDamager();
        damager.getWorld().playSound(damager.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 4f, (float) (integer/3));
        if(integer>=4) {
            ThrownPotion potion = ((ProjectileSource) damager).launchProjectile(ThrownPotion.class, down);
            potion.setItem(pot);
            integer = 1;
        }
        else
            integer++;
        meta.getPersistentDataContainer().set(Utility.pack, PersistentDataType.INTEGER, integer);
        item.setItemMeta(meta);
        if(!(damager instanceof Player))
            ((LivingEntity) damager).getEquipment().setItemInMainHand(item);
    }

    public void registerRecipes(){}
    public void ability(EntityDamageByEntityEvent event, boolean broken){}
    public void removeEffect(InventoryClickEvent event){}
    public void effect(PlayerInteractEvent event){}
    public void effect(PlayerDeathEvent event){}
}
