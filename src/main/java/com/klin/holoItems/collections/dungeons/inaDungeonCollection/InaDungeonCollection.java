package com.klin.holoItems.collections.dungeons.inaDungeonCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.dungeons.inaDungeonCollection.items.*;
import org.bukkit.entity.Player;

import java.util.Map;

public class InaDungeonCollection extends Collection {
    public static final String name = null;
    public static final String desc = null;
    public static final String theme = null;
    public static final String ign = null;
    public static final String uuid = null;
    public static final String base64 = null;

    public static final char key = '!';

    public InaDungeonCollection(){
        super(name, desc, theme, ign, uuid, key, base64);
        collection.put(Payload.key, new Payload());
        collection.put(Torrent.key, new Torrent());
        collection.put(BoneFragment.key, new BoneFragment());
        collection.put(BoneShard.key, new BoneShard());
        collection.put(BoneCrystal.key, new BoneCrystal());
        collection.put(AshWood.key, new AshWood());
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
