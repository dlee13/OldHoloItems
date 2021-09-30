package com.klin.holoItems.collections.gen0.soraCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class SoraCollection extends Collection {
    public static final String name = "Sora";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "tokinosorach";
//    public static final String uuid = "a6a4d2a9-4030-4927-b482-9499a6f6baaf";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYyMDE4NDQwMzQzMywKICAicHJvZmlsZUlkIiA6ICJhNmE0ZDJhOTQwMzA0OTI3YjQ4Mjk0OTlhNmY2YmFhZiIsCiAgInByb2ZpbGVOYW1lIiA6ICJ0b2tpbm9zb3JhY2giLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzg2ZjZmZWIyODViNTNlN2E4NWY5MjRkYzAzMmQyZTU4MTZmNTA0MmE0NTMwZWVjYzVjMDM0YmVlMTdiMWJkMCIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9=";

    public SoraCollection(){
        super(name, desc, theme, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
