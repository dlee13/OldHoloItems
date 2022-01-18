package com.klin.holoItems.collections.id1.moonaCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.id1.moonaCollection.items.LunarLaser;
import com.klin.holoItems.collections.id1.moonaCollection.items.Partitioner;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class MoonaCollection extends Collection {
    public static final String name = "Moona";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "moonahoshinova";
//    public static final String uuid = "3800f421-5195-478b-b8f2-c3224cc7e041";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzdjYzc4YzNlMmI5ZDczYmJiMTM3NzFjNGY1YzVlYzJlYjFmMGEzMzI0ODdjYzgxY2IwMDI5NjhjMGE2MzUzOSJ9fX0=";

    public MoonaCollection(){
        super(name, desc, theme, base64);
        collection.add(new Partitioner());
        collection.add(new LunarLaser());
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
