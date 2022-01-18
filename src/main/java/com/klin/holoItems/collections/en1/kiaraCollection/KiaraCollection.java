package com.klin.holoItems.collections.en1.kiaraCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class KiaraCollection extends Collection {
    public static final String name = "Kiara";
    public static final String desc = "";
    public static final String theme = "Employees hired";
//    public static final String ign = "kiara_holoen";
//    public static final String uuid = "604ed1ff-de0b-4eef-8629-dc96cc29d652";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGRmNzdiZDFlODQ1ZDY5ZDZiZWRjMmZlMGNiZTJhMmJiNTFmYzY4MGQ1MWI5ZWM4ZDMwNjY1MTQyMGMzODMwMCJ9fX0=";

    public KiaraCollection(){
        super(name, desc, theme, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
