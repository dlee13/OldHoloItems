package com.klin.holoItems.collections.gen4.towaCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.gen4.towaCollection.items.HolyFire;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class TowaCollection extends Collection {
    public static final String name = "Towa";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "towasama";
//    public static final String uuid = "8d257d00-57cd-4bb5-a75a-4ee476f72953";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGY5NWZjMjMzOTJhZjdlNWM1YzIwYzc2YTIyMDhkZjQ5MjJiMTA4ZjJjODllMzM1MjI0MDI2MTJjNThkYjUwOCJ9fX0=";

    public TowaCollection(){
        super(name, desc, theme, base64);
        collection.add(new HolyFire());
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
