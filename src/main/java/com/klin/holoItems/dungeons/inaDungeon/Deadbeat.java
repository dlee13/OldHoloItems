package com.klin.holoItems.dungeons.inaDungeon;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

public class Deadbeat {
    private Location center;
    public final Mob deadbeat;
    private boolean load;

    public Deadbeat(World world, Location location, Class<? extends Mob> mob){
        this.center = location;
        this.deadbeat = world.spawn(center, mob);
        this.load = true;
        deadbeat.setAware(false);
        deadbeat.setGravity(false);
        new Task(HoloItems.getInstance(), 1, 1) {
            int increment = 0;
            Location loc = center.clone();
            int x = 0;
            int z = 0;
            Player focus = null;
            public void run() {
                if (!deadbeat.isValid() || deadbeat.isDead()) {
                    cancel();
                    return;
                }
                deadbeat.teleport(loc.clone().add(0.2 * increment * x, 0.2 * (5 - Math.abs(increment % 10 - 5)), 0.2 * increment * z));
                increment++;
                if (increment > 10) {
                    increment = 0;
                    loc = deadbeat.getLocation();
                    boolean alone = true;
                    for(Entity entity : deadbeat.getNearbyEntities(20, 2, 20)){
                        if(focus==null){
                            if(entity instanceof Player)
                                focus = (Player) entity;
                        }
                        if(entity.equals(focus)) {
                            alone = false;
                            break;
                        }
                    }
                    Vector dir;
                    if(alone || Math.random() < 0.5) {
                        if(alone)
                            focus = null;
                        double random = Math.random() * 3;
                        random = Math.max(0, Math.min(2, random + (center.getBlockX() - loc.getBlockX()) * 0.2));
                        x = (int) random - 1;
                        random = Math.max(0, Math.min(2, Math.random() * 3 + (center.getBlockZ() - loc.getBlockZ()) * 0.2));
                        z = (int) random - 1;
                        dir = new Vector(x, 0, z);
                    } else{
                        center = focus.getLocation();
                        x = (int) Math.signum(center.getBlockX() - loc.getBlockX());
                        z = (int) Math.signum(center.getBlockZ() - loc.getBlockZ());
                        dir = new Vector(x, 0, z);
                        center = deadbeat.getLocation();
                        if(load && Math.random() < 0.5){
                            load = false;
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
                            stand.getEquipment().setItemInMainHand(new ItemStack(Material.BONE));
                            stand.setRightArmPose(stand.getRightArmPose().setZ(-Math.PI/2));
                            Location origin = center.clone();
                            Vector direction = dir.clone().normalize();
                            new Task(HoloItems.getInstance(), 1, 1){
                                int increment = 0;
                                boolean straight = true;
                                boolean boomerang = false;
                                double angle = 0.4 * Math.signum(focus.getLocation().subtract(origin).toVector().setY(0).normalize().crossProduct(direction).getY());
                                public void run(){
                                    Location loc = stand.getLocation();
                                    if(increment>=120 || increment>0 && loc.distance(origin) < 1 || !stand.isValid()) {
                                        load = true;
                                        stand.remove();
                                        cancel();
                                        return;
                                    }
                                    if(straight) {
                                        if(focus==null || loc.distance(origin) > focus.getLocation().distance(origin))
                                            straight = false;
                                    } else if(angle!=0){
                                        if(boomerang) {
                                            double magnitude = Math.abs(stand.getLocation().subtract(origin).toVector().setY(0).normalize().crossProduct(direction).getY());
                                            if (magnitude < Math.abs(angle))
                                                angle = magnitude * Math.signum(angle);
                                        } else
                                            boomerang = true;
                                        direction.rotateAroundY(angle);
                                    } else if(increment<100)
                                        increment = 100;
                                    try {
                                        stand.teleport(loc.add(direction));
                                    }catch (IllegalArgumentException e){
                                        load = true;
                                        stand.remove();
                                        cancel();
                                        return;
                                    }
                                    for(Entity entity : stand.getNearbyEntities(0.5, 0.5, 0.5)){
                                        if(entity instanceof Player) {
                                            Player player = (Player) entity;
                                            player.setNoDamageTicks(0);
                                            player.damage(1);
                                            player.setNoDamageTicks(0);
                                        }
                                    }
                                    stand.setRightArmPose(stand.getRightArmPose().setX(increment*Math.PI/3));
                                    increment++;
                                }
                            };
                        }
                    }
                    loc.setDirection(dir);
                }
            }
        };
    }
}
