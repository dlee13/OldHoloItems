package com.klin.holoItems.collections.gen0.robocosanCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class RobocosanCollection extends Collection {
    public static final String name = "Robocosan";
    public static final String desc = "";
    public static final String theme = "Infrastructure contributed";
    public static final String ign = "robocosan";
    public static final String uuid = "132dfbe1-071d-4932-a5e0-bdc323537bd4";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYyMDE4NDM2Nzg0MSwKICAicHJvZmlsZUlkIiA6ICIxMzJkZmJlMTA3MWQ0OTMyYTVlMGJkYzMyMzUzN2JkNCIsCiAgInByb2ZpbGVOYW1lIiA6ICJyb2JvY29zYW4iLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWIyOWVlYmM5ZWJkZGFiMmZlNmQ4Nzc1YTdiOGE5NGExZDQxMDg5YmI4MTc2Y2E1ZTY2OWU2ZDYxYTgwNjdmNCIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9=";

    public static final char key = 'c';

    public RobocosanCollection(){
        super(name, desc, theme, ign, uuid, key, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
