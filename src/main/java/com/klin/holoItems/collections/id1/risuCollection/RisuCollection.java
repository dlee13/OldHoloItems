package com.klin.holoItems.collections.id1.risuCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.Map;

public class RisuCollection extends Collection {
    public static final String name = "Risu";
    public static final String desc = "";
    public static final String theme = "";
    public static final String ign = "ayunda_risu";
    public static final String uuid = "8b06684f-7615-489e-a1b6-e84838d398f2";

    public static final char key = 'C';

    public RisuCollection(){
        super(name, desc, theme, ign, uuid, key);
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
