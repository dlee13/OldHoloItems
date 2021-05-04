package com.klin.holoItems.collections.gen0.mikoCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.Map;

public class MikoCollection extends Collection {
    public static final String name = "Miko";
    public static final String desc = "";
    public static final String theme = "Raids conquered";
    public static final String ign = "sakuramiko35";
    public static final String uuid = "848f57f0-1966-478e-bac0-935bddae04f8";

    public static final char key = 'd';

    public MikoCollection(){
        super(name, desc, theme, ign, uuid, key);
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
