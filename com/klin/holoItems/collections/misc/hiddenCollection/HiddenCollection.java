package com.klin.holoItems.collections.misc.hiddenCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.misc.hiddenCollection.items.GalleryFrame;
import com.klin.holoItems.collections.misc.hiddenCollection.items.Sokoban;
import com.klin.holoItems.collections.misc.hiddenCollection.items.WheatPortal;
import org.bukkit.entity.Player;

import java.util.Map;

public class HiddenCollection extends Collection {
    public static final String name = "Hidden";
    public static final String desc = null;
    public static final String theme = null;
    public static final String ign = null;
    public static final String uuid = null;
    public static final String base64 = null;

    public static final char key = 'Z';

    public HiddenCollection(){
        super(name, desc, theme, ign, uuid, key, base64);
        collection.put(Sokoban.key, new Sokoban());
        collection.put(WheatPortal.key, new WheatPortal());
        collection.put(GalleryFrame.key, new GalleryFrame());
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
