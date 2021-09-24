package com.klin.holoItems.collections.misc.opCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.misc.opCollection.items.*;
import org.bukkit.entity.Player;

import java.util.Map;

public class OpCollection extends Collection {
    public static final String name = null;
    public static final String desc = null;
    public static final String theme = null;
    public static final String ign = null;
    public static final String uuid = null;
    public static final String base64 = null;

    public OpCollection(){
        super(name, desc, theme, ign, uuid, 'Z', base64);
        collection.put('0', new Sokoban());
        collection.put('1', new WheatPortal());
        collection.put('2', new GalleryFrame());
        collection.put(Payload.key, new Payload());
        collection.put('4', new Falchion());
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
