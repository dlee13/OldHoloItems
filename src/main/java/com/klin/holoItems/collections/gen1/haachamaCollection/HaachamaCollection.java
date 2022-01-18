package com.klin.holoItems.collections.gen1.haachamaCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.gen1.haachamaCollection.items.Gnaw;
import com.klin.holoItems.collections.gen1.haachamaCollection.items.RiftWalker;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class HaachamaCollection extends Collection {
    public static final String name = "Haachama";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "akaihaato";
//    public static final String uuid = "f03a5a88-6ecc-4930-ae21-081b926348fd";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTc2M2ZjYzY1NzQ5NDBjODNiMTIwY2M2ZGUxNmQ1YzhhZWU0YzRlMzRjMzA0M2E5M2FlZThhN2U3NGFjMWVkNCJ9fX0=";

    public HaachamaCollection(){
        super(name, desc, theme, base64);
        collection.add(new RiftWalker());
        collection.add(new Gnaw());
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
