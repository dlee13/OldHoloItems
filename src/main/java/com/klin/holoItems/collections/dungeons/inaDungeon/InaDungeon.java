package com.klin.holoItems.collections.dungeons.inaDungeon;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.dungeons.inaDungeon.items.Payload;
import org.bukkit.entity.Player;

import java.util.Map;

public class InaDungeon extends Collection {
    public static final String name = null;
    public static final String desc = null;
    public static final String theme = null;
    public static final String ign = null;
    public static final String uuid = null;
    public static final String base64 = null;

    public static final char key = '!';

    public InaDungeon(){
        super(name, desc, theme, ign, uuid, key, base64);
        collection.put(Payload.key, new Payload());
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
