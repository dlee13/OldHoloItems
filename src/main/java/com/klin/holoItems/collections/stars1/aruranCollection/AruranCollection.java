package com.klin.holoItems.collections.stars1.aruranCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class AruranCollection extends Collection {
    public static final String name = "Aruran";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "arurandeisu";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmUxZDQ1YzgzOWQxOGI0OThiYmMyMWVhMWQwZmRjZGMxY2MwN2ZmYmQ4MzkzYjA0MjVmMTk2NmJiYjk2MThiMyJ9fX0=";

    public AruranCollection(){
        super(name, desc, theme, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
