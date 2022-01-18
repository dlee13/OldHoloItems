package com.klin.holoItems.collections.en2.MumeiCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class MumeiCollection extends Collection {
    public static final String name = "Mumei";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "nana_mumei";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWQ3NmFkMjM1YzAzOTFlYmJkZTcyNTllYjljZDBlYWQ3NzY4MzNkZTI3MjI3N2JlNDc3NTI4ZjBjOGJiMmY3In19fQ==";

    public MumeiCollection(){
        super(name, desc, theme, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
