package com.klin.holoItems.collections.misc.franCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.misc.franCollection.items.HpModifier;
import com.klin.holoItems.collections.misc.franCollection.items.SteinsEgg;
import com.klin.holoItems.collections.misc.franCollection.items.TotemicEgg;
import com.klin.holoItems.collections.misc.hiddenCollection.items.*;
import org.bukkit.entity.Player;

import java.util.Map;

public class FranCollection extends Collection {
    public static final String name = "Fran";
    public static final String desc = null;
    public static final String theme = null;
    public static final String ign = null;
    public static final String uuid = null;
    public static final String base64 = null;

    public static final char key = 'X';

    public FranCollection(){
        super(name, desc, theme, ign, uuid, key, base64);
        collection.put(TotemicEgg.key, new TotemicEgg());
        collection.put(SteinsEgg.key, new SteinsEgg());
        collection.put(HpModifier.key, new HpModifier());
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
