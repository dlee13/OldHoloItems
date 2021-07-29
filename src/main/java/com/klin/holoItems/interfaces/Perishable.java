package com.klin.holoItems.interfaces;

import com.klin.holoItems.utility.Utility;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashSet;
import java.util.Set;

public interface Perishable {
    Set<EntityType> prohibited = new HashSet<EntityType>(){{
        add(EntityType.GHAST);
        add(EntityType.GIANT);
        add(EntityType.SNOWMAN);
        add(EntityType.IRON_GOLEM);
        add(EntityType.ELDER_GUARDIAN);
        add(EntityType.WITHER);
        add(EntityType.ENDER_DRAGON);
        add(EntityType.RAVAGER);
    }};

    default void cause(EntityDeathEvent event, ItemStack item){
        EntityType type = event.getEntity().getType();
        if(prohibited.contains(type))
            return;

        ItemMeta meta = item.getItemMeta();
        String souls = meta.getPersistentDataContainer().
                get(Utility.pack, PersistentDataType.STRING);
        if(souls==null)
            souls = "";
        else {
            String[] captured = souls.split(" ");
            //magic number: capacity = 5
            if(captured.length>=5) {
                souls = "";
                for(int i=0; i<4; i++)
                    souls += " " + captured[i];
            }
            else
                souls = " " + souls;
        }

        souls = type + souls;
        meta.getPersistentDataContainer().
                set(Utility.pack, PersistentDataType.STRING, souls);
        item.setItemMeta(meta);
    }

    void ability(EntityTargetLivingEntityEvent event);
}
