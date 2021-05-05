package com.klin.holoItems.collections.gen1.haachamaCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.gen1.haachamaCollection.items.RiftWalker;
import org.bukkit.entity.Player;

import java.util.Map;

public class HaachamaCollection extends Collection {
    public static final String name = "Haachama";
    public static final String desc = "";
    public static final String theme = "";
    public static final String ign = "akaihaato";
    public static final String uuid = "f03a5a88-6ecc-4930-ae21-081b926348fd";

    public static final char key = 'j';

    public HaachamaCollection(){
        super(name, desc, theme, ign, uuid, key);
        collection.put(RiftWalker.key, new RiftWalker());
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
