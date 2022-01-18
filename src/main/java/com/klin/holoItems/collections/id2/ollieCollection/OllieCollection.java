package com.klin.holoItems.collections.id2.ollieCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class OllieCollection extends Collection {
    public static final String name = "Ollie";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "kureijiollie";
//    public static final String uuid = "12e1376a-6494-4131-aaa3-b3c37bf89ba4";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTljMWQ2NDQ3MGVmZmNlOTU1ZWRlYjIyMzRlODU1Zjg2OWM2OGJkMzdiYTQ1NjJmYmRlNGI5ZjI1ZDE1YWM2ZiJ9fX0=";

    public OllieCollection(){
        super(name, desc, theme, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
