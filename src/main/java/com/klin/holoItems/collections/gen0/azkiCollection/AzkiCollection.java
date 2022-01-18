package com.klin.holoItems.collections.gen0.azkiCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class AzkiCollection extends Collection {
    public static final String name = "Azki";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "azki_";
//    public static final String uuid = "031fd87e-83a4-43ad-af62-71a3e4dc5b34";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDVhNjU0ZjM4MzAyZDI0NWU1OWVjNWY5ZjZjYjQ2NzQ4YzgzNDJjYjU1MmQ3OTY1M2YxMTk4YTVmYWEwYTQ2OCJ9fX0=";

    public AzkiCollection(){
        super(name, desc, theme, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
