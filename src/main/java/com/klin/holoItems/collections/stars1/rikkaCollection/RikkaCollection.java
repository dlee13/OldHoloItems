package com.klin.holoItems.collections.stars1.rikkaCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class RikkaCollection extends Collection {
    public static final String name = "Rikka";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "rikka415";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzFhYmE3ZTZlNjBiY2M4MDgyY2QyNTExZjZkOTZjODJkYTMxODRhMGJiMmI2MmYxN2QyMDBkNDhjOTZiM2U3MyJ9fX0=";

    public RikkaCollection(){
        super(name, desc, theme, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
