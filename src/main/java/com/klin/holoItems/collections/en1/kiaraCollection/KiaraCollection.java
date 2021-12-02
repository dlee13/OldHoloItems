package com.klin.holoItems.collections.en1.kiaraCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class KiaraCollection extends Collection {
    public static final String name = "Kiara";
    public static final String desc = "";
    public static final String theme = "Employees hired";
//    public static final String ign = "kiara_holoen";
//    public static final String uuid = "604ed1ff-de0b-4eef-8629-dc96cc29d652";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYyMDE4NDE0NTUwNiwKICAicHJvZmlsZUlkIiA6ICI2MDRlZDFmZmRlMGI0ZWVmODYyOWRjOTZjYzI5ZDY1MiIsCiAgInByb2ZpbGVOYW1lIiA6ICJLaWFyYV9IT0xPRU4iLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGRmNzdiZDFlODQ1ZDY5ZDZiZWRjMmZlMGNiZTJhMmJiNTFmYzY4MGQ1MWI5ZWM4ZDMwNjY1MTQyMGMzODMwMCIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9=";

    public KiaraCollection(){
        super(name, desc, theme, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
