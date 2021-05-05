package com.klin.holoItems.collections.gamers.koroneCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.Map;

public class KoroneCollection extends Collection {
    public static final String name = "Korone";
    public static final String desc = "";
    public static final String theme = "Pastry baked";
    public static final String ign = "inugamikorone";
    public static final String uuid = "10752967-5daf-47a2-be31-01f70f0b3294";

    public static final char key = 'r';

    public KoroneCollection(){
        super(name, desc, theme, ign, uuid, key);
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
