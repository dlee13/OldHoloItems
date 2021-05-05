package com.klin.holoItems.collections.gen3.noelCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.Map;

public class NoelCollection extends Collection {
    public static final String name = "Noel";
    public static final String desc = "";
    public static final String theme = "Niku consumed";
    public static final String ign = "shiroganenoel";
    public static final String uuid = "a98be009-c62d-43f1-91ce-ad7530cab205";

    public static final char key = 'v';

    public NoelCollection(){
        super(name, desc, theme, ign, uuid, key);
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
