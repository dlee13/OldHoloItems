package com.klin.holoItems.collections.gen5.botanCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.gen5.botanCollection.BotanCollection;
import com.klin.holoItems.interfaces.Hitable;
import com.klin.holoItems.interfaces.Interactable;
import com.klin.holoItems.interfaces.Manipulatable;
import com.klin.holoItems.interfaces.Retaliable;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.*;

public class Sentry extends Item implements Interactable, Manipulatable, Retaliable, Hitable {
    public static final String name = "sentry";
    public static final Set<Enchantment> accepted = null;
    private static Map<Player, AbstractMap.SimpleEntry<Location, ArmorStand>> stands = new HashMap<>();

    private static final Material material = Material.CROSSBOW;
    private static final int quantity = 1;
    private static final String lore =
            "§6Ability" +"/n"+
                "Mount on a fence post and bring"+"/n"+
                "down a hail of arrows";
    private static final int durability = 0;
    private static final boolean stackable = false;
    private static final boolean shiny = false;

    public static final int cost = 0 ;
    public static final char key = '2';
    public static final String id = ""+BotanCollection.key+key;

    public Sentry(){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost, id, key);
    }

    public void registerRecipes(){
        ShapedRecipe recipe =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("a","b","c");
        recipe.setIngredient('a', Material.CROSSBOW);
        recipe.setIngredient('b', Material.CONDUIT);
        recipe.setIngredient('c', new RecipeChoice.MaterialChoice(Material.ACACIA_FENCE_GATE, Material.BIRCH_FENCE_GATE, Material.DARK_OAK_FENCE_GATE,
                Material.JUNGLE_FENCE_GATE, Material.OAK_FENCE_GATE, Material.SPRUCE_FENCE_GATE, Material.CRIMSON_FENCE_GATE, Material.WARPED_FENCE_GATE));
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);

        ItemStack bomb = item.clone();
        ItemMeta meta = bomb.getItemMeta();
        meta.getPersistentDataContainer().set(Utility.pack, PersistentDataType.INTEGER, 0);
        List<String> lore = meta.getLore();
        lore.add(0, "§7Bomb");
        meta.setLore(lore);
        bomb.setItemMeta(meta);
        ShapelessRecipe bombRecipe =
                new ShapelessRecipe(new NamespacedKey(HoloItems.getInstance(), name+"Bomb"), bomb);
        bombRecipe.addIngredient(new RecipeChoice.ExactChoice(item));
        bombRecipe.addIngredient(Material.TNT);
        bombRecipe.setGroup(name);
        Bukkit.getServer().addRecipe(bombRecipe);

        ShapelessRecipe revertBomb =
                new ShapelessRecipe(new NamespacedKey(HoloItems.getInstance(), name+"RevertBomb"), item);
        revertBomb.addIngredient(new RecipeChoice.ExactChoice(bomb));
        revertBomb.setGroup(name);
        Bukkit.getServer().addRecipe(revertBomb);

        ItemStack sniper = item.clone();
        meta.getPersistentDataContainer().set(Utility.pack, PersistentDataType.INTEGER, 1);
        lore.set(0, "§7Sniper");
        meta.setLore(lore);
        sniper.setItemMeta(meta);
        ShapelessRecipe sniperRecipe =
                new ShapelessRecipe(new NamespacedKey(HoloItems.getInstance(), name+"Sniper"), sniper);
        sniperRecipe.addIngredient(new RecipeChoice.ExactChoice(item));
        sniperRecipe.addIngredient(Material.SPECTRAL_ARROW);
        sniperRecipe.setGroup(name);
        Bukkit.getServer().addRecipe(sniperRecipe);

        ShapelessRecipe revertSniper =
                new ShapelessRecipe(new NamespacedKey(HoloItems.getInstance(), name+"RevertSniper"), item);
        revertSniper.addIngredient(new RecipeChoice.ExactChoice(sniper));
        revertSniper.setGroup(name);
        Bukkit.getServer().addRecipe(revertSniper);

        ShapelessRecipe bombToSniper =
                new ShapelessRecipe(new NamespacedKey(HoloItems.getInstance(), name+"BombToSniper"), sniper);
        bombToSniper.addIngredient(new RecipeChoice.ExactChoice(bomb));
        bombToSniper.addIngredient(Material.SPECTRAL_ARROW);
        bombToSniper.setGroup(name);
        Bukkit.getServer().addRecipe(bombToSniper);

        ShapelessRecipe sniperToBomb =
                new ShapelessRecipe(new NamespacedKey(HoloItems.getInstance(), name+"SniperToBomb"), bomb);
        sniperToBomb.addIngredient(new RecipeChoice.ExactChoice(sniper));
        sniperToBomb.addIngredient(Material.TNT);
        sniperToBomb.setGroup(name);
        Bukkit.getServer().addRecipe(sniperToBomb);
    }

    public void ability(PlayerInteractEvent event, Action action){
        event.setCancelled(true);
        ItemStack item = event.getItem();
        Player player = event.getPlayer();
        if(cause(item, player))
            return;

        if(action!=Action.RIGHT_CLICK_BLOCK)
            return;
        Block block = event.getClickedBlock();
        Material type = block.getType();
        if(!Utility.fences.contains(type))
            return;
        Location center = block.getLocation().add(0.5, 1.62, 0.5);
        Location reference = center.clone();
        center.setY(0);
        //1.17
        double y = block.getLocation().getY()-0.3;
        Location loc = center.clone().add(player.getLocation().subtract(center).toVector().setY(0).normalize());
        loc.add(0.335*(center.getZ()-loc.getZ()), y, -0.335*(center.getX()-loc.getX())).setDirection(player.getEyeLocation().subtract(reference).toVector().multiply(-1).normalize());
        ArmorStand stand = block.getWorld().spawn(loc, ArmorStand.class);
        //
//        ArmorStand stand = block.getWorld().spawn(center, ArmorStand.class);
        stand.setInvisible(true);
        stand.setInvulnerable(true);
        stand.setGravity(false);
        stand.setBasePlate(false);
        stand.setCanPickupItems(false);
        stand.addEquipmentLock(EquipmentSlot.CHEST, ArmorStand.LockType.ADDING);
        stand.addEquipmentLock(EquipmentSlot.FEET, ArmorStand.LockType.ADDING);
        stand.addEquipmentLock(EquipmentSlot.HEAD, ArmorStand.LockType.ADDING);
        stand.addEquipmentLock(EquipmentSlot.LEGS, ArmorStand.LockType.ADDING);
        stand.addEquipmentLock(EquipmentSlot.OFF_HAND, ArmorStand.LockType.ADDING);
        stand.getPersistentDataContainer().set(Utility.key, PersistentDataType.STRING, id);
        stand.getEquipment().setItemInMainHand(item);
        stand.setArms(true);
        stand.setRightArmPose(new EulerAngle(Math.PI/2, 3.405, 0));

        stands.put(player, new AbstractMap.SimpleEntry<>(reference.clone().add(0, -0.6, 0), stand));
        item.setType(Material.LEVER);

//        double y = block.getLocation().getY()-0.3;
        new Task(HoloItems.getInstance(), 0, 1){
            int increment = 0;
            double health = player.getHealth();
            public void run(){
                if(increment>=1200 || !stand.isValid() || block.getType()!=type ||
                        !player.isValid() || health>player.getHealth() || increment!=0 &&
                        player.getLocation().distance(stand.getLocation())>4 && player.getGameMode()!=GameMode.CREATIVE){
                    if(stand.isValid())
                        stand.remove();
                    item.setType(Material.CROSSBOW);
                    stands.remove(player);
                    cancel();
                    return;
                }
                health = player.getHealth();

                Location loc = center.clone().add(player.getLocation().subtract(center).toVector().setY(0).normalize());
                stand.teleport(loc.clone().
                        add(0.335*(center.getZ()-loc.getZ()), y, -0.335*(center.getX()-loc.getX())).
                        setDirection(player.getEyeLocation().subtract(reference).toVector().multiply(-1).normalize()));
                increment++;
            }
        };
    }

    public void ability(PlayerArmorStandManipulateEvent event) {
        cause(event.getPlayerItem(), event.getPlayer());
    }

    public void ability(EntityDamageByEntityEvent event, Entity damager) {
        Entity entity = event.getEntity();
        if(entity instanceof LivingEntity) {
            if (damager instanceof SpectralArrow)
                event.setDamage(30);
            else
                event.setDamage(1);
        }
    }

    public void ability(ProjectileHitEvent event) {
        Entity entity = event.getHitEntity();
        if (entity instanceof LivingEntity)
            ((LivingEntity) entity).setNoDamageTicks(0);
    }

    private boolean cause(ItemStack item, Player player){
        if(item.getType()==Material.LEVER){
            AbstractMap.SimpleEntry<Location, ArmorStand> entry = stands.get(player);
            if(entry==null){
                item.setType(Material.CROSSBOW);
                stands.remove(player);
            }
            else {
                if(Utility.onCooldown(item))
                    return true;
                Location loc = entry.getKey();
                ArmorStand stand = entry.getValue();
                Vector velocity = stand.getLocation().getDirection().multiply(2);
                if(velocity.getY()<0)
                    loc = loc.clone().add(velocity.clone().setY(0).normalize().multiply(0.2));
                Integer upgrade = item.getItemMeta().getPersistentDataContainer().get(Utility.pack, PersistentDataType.INTEGER);
                Material cost = Material.ARROW;
                EntityType type = EntityType.ARROW;
                if(upgrade!=null){
                    if(upgrade==0) {
                        Utility.cooldown(item, 20);
                        cost = Material.TNT;
                        type = EntityType.PRIMED_TNT;
                        velocity.multiply(0.5).add(new Vector(0, 0.2, 0));
                    }
                    else if(upgrade==1) {
                        Utility.cooldown(item, 40);
                        cost = Material.SPECTRAL_ARROW;
                        type = EntityType.SPECTRAL_ARROW;
                        velocity.multiply(8);
                    }
                }
                if(!player.getInventory().removeItem(new ItemStack(cost)).isEmpty() && player.getGameMode()!= GameMode.CREATIVE)
                    return true;

                Entity entity = loc.getWorld().spawnEntity(loc, type);
                if(entity instanceof Projectile) {
                    entity.getPersistentDataContainer().set(Utility.key, PersistentDataType.STRING, id);
                    ((Projectile) entity).setShooter(player);
                }
                entity.setVelocity(velocity);
                if(type==EntityType.SPECTRAL_ARROW)
                    entity.setGlowing(true);
                else if(type==EntityType.PRIMED_TNT){
                    TNTPrimed tnt = (TNTPrimed) entity;
                    tnt.setFuseTicks(60);
                    new Task(HoloItems.getInstance(), 3, 1){
                        int increment = 0;
                        public void run(){
                            if(!entity.isValid()){
                                cancel();
                                return;
                            }
                            int stand = 0;
                            List<Entity> trigger = entity.getNearbyEntities(1, 1, 1);
                            for(Entity entity : trigger){
                                if(!(entity instanceof LivingEntity) || entity instanceof ArmorStand)
                                    stand++;
                            }
                            if(increment>=48 || stand<trigger.size() || entity.isOnGround()){
                                tnt.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, tnt.getLocation(), 1);
                                tnt.remove();
                                for(Entity nearby : entity.getNearbyEntities(4, 4, 4)){
                                    if(nearby instanceof LivingEntity && !(nearby instanceof ArmorStand)){
                                        LivingEntity livingEntity = (LivingEntity) nearby;
                                        livingEntity.setNoDamageTicks(0);
                                        livingEntity.damage(8, player);
                                        livingEntity.setNoDamageTicks(0);
                                    }
                                }
                            }
                            increment++;
                        }
                    };
                }
            }
            return true;
        }
        return false;
    }
}

//    Vector dir = player.getEyeLocation().subtract(reference).toVector().multiply(-1).normalize();
//    stand.setRightArmPose(stand.getRightArmPose().setX(Math.PI*(0.5+dir.getY()/2)).setY(3.405+dir.getY()/2.2));
//    Location loc = center.clone().add(player.getLocation().subtract(center).toVector().setY(0).normalize().multiply(1-Math.pow(Math.abs(dir.getY()), 2)));
//    stand.teleport(loc.clone().add(0.335*(center.getZ()-loc.getZ()), y+dir.getY()*-1, -0.335*(center.getX()-loc.getX())).setDirection(dir));