package com.klin.holoItems.collections.stars1.izuruCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class IzuruCollection extends Collection {
    public static final String name = "Izuru";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "kanadeizuru";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYyNDg3MzY1NDMxNCwKICAicHJvZmlsZUlkIiA6ICJlNzVkNjkzY2ZjMDM0ODNmOWRhNWEwMmU4Mzg4OWZkNCIsCiAgInByb2ZpbGVOYW1lIiA6ICJrYW5hZGVpenVydSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9mMmFjYTM4MGE4M2RhOWNjZmEyNjIxZjNkZWRkYjE5YzRhMWI0MDk1ZjUyMzc1NmFmMTM0YzIzMTY3ZDA1NWJlIgogICAgfQogIH0KfQ==";

    public IzuruCollection(){
        super(name, desc, theme, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
