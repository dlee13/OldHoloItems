package com.klin.holoItems.collections.gen2.subaruCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.Map;

public class SubaruCollection extends Collection {
    public static final String name = "Subaru";
    public static final String desc = "";
    public static final String theme = "";
    public static final String ign = "oozorasubaru";
    public static final String uuid = "1f34ec92-bf0d-40b6-b5db-b8b97265d87d";

    public static final char key = 'o';

    public SubaruCollection(){
        super(name, desc, theme, ign, uuid, key);
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
