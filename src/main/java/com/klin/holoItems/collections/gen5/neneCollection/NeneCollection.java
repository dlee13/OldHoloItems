package com.klin.holoItems.collections.gen5.neneCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class NeneCollection extends Collection {
    public static final String name = "Nene";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "supernenechii";
//    public static final String uuid = "6252f15b-4e9f-4207-9ab9-793dee88436c";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTM0NjRlODY5ZjZhNmEwNGVhN2M0OTQ4Yjk4YjYyMzM0NWQ2ODViYTMyMzM4YTY5ZGIyOTcxOTJjZTE2NzcyNCJ9fX0=";

    public NeneCollection(){
        super(name, desc, theme, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
