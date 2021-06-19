package com.klin.holoItems.collections.gen4.kanataCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.gen4.kanataCollection.items.Shuribow;
import com.klin.holoItems.collections.gen4.kanataCollection.items.Zipline;
import org.bukkit.entity.Player;

import java.util.Map;

public class KanataCollection extends Collection {
    public static final String name = "Kanata";
    public static final String desc = "";
    public static final String theme = "";
    public static final String ign = "amane_kanata";
    public static final String uuid = "7ec7cca2-6085-4eed-89c6-3c0ef6eef5ee";

    public static final char key = 'x';

    public KanataCollection(){
        super(name, desc, theme, ign, uuid, key);
        collection.put(Shuribow.key, new Shuribow());
        collection.put(Zipline.key, new Zipline());
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
