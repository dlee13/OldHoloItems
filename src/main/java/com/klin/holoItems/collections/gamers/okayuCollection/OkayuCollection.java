package com.klin.holoItems.collections.gamers.okayuCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.gamers.okayuCollection.items.BadAutoEat;
import com.klin.holoItems.collections.gamers.okayuCollection.items.MoguMogu;
import com.klin.holoItems.collections.gamers.okayuCollection.items.GoodAutoEat;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.*;

public class OkayuCollection extends Collection {
    public static final String name = "Okayu";
    public static final String desc = "Mogu mogu yummy!";
    public static final String theme = "Good food eaten";
//    public static final String ign = "nekomata_okayu";
//    public static final String uuid = "ece77695-b296-4172-a7e4-fd1b96503534";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDc5ZGQ2ZDYwZWIzOTM0NTY1ODcwOTJhYzQ5MDhmM2U1NmFlYmNiNzVkZTIxODY4MzY1MTJiNjhiOGY2NDUyZCJ9fX0=";

    // All autoEat enchantments
    // Used because all auto-eat enchantments are exclusive to all other auto-eat enchantments
    public static final ArrayList<String> autoEatEnchs = new ArrayList<>(List.of(BadAutoEat.name, MoguMogu.name, AutoEat.name, GoodAutoEat.name));

    public OkayuCollection(){
        super(name, desc, theme, base64);
        // Enchantments are done this way because Klin decided to write them this way.
        // Not his fault, though: For some reason papermc doesn't let you add custom enchantments.
        // His workaround is good, especially for this, but yeah.

        collection.add(new BadAutoEat());
        collection.add(new MoguMogu());
        collection.add(new AutoEat());
        collection.add(new GoodAutoEat());
        // If I ever get approval/feel like doing the Lv5 which can also search your e-chest and its shulkers:
        // SuperAutoEat
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        stat.put("God apples eaten", player.getStatistic(Statistic.USE_ITEM, Material.ENCHANTED_GOLDEN_APPLE));
        stat.put("Golden carrots eaten", player.getStatistic(Statistic.USE_ITEM, Material.GOLDEN_CARROT));
        stat.put("Cooked mutton eaten", player.getStatistic(Statistic.USE_ITEM, Material.COOKED_MUTTON));
        stat.put("Cooked porkchop eaten", player.getStatistic(Statistic.USE_ITEM, Material.COOKED_PORKCHOP));
        stat.put("Cooked salmon eaten", player.getStatistic(Statistic.USE_ITEM, Material.COOKED_SALMON));
        stat.put("Cooked steak eaten", player.getStatistic(Statistic.USE_ITEM, Material.COOKED_BEEF));
        return stat;
    }
}
