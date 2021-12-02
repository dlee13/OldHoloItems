package com.klin.holoItems.collections.en1.watsonCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.en1.watsonCollection.items.*;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class WatsonCollection extends Collection {
    public static final String name = "Watson";
    public static final String desc = "";
    public static final String theme = "Ground excavated";
//    public static final String ign = "amwatson";
//    public static final String uuid = "6b6c5fbd-0789-4489-b5bf-91954a28e821";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYyMDE4NDE4ODQ4NywKICAicHJvZmlsZUlkIiA6ICI2YjZjNWZiZDA3ODk0NDg5YjViZjkxOTU0YTI4ZTgyMSIsCiAgInByb2ZpbGVOYW1lIiA6ICJhbXdhdHNvbiIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS81NDU0NjE5ZmU3MTk4NTZhMzQ0MDZkMWZlY2NjYTQ4MDg1YTVlMDRiMzcxMTI3ZTIwNWI4OTRkZDE5MDk2NGM5IiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0=";

    public WatsonCollection(){
        super(name, desc, theme, base64);
        collection.add(new SandPortal());
        collection.add(new Filter());
        collection.add(new GemKnife());
        collection.add(new Hourglass());
        collection.add(new MagnifyingGlass());
        collection.add(new GroundPounder());
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        stat.put("Sand mined", player.getStatistic(Statistic.MINE_BLOCK, Material.SAND));
        stat.put("Red Sand mined", player.getStatistic(Statistic.MINE_BLOCK, Material.RED_SAND));
        stat.put("Gravel mined", player.getStatistic(Statistic.MINE_BLOCK, Material.GRAVEL));
        return stat;
    }
}
