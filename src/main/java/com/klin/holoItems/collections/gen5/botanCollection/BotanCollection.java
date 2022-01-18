package com.klin.holoItems.collections.gen5.botanCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.gen5.botanCollection.items.Backdash;
import com.klin.holoItems.collections.gen5.botanCollection.items.ScopedRifle;
import com.klin.holoItems.collections.gen5.botanCollection.items.Sentry;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class BotanCollection extends Collection {
    public static final String name = "Botan";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "botaaan";
//    public static final String uuid = "fb729943-3352-4c87-ae87-ba104a174d3d";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTYwMWM4MTJmMDM5N2M4MjEzOWFiNmJhZWE2MTRlNjlmZDhhYTNlNTYzYjU3NDM3YTlmZTA4Nzk1NzZmNmI1MSJ9fX0=";

    public BotanCollection(){
        super(name, desc, theme, base64);
        collection.add(new Backdash());
        collection.add(new Sentry());
        collection.add(new ScopedRifle());
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
