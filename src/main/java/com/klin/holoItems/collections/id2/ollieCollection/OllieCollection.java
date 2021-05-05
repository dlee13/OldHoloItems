package com.klin.holoItems.collections.id2.ollieCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.Map;

public class OllieCollection extends Collection {
    public static final String name = "Ollie";
    public static final String desc = "";
    public static final String theme = "";
    public static final String ign = "kureijiollie";
    public static final String uuid = "12e1376a-6494-4131-aaa3-b3c37bf89ba4";

    public static final char key = 'O';

    public OllieCollection(){
        super(name, desc, theme, ign, uuid, key);
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
