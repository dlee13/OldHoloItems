package com.klin.holoItems.collections.gen2.subaruCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class SubaruCollection extends Collection {
    public static final String name = "Subaru";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "oozorasubaru";
//    public static final String uuid = "1f34ec92-bf0d-40b6-b5db-b8b97265d87d";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGE3MjllNDViN2I4N2U5YzIwYWMyYzI0NTNlYWRmNzQ4MDRlYTA0NzBmYzNjZjA3N2JlMTRmNWI1NTY5Zjk1MiJ9fX0=";

    public SubaruCollection(){
        super(name, desc, theme, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
