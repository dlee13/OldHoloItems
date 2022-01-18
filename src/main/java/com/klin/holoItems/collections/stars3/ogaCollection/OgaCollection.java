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
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmQzODA0YzE5NTA0MTAwY2IwZWI1MjMxZGZjOWVjY2ZiMWIwNTRlNjRhNWU1OWQ3MTQ5NmU0ZjJhYzBiNjIzIn19fQ==";

    public OgaCollection(){
        super(name, desc, theme, base64);
        collection.add(new DemonAura());
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
