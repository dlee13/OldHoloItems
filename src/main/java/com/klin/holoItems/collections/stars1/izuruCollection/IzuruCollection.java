package com.klin.holoItems.collections.stars1.izuruCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class IzuruCollection extends Collection {
    public static final String name = "Izuru";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "kanadeizuru";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjJhY2EzODBhODNkYTljY2ZhMjYyMWYzZGVkZGIxOWM0YTFiNDA5NWY1MjM3NTZhZjEzNGMyMzE2N2QwNTViZSJ9fX0=";

    public IzuruCollection(){
        super(name, desc, theme, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
