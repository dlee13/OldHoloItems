package com.klin.holoItems.collections.gen3.noelCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.gen3.noelCollection.items.MilkBottle;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class NoelCollection extends Collection {
    public static final String name = "Noel";
    public static final String desc = "";
    public static final String theme = "Niku consumed";
//    public static final String ign = "shiroganenoel";
//    public static final String uuid = "a98be009-c62d-43f1-91ce-ad7530cab205";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjYzMjQ2MTQ0NWU0YmZmOTIwMzlkZmNiODljNWIxN2ZkYzJkNzNhNWQxMTIwYjFjOTI3MWQ4NjQ0YTRlOGMwOCJ9fX0=";

    public NoelCollection(){
        super(name, desc, theme, base64);
        collection.add(new MilkBottle());
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
