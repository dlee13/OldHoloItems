package com.klin.holoItems.collections.gen4.kanataCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.gen4.kanataCollection.items.Shuribow;
import com.klin.holoItems.collections.gen4.kanataCollection.items.Zipline;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class KanataCollection extends Collection {
    public static final String name = "Kanata";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "amane_kanata";
//    public static final String uuid = "7ec7cca2-6085-4eed-89c6-3c0ef6eef5ee";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTdhYmRhMGRiYzRhMWE1NDJjNTRhZTM4MDUxMDFhZWUxNWI4MDgyZDVkNjM5ZTI1NDNlODk4OGRjYWZmZTljIn19fQ==";

    public KanataCollection(){
        super(name, desc, theme, base64);
        collection.add(new Shuribow());
        collection.add(new Zipline());
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
