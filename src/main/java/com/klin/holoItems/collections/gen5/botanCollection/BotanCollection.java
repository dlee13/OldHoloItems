package com.klin.holoItems.collections.gen5.botanCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.gen5.botanCollection.items.Backdash;
import com.klin.holoItems.collections.gen5.botanCollection.items.AirStall;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class BotanCollection extends Collection {
    public static final String name = "Botan";
    public static final String desc = "";
    public static final String theme = "";
    public static final String ign = "botaaan";
    public static final String uuid = "fb729943-3352-4c87-ae87-ba104a174d3d";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYyMDE4NTU0OTA0MywKICAicHJvZmlsZUlkIiA6ICJmYjcyOTk0MzMzNTI0Yzg3YWU4N2JhMTA0YTE3NGQzZCIsCiAgInByb2ZpbGVOYW1lIiA6ICJib3RhYWFuIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzE2MDFjODEyZjAzOTdjODIxMzlhYjZiYWVhNjE0ZTY5ZmQ4YWEzZTU2M2I1NzQzN2E5ZmUwODc5NTc2ZjZiNTEiLAogICAgICAibWV0YWRhdGEiIDogewogICAgICAgICJtb2RlbCIgOiAic2xpbSIKICAgICAgfQogICAgfQogIH0KfQ==";

    public static final char key = 'H';

    public BotanCollection(){
        super(name, desc, theme, ign, uuid, key, base64);
        collection.put(Backdash.key, new Backdash());
        collection.put(AirStall.key, new AirStall());
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
