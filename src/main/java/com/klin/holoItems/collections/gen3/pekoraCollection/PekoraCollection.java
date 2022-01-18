package com.klin.holoItems.collections.gen3.pekoraCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.gen3.pekoraCollection.items.*;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class PekoraCollection extends Collection {
    public static final String name = "Pekora";
    public static final String desc = "";
    public static final String theme = "War crimes committed";
//    public static final String ign = "usadapekora";
//    public static final String uuid = "3e255051-4ea8-465a-af46-28f019ec7ebb";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzhiYmRiMDAxOTViZjU2OWFhNWZkYjBmNjFlNTBiZDkxMjM1ZWJiMmYzNmJhYTRhZDY2YjYyZjc0ZTU2ZGZhNSJ9fX0=";

    public PekoraCollection(){
        super(name, desc, theme, base64);
        collection.add(new DoubleUp());
        collection.add(new CarrotCannon());
        collection.add(new Compactor());
        collection.add(new PekoNote());
        collection.add(new Detonator());
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        stat.put("100 Players killed", player.getStatistic(Statistic.PLAYER_KILLS)/100);
        stat.put("Villagers killed", player.getStatistic(Statistic.KILL_ENTITY, EntityType.VILLAGER));
        stat.put("Wandering Traders killed", player.getStatistic(Statistic.KILL_ENTITY, EntityType.WANDERING_TRADER));
        stat.put("Iron Golems killed", player.getStatistic(Statistic.KILL_ENTITY, EntityType.IRON_GOLEM));
        stat.put("Snow Golems killed", player.getStatistic(Statistic.KILL_ENTITY, EntityType.SNOWMAN));
        return stat;
    }
}
