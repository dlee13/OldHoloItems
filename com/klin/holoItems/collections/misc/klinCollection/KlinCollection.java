package com.klin.holoItems.collections.misc.klinCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.misc.klinCollection.items.Bore;
import com.klin.holoItems.collections.misc.klinCollection.items.Paver;
import com.klin.holoItems.collections.misc.klinCollection.items.Trowel;
import org.bukkit.entity.Player;

import java.util.Map;

public class KlinCollection extends Collection {
    public static final String name = "Klin";
    public static final String desc = null;
    public static final String theme = null;
    public static final String ign = null;
    public static final String uuid = null;
    public static final String base64 = null;

    public static final char key = 'Y';

    public KlinCollection(){
        super(name, desc, theme, ign, uuid, key, base64);
        collection.put(Bore.key, new Bore());
        collection.put(Trowel.key, new Trowel());
        collection.put(Paver.key, new Paver());
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
