package com.klin.holoItems.collections.gen0.soraCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class SoraCollection extends Collection {
    public static final String name = "Sora";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "tokinosorach";
//    public static final String uuid = "a6a4d2a9-4030-4927-b482-9499a6f6baaf";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzg2ZjZmZWIyODViNTNlN2E4NWY5MjRkYzAzMmQyZTU4MTZmNTA0MmE0NTMwZWVjYzVjMDM0YmVlMTdiMWJkMCJ9fX0=";

    public SoraCollection(){
        super(name, desc, theme, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
