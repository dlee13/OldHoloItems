package com.klin.holoItems.collections.gen3.rushiaCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.gen3.rushiaCollection.items.NecromancersTome;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class RushiaCollection extends Collection {
    public static final String name = "Rushia";
    public static final String desc = "";
    public static final String theme = "Magic dealt";
    public static final String ign = "uruharushia";
    public static final String uuid = "a8d49696-9a90-4a3f-8f16-7f93c16ec6ec";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYyMDE4NDg5OTg5NCwKICAicHJvZmlsZUlkIiA6ICJhOGQ0OTY5NjlhOTA0YTNmOGYxNjdmOTNjMTZlYzZlYyIsCiAgInByb2ZpbGVOYW1lIiA6ICJ1cnVoYXJ1c2hpYSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8yMmY3NmI4MWY3ZWFmZGJkNzgyNTJlZjVkYWIyYjRhYTFjYWZkNGFjZjZlMDEzN2EyYTE0Nzk3NWU3YThhZTljIiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0=";

    public static final char key = 't';

    public RushiaCollection(){
        super(name, desc, theme, ign, uuid, key, base64);
        collection.put(NecromancersTome.key, new NecromancersTome());
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        stat.put("Splash Potions thrown", player.getStatistic(Statistic.USE_ITEM, Material.SPLASH_POTION));
        stat.put("Lingering Potions thrown", player.getStatistic(Statistic.USE_ITEM, Material.LINGERING_POTION));

        return stat;
    }
}
