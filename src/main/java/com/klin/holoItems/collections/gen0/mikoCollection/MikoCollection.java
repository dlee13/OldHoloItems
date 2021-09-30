package com.klin.holoItems.collections.gen0.mikoCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.gen0.mikoCollection.items.Record;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class MikoCollection extends Collection {
    public static final String name = "Miko";
    public static final String desc = "";
    public static final String theme = "Raids conquered";
//    public static final String ign = "sakuramiko35";
//    public static final String uuid = "848f57f0-1966-478e-bac0-935bddae04f8";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYyMDE4NDMzNDgzMiwKICAicHJvZmlsZUlkIiA6ICI4NDhmNTdmMDE5NjY0NzhlYmFjMDkzNWJkZGFlMDRmOCIsCiAgInByb2ZpbGVOYW1lIiA6ICJzYWt1cmFtaWtvMzUiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjk5ZGMwMWRjZjI4NDQ1NTc2ZjIyNjg4ODJhNzc3MDZmY2I5MzUzYWIwYzk1NGY5NjA0NTU2MWE3OTI0NGMxZSIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9=";

    public MikoCollection(){
        super(name, desc, theme, base64);
        collection.add(new Record());
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
