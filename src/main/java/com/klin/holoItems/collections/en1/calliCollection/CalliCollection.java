package com.klin.holoItems.collections.en1.calliCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.en1.calliCollection.items.Momento;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class CalliCollection extends Collection {
    public static final String name = "Calli";
    public static final String desc = "";
    public static final String theme = "Returned to grave";
//    public static final String ign = "moricalliopeen";
//    public static final String uuid = "14abe999-ac61-432a-ba3f-216bbe9454b3";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzRiNzU5NmViOTllMzhiOTE3ZDliNmI2YzhlMjAxMDA0MDhjZThhNjZmMDBkZDVjY2E0ODdlYjJhOTA5MDg4YyJ9fX0=";

    public CalliCollection(){
        super(name, desc, theme, base64);
        collection.add(new Momento());
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
