package com.klin.holoItems.collections.stars3.shienCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.stars3.shienCollection.items.Spur;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class ShienCollection extends Collection {
    public static final String name = "Shien";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "kageyamashien";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjI1YWRjYzU0M2FkYWIzYWY2OWNkOTFjOWY2NTg3ZTljOTY1MDEzNDljMmNkYjAxMjIzMTAzYzY2NDA1M2Y5NyJ9fX0=";

    public ShienCollection(){
        super(name, desc, theme, base64);
        collection.add(new Spur());
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
