package com.klin.holoItems.collections.gen4.lunaCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.Map;

public class LunaCollection extends Collection {
    public static final String name = "Luna";
    public static final String desc = "";
    public static final String theme = "Lavishly spent";
    public static final String ign = "himemoriluna";
    public static final String uuid = "7624f07c-5453-4c6c-88c1-f85316bd9e01";

    public static final char key = 'B';

    public LunaCollection(){
        super(name, desc, theme, ign, uuid, key);
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
