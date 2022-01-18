package com.klin.holoItems.collections.stars2.temmaCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.stars2.temmaCollection.items.SSKSword;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class TemmaCollection extends Collection {
    public static final String name = "Temma";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "kishidotemma";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmJhNTI1MjhjMTM4M2QwMzI5MzJjNzQxYTQ4ZWQyYTg3Y2JiMjJjOTllMWIzOTdkNTMyMjQ1NTE1NTU4M2MwYSJ9fX0=";

    public TemmaCollection(){
        super(name, desc, theme, base64);
        collection.add(new SSKSword());
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
