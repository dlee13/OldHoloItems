package com.klin.holoItems.collections.misc.utilityCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.misc.klinCollection.items.*;
import com.klin.holoItems.collections.misc.utilityCollection.items.NoDrop;
import org.bukkit.entity.Player;

import java.util.Map;

public class UtilityCollection extends Collection {
    public static final String name = null;
    public static final String desc = null;
    public static final String theme = null;
    public static final String ign = null;
    public static final String uuid = null;
    public static final String base64 = null;

    public static final char key = '?';

    public UtilityCollection(){
        super(name, desc, theme, ign, uuid, key, base64);
        collection.put(NoDrop.key, new NoDrop());
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
