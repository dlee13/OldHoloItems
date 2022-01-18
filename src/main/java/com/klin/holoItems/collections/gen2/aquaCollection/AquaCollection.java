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
//    public static final String ign = "minatoaqua";
//    public static final String uuid = "cbd93537-21e9-4852-99fc-ff459cc4ce41";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODU1M2Y1NGVlNGE4ODY0YjdkNTIyZDVjYTkyNTE2Y2U5ZTlmOWE0YWU4YmYzZmRkMmQzOWJmYjc0MzY0ZDgyOCJ9fX0=";

    public AquaCollection(){
        super(name, desc, theme, base64);
        collection.add(new OnionRing());
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        stat.put("Playtime", player.getStatistic(Statistic.PLAY_ONE_MINUTE)/1200);
        return stat;
    }
}
