package com.klin.holoItems.collections.holostars.miyabiCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class MiyabiCollection extends Collection {
    public static final String name = "Miyabi";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "miyabi_hanasaki";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYyNDg3Mzc5MDYzMCwKICAicHJvZmlsZUlkIiA6ICIxZWExNTY3MjNlMjE0MzM5YmUxNDE1MzBiMTI2M2I5MSIsCiAgInByb2ZpbGVOYW1lIiA6ICJNaXlhYmlfSGFuYXNha2kiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTM2NDQ4NDcyZjRiOWE0OGY4MDM0NThhZWUxMDgwZDliODJmNmFjYzE1OWYzMTJkZWUxZjAwZjM5MGYyYWVhNyIKICAgIH0KICB9Cn0=";

    public MiyabiCollection(){
        super(name, desc, theme, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
