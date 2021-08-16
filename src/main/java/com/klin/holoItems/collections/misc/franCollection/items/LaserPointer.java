package com.klin.holoItems.collections.misc.franCollection.items;

import com.klin.holoItems.Item;
import com.klin.holoItems.collections.misc.franCollection.FranCollection;
import com.klin.holoItems.interfaces.customMobs.Combinable;
import com.klin.holoItems.interfaces.customMobs.Spawnable;
import com.klin.holoItems.interfaces.customMobs.Targetable;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LaserPointer extends Item implements Combinable, Targetable, Spawnable {
    public static final String name = "laserPointer";
    public static final Set<Enchantment> accepted = null;
    protected static Map<Entity, LivingEntity> pair = new HashMap<>();

    private static final Material material = Material.END_ROD;
    private static final int quantity = 1;
    private static final String lore =
            "Will not be distracted";
    private static final int durability = 0;
    public static final boolean stackable = true;
    private static final boolean shiny = true;

    public static final int cost = -1;
    public static final char key = '4';

    public LaserPointer(){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost,
                ""+FranCollection.key+key, key);
    }

    public LaserPointer(String name, Material material, String lore, String id, char key){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost,
                ""+FranCollection.key+key, key);
    }

    public void registerRecipes(){}

    public void ability(EntityTargetLivingEntityEvent event) {
        Entity entity = event.getEntity();
        LivingEntity target = pair.get(entity);
        if(target==null)
            pair.put(entity, event.getTarget());
        else if(target.isValid() && target.getLocation().distance(entity.getLocation())<=10) {
            event.setCancelled(true);
            Mob mob = (Mob) entity;
            if(mob.getTarget()==null || !mob.getTarget().equals(target))
                mob.setTarget(target);
        }
        else
            pair.remove(entity);
    }

    public void ability(LivingEntity entity, String info) {
        Entity target = null;
        double closest = 11;
        Location loc = entity.getLocation();
        for(Entity nearby : entity.getNearbyEntities(10, 2, 10)){
            if(!(nearby instanceof LivingEntity))
                continue;
            double distance = loc.distance(nearby.getLocation());
            if(distance < closest){
                closest = distance;
                target = nearby;
            }
        }
        if(target!=null) {
            ((Mob) entity).setTarget((LivingEntity) target);
            pair.put(entity, (LivingEntity) target);
        }
    }
}