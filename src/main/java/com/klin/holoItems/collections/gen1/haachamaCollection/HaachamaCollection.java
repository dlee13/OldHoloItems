package com.klin.holoItems.collections.gen1.haachamaCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.gen1.haachamaCollection.items.RiftWalker;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class HaachamaCollection extends Collection {
    public static final String name = "Haachama";
    public static final String desc = "";
    public static final String theme = "";
    public static final String ign = "akaihaato";
    public static final String uuid = "f03a5a88-6ecc-4930-ae21-081b926348fd";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYyMDE4NDUzMzY1OSwKICAicHJvZmlsZUlkIiA6ICJmMDNhNWE4ODZlY2M0OTMwYWUyMTA4MWI5MjYzNDhmZCIsCiAgInByb2ZpbGVOYW1lIiA6ICJBa2FpSGFhdG8iLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTc2M2ZjYzY1NzQ5NDBjODNiMTIwY2M2ZGUxNmQ1YzhhZWU0YzRlMzRjMzA0M2E5M2FlZThhN2U3NGFjMWVkNCIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9=";

    public static final char key = 'j';

    public HaachamaCollection(){
        super(name, desc, theme, ign, uuid, key, base64);
        collection.put(RiftWalker.key, new RiftWalker());
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
