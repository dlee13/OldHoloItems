package com.klin.holoItems.collections.gamers.okayuCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.Map;

public class OkayuCollection extends Collection {
    public static final String name = "Okayu";
    public static final String desc = "";
    public static final String theme = "";
    public static final String ign = "nekomata_okayu";
    public static final String uuid = "ece77695-b296-4172-a7e4-fd1b96503534";

    public static final char key = 'q';

    public OkayuCollection(){
        super(name, desc, theme, ign, uuid, key);
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
