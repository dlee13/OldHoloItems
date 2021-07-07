package com.klin.holoItems.collections.id1.iofiCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class IofiCollection extends Collection {
    public static final String name = "Iofi";
    public static final String desc = "";
    public static final String theme = "";
    public static final String ign = "iofi15";
    public static final String uuid = "08046169-6f77-45f7-8375-1e11f40488f5";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYyMDE4NTU4MjMyNCwKICAicHJvZmlsZUlkIiA6ICIwODA0NjE2OTZmNzc0NWY3ODM3NTFlMTFmNDA0ODhmNSIsCiAgInByb2ZpbGVOYW1lIiA6ICJJT0ZJMTUiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDNiOWQyNzJkNmFmYmUyOGYwNGI3ODljMjc1YzJlY2YwMzMyOTljYTY3MGM1MjI1MTU1YzIwODViMGNlMTUyZSIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9";

    public static final char key = 'E';

    public IofiCollection(){
        super(name, desc, theme, ign, uuid, key, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
