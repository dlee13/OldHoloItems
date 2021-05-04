package com.klin.holoItems.collections.gen2.ayameCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.Map;

public class AyameCollection extends Collection {
    public static final String name = "Ayame";
    public static final String desc = "";
    public static final String theme = "";
    public static final String ign = "nakiriayame";
    public static final String uuid = "26c349f8-d2ac-45d9-be08-02d00be7ffd4";

    public static final char key = 'm';

    public AyameCollection(){
        super(name, desc, theme, ign, uuid, key);
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
