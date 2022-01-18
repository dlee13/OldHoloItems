package com.klin.holoItems.collections.gen1.akiCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.gen1.akiCollection.items.QuartzBlossom;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class AkiCollection extends Collection {
    public static final String name = "Aki";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "akirosenthal";
//    public static final String uuid = "9d7bc284-9f61-498e-b90f-92bbf8bc6843";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODBhMTQ0ZWMwMzFmMDNiM2YzZGQ0MDcwYzc4YjM0Y2M1YTRiMWI5YmExNWJkNWVhYmY1ZDAyODBkYzI1ZGJmMSJ9fX0=";

    public AkiCollection(){
        super(name, desc, theme, base64);
        collection.add(new QuartzBlossom());
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
