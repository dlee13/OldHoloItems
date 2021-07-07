package com.klin.holoItems.collections.gen4.watameCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class WatameCollection extends Collection {
    public static final String name = "Watame";
    public static final String desc = "";
    public static final String theme = "Gaming hitsuji";
    //all colors of wool collected
    public static final String ign = "tsunomakiwatame";
    public static final String uuid = "d568ad85-75b8-429a-af5e-7e190a6babff";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYyMDE4NDkzOTgyNCwKICAicHJvZmlsZUlkIiA6ICJkNTY4YWQ4NTc1Yjg0MjlhYWY1ZTdlMTkwYTZiYWJmZiIsCiAgInByb2ZpbGVOYW1lIiA6ICJUc3Vub21ha2l3YXRhbWUiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTU4M2JhYWZhMjIwMGFhZGJhOWZlYTkxZTBkOGExNzRiYTEwZTNmYzY5NTU3M2M4YTUwZDAwMmYzMzhlNDg2YiIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9";

    public static final char key = 'z';

    public WatameCollection(){
        super(name, desc, theme, ign, uuid, key, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
