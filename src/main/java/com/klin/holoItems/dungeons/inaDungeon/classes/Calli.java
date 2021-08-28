package com.klin.holoItems.dungeons.inaDungeon.classes;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;

public class Calli extends Member {
    private final Set<Material> tools;

    public Calli(Player player){
        super(player);
        tools = new HashSet<>(){{
            addAll(Utility.axes);
            addAll(Utility.hoes);
            addAll(Utility.pickaxes);
            addAll(Utility.shovels);
            addAll(Utility.swords);
        }};
    }

    public void ability(double angle, PlayerInteractEvent event) {
        Action action = event.getAction();
        if(cooldown || action!=Action.LEFT_CLICK_AIR && action!=Action.LEFT_CLICK_BLOCK)
            return;
        ItemStack item = event.getItem();
        if(item==null)
            return;
        Material material = item.getType();
        if(!tools.contains(material))
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
        Set<LivingEntity> glowing = new HashSet<>();
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("Command: After Image"));
        new Task(HoloItems.getInstance(), 0, 1){
            int increment = 0;
            public void run(){
                if(increment>=30 || !stand.isValid()){
                    if(stand.isValid())
                        stand.remove();
                    cooldown = false;
                    for(LivingEntity glow : glowing)
                        glow.setGlowing(false);
                    cancel();
                    return;
                }
                Location loc = stand.getLocation();
                world.spawnParticle(Particle.LAVA, loc, 1);
                if(increment>=10) {
                    stand.teleport(loc.add(dir.clone().multiply(increment)));
                    for(Entity entity : stand.getNearbyEntities(1, 1, 1)){
                        if(entity instanceof LivingEntity) {
                            LivingEntity living = (LivingEntity) entity;
                            living.setNoDamageTicks(0);
                            Utility.damage(item, 10, true, player, living, true, false, true);
                            living.setNoDamageTicks(0);
                            glowing.add(living);
                            living.setGlowing(true);
                            living.setVelocity(living.getVelocity().add(new Vector(0, 0.15, 0)));
                        }
                    }
                }
                increment++;
            }
        };
    }
}
