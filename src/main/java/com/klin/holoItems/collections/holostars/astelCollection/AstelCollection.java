package com.klin.holoItems.collections.holostars.astelCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class AstelCollection extends Collection {
    public static final String name = "Astel";
    public static final String desc = "";
    public static final String theme = "";
    public static final String ign = "astelleda";
    public static final String uuid = "fb729943-3352-4c87-ae87-ba104a174d3d";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYxMTU3Njc1MTU1NSwKICAicHJvZmlsZUlkIiA6ICJkZGVkNTZlMWVmOGI0MGZlOGFkMTYyOTIwZjdhZWNkYSIsCiAgInByb2ZpbGVOYW1lIiA6ICJEaXNjb3JkQXBwIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzNjMjc0YmQ3ZjI3ZTA3ODcxNDYwMTMyNzE0NjM2YTcxNmRkNjE5ODJhMjc1MTk3NzIxNmE5Y2JmY2RkOWFiYjQiCiAgICB9CiAgfQp9";
    public static final char key = '5';

    public AstelCollection(){
        super(name, desc, theme, ign, uuid, key, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
