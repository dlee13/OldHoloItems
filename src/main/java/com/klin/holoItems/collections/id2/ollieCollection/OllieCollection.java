package com.klin.holoItems.collections.id2.ollieCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class OllieCollection extends Collection {
    public static final String name = "Ollie";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "kureijiollie";
//    public static final String uuid = "12e1376a-6494-4131-aaa3-b3c37bf89ba4";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYyMDE4NTczODM1NSwKICAicHJvZmlsZUlkIiA6ICIxMmUxMzc2YTY0OTQ0MTMxYWFhM2IzYzM3YmY4OWJhNCIsCiAgInByb2ZpbGVOYW1lIiA6ICJrdXJlaWppb2xsaWUiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTljMWQ2NDQ3MGVmZmNlOTU1ZWRlYjIyMzRlODU1Zjg2OWM2OGJkMzdiYTQ1NjJmYmRlNGI5ZjI1ZDE1YWM2ZiIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9";

    public OllieCollection(){
        super(name, desc, theme, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
