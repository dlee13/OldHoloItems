package com.klin.holoItems.collections.misc.achanCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.Map;

public class AchanCollection extends Collection {
    public static final String name = "Achan";
    public static final String desc = "";
    public static final String theme = "";
    public static final String ign = "achan_uga";
    public static final String uuid = "30d90d50-d3f4-476a-9221-6f0388a3a9de";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYyMDE4NTgyMzQ3NCwKICAicHJvZmlsZUlkIiA6ICIzMGQ5MGQ1MGQzZjQ0NzZhOTIyMTZmMDM4OGEzYTlkZSIsCiAgInByb2ZpbGVOYW1lIiA6ICJhY2hhbl9VR0EiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTA0OGQ2ZGFlZWI4MDgyNjlhZmY1MzMzNzI0YWY4Y2Y4NTY1NmI5MjM2MGI0NTM1M2FmZjcyZWNhMmIwYzZkNiIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9";

    public static final char key = 'X';

    public AchanCollection(){
        super(name, desc, theme, ign, uuid, key, base64);
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
