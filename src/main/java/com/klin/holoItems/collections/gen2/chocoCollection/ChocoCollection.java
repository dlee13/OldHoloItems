package com.klin.holoItems.collections.gen2.chocoCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class ChocoCollection extends Collection {
    public static final String name = "Choco";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "yuzukichoco";
//    public static final String uuid = "0c3e0035-d7ce-4efa-b3ad-f5a43e2a74f6";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjFkOGI0Njg4ZGUyNGFkZDFjYTkwNmVkOGEyYmVhOWYzYTVjZTI0MTZkMjk4ZTI1YmUwNzJjZTAzYjUwYTY1MSJ9fX0=";

    public ChocoCollection(){
        super(name, desc, theme, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
