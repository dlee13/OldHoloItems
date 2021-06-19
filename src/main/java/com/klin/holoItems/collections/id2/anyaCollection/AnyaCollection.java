package com.klin.holoItems.collections.id2.anyaCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.Map;

public class AnyaCollection extends Collection {
    public static final String name = "Anya";
    public static final String desc = "";
    public static final String theme = "";
    public static final String ign = "anya_melfissa";
    public static final String uuid = "a39a7d00-0ad5-42f3-903d-ccb553b682d7";

    public static final char key = 'P';

    public AnyaCollection(){
        super(name, desc, theme, ign, uuid, key);
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
