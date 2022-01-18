package com.klin.holoItems.collections.id2.reineCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class ReineCollection extends Collection {
    public static final String name = "Reine";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "reineeeee";
//    public static final String uuid = "f91804e5-6637-4ac8-9684-6921d8b76b5c";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGRjZGQyZjA2N2JiOTY1MjI5N2Q0ZWZlYmViMDlkODEyMThjYzM3NjllMjVlNTg0YTQ4ZDI2NWFhMmM5MzQ4MiJ9fX0=";

    public ReineCollection(){
        super(name, desc, theme, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
