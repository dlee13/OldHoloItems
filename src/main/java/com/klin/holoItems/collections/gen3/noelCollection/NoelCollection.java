package com.klin.holoItems.collections.gen3.noelCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.gen3.noelCollection.items.MilkBottle;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class NoelCollection extends Collection {
    public static final String name = "Noel";
    public static final String desc = "";
    public static final String theme = "Niku consumed";
    public static final String ign = "shiroganenoel";
    public static final String uuid = "a98be009-c62d-43f1-91ce-ad7530cab205";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYyMDE4NDg0NTMwMSwKICAicHJvZmlsZUlkIiA6ICJhOThiZTAwOWM2MmQ0M2YxOTFjZWFkNzUzMGNhYjIwNSIsCiAgInByb2ZpbGVOYW1lIiA6ICJzaGlyb2dhbmVub2VsIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2I2MzI0NjE0NDVlNGJmZjkyMDM5ZGZjYjg5YzViMTdmZGMyZDczYTVkMTEyMGIxYzkyNzFkODY0NGE0ZThjMDgiLAogICAgICAibWV0YWRhdGEiIDogewogICAgICAgICJtb2RlbCIgOiAic2xpbSIKICAgICAgfQogICAgfQogIH0KfQ=";

    public static final char key = 'v';

    public NoelCollection(){
        super(name, desc, theme, ign, uuid, key, base64);
        collection.put(MilkBottle.key, new MilkBottle());
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
