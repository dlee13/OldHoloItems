package com.klin.holoItems.collections.en.inaCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.en.inaCollection.items.*;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class InaCollection extends Collection {
    public static final String name = "Ina";
    public static final String desc = "";
    public static final String theme = "Mats farmed";
//    public static final String ign = "ninoina";
//    public static final String uuid = "8237e9c4-1c11-4c19-b29a-67c51f2045b8";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYyMDE4NDEwOTA5MCwKICAicHJvZmlsZUlkIiA6ICI4MjM3ZTljNDFjMTE0YzE5YjI5YTY3YzUxZjIwNDViOCIsCiAgInByb2ZpbGVOYW1lIiA6ICJOaW5vSW5hIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzNiMzA2OGJmN2M0NDEyODBkYTU1ZjBkOTY2ODZlMzk0MDhjNDYzNWVjMTBiZmFmNTk0ZTk4ZTc0MzMxOWU5NiIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9=";

    public InaCollection(){
        super(name, desc, theme, base64);
        collection.add(new CorruptedWheat());
        collection.add(new EarthenSpoon());
        collection.add(new CeramicLadle());
        collection.add(new WateringCan());
        collection.add(new Ingraining());
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        stat.put("Wheat harvested", player.getStatistic(Statistic.BREAK_ITEM, Material.WHEAT));
        stat.put("Beetroot harvested", player.getStatistic(Statistic.BREAK_ITEM, Material.BEETROOT));
        stat.put("Carrots harvested", player.getStatistic(Statistic.BREAK_ITEM, Material.CARROT));
        stat.put("Potato harvested", player.getStatistic(Statistic.BREAK_ITEM, Material.POTATO));
        stat.put("Bamboo harvested, halved", player.getStatistic(Statistic.BREAK_ITEM, Material.BAMBOO)/2);
        stat.put("Cocoa harvested", player.getStatistic(Statistic.BREAK_ITEM, Material.COCOA_BEANS));
        stat.put("Berries harvested", player.getStatistic(Statistic.BREAK_ITEM, Material.SWEET_BERRIES));
        stat.put("Nether Warts harvested", player.getStatistic(Statistic.BREAK_ITEM, Material.NETHER_WART));

        return stat;
    }
}
