package com.klin.holoItems.collections.gamers.okayuCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class OkayuCollection extends Collection {
    public static final String name = "Okayu";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "nekomata_okayu";
//    public static final String uuid = "ece77695-b296-4172-a7e4-fd1b96503534";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDc5ZGQ2ZDYwZWIzOTM0NTY1ODcwOTJhYzQ5MDhmM2U1NmFlYmNiNzVkZTIxODY4MzY1MTJiNjhiOGY2NDUyZCJ9fX0=";

    public OkayuCollection(){
        super(name, desc, theme, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
