package com.klin.holoItems.dungeons.inaDungeon;

import com.klin.holoItems.Collections;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.collections.gen3.noelCollection.items.MilkBottle;
import com.klin.holoItems.dungeons.Resetable;
import com.klin.holoItems.utility.SkullCreator;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AoShop implements Resetable {
    //shop world -123.5 53.1 -124.5
    private final World world;
    private final Location loc;
    private final ArmorStand stand;
    private int taskId;
    private Player focus;
    private boolean mixing;
    public final Map<Player, Integer> money;

    public AoShop(World world, Location loc){
        this.world = world;
        this.loc = loc;
        stand = world.spawn(loc.setDirection(new Vector(0, 0, -1)), ArmorStand.class);
        stand.setInvisible(true);
        stand.setInvulnerable(true);
        stand.setGravity(false);
        stand.setBasePlate(false);
        stand.setCanPickupItems(false);
        stand.addEquipmentLock(EquipmentSlot.CHEST, ArmorStand.LockType.ADDING);
        stand.addEquipmentLock(EquipmentSlot.FEET, ArmorStand.LockType.ADDING);
        stand.addEquipmentLock(EquipmentSlot.LEGS, ArmorStand.LockType.ADDING);
        stand.addEquipmentLock(EquipmentSlot.HAND, ArmorStand.LockType.ADDING);
        stand.addEquipmentLock(EquipmentSlot.OFF_HAND, ArmorStand.LockType.ADDING);
        stand.getPersistentDataContainer().set(Utility.key, PersistentDataType.STRING, "hI");
        stand.getEquipment().setHelmet(SkullCreator.itemFromBase64("ewogICJ0aW1lc3RhbXAiIDogMTYzMDI5NDI1MTU3NCwKICAicHJvZmlsZUlkIiA6ICI5MWYwNGZlOTBmMzY0M2I1OGYyMGUzMzc1Zjg2ZDM5ZSIsCiAgInByb2ZpbGVOYW1lIiA6ICJTdG9ybVN0b3JteSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8zNWE2MjljYmU5MWNlNzdkMjM1N2UzZmQ5OWJkMDFiOWY1NTQ4MDcyYmJjNjkxYmVlMTdmZDM5NDc2NDQ2MWJjIiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0="));
        focus = null;
        mixing = false;
        money = new HashMap<>();
        idle();
    }

    public void idle(){
        stand.teleport(loc);
        Bukkit.getScheduler().cancelTask(taskId);
        taskId = new Task(HoloItems.getInstance(), 1, 1){
            double increment = 0;
            public void run(){
                Location loc = stand.getLocation().add(0, Math.sin(increment)/12, 0);
                List<Entity> nearby = stand.getNearbyEntities(6, 2, 6);
                boolean alone = true;
                for(Entity entity : nearby){
                    if(focus==null){
                        if(entity instanceof Player)
                            focus = (Player) entity;
                    }
                    else if(entity instanceof Item){
                        Item item = (Item) entity;
                        if(focus.getUniqueId().equals(item.getThrower())) {
                            ItemStack itemStack = item.getItemStack();
                            if (itemStack.isSimilar(new ItemStack(Material.GLASS_BOTTLE)) && mix()) {
                                int amount = itemStack.getAmount();
                                if (amount == 1)
                                    item.remove();
                                else {
                                    itemStack.setAmount(amount - 1);
                                    item.setItemStack(itemStack);
                                }
                            }
                        }
                    }
                    if(entity.equals(focus))
                        alone = false;
                }
                if(alone) {
                    focus = null;
                    loc.setDirection(new Vector(0, 0, -1));
                }
                else
                    loc.setDirection(focus.getLocation().subtract(loc).toVector());
                stand.teleport(loc);
                increment += Math.PI/20;
                if(Math.abs(increment-Math.PI*2)<0.01)
                    increment = 0;
            }
        }.getTaskId();
    }

    public boolean mix(){
        if(mixing)
            return false;
        mixing = true;
        stand.teleport(loc);
        ArmorStand bottle = world.spawn(loc.clone().add(0, -1, 0), ArmorStand.class);
        bottle.setInvisible(true);
        bottle.setInvulnerable(true);
        bottle.setGravity(false);
        bottle.setBasePlate(false);
        bottle.setCanPickupItems(false);
        bottle.addEquipmentLock(EquipmentSlot.CHEST, ArmorStand.LockType.ADDING);
        bottle.addEquipmentLock(EquipmentSlot.FEET, ArmorStand.LockType.ADDING);
        bottle.addEquipmentLock(EquipmentSlot.LEGS, ArmorStand.LockType.ADDING);
        bottle.addEquipmentLock(EquipmentSlot.HAND, ArmorStand.LockType.ADDING);
        bottle.addEquipmentLock(EquipmentSlot.OFF_HAND, ArmorStand.LockType.ADDING);
        bottle.getPersistentDataContainer().set(Utility.key, PersistentDataType.STRING, "hI");
        bottle.getEquipment().setHelmet(new ItemStack(Material.GLASS_BOTTLE));

        Vector dir = new Vector(0.15, 0, 0.05);
        Bukkit.getScheduler().cancelTask(taskId);
        taskId = new Task(HoloItems.getInstance(), 1, 1){
            int increment = 0;
            public void run(){
                Location loc = stand.getLocation();
                if(increment>=20){
                    if(increment==20) {
                        bottle.remove();
                        stand.teleport(loc.setDirection(new Vector(0, 0, 1)));
                    }
                    else if(increment>=80){
                        if(increment==80) {
                            dir.multiply(-1);
                            loc.setDirection(dir);
                        }
                        stand.teleport(loc.add(dir));
                        if(increment>=100) {
                            Location location = focus.getEyeLocation();
                            Vector dir = location.getDirection();
                            Item item = world.spawn(location.add(dir), Item.class);
                            item.setItemStack(Collections.findItem(MilkBottle.id).item);
                            item.setVelocity(dir.multiply(-1));
                            mixing = false;
                            idle();
                            cancel();
                        }
                    }
                }
                else {
                    if(increment==0)
                        loc.setDirection(dir);
                    stand.teleport(loc.add(dir));
                    bottle.teleport(loc.add(0, -1, 0));
                }
                increment++;
            }
        }.getTaskId();
        return true;
    }

    public void reset(){
        stand.remove();
        Bukkit.getScheduler().cancelTask(taskId);
    }
}
