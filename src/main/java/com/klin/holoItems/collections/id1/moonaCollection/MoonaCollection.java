package com.klin.holoItems.collections.id1.moonaCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.Map;

public class MoonaCollection extends Collection {
    public static final String name = "Moona";
    public static final String desc = "";
    public static final String theme = "";
    public static final String ign = "moonahoshinova";
    public static final String uuid = "3800f421-5195-478b-b8f2-c3224cc7e041";

    public static final char key = 'D';

    public MoonaCollection(){
        super(name, desc, theme, ign, uuid, key);
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
