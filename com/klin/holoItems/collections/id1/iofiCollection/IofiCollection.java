package com.klin.holoItems.collections.id1.iofiCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.Map;

public class IofiCollection extends Collection {
    public static final String name = "Iofi";
    public static final String desc = "";
    public static final String theme = "";
    public static final String ign = "iofi15";
    public static final String uuid = "08046169-6f77-45f7-8375-1e11f40488f5";

    public static final char key = 'E';

    public IofiCollection(){
        super(name, desc, theme, ign, uuid, key);
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
