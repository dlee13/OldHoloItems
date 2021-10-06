package com.klin.holoItems.collections.holoCouncil.KroniiCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class KroniiCollection extends Collection {
    public static final String name = "Kronii";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "ourkronii";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYyOTg3NTU4NDgxOSwKICAicHJvZmlsZUlkIiA6ICJjNjc3MGJjZWMzZjE0ODA3ODc4MTU0NWRhMGFmMDI1NCIsCiAgInByb2ZpbGVOYW1lIiA6ICJDVUNGTDE2IiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzUyNWVhYmY5MTYwOGRlNmQyNTRkZDhkMGRlNjI0ZmRkYjk5MjQ4NTUwZGM2YjQ2Nzc2ZTA5YTZiMDVhMmJhY2IiCiAgICB9CiAgfQp9";

    public KroniiCollection(){
        super(name, desc, theme, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
