package com.klin.holoItems.collections.stars3.ogaCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.stars3.ogaCollection.items.DemonAura;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class OgaCollection extends Collection {
    public static final String name = "Oga";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "aragamioga";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYzMjk3MzM3MTMzMiwKICAicHJvZmlsZUlkIiA6ICJhYTJhMWE0ZTExZjE0YWViYmE5YmRlZDdiNjIzNjc5MCIsCiAgInByb2ZpbGVOYW1lIiA6ICJhcmFnYW1pb2dhIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2ZkMzgwNGMxOTUwNDEwMGNiMGViNTIzMWRmYzllY2NmYjFiMDU0ZTY0YTVlNTlkNzE0OTZlNGYyYWMwYjYyMyIKICAgIH0KICB9Cn0=";

    public OgaCollection(){
        super(name, desc, theme, base64);
        collection.add(new DemonAura());
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
