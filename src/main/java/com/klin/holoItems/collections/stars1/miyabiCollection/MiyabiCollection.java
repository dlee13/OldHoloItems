package com.klin.holoItems.collections.stars1.miyabiCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class MiyabiCollection extends Collection {
    public static final String name = "Miyabi";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "miyabi_hanasaki";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTM2NDQ4NDcyZjRiOWE0OGY4MDM0NThhZWUxMDgwZDliODJmNmFjYzE1OWYzMTJkZWUxZjAwZjM5MGYyYWVhNyJ9fX0=";

    public MiyabiCollection(){
        super(name, desc, theme, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
