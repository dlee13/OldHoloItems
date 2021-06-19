package com.klin.holoItems.collections.gen0.soraCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.Map;

public class SoraCollection extends Collection {
    public static final String name = "Sora";
    public static final String desc = "";
    public static final String theme = "";
    public static final String ign = "tokinosorach";
    public static final String uuid = "a6a4d2a9-4030-4927-b482-9499a6f6baaf";

    public static final char key = 'b';

    public SoraCollection(){
        super(name, desc, theme, ign, uuid, key);
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
