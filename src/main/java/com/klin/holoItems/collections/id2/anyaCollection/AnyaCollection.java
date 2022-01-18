package com.klin.holoItems.collections.id2.anyaCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.id2.anyaCollection.items.UnlimitedKrisWorks;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class AnyaCollection extends Collection {
    public static final String name = "Anya";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "anyaaaaam3lfi";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDU0N2YwZWI4ZjM2NDRlOTExMWE5NTdlMTExOTI0NWQ3YTQwMzJkZTIwMDk2ZTAzODg4OWUyZDk2M2FiNzRmNiJ9fX0=";

    public AnyaCollection(){
        super(name, desc, theme, base64);
        collection.add(new UnlimitedKrisWorks());
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
