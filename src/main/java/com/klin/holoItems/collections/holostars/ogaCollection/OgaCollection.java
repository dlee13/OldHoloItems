package com.klin.holoItems.collections.holostars.ogaCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class OgaCollection extends Collection {
    public static final String name = "Oga";
    public static final String desc = "";
    public static final String theme = "";
    public static final String ign = "aragamioga";
    public static final String uuid = "fb729943-3352-4c87-ae87-ba104a174d3d";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYyNDg2OTI0OTQ2MiwKICAicHJvZmlsZUlkIiA6ICJkZGVkNTZlMWVmOGI0MGZlOGFkMTYyOTIwZjdhZWNkYSIsCiAgInByb2ZpbGVOYW1lIiA6ICJEaXNjb3JkQXBwIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2VlMDg0YzlkYTkxOWM3OTI0OWMzODg5Zjg2ZTNkNjNiNzZjNTMyYWE1ZjZkMTQ3MzIwZTRhYjZjNGRmMTljZDIiLAogICAgICAibWV0YWRhdGEiIDogewogICAgICAgICJtb2RlbCIgOiAic2xpbSIKICAgICAgfQogICAgfQogIH0KfQ==";
    public static final char key = '9';

    public OgaCollection(){
        super(name, desc, theme, ign, uuid, key, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
