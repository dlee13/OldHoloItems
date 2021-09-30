package com.klin.holoItems.collections.misc.klinCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.misc.klinCollection.items.*;
import org.bukkit.entity.Player;

import java.util.Map;

public class KlinCollection extends Collection {
    public static final String name = null;
    public static final String desc = null;
    public static final String theme = null;
    public static final String base64 = null;

    public KlinCollection(){
        super(name, desc, theme, base64);
        collection.add(new Bore());
        collection.add(new Trowel());
        collection.add(new Paver());
        collection.add(new Vacuum());
        collection.add(new KFPGuide());
        collection.add(new SecondHand());
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
