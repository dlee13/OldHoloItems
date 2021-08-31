package com.klin.holoItems.dungeons.inaDungeon;

import com.klin.holoItems.Collections;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.collections.dungeons.inaDungeonCollection.items.AshWood;
import com.klin.holoItems.collections.dungeons.inaDungeonCollection.items.BlackPowder;
import com.klin.holoItems.collections.dungeons.inaDungeonCollection.items.TemperedBottle;
import com.klin.holoItems.collections.gen3.noelCollection.items.MilkBottle;
import com.klin.holoItems.dungeons.Resetable;
import com.klin.holoItems.utility.SkullCreator;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
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
                        if(item.getPickupDelay()==0 && focus.getUniqueId().equals(item.getThrower())) {
                            ItemStack itemStack = item.getItemStack();
                            ItemMeta meta = itemStack.getItemMeta();
                            String id = meta==null?"":meta.getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
                            id = id==null?"":id;
                            boolean accept = false;
                            switch(id){
                                case AshWood.id:
                                case BlackPowder.id:
                                case TemperedBottle.id:
                                    accept = mix(itemStack, Collections.findItem(MilkBottle.id).item);
                                    break;
                                case "":
                                    double divisor;
                                    switch (itemStack.getType()){
                                        case POTION:
                                        case SPLASH_POTION:
                                            divisor = 1;
                                            break;
                                        case LINGERING_POTION:
                                            divisor = 4;
                                            break;
                                        default:
                                            divisor = -1;
                                    }
                                    if(divisor==-1) {
                                        serve(focus, itemStack);
                                        accept = true;
                                        break;
                                    }
                                    PotionMeta potionMeta = (PotionMeta) meta;
                                    PotionData potionData = potionMeta.getBasePotionData();
                                    PotionType type = potionData.getType();
                                    PotionEffectType effectType = type.getEffectType();
                                    Integer duration = Utility.durations.get(type);
                                    if(effectType==null || duration==null) {
                                        serve(focus, itemStack);
                                        accept = true;
                                        break;
                                    }
                                    String name = potionMeta.getDisplayName();
                                    if(name.isEmpty())
                                        name = "§6Enhanced Potion";
                                    Color color = potionMeta.getColor();
                                    potionMeta.setBasePotionData(new PotionData(PotionType.MUNDANE));
                                    potionMeta.setColor(color);
                                    potionMeta.setDisplayName(name);
                                    potionMeta.addCustomEffect(new PotionEffect(effectType, (int) (duration/(divisor*(potionData.isExtended()?0.5:potionData.isUpgraded()?2:1))), potionData.isUpgraded()?3:2), true);
                                    ItemStack glass = itemStack.clone();
                                    itemStack.setItemMeta(potionMeta);
                                    accept = mix(glass, itemStack);
                                    break;
                                default:
                                    serve(focus, itemStack);
                            }
                            if(accept){
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

    public boolean mix(ItemStack glass, ItemStack drink){
        if(mixing)
            return false;
        mixing = true;
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
        bottle.getEquipment().setHelmet(glass);

        Vector dir = new Vector(0.15, 0, 0.05);
        Bukkit.getScheduler().cancelTask(taskId);
        taskId = new Task(HoloItems.getInstance(), 1, 1){
            int increment = 0;
            public void run(){
                Location location = stand.getLocation();
                if(increment>=20){
                    if(increment==20) {
                        stand.teleport(location.setDirection(new Vector(0, 0, 1)));
                        bottle.getEquipment().setHelmet(null);
                    }
                    else if(increment>=80){
                        if(increment==80) {
                            dir.multiply(-1);
                            location.setDirection(dir);
                            bottle.getEquipment().setHelmet(drink);
                        }
                        stand.teleport(location.add(dir));
                        bottle.teleport(location.add(0, -1, 0));
                        if(increment>=100) {
                            bottle.remove();
                            serve(focus, drink);
                            mixing = false;
                            idle();
                            cancel();
                        }
                    }
                }
                else {
                    if(increment==0)
                        location = loc.clone().setDirection(dir);
                    stand.teleport(location.add(dir));
                    bottle.teleport(location.add(0, -1, 0));
                }
                increment++;
            }
        }.getTaskId();
        return true;
    }

    private void serve(Player player, ItemStack drink){
        Location eyeLocation = player.getEyeLocation();
        Vector dir = eyeLocation.getDirection();
        Item item = world.spawn(eyeLocation.add(dir), Item.class);
        item.setItemStack(drink);
        item.setVelocity(dir.multiply(-1));
    }

    private void pocket(Player player, int amount){

    }

    public void reset(){
        stand.remove();
        Bukkit.getScheduler().cancelTask(taskId);
    }
}
