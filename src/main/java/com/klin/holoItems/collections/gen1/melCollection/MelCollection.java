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
    public static final String ign = "yozoramel";
    public static final String uuid = "ece6a72b-8cd4-497b-b01f-aed07229fa3e";

    public static final char key = 'f';

    public MelCollection(){
        super(name, desc, theme, ign, uuid, key);
        collection.put(ReadingGlasses.key, new ReadingGlasses());
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        stat.put("Bats farmed", player.getStatistic(Statistic.KILL_ENTITY, EntityType.BAT));

        return stat;
    }
}
