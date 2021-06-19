package com.klin.holoItems.collections.gen1.matsuriCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.Map;

public class MatsuriCollection extends Collection {
    public static final String name = "Matsuri";
    public static final String desc = "";
    public static final String theme = "";
    public static final String ign = "natsuiromatsuri";
    public static final String uuid = "5d4814bd-54ec-4461-952a-3557b9db9a51";

    public static final char key = 'h';

    public MatsuriCollection(){
        super(name, desc, theme, ign, uuid, key);
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
