package com.klin.holoItems.collections.gen1.melCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.gen1.melCollection.items.ReadingGlasses;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class MelCollection extends Collection {
    public static final String name = "Mel";
    public static final String desc = "";
    public static final String theme = "Creatures gathered";
//    public static final String ign = "yozoramel";
//    public static final String uuid = "ece6a72b-8cd4-497b-b01f-aed07229fa3e";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWRlNmMxMWU0ODk4NmFlNWM0MTE3N2UyZmIzMTdiMGU3NTdiNTUxM2RjOTVlOGJhMTg5MWQzMjY4YjVhOGZjMCJ9fX0=";

    public MelCollection(){
        super(name, desc, theme, base64);
        collection.add(new ReadingGlasses());
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        stat.put("Bats farmed", player.getStatistic(Statistic.KILL_ENTITY, EntityType.BAT));
        return stat;
    }
}
