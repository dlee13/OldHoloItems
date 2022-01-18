package com.klin.holoItems.collections.gen4.watameCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.gen4.watameCollection.items.LaunchPad;
import com.klin.holoItems.collections.gen4.watameCollection.items.UberSheepPackage;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class WatameCollection extends Collection {
    public static final String name = "Watame";
    public static final String desc = "";
    public static final String theme = "Gaming hitsuji";
//    public static final String ign = "tsunomakiwatame";
//    public static final String uuid = "d568ad85-75b8-429a-af5e-7e190a6babff";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTU4M2JhYWZhMjIwMGFhZGJhOWZlYTkxZTBkOGExNzRiYTEwZTNmYzY5NTU3M2M4YTUwZDAwMmYzMzhlNDg2YiJ9fX0=";

    public WatameCollection(){
        super(name, desc, theme, base64);
        collection.add(new UberSheepPackage());
        collection.add(new LaunchPad());
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
