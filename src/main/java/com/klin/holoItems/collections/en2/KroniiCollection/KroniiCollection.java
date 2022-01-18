package com.klin.holoItems.collections.en2.KroniiCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.en2.KroniiCollection.items.TimeLapse;
import com.klin.holoItems.collections.en2.KroniiCollection.items.Timefall;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class KroniiCollection extends Collection {
    public static final String name = "Kronii";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "ourkronii";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTI1ZWFiZjkxNjA4ZGU2ZDI1NGRkOGQwZGU2MjRmZGRiOTkyNDg1NTBkYzZiNDY3NzZlMDlhNmIwNWEyYmFjYiJ9fX0=";

    public KroniiCollection(){
        super(name, desc, theme, base64);
        collection.add(new Timefall());
        collection.add(new TimeLapse());
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
