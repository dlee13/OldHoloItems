package com.klin.holoItems.collections.en2.BaeCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class BaeCollection extends Collection {
    public static final String name = "Bae";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "whatabae";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYyOTk4NzkxOTQxOSwKICAicHJvZmlsZUlkIiA6ICIwNWJhN2FmOGY0M2M0NGFjYWJkZjkzZjVmMTk2Njg3NiIsCiAgInByb2ZpbGVOYW1lIiA6ICJESkdyb3VuZDAiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjRhOTg1ZjE1NjBmNTdjMTgzODNkYjNjYzQ2MTUwMjg3OWY5YjYyOGM0ZDNlMTJhZGUzN2JlNjU2NzMyMzY5MCIKICAgIH0KICB9Cn0=";

    public BaeCollection(){
        super(name, desc, theme, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
