package com.klin.holoItems.collections.gen1.melCollection.items;

import com.klin.holoItems.abstractClasses.Armor;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.collections.gen1.melCollection.MelCollection;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.Set;

public class ReadingGlasses extends Armor {
    public static final String name = "readingGlasses";
    public static final Set<Enchantment> accepted = new HashSet<>() {{
        add(Enchantment.BINDING_CURSE);
    }};

    private static final Material material = Material.BLACK_STAINED_GLASS;
    private static final String lore =
            "Wear to gain night vision";
    private static final int durability = 1;
    public static final boolean stackable = true;

    private static final int armorPiece = 5;

    public static final int cost = 0;
    public static final char key = '0';

    public ReadingGlasses(){
        super(name, accepted, material, lore, durability, stackable, cost, armorPiece,
                ""+MelCollection.key+key, key);
    }

    public void registerRecipes(){
        ShapedRecipe recipe =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("***","* *","***");
        recipe.setIngredient('*', Material.PHANTOM_MEMBRANE);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(EntityDamageByEntityEvent event, boolean broken){
        Entity entity = event.getEntity();
        if(broken && entity instanceof LivingEntity) {
            if (entity instanceof Player)
                removeEffect((Player) entity);
            else
                ((Damageable) entity).damage(40);
        }
    }

    public void removeEffect(InventoryClickEvent event){
        removeEffect((Player) event.getWhoClicked());
    }

    public void effect(PlayerInteractEvent event){
        effect(event.getPlayer());
    }

    public void effect(PlayerDeathEvent event){}

    private void effect(Player player){
        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,
                99999, 0, true, false));
    }

    private void removeEffect(Player player){
        player.removePotionEffect(PotionEffectType.NIGHT_VISION);
    }
}
