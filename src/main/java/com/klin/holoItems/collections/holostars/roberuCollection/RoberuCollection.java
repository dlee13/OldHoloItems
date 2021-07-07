package com.klin.holoItems.collections.holostars.roberuCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class RoberuCollection extends Collection {
    public static final String name = "Roberu";
    public static final String desc = "";
    public static final String theme = "";
    public static final String ign = "yukokuroberu";
    public static final String uuid = "fb729943-3352-4c87-ae87-ba104a174d3d";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYxMTU3NzI4ODQwMiwKICAicHJvZmlsZUlkIiA6ICJiYjdjY2E3MTA0MzQ0NDEyOGQzMDg5ZTEzYmRmYWI1OSIsCiAgInByb2ZpbGVOYW1lIiA6ICJsYXVyZW5jaW8zMDMiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTIyMTlhOTliZmI2MDNlMjUzMjM5NTE5NWQzNzQ2ZGRlZTI1NWYyMTIyNjk5MzZiZDZjYTk3MjQ2MzRmZTg1IgogICAgfQogIH0KfQ==";
    public static final char key = '7';

    public RoberuCollection(){
        super(name, desc, theme, ign, uuid, key, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
