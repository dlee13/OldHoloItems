package com.klin.holoItems.collections.holostars.temmaCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.Map;

public class TemmaCollection extends Collection {
    public static final String name = "Temma";
    public static final String desc = "";
    public static final String theme = "";
    public static final String ign = "kishidotemma";
    public static final String uuid = "fb729943-3352-4c87-ae87-ba104a174d3d";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYyNDg3Mzg5NDk4OCwKICAicHJvZmlsZUlkIiA6ICJjNzJjNjU1YzU5NDA0ZjVmYjUzOTMwZWRkZDFjOTNkMiIsCiAgInByb2ZpbGVOYW1lIiA6ICJraXNoaWRvdGVtbWEiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmJhNTI1MjhjMTM4M2QwMzI5MzJjNzQxYTQ4ZWQyYTg3Y2JiMjJjOTllMWIzOTdkNTMyMjQ1NTE1NTU4M2MwYSIKICAgIH0KICB9Cn0=";
    public static final char key = '6';

    public TemmaCollection(){
        super(name, desc, theme, ign, uuid, key, base64);
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
