package com.klin.holoItems.collections.stars2.astelCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class AstelCollection extends Collection {
    public static final String name = "Astel";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "astelleda";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2MyNzRiZDdmMjdlMDc4NzE0NjAxMzI3MTQ2MzZhNzE2ZGQ2MTk4MmEyNzUxOTc3MjE2YTljYmZjZGQ5YWJiNCJ9fX0=";

    public AstelCollection(){
        super(name, desc, theme, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
