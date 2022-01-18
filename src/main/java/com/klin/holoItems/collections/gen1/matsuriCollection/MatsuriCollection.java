package com.klin.holoItems.collections.gen1.matsuriCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class MatsuriCollection extends Collection {
    public static final String name = "Matsuri";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "natsuiromatsuri";
//    public static final String uuid = "5d4814bd-54ec-4461-952a-3557b9db9a51";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDhlN2UzYjA4YmExN2M4OWRiYzJiODVhYjVmYTViNDk5MTZlZmVmMTQ5YzgwMzViOTZjNjJmMjE1Y2U5YzJjMSJ9fX0=";

    public MatsuriCollection(){
        super(name, desc, theme, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
