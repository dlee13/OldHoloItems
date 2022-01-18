package com.klin.holoItems.collections.gen2.ayameCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class AyameCollection extends Collection {
    public static final String name = "Ayame";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "nakiriayame";
//    public static final String uuid = "26c349f8-d2ac-45d9-be08-02d00be7ffd4";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmE3Y2JiM2FhY2Q2ZTNkMDgyNjQ3OGJlM2I2ZjQyNTkwOWQ3ZTAyM2IzMGNmNzMzMmYzY2ExZjk5NWVmYTg2OCJ9fX0=";

    public AyameCollection(){
        super(name, desc, theme, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
