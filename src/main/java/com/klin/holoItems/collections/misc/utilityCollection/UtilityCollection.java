package com.klin.holoItems.collections.misc.utilityCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.misc.opCollection.items.*;
import com.klin.holoItems.collections.misc.utilityCollection.items.Enchanted;
import com.klin.holoItems.collections.misc.utilityCollection.items.Modified;
import org.bukkit.entity.Player;

import java.util.Map;

public class UtilityCollection extends Collection {
    public static final String name = null;
    public static final String desc = null;
    public static final String theme = null;
    public static final String base64 = null;

    public UtilityCollection(){
        super(name, desc, theme, base64);
        collection.add(new Modified());
        collection.add(new Enchanted());
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
