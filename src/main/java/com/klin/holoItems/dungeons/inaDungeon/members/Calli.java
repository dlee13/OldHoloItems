package com.klin.holoItems.dungeons.inaDungeon.members;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

public class Calli extends Member {
    private final Map<Material, Double> tools;
    private boolean hit;

    public Calli(Player player){
        super(player, BarColor.PINK);
        tools = new HashMap<>();
        tools.put(Material.WOODEN_AXE, 7.);
        tools.put(Material.GOLDEN_AXE, 7.);
        tools.put(Material.STONE_AXE, 9.);
        tools.put(Material.IRON_AXE, 9.);
        tools.put(Material.DIAMOND_AXE, 9.);
        tools.put(Material.NETHERITE_AXE, 10.);
        for(Material hoe : Utility.hoes)
            tools.put(hoe, 1.);
        tools.put(Material.WOODEN_PICKAXE, 2.);
        tools.put(Material.GOLDEN_PICKAXE, 2.);
        tools.put(Material.STONE_PICKAXE, 3.);
        tools.put(Material.IRON_PICKAXE, 4.);
        tools.put(Material.DIAMOND_PICKAXE, 5.);
        tools.put(Material.NETHERITE_PICKAXE, 6.);
        tools.put(Material.WOODEN_SHOVEL, 2.5);
        tools.put(Material.GOLDEN_SHOVEL, 2.5);
        tools.put(Material.STONE_SHOVEL, 3.5);
        tools.put(Material.IRON_SHOVEL, 4.5);
        tools.put(Material.DIAMOND_SHOVEL, 5.5);
        tools.put(Material.NETHERITE_SHOVEL, 6.5);
        tools.put(Material.WOODEN_SWORD, 4.);
        tools.put(Material.GOLDEN_SWORD, 4.);
        tools.put(Material.STONE_SWORD, 5.);
        tools.put(Material.IRON_SWORD, 6.);
        tools.put(Material.DIAMOND_SWORD, 7.);
        tools.put(Material.NETHERITE_SWORD, 8.);
        hit = false;
    }

    public void ability(double angle, PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if(item==null)
            return;
        Material material = item.getType();
        if(!tools.containsKey(material))
            return;
        if(Math.abs(angle)<Math.PI*1.2){
            if(!hit && event.getAction()==Action.LEFT_CLICK_AIR){
                Location loc = player.getEyeLocation();
                RayTraceResult result = player.getWorld().rayTrace(loc, loc.getDirection(), 4+Math.max(0, 20-player.getHealth())/4+(Utility.hoes.contains(material)?4:0), FluidCollisionMode.NEVER, true, 0.5, entity -> (entity != player && entity instanceof LivingEntity && !(entity instanceof ArmorStand)));
                if (result!=null) {
                    LivingEntity entity = (LivingEntity) result.getHitEntity();
                    if(entity!=null) {
                        Utility.damage(item, tools.get(material), !((Entity) player).isOnGround() && player.getVelocity().getY() < 0, player, entity, true, false, false);
                        entity.setVelocity(entity.getVelocity().add(entity.getLocation().subtract(loc).toVector().setY(0).normalize().setY(0.45)));
                    }
                }
            }
            return;
        }
        if(cooldown)
            return;
        Action action = event.getAction();
        if(action!=Action.LEFT_CLICK_AIR && action!=Action.LEFT_CLICK_BLOCK)
            return;
        cooldown = true;

        World world = player.getWorld();
        Location loc = player.getLocation();
        ArmorStand stand = world.spawn(loc, ArmorStand.class);
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
        stand.getPersistentDataContainer().set(Utility.key, PersistentDataType.STRING, "hI");
        stand.getEquipment().setItemInMainHand(item);
        stand.setRightArmPose(new EulerAngle(-0.142+Math.PI*loc.getPitch()/180, 0.04, 0.0145));

        Vector dir = loc.getDirection().multiply(0.05);
        int time = (int) Math.sqrt((Math.max(0, 20-player.getHealth())/4+(Utility.hoes.contains(material)?4:0))*40)+10;
        PotionEffect glow = new PotionEffect(PotionEffectType.GLOWING, time, 1);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("Command: After Image"));
        new Task(HoloItems.getInstance(), 0, 1){
            int increment = 0;
            public void run(){
                if(increment>=time || !stand.isValid()){
                    if(stand.isValid())
                        stand.remove();
                    cooldown = false;
                    cancel();
                    return;
                }
                Location loc = stand.getLocation();
                world.spawnParticle(Particle.LAVA, loc, 1);
                stand.teleport(loc.add(dir.clone().multiply(increment)));
                for(Entity entity : stand.getNearbyEntities(1, 1, 1)){
                    if(entity instanceof LivingEntity && !(entity instanceof ArmorStand) && !player.equals(entity)) {
                        entity.setVelocity(entity.getVelocity().add(dir));
                        LivingEntity living = (LivingEntity) entity;
                        living.setNoDamageTicks(0);
                        Utility.damage(item, tools.get(material), false, player, living, true, false, true);
                        living.setNoDamageTicks(0);
                        living.addPotionEffect(glow);
                    }
                }
                increment++;
            }
        };
    }

    public void attack(EntityDamageByEntityEvent event, Entity entity) {
        if(hit || !(entity instanceof LivingEntity))
            return;
        double damage = event.getFinalDamage();
        if(damage==0)
            return;
        LivingEntity living = (LivingEntity) entity;
        if(living.hasPotionEffect(PotionEffectType.GLOWING)){
            damage *= 2;
            living.removePotionEffect(PotionEffectType.GLOWING);
        }
        double health = player.getHealth();
        double modifier = (20-health)/4;
        event.setDamage(damage+modifier);
        player.setHealth(Math.min(20, health+damage*modifier/5));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, (int) modifier - 2));
        hit = true;
        new BukkitRunnable(){
            public void run() {
                hit = false;
            }
        }.runTask(HoloItems.getInstance());
    }

    public void defend(EntityDamageByEntityEvent event, Entity damager) {
    }

    public void burst(int charge) {
        player.damage(charge);
        Location loc = player.getLocation();
        double radius = (double) charge/2;
        PotionEffect glow = new PotionEffect(PotionEffectType.GLOWING, 60, 1);
        for(Entity entity : player.getNearbyEntities(radius, radius, radius)){
            if(entity instanceof LivingEntity && !(entity instanceof ArmorStand)) {
                entity.setVelocity(entity.getVelocity().add(entity.getLocation().subtract(loc).toVector().setY(0).normalize().setY(0.45)));
                LivingEntity living = (LivingEntity) entity;
                living.setNoDamageTicks(0);
                living.damage(charge);
                living.setNoDamageTicks(0);
                living.addPotionEffect(glow);
            }
        }
    }
}
