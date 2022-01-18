package com.klin.holoItems.collections.id1.iofiCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class IofiCollection extends Collection {
    public static final String name = "Iofi";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "iofi15";
//    public static final String uuid = "08046169-6f77-45f7-8375-1e11f40488f5";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDNiOWQyNzJkNmFmYmUyOGYwNGI3ODljMjc1YzJlY2YwMzMyOTljYTY3MGM1MjI1MTU1YzIwODViMGNlMTUyZSJ9fX0=";

    public IofiCollection(){
        super(name, desc, theme, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
