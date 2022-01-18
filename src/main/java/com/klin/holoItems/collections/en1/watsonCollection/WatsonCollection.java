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
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTQ1NDYxOWZlNzE5ODU2YTM0NDA2ZDFmZWNjY2E0ODA4NWE1ZTA0YjM3MTEyN2UyMDViODk0ZGQxOTA5NjRjOSJ9fX0=";

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
