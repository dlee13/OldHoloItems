package com.klin.holoItems.collections.misc.franCollection.items;

import com.klin.holoItems.Item;
import com.klin.holoItems.collections.misc.franCollection.FranCollection;
import com.klin.holoItems.interfaces.combinable.Combinable;
import com.klin.holoItems.interfaces.combinable.Targetable;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LockOn extends Item implements Combinable, Targetable {
    public static final String name = "lockOn";
    public static final Set<Enchantment> accepted = null;
    private static Map<Entity, LivingEntity> pair = new HashMap<>();

    private static final Material material = Material.LIGHT_GRAY_DYE;
    private static final int quantity = 1;
    private static final String lore =
            "§6Ability" +"/n"+
                "Will not be distracted";
    private static final int durability = 0;
    public static final boolean stackable = false;
    private static final boolean shiny = true;

    public static final int cost = -1;
    public static final char key = '3';

    public LockOn(){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost,
                ""+FranCollection.key+key, key);
    }

    public void registerRecipes(){}

    public void ability(EntityTargetLivingEntityEvent event) {
        Entity entity = event.getEntity();
        LivingEntity target = pair.get(entity);
        if(target.isValid() && target.getLocation().distance(entity.getLocation())<=5)
            event.setCancelled(true);
        else
            pair.remove(entity);
    }
}