package com.klin.holoItems.collections.en.calliCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.en.calliCollection.items.MomentoMori;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class CalliCollection extends Collection {
    public static final String name = "Calli";
    public static final String desc = "";
    public static final String theme = "Returned to grave";
//    public static final String ign = "moricalliopeen";
//    public static final String uuid = "14abe999-ac61-432a-ba3f-216bbe9454b3";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYyMDE4NDA1NjUwMywKICAicHJvZmlsZUlkIiA6ICIxNGFiZTk5OWFjNjE0MzJhYmEzZjIxNmJiZTk0NTRiMyIsCiAgInByb2ZpbGVOYW1lIiA6ICJtb3JpY2FsbGlvcGVFTiIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9jNGI3NTk2ZWI5OWUzOGI5MTdkOWI2YjZjOGUyMDEwMDQwOGNlOGE2NmYwMGRkNWNjYTQ4N2ViMmE5MDkwODhjIiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0=";

    public CalliCollection(){
        super(name, desc, theme, base64);
        collection.add(new MomentoMori());
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        stat.put("Drowned", player.getStatistic(Statistic.KILL_ENTITY, EntityType.DROWNED));
        stat.put("Giant", player.getStatistic(Statistic.KILL_ENTITY, EntityType.GIANT));
        stat.put("Husk", player.getStatistic(Statistic.KILL_ENTITY, EntityType.HUSK));
        stat.put("Phantom", player.getStatistic(Statistic.KILL_ENTITY, EntityType.PHANTOM));
        stat.put("Skeleton", player.getStatistic(Statistic.KILL_ENTITY, EntityType.SKELETON));
        stat.put("Skeleton Horse", player.getStatistic(Statistic.KILL_ENTITY, EntityType.SKELETON_HORSE));
        stat.put("Stray", player.getStatistic(Statistic.KILL_ENTITY, EntityType.STRAY));
        stat.put("Zombie", player.getStatistic(Statistic.KILL_ENTITY, EntityType.ZOMBIE));
        stat.put("Zombie Horse", player.getStatistic(Statistic.KILL_ENTITY, EntityType.ZOMBIE_HORSE));
        stat.put("Zombie Villager", player.getStatistic(Statistic.KILL_ENTITY, EntityType.ZOMBIE_VILLAGER));
        return stat;
    }
}
