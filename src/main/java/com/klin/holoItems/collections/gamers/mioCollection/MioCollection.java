package com.klin.holoItems.collections.gamers.mioCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class MioCollection extends Collection {
    public static final String name = "Mio";
    public static final String desc = "";
    public static final String theme = "Time played";
//    public static final String ign = "ookamimio";
//    public static final String uuid = "8a017472-7d0f-4c4e-87f6-9cda380dccab";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYyMDE4NDI2MzU0MCwKICAicHJvZmlsZUlkIiA6ICI4YTAxNzQ3MjdkMGY0YzRlODdmNjljZGEzODBkY2NhYiIsCiAgInByb2ZpbGVOYW1lIiA6ICJvb2thbWltaW8iLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VkYTNhNWFhZjY1YmNhYTIwYzc0NjE4YTA4NjEzMGJmMTA5OTFiODAyZmE5MDMwNTU2MGI3YjAzMTNiNjVhNCIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9=";

    public MioCollection(){
        super(name, desc, theme, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
