package com.klin.holoItems.collections.gen1.fubukiCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.Map;

public class FubukiCollection extends Collection {
    public static final String name = "Fubuki";
    public static final String desc = "";
    public static final String theme = "Damage dealt";
    public static final String ign = "shirakamifubuki";
    public static final String uuid = "703a3d75-8988-4d96-a24e-1f2cc83c5439";

    public static final char key = 'g';

    public FubukiCollection(){
        super(name, desc, theme, ign, uuid, key);
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
