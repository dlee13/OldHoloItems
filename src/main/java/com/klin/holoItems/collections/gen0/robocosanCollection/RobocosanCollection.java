package com.klin.holoItems.collections.gen0.robocosanCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.Map;

public class RobocosanCollection extends Collection {
    public static final String name = "Robocosan";
    public static final String desc = "";
    public static final String theme = "Infrastructure contributed";
    public static final String ign = "robocosan";
    public static final String uuid = "132dfbe1-071d-4932-a5e0-bdc323537bd4";

    public static final char key = 'c';

    public RobocosanCollection(){
        super(name, desc, theme, ign, uuid, key);
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
