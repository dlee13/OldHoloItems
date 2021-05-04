package com.klin.holoItems.collections.misc.achanCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.Map;

public class AchanCollection extends Collection {
    public static final String name = "Achan";
    public static final String desc = "";
    public static final String theme = "";
    public static final String ign = "achan_uga";
    public static final String uuid = "30d90d50-d3f4-476a-9221-6f0388a3a9de";

    public static final char key = 'X';

    public AchanCollection(){
        super(name, desc, theme, ign, uuid, key);
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
