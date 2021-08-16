package com.klin.holoItems.collections.gen3.flareCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.gen3.flareCollection.items.Splinter;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class FlareCollection extends Collection {
    public static final String name = "Flare";
    public static final String desc = "";
    public static final String theme = "Forestry achieved";
    public static final String ign = "shiranuiflare";
    public static final String uuid = "5073760c-28cc-49e4-8bfe-2561a3f7e8ef";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYyMDE4NDc5MzI4NCwKICAicHJvZmlsZUlkIiA6ICI1MDczNzYwYzI4Y2M0OWU0OGJmZTI1NjFhM2Y3ZThlZiIsCiAgInByb2ZpbGVOYW1lIiA6ICJzaGlyYW51aWZsYXJlIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2NkYTdiYTNmMWYxMDc2ZGE0NzkwNDFlNDA3YzU5YzQzZjQ3MjhiMWVmNGEzMGM3NTVjMjBiY2ZlNmFkZDc3MzQiLAogICAgICAibWV0YWRhdGEiIDogewogICAgICAgICJtb2RlbCIgOiAic2xpbSIKICAgICAgfQogICAgfQogIH0KfQ=";

    public static final char key = 'u';

    public FlareCollection(){
        super(name, desc, theme, ign, uuid, key, base64);
        collection.put(Splinter.key, new Splinter());
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        stat.put("Oak Logs cut down", player.getStatistic(Statistic.MINE_BLOCK, Material.OAK_LOG));
        stat.put("Birch Logs cut down", player.getStatistic(Statistic.MINE_BLOCK, Material.BIRCH_LOG));
        stat.put("Spruce Logs cut down", player.getStatistic(Statistic.MINE_BLOCK, Material.SPRUCE_LOG));
        stat.put("Dark Oak Logs cut down", player.getStatistic(Statistic.MINE_BLOCK, Material.DARK_OAK_LOG));
        stat.put("Acacia Logs cut down", player.getStatistic(Statistic.MINE_BLOCK, Material.ACACIA_LOG));
        stat.put("Jungle Logs cut down", player.getStatistic(Statistic.MINE_BLOCK, Material.JUNGLE_LOG));
        stat.put("Crimson Stems cut down", player.getStatistic(Statistic.MINE_BLOCK, Material.CRIMSON_STEM));
        stat.put("Warped Stems cut down", player.getStatistic(Statistic.MINE_BLOCK, Material.WARPED_STEM));

        return stat;
    }
}
