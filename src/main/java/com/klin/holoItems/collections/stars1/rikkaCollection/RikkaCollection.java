package com.klin.holoItems.collections.stars1.rikkaCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class RikkaCollection extends Collection {
    public static final String name = "Rikka";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "rikka415";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYxNTA4NjU5ODAxOCwKICAicHJvZmlsZUlkIiA6ICIzOTg5OGFiODFmMjU0NmQxOGIyY2ExMTE1MDRkZGU1MCIsCiAgInByb2ZpbGVOYW1lIiA6ICJNeVV1aWRJcyIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS83MWFiYTdlNmU2MGJjYzgwODJjZDI1MTFmNmQ5NmM4MmRhMzE4NGEwYmIyYjYyZjE3ZDIwMGQ0OGM5NmIzZTczIgogICAgfQogIH0KfQ==";

    public RikkaCollection(){
        super(name, desc, theme, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
