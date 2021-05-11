package com.klin.holoItems.collections.gen2.shionCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.gen2.shionCollection.items.PotionSatchel;
import com.klin.holoItems.collections.gen2.shionCollection.items.WitchsBracelet;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class ShionCollection extends Collection {
    public static final String name = "Shion";
    public static final String desc = "NEEEEEEEEEEEEEEEEEEEEEEE";
    public static final String theme = "Ingredients mixed";
    public static final String ign = "murasakishion";
    public static final String uuid = "1ffbc76f-98ad-4c29-9fcd-8d7878b29248";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYyMDE4NDcwNDcyMiwKICAicHJvZmlsZUlkIiA6ICIxZmZiYzc2Zjk4YWQ0YzI5OWZjZDhkNzg3OGIyOTI0OCIsCiAgInByb2ZpbGVOYW1lIiA6ICJtdXJhc2FraXNoaW9uIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2I5MWQxNzUyYzI1Zjc1Y2EyM2ZhODJhZmE1MjcxMDAxM2IwYTIzZjU3ZjZiOWY2MjI2MmJlNmMyNWI3NzAyOGIiLAogICAgICAibWV0YWRhdGEiIDogewogICAgICAgICJtb2RlbCIgOiAic2xpbSIKICAgICAgfQogICAgfQogIH0KfQ=";

    public static final char key = 'l';

    public ShionCollection(){
        super(name, desc, theme, ign, uuid, key, base64);
        collection.put(WitchsBracelet.key, new WitchsBracelet());
        collection.put(PotionSatchel.key, new PotionSatchel());
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        stat.put("Redstone infused", player.getStatistic(Statistic.BREAK_ITEM, Material.REDSTONE));
        stat.put("Glowstone infused", player.getStatistic(Statistic.BREAK_ITEM, Material.GLOWSTONE_DUST));
        stat.put("Fermented Spider Eye infused", player.getStatistic(Statistic.BREAK_ITEM, Material.FERMENTED_SPIDER_EYE));
        stat.put("Gunpowder infused", player.getStatistic(Statistic.BREAK_ITEM, Material.GUNPOWDER));
        stat.put("Dragon's Breath infused", player.getStatistic(Statistic.BREAK_ITEM, Material.DRAGON_BREATH));
        stat.put("Sugar infused", player.getStatistic(Statistic.BREAK_ITEM, Material.SUGAR));
        stat.put("Rabbit's Foot infused", player.getStatistic(Statistic.BREAK_ITEM, Material.RABBIT_FOOT));
        stat.put("Glistering Melon infused", player.getStatistic(Statistic.BREAK_ITEM, Material.GLISTERING_MELON_SLICE));
        stat.put("Spider Eye infused", player.getStatistic(Statistic.BREAK_ITEM, Material.SPIDER_EYE));
        stat.put("Pufferfish infused", player.getStatistic(Statistic.BREAK_ITEM, Material.PUFFERFISH));
        stat.put("Magma Cream infused", player.getStatistic(Statistic.BREAK_ITEM, Material.MAGMA_CREAM));
        stat.put("Golden Carrot infused", player.getStatistic(Statistic.BREAK_ITEM, Material.GOLDEN_CARROT));
        stat.put("Blaze Powder infused", player.getStatistic(Statistic.BREAK_ITEM, Material.BLAZE_POWDER));
        stat.put("Ghast Tear infused", player.getStatistic(Statistic.BREAK_ITEM, Material.GHAST_TEAR));
        stat.put("Turtle Shell infused", player.getStatistic(Statistic.BREAK_ITEM, Material.TURTLE_HELMET));
        stat.put("Phantom Membrane infused", player.getStatistic(Statistic.BREAK_ITEM, Material.PHANTOM_MEMBRANE));

        return stat;
    }
}
