package com.klin.holoItems.collections.gen4.towaCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.Map;

public class TowaCollection extends Collection {
    public static final String name = "Towa";
    public static final String desc = "";
    public static final String theme = "";
    public static final String ign = "towasama";
    public static final String uuid = "8d257d00-57cd-4bb5-a75a-4ee476f72953";

    public static final char key = 'A';

    public TowaCollection(){
        super(name, desc, theme, ign, uuid, key);
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
