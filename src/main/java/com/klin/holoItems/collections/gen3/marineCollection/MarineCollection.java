package com.klin.holoItems.collections.gen3.marineCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.gen3.marineCollection.items.PiratesHook;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class MarineCollection extends Collection {
    public static final String name = "Marine";
    public static final String desc = "";
    public static final String theme = "Treasure plundered";
//    public static final String ign = "houshou_marine";
//    public static final String uuid = "d604942f-2b4b-4452-a8da-3dbf83e235c3";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTQ1Mjc5OTMwMTU2OGFiZDVjNWU5YWJjZDU4MzE3NmJiNTkxMGQxNzA4NzE1MTA5YmY2Y2Y1MjM3OWY1N2U3In19fQ==";

    public MarineCollection(){
        super(name, desc, theme, base64);
        collection.add(new PiratesHook());
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
