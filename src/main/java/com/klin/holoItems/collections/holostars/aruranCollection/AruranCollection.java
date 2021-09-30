package com.klin.holoItems.collections.holostars.aruranCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class AruranCollection extends Collection {
    public static final String name = "Aruran";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "arurandeisu";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYyNDg3MzE2Nzk3MSwKICAicHJvZmlsZUlkIiA6ICI2Mzk3ZWE2NTVkNzE0YTZmYmE2OGM0MzE0OTQxODA5OSIsCiAgInByb2ZpbGVOYW1lIiA6ICJBcnVyYW5kZWlzdSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9mZTFkNDVjODM5ZDE4YjQ5OGJiYzIxZWExZDBmZGNkYzFjYzA3ZmZiZDgzOTNiMDQyNWYxOTY2YmJiOTYxOGIzIgogICAgfQogIH0KfQ==";

    public AruranCollection(){
        super(name, desc, theme, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
