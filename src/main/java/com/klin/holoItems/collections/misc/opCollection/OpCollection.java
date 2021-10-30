package com.klin.holoItems.collections.misc.opCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.misc.opCollection.items.*;
import org.bukkit.entity.Player;

import java.util.Map;

public class OpCollection extends Collection {
    public static final String name = null;
    public static final String desc = null;
    public static final String theme = null;
    public static final String base64 = null;

    public OpCollection(){
        super(name, desc, theme, base64);
        collection.add(new Sokoban());
        collection.add(new WheatPortal());
        collection.add(new GalleryFrame());
        collection.add(new Payload());
        collection.add(new Falchion());
        collection.add(new NameTag());
        collection.add(new QuartzGranule());
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
