package com.klin.holoItems.collections.gen6.chloeCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class ChloeCollection extends Collection {
    public static final String name = "Chloe";
    public static final String desc = "";
    public static final String theme = "";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGQ3NmVjMWE0ZGQwNTllOWU5M2FkZWNmYTBkNGRkOThiYWM3MzFiMTFlMjNkMTMyYTdlODg5MTA4MzQ5Y2E1NyJ9fX0=";

    public ChloeCollection(){
        super(name, desc, theme, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
