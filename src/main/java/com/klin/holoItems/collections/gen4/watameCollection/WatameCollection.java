package com.klin.holoItems.collections.gen4.watameCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.Map;

public class WatameCollection extends Collection {
    public static final String name = "Watame";
    public static final String desc = "";
    public static final String theme = "Gaming hitsuji";
    //all colors of wool collected
    public static final String ign = "tsunomakiwatame";
    public static final String uuid = "d568ad85-75b8-429a-af5e-7e190a6babff";

    public static final char key = 'z';

    public WatameCollection(){
        super(name, desc, theme, ign, uuid, key);
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
