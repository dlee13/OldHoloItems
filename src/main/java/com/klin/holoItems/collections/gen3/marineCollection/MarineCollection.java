package com.klin.holoItems.collections.gen3.marineCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.gen3.marineCollection.items.DetonationWire;
import org.bukkit.entity.Player;

import java.util.Map;

public class MarineCollection extends Collection {
    public static final String name = "Marine";
    public static final String desc = "";
    public static final String theme = "Treasure plundered";
    public static final String ign = "houshou_marine";
    public static final String uuid = "d604942f-2b4b-4452-a8da-3dbf83e235c3";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYyMDE4NDgxNTU2NCwKICAicHJvZmlsZUlkIiA6ICJkNjA0OTQyZjJiNGI0NDUyYThkYTNkYmY4M2UyMzVjMyIsCiAgInByb2ZpbGVOYW1lIiA6ICJob3VzaG91X21hcmluZSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9hNDUyNzk5MzAxNTY4YWJkNWM1ZTlhYmNkNTgzMTc2YmI1OTEwZDE3MDg3MTUxMDliZjZjZjUyMzc5ZjU3ZTciLAogICAgICAibWV0YWRhdGEiIDogewogICAgICAgICJtb2RlbCIgOiAic2xpbSIKICAgICAgfQogICAgfQogIH0KfQ=";

    public static final char key = 'w';

    public MarineCollection(){
        super(name, desc, theme, ign, uuid, key, base64);
        collection.put(DetonationWire.key, new DetonationWire());
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
