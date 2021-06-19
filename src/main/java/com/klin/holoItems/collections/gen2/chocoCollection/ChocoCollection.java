package com.klin.holoItems.collections.gen2.chocoCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.Map;

public class ChocoCollection extends Collection {
    public static final String name = "Choco";
    public static final String desc = "";
    public static final String theme = "";
    public static final String ign = "yuzukichoco";
    public static final String uuid = "0c3e0035-d7ce-4efa-b3ad-f5a43e2a74f6";

    public static final char key = 'n';

    public ChocoCollection(){
        super(name, desc, theme, ign, uuid, key);
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
