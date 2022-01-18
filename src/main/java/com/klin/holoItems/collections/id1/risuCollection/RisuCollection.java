package com.klin.holoItems.collections.id1.risuCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class RisuCollection extends Collection {
    public static final String name = "Risu";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "ayunda_risu";
//    public static final String uuid = "8b06684f-7615-489e-a1b6-e84838d398f2";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzY5ZTdmZTgxMjY5MjM5MzU1NDI0NWIxZjkzYTk1NTBkZGI0Y2M1Nzc1MjExYmY4ZTE2NzIyNTdkOTA2ZWU4YyJ9fX0=";

    public RisuCollection(){
        super(name, desc, theme, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
