package com.klin.holoItems.collections.gen5.neneCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.Map;

public class NeneCollection extends Collection {
    public static final String name = "Nene";
    public static final String desc = "";
    public static final String theme = "";
    public static final String ign = "supernenechii";
    public static final String uuid = "6252f15b-4e9f-4207-9ab9-793dee88436c";

    public static final char key = 'G';

    public NeneCollection(){
        super(name, desc, theme, ign, uuid, key);
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
