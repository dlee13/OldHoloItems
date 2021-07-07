package com.klin.holoItems.collections.gen5.neneCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class NeneCollection extends Collection {
    public static final String name = "Nene";
    public static final String desc = "";
    public static final String theme = "";
    public static final String ign = "supernenechii";
    public static final String uuid = "6252f15b-4e9f-4207-9ab9-793dee88436c";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYyMDE4NTQ1NjAxMCwKICAicHJvZmlsZUlkIiA6ICI2MjUyZjE1YjRlOWY0MjA3OWFiOTc5M2RlZTg4NDM2YyIsCiAgInByb2ZpbGVOYW1lIiA6ICJzdXBlcm5lbmVjaGlpIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2EzNDY0ZTg2OWY2YTZhMDRlYTdjNDk0OGI5OGI2MjMzNDVkNjg1YmEzMjMzOGE2OWRiMjk3MTkyY2UxNjc3MjQiLAogICAgICAibWV0YWRhdGEiIDogewogICAgICAgICJtb2RlbCIgOiAic2xpbSIKICAgICAgfQogICAgfQogIH0KfQ==";

    public static final char key = 'G';

    public NeneCollection(){
        super(name, desc, theme, ign, uuid, key, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
