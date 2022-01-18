package com.klin.holoItems.collections.misc.achanCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.misc.achanCollection.items.Clipboard;
import com.klin.holoItems.collections.misc.achanCollection.items.DebugStick;
import com.klin.holoItems.collections.misc.achanCollection.items.ShoulderBagStrap;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class AchanCollection extends Collection {
    public static final String name = "Achan";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "achan_uga";
//    public static final String uuid = "30d90d50-d3f4-476a-9221-6f0388a3a9de";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTA0OGQ2ZGFlZWI4MDgyNjlhZmY1MzMzNzI0YWY4Y2Y4NTY1NmI5MjM2MGI0NTM1M2FmZjcyZWNhMmIwYzZkNiJ9fX0=";

    public AchanCollection(){
        super(name, desc, theme, base64);
        collection.add(new ShoulderBagStrap());
        collection.add(new DebugStick());
        collection.add(new Clipboard());
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
