package com.klin.holoItems.collections.stars2.roberuCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class RoberuCollection extends Collection {
    public static final String name = "Roberu";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "yukokuroberu";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTIyMTlhOTliZmI2MDNlMjUzMjM5NTE5NWQzNzQ2ZGRlZTI1NWYyMTIyNjk5MzZiZDZjYTk3MjQ2MzRmZTg1In19fQ==";

    public RoberuCollection(){
        super(name, desc, theme, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
