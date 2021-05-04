package com.klin.holoItems.collections.gen5.polkaCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.Map;

public class PolkaCollection extends Collection {
    public static final String name = "Polka";
    public static final String desc = "";
    public static final String theme = "";
    public static final String ign = "omapol";
    public static final String uuid = "6148828d-ed79-488e-b82e-55831c709724";

    public static final char key = 'I';

    public PolkaCollection(){
        super(name, desc, theme, ign, uuid, key);
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
