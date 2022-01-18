package com.klin.holoItems.collections.gen5.lamyCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.gen5.lamyCollection.items.Condensation;
import com.klin.holoItems.collections.gen5.lamyCollection.items.FrostLayer;
import com.klin.holoItems.collections.gen5.lamyCollection.items.Starch;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class LamyCollection extends Collection {
    public static final String name = "Lamy";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "lamy_yukihana";
//    public static final String uuid = "23c163a1-e2c4-4790-a9ee-799e79a3cb56";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmExNTlmMzIxMmViZTI2OWUwZmQ3YzA4NjZhMjc5MTRjOTFiNWZkM2Y2NzY3MmFmZjE1MGZiMDAzNTQwYTEyOCJ9fX0=";

    public LamyCollection(){
        super(name, desc, theme, base64);
        collection.add(new Starch());
        collection.add(new Condensation());
        collection.add(new FrostLayer());
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
