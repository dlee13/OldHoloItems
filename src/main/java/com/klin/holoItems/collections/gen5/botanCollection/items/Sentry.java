package com.klin.holoItems.collections.gen5.botanCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.gen5.botanCollection.BotanCollection;
import com.klin.holoItems.interfaces.Hitable;
import com.klin.holoItems.interfaces.Interactable;
import com.klin.holoItems.interfaces.Manipulatable;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Sentry extends Item implements Interactable, Manipulatable, Hitable {
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

    public Sentry(){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost,
                ""+BotanCollection.key+key, key);
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
        ArmorStand stand = block.getWorld().spawn(center, ArmorStand.class);
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

        double y = block.getLocation().getY()-0.3;
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

    public void ability(ProjectileHitEvent event) {
        Entity entity = event.getHitEntity();
        if(entity instanceof LivingEntity)
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
                if(!player.getInventory().removeItem(new ItemStack(Material.ARROW)).isEmpty() && player.getGameMode()!= GameMode.CREATIVE)
                    return true;
                Location loc = entry.getKey();
                ArmorStand stand = entry.getValue();
                Vector velocity = stand.getLocation().getDirection().multiply(2);
                if(velocity.getY()<0)
                    loc = loc.clone().add(velocity.clone().setY(0).normalize().multiply(0.2));
                Entity entity = loc.getWorld().spawnEntity(loc, EntityType.ARROW);
                entity.getPersistentDataContainer().set(Utility.key, PersistentDataType.STRING, id);
                entity.setVelocity(velocity);
                ((Projectile) entity).setShooter(player);
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