package com.klin.holoItems.collections.en.calliCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.en.calliCollection.items.MomentoMori;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class CalliCollection extends Collection {
    public static final String name = "Calli";
    public static final String desc = "";
    public static final String theme = "Returned to grave";
    public static final String ign = "moricalliopeen";
    public static final String uuid = "14abe999-ac61-432a-ba3f-216bbe9454b3";

    public static final char key = 'J';

    public CalliCollection(){
        super(name, desc, theme, ign, uuid, key);
        collection.put(MomentoMori.key, new MomentoMori());
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        stat.put("Drowned", player.getStatistic(Statistic.KILL_ENTITY, EntityType.DROWNED));
        stat.put("Giant", player.getStatistic(Statistic.KILL_ENTITY, EntityType.GIANT));
        stat.put("Husk", player.getStatistic(Statistic.KILL_ENTITY, EntityType.HUSK));
        stat.put("Phantom", player.getStatistic(Statistic.KILL_ENTITY, EntityType.PHANTOM));
        stat.put("Skeleton", player.getStatistic(Statistic.KILL_ENTITY, EntityType.SKELETON));
        stat.put("Skeleton Horse", player.getStatistic(Statistic.KILL_ENTITY, EntityType.SKELETON_HORSE));
        stat.put("Stray", player.getStatistic(Statistic.KILL_ENTITY, EntityType.STRAY));
        stat.put("Zombie", player.getStatistic(Statistic.KILL_ENTITY, EntityType.ZOMBIE));
        stat.put("Zombie Horse", player.getStatistic(Statistic.KILL_ENTITY, EntityType.ZOMBIE_HORSE));
        stat.put("Zombie Villager", player.getStatistic(Statistic.KILL_ENTITY, EntityType.ZOMBIE_VILLAGER));

        return stat;
    }
}
