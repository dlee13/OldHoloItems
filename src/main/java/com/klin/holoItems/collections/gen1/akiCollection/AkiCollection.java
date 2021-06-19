package com.klin.holoItems.collections.gen1.akiCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.gen1.akiCollection.items.QuartzBlossom;
import org.bukkit.entity.Player;

import java.util.Map;

public class AkiCollection extends Collection {
    public static final String name = "Aki";
    public static final String desc = "";
    public static final String theme = "";
    public static final String ign = "akirosenthal";
    public static final String uuid = "9d7bc284-9f61-498e-b90f-92bbf8bc6843";

    public static final char key = 'i';

    public AkiCollection(){
        super(name, desc, theme, ign, uuid, key);
        collection.put(QuartzBlossom.key, new QuartzBlossom());
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
