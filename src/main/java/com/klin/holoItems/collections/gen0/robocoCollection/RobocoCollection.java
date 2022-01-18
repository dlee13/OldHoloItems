package com.klin.holoItems.collections.gen0.robocoCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.gen0.robocoCollection.items.Magnet;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class RobocoCollection extends Collection {
    public static final String name = "Roboco";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "robocosan";
//    public static final String uuid = "132dfbe1-071d-4932-a5e0-bdc323537bd4";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWIyOWVlYmM5ZWJkZGFiMmZlNmQ4Nzc1YTdiOGE5NGExZDQxMDg5YmI4MTc2Y2E1ZTY2OWU2ZDYxYTgwNjdmNCJ9fX0=";

    public RobocoCollection(){
        super(name, desc, theme, base64);
        collection.add(new Magnet());
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
