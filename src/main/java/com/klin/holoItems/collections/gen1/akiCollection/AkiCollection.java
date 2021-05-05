package com.klin.holoItems.collections.gen1.akiCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.Map;

public class AkiCollection extends Collection {
    public static final String name = "Aki";
    public static final String desc = "";
    public static final String theme = "";
    public static final String ign = "akirosenthal";
    public static final String uuid = "9d7bc284-9f61-498e-b90f-92bbf8bc6843";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYyMDE4NDQ4NDQ1OSwKICAicHJvZmlsZUlkIiA6ICI5ZDdiYzI4NDlmNjE0OThlYjkwZjkyYmJmOGJjNjg0MyIsCiAgInByb2ZpbGVOYW1lIiA6ICJha2lyb3NlbnRoYWwiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODBhMTQ0ZWMwMzFmMDNiM2YzZGQ0MDcwYzc4YjM0Y2M1YTRiMWI5YmExNWJkNWVhYmY1ZDAyODBkYzI1ZGJmMSIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9=";

    public static final char key = 'i';

    public AkiCollection(){
        super(name, desc, theme, ign, uuid, key, base64);
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
