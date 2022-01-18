package com.klin.holoItems.collections.gen4.lunaCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class LunaCollection extends Collection {
    public static final String name = "Luna";
    public static final String desc = "";
    public static final String theme = "Lavishly spent";
//    public static final String ign = "himemoriluna";
//    public static final String uuid = "7624f07c-5453-4c6c-88c1-f85316bd9e01";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzJkZTI2ODMxZjViNDQ2MTM1N2MyZWI4OWI2ODQ1ZDM0NzRiMjUyMjgyN2Y0MDY2ZGZlYThlZGMxYTgyNGY3MSJ9fX0=";

    public LunaCollection(){
        super(name, desc, theme, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
