package com.klin.holoItems.collections.gen0.suiseiCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.interfaces.Interactable;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;

public class Comet extends Item implements Interactable {
    public static final String name = "comet";
    public static final Set<Enchantment> accepted = Set.of(
        Enchantment.FIRE_ASPECT,
        Enchantment.DAMAGE_ALL,
        Enchantment.DAMAGE_ARTHROPODS,
        Enchantment.DAMAGE_UNDEAD,
        Enchantment.DIG_SPEED,
        Enchantment.DURABILITY,
        Enchantment.LOOT_BONUS_BLOCKS,
        Enchantment.LOOT_BONUS_MOBS,
        Enchantment.MENDING,
        Enchantment.PIERCING,
        Enchantment.SILK_TOUCH
    );

    private static final Material material = Material.GOLDEN_AXE;
    private static final int quantity = 1;
    private static final String lore =
            "Right click to throw an axe";
    private static final int durability = 32;
    public static final boolean stackable = false;
    private static final boolean shiny = false;
    public static final int cost = 2100;

    public Comet(){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost);
    }

    public void registerRecipes(){
        ShapedRecipe recipe0 =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"0"), item);
        recipe0.shape("**","*%"," %");
        recipe0.setIngredient('*', Material.NETHER_STAR);
        recipe0.setIngredient('%', Material.CHAIN);
        recipe0.setGroup(name);
        Bukkit.getServer().addRecipe(recipe0);

        ShapedRecipe recipe1 =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"1"), item);
        recipe1.shape("**","%*","% ");
        recipe1.setIngredient('*', Material.NETHER_STAR);
        recipe1.setIngredient('%', Material.CHAIN);
        recipe1.setGroup(name);
        Bukkit.getServer().addRecipe(recipe1);
    }

    public void ability(PlayerInteractEvent event, Action action){
        if(!(action==Action.RIGHT_CLICK_AIR || action==Action.RIGHT_CLICK_BLOCK) ||
                event.useInteractedBlock()==Event.Result.ALLOW)
            return;

        Player player = event.getPlayer();
        if(player.hasPotionEffect(PotionEffectType.WEAKNESS)){
            player.sendMessage("§7The axe weighs heavily on your arms");
            return;
        }
        ItemStack item = event.getItem();
        if(Utility.onCooldown(item))
            return;
        Utility.cooldown(item, 20);
        //temp
        if(item.getEnchantmentLevel(Enchantment.DAMAGE_ALL) > 0){
            item.removeEnchantment(Enchantment.DAMAGE_UNDEAD);
            item.removeEnchantment(Enchantment.DAMAGE_ARTHROPODS);
        }
        //
        double damage = 7 + 3 * (Utility.checkPotionEffect(player, PotionEffectType.INCREASE_DAMAGE) -
                Utility.checkPotionEffect(player, PotionEffectType.WEAKNESS));

        Location location = player.getEyeLocation();
        World world = player.getWorld();
        boolean hand = event.getHand()==EquipmentSlot.HAND;

        double distance = 50;
        Vector dir = location.getDirection().multiply(3);
        Set<LivingEntity> targets = new HashSet<>();
        for(int i=0; i<1+item.getEnchantmentLevel(Enchantment.PIERCING); i++) {
            RayTraceResult result = world.rayTrace(location, dir, 50,
                    FluidCollisionMode.NEVER, true, 0.5,
                    entity -> (entity != player &&
                            entity instanceof LivingEntity && !(entity instanceof ArmorStand) &&
                            !targets.contains(entity)));
            if (result != null) {
                LivingEntity entity = (LivingEntity) result.getHitEntity();
                if (entity != null) {
                    distance = location.distance(result.getHitEntity().getLocation());
                    targets.add(entity);
                }
                else if (result.getHitBlock() != null) {
                    distance = location.distance(result.getHitBlock().getLocation());
                    break;
                }
            }
        }
        double iterations = distance/3;

        ArmorStand stand = world.spawn(location.clone().add(0, -1, 0), ArmorStand.class);
        stand.setInvisible(true);
        stand.setInvulnerable(true);
        stand.setGravity(false);
        stand.setBasePlate(false);
        stand.setCanPickupItems(false);
        stand.addEquipmentLock(EquipmentSlot.CHEST, ArmorStand.LockType.ADDING);
        stand.addEquipmentLock(EquipmentSlot.FEET, ArmorStand.LockType.ADDING);
        stand.addEquipmentLock(EquipmentSlot.HEAD, ArmorStand.LockType.ADDING);
        stand.addEquipmentLock(EquipmentSlot.LEGS, ArmorStand.LockType.ADDING);
        stand.getPersistentDataContainer().set(Utility.key, PersistentDataType.STRING, "hI");

        if(hand) {
            stand.addEquipmentLock(EquipmentSlot.HAND, ArmorStand.LockType.REMOVING_OR_CHANGING);
            stand.addEquipmentLock(EquipmentSlot.OFF_HAND, ArmorStand.LockType.ADDING);
            stand.getEquipment().setItemInMainHand(item);
        }
        else {
            stand.addEquipmentLock(EquipmentSlot.OFF_HAND, ArmorStand.LockType.REMOVING_OR_CHANGING);
            stand.addEquipmentLock(EquipmentSlot.HAND, ArmorStand.LockType.ADDING);
            stand.getEquipment().setItemInOffHand(item);
        }

        if (player.getGameMode()!=GameMode.CREATIVE)
            Utility.addDurability(item, -1, player);

        double height = player.getLocation().getY();
        new Task(HoloItems.getInstance(), 1, 1){
            double increment = 0;
            final boolean crit = player.getLocation().getY()<height;

            public void run(){
                if(increment>=0.3*iterations) {
                    stand.remove();
                    if(!targets.isEmpty()) {
                        if (player.getGameMode()!=GameMode.CREATIVE)
                            Utility.addDurability(item, 0.5, player);
                        for (LivingEntity target : targets) {
                            if (target.isValid() && (!(target instanceof Player) || !((Player) target).isBlocking()))
                                Utility.damage(item, damage, crit, player, target, false, true, false);
                        }
                    }
                    cancel();
                    return;
                }

                double angle = increment*Math.PI;
                if(hand)
                    stand.setRightArmPose(stand.getRightArmPose().setX(angle));
                else
                    stand.setLeftArmPose(stand.getLeftArmPose().setX(angle));
                increment += 0.3;

                stand.teleport(stand.getLocation().clone().
                        add(dir.getX(), -0.3*Math.sin(angle)+dir.getY(), dir.getZ()));
            }
        };
    }
}
