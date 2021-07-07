package com.klin.holoItems.collections.gen4.cocoCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.gen4.cocoCollection.items.DragonHorns;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class CocoCollection extends Collection {
    public static final String name = "Coco";
    public static final String desc = "";
    public static final String theme = "";
    public static final String ign = "kiryucoco";
    public static final String uuid = "7b33e0e2-2d4a-4613-95ed-195e45f17b0f";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYyMDE4NTM3NDk4NywKICAicHJvZmlsZUlkIiA6ICI3YjMzZTBlMjJkNGE0NjEzOTVlZDE5NWU0NWYxN2IwZiIsCiAgInByb2ZpbGVOYW1lIiA6ICJraXJ5dWNvY28iLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWZlODczZDI3Y2M1YzM5ODgwYjk0ZGQyZGJmNDViOWM3NTc4OWExOGVhNDQyZmJlZTFmYTI4YWI4N2FjMTk4MSIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9";

    public static final char key = 'y';

    public CocoCollection(){
        super(name, desc, theme, ign, uuid, key, base64);
        collection.put(DragonHorns.key, new DragonHorns());
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
