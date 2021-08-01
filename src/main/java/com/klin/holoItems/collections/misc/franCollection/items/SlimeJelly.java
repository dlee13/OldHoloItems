package com.klin.holoItems.collections.misc.franCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.collections.misc.franCollection.FranCollection;
import com.klin.holoItems.interfaces.customMobs.Retaliable;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.stream.Collectors;

public class SlimeJelly extends LaserPointer implements Retaliable {
    public static final String name = "slimeJelly";

    private static final Material material = Material.SCUTE;
    private static final String lore =
            "§6Ability" +"/n"+
                    "Find opportunities to rejuvenate";

    public static final char key = '5';

    public SlimeJelly(){
        super(name, material, lore, ""+FranCollection.key+key, key);
    }

    @Override
    public void ability(LivingEntity entity, String info) {}

    public void ability(EntityDamageByEntityEvent event, Entity damager) {
        pair.remove(damager);
        Slime slime = (Slime) damager;
        LivingEntity entity = (LivingEntity) event.getEntity();
        if(entity instanceof Slime){
            int size = slime.getSize();
            int targetSize = ((Slime) entity).getSize();
            if(size==targetSize){
                entity.remove();
                slime.setSize(size+2);
            }
            else if(size>targetSize){
                entity.remove();
                slime.setSize(size+1);
            }
            else{
                slime.remove();
                slime = ((Slime) entity);
                slime.setSize(targetSize+1);
            }
            Entity target = null;
            double closest = 11;
            Location loc = slime.getLocation();
            for(Entity nearby : slime.getNearbyEntities(10, 2, 10)){
                if(!(nearby instanceof Player))
                    continue;
                double distance = loc.distance(nearby.getLocation());
                if(distance < closest){
                    closest = distance;
                    target = nearby;
                }
            }
            if(target!=null) {
                slime.setTarget((LivingEntity) target);
                pair.put(slime, (LivingEntity) target);
            }
        }
        else {
            //toDo: change velocity to launch players beyond 10 block radius
            entity.setVelocity(entity.getLocation().subtract(slime.getLocation()).toVector().normalize().multiply(4));
            entity.damage(slime.getSize()*2);
            Entity target = null;
            double closest = 11;
            Location loc = slime.getLocation();
            for(Entity nearby : slime.getNearbyEntities(10, 2, 10)){
                if(!(nearby instanceof Slime))
                    continue;
                String modifiers = nearby.getPersistentDataContainer().get(Utility.pack, PersistentDataType.STRING);
                if(modifiers==null || !Arrays.stream(modifiers.split("-")).collect(Collectors.toSet()).contains(id))
                    continue;
                double distance = loc.distance(nearby.getLocation());
                if(distance < closest){
                    closest = distance;
                    target = nearby;
                }
            }
            if(target!=null) {
                Slime assimilate = (Slime) target;
                (slime).setTarget(assimilate);
                pair.put(slime, assimilate);

                int distance = (slime.getSize()+assimilate.getSize())/2;
                new Task(HoloItems.getInstance(), 1, 1){
                    int increment = 0;
                    public void run(){
                        if(increment>=1200){
                            cancel();
                            return;
                        }
                        if(damager.getLocation().distance(assimilate.getLocation())<=distance) {
                            assimilate.damage(1, damager);
                            cancel();
                        }
                        increment++;
                    }
                };
            }
        }
    }
}
