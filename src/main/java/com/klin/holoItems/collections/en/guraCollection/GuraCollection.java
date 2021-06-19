package com.klin.holoItems.collections.en.guraCollection;

import com.klin.holoItems.Collection;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class GuraCollection extends Collection {
    public static final String name = "Gura";
    public static final String desc = "";
    public static final String theme = "Shrimps caught";
    public static final String ign = "gaugura";
    public static final String uuid = "4b05d8d3-2362-43c2-9494-b2866c701267";

    public static final char key = 'M';

    public GuraCollection(){
        super(name, desc, theme, ign, uuid, key);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        stat.put("Caught with Rod", player.getStatistic(Statistic.FISH_CAUGHT));
        stat.put("Cod", player.getStatistic(Statistic.KILL_ENTITY, EntityType.COD));
        stat.put("Elder Guardian", player.getStatistic(Statistic.KILL_ENTITY, EntityType.ELDER_GUARDIAN));
        stat.put("Guardian", player.getStatistic(Statistic.KILL_ENTITY, EntityType.GUARDIAN));
        stat.put("Salman", player.getStatistic(Statistic.KILL_ENTITY, EntityType.SALMON));
        stat.put("Squid", player.getStatistic(Statistic.KILL_ENTITY, EntityType.SQUID));
        stat.put("Tropical Fish", player.getStatistic(Statistic.KILL_ENTITY, EntityType.TROPICAL_FISH));
        stat.put("Pufferfish", player.getStatistic(Statistic.KILL_ENTITY, EntityType.PUFFERFISH));

        return stat;
    }
}
