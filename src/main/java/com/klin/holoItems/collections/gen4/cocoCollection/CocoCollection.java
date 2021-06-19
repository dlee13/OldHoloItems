package com.klin.holoItems.collections.gen4.cocoCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.gen4.cocoCollection.items.DragonHorns;
import org.bukkit.entity.Player;

import java.util.Map;

public class CocoCollection extends Collection {
    public static final String name = "Coco";
    public static final String desc = "";
    public static final String theme = "";
    public static final String ign = "kiryucoco";
    public static final String uuid = "7b33e0e2-2d4a-4613-95ed-195e45f17b0f";

    public static final char key = 'y';

    public CocoCollection(){
        super(name, desc, theme, ign, uuid, key);
        collection.put(DragonHorns.key, new DragonHorns());
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
