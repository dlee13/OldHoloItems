package com.klin.holoItems.collections.gen2.aquaCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.gen2.aquaCollection.items.OnionRing;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class AquaCollection extends Collection {
    public static final String name = "Aqua";
    public static final String desc = "";
    public static final String theme = "Performed as a Maid";
//    public static final String ign = "minatoaqua";
//    public static final String uuid = "cbd93537-21e9-4852-99fc-ff459cc4ce41";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYyMDE4NDYzMjY5NCwKICAicHJvZmlsZUlkIiA6ICJjYmQ5MzUzNzIxZTk0ODUyOTlmY2ZmNDU5Y2M0Y2U0MSIsCiAgInByb2ZpbGVOYW1lIiA6ICJtaW5hdG9hcXVhIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzg1NTNmNTRlZTRhODg2NGI3ZDUyMmQ1Y2E5MjUxNmNlOWU5ZjlhNGFlOGJmM2ZkZDJkMzliZmI3NDM2NGQ4MjgiLAogICAgICAibWV0YWRhdGEiIDogewogICAgICAgICJtb2RlbCIgOiAic2xpbSIKICAgICAgfQogICAgfQogIH0KfQ=";

    public AquaCollection(){
        super(name, desc, theme, base64);
        collection.add(new OnionRing());
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        stat.put("Playtime", player.getStatistic(Statistic.PLAY_ONE_MINUTE)/1200);
        return stat;
    }
}
