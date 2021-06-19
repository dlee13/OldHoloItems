package com.klin.holoItems.collections.gamers.mioCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.Map;

public class MioCollection extends Collection {
    public static final String name = "Mio";
    public static final String desc = "";
    public static final String theme = "Time played";
    public static final String ign = "ookamimio";
    public static final String uuid = "8a017472-7d0f-4c4e-87f6-9cda380dccab";

    public static final char key = 'p';

    public MioCollection(){
        super(name, desc, theme, ign, uuid, key);
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
