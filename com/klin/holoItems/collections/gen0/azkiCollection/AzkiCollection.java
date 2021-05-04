package com.klin.holoItems.collections.gen0.azkiCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.Map;

public class AzkiCollection extends Collection {
    public static final String name = "Azki";
    public static final String desc = "";
    public static final String theme = "";
    public static final String ign = "azki_";
    public static final String uuid = "031fd87e-83a4-43ad-af62-71a3e4dc5b34";

    public static final char key = 'e';

    public AzkiCollection(){
        super(name, desc, theme, ign, uuid, key);
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
