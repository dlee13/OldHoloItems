package com.klin.holoItems.collections.gen5.botanCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.Map;

public class BotanCollection extends Collection {
    public static final String name = "Botan";
    public static final String desc = "";
    public static final String theme = "";
    public static final String ign = "botaaan";
    public static final String uuid = "fb729943-3352-4c87-ae87-ba104a174d3d";

    public static final char key = 'H';

    public BotanCollection(){
        super(name, desc, theme, ign, uuid, key);
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
