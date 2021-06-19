package com.klin.holoItems.collections.gen3.marineCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.Map;

public class MarineCollection extends Collection {
    public static final String name = "Marine";
    public static final String desc = "";
    public static final String theme = "Treasure plundered";
    public static final String ign = "houshou_marine";
    public static final String uuid = "d604942f-2b4b-4452-a8da-3dbf83e235c3";

    public static final char key = 'w';

    public MarineCollection(){
        super(name, desc, theme, ign, uuid, key);
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
