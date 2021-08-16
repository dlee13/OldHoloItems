package com.klin.holoItems.collections.gen0.suiseiCollection.items;

import com.klin.holoItems.interfaces.Hitable;
import com.klin.holoItems.interfaces.Interactable;
import com.klin.holoItems.interfaces.Launchable;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.gen0.suiseiCollection.SuiseiCollection;
import com.klin.holoItems.utility.Utility;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Hoshiyumi extends Item implements Interactable, Launchable, Hitable {
    public static final String name = "hoshiyumi";
    public static final Set<Enchantment> accepted = new HashSet<Enchantment>(){{
        add(Enchantment.ARROW_DAMAGE);
        add(Enchantment.ARROW_FIRE);
        add(Enchantment.ARROW_KNOCKBACK);
        add(Enchantment.FIRE_ASPECT);
        add(Enchantment.MENDING);
        add(Enchantment.KNOCKBACK);
    }};

    private static final Material material = Material.BOW;
    private static final int quantity = 1;
    private static final String lore =
            "Mark enemies with your arrows\n"+
            "Left click within 5sec to rend them";
    private static final int durability = 92;
    public static final boolean stackable = false;
    private static final boolean shiny = false;

    public static final int cost = 1500;
    public static final char key = '1';

    private Map<Player, Set<LivingEntity>> hits = new HashMap<>();

    public Hoshiyumi(){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost,
                ""+SuiseiCollection.key+key, key);
    }

    public void registerRecipes(){
        ShapedRecipe bowRight = new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"Right"), item);
        bowRight.shape("%* ","% *","%* ");
        bowRight.setIngredient('*', Material.NETHER_STAR);
        bowRight.setIngredient('%', Material.CHAIN);
        bowRight.setGroup(name);
        Bukkit.getServer().addRecipe(bowRight);

        ShapedRecipe bowLeft = new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"Left"), item);
        bowLeft.shape(" *%","* %"," *%");
        bowLeft.setIngredient('*', Material.NETHER_STAR);
        bowLeft.setIngredient('%', Material.CHAIN);
        bowLeft.setGroup(name);
        Bukkit.getServer().addRecipe(bowLeft);
    }

    public void ability(PlayerInteractEvent event, Action action){
        if(!(action==Action.LEFT_CLICK_AIR || action==Action.LEFT_CLICK_BLOCK))
            return;
        Set<LivingEntity> entities = hits.get(event.getPlayer());
        if(entities==null)
            return;

        ItemStack item = event.getItem();
        Player player = event.getPlayer();
        World world = player.getWorld();
        for(LivingEntity entity : entities){
            if(!entity.isValid())
                continue;
            entity.setVelocity(entity.getVelocity().setY(1.4));
            entity.setArrowsInBody(Math.max(0, entity.getArrowsInBody()-1));
            world.spawnParticle(Particle.EXPLOSION_LARGE, entity.getLocation(), 1);
            world.playSound(entity.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 0.2f, 1f);
            if (player.getGameMode()!=GameMode.CREATIVE)
                Utility.addDurability(item, -1, player);
        }
        hits.remove(event.getPlayer());
    }

    public void ability(ProjectileLaunchEvent event, ItemStack item){
        event.getEntity().getPersistentDataContainer().set(Utility.key, PersistentDataType.STRING, id);
    }

    public void ability(ProjectileHitEvent event){
        Projectile proj = event.getEntity();
        Entity hit = event.getHitEntity();
        if(!(hit instanceof LivingEntity) || !(proj.getShooter() instanceof Player))
            return;
        Player player = (Player) proj.getShooter();
        LivingEntity target = (LivingEntity) hit;
        if(!player.equals(target) &&
                !Utility.damage(null, 1, false,
                        player, target, false, false, false))
            return;

        Set<LivingEntity> entities = hits.get(player);
        if(entities==null)
            entities = new HashSet<>();
        entities.add(target);
        hits.put(player, entities);

        new BukkitRunnable() {
            public void run() {
                Set<LivingEntity> entities = hits.get(player);
                if(entities==null)
                    return;
                entities.remove(hit);
                if(entities.isEmpty())
                    hits.remove(player);
                else
                    hits.put(player, entities);
            }
        }.runTaskLater(HoloItems.getInstance(), 100);
    }
}
