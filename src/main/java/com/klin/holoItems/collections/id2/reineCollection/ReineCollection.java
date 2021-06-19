package com.klin.holoItems.collections.id2.reineCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.Map;

public class ReineCollection extends Collection {
    public static final String name = "Reine";
    public static final String desc = "";
    public static final String theme = "";
    public static final String ign = "reineeeee";
    public static final String uuid = "f91804e5-6637-4ac8-9684-6921d8b76b5c";

    public static final char key = 'Q';

    public ReineCollection(){
        super(name, desc, theme, ign, uuid, key);
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
