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
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYyMDE4NTMzNjk3MiwKICAicHJvZmlsZUlkIiA6ICI3ZWM3Y2NhMjYwODU0ZWVkODljNjNjMGVmNmVlZjVlZSIsCiAgInByb2ZpbGVOYW1lIiA6ICJBbWFuZV9LYW5hdGEiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTdhYmRhMGRiYzRhMWE1NDJjNTRhZTM4MDUxMDFhZWUxNWI4MDgyZDVkNjM5ZTI1NDNlODk4OGRjYWZmZTljIiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0=";

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
