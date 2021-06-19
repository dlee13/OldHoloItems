package com.klin.holoItems.collections.gen2.aquaCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.gen2.aquaCollection.items.OnionRing;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class AquaCollection extends Collection {
    public static final String name = "Aqua";
    public static final String desc = "";
    public static final String theme = "Performed as a Maid";
    public static final String ign = "minatoaqua";
    public static final String uuid = "cbd93537-21e9-4852-99fc-ff459cc4ce41";

    public static final char key = 'k';

    public AquaCollection(){
        super(name, desc, theme, ign, uuid, key);
        collection.put(OnionRing.key, new OnionRing());
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        stat.put("Playtime", player.getStatistic(Statistic.PLAY_ONE_MINUTE)/1200);

        return stat;
    }
}
