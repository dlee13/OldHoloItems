package com.klin.holoItems.collections.en.kiaraCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.Map;

public class KiaraCollection extends Collection {
    public static final String name = "Kiara";
    public static final String desc = "";
    public static final String theme = "Employees hired";
    public static final String ign = "kiara_holoen";
    public static final String uuid = "604ed1ff-de0b-4eef-8629-dc96cc29d652";

    public static final char key = 'K';

    public KiaraCollection(){
        super(name, desc, theme, ign, uuid, key);
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
