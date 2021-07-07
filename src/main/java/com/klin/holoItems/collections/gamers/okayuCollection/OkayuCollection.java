package com.klin.holoItems.collections.gamers.okayuCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class OkayuCollection extends Collection {
    public static final String name = "Okayu";
    public static final String desc = "";
    public static final String theme = "";
    public static final String ign = "nekomata_okayu";
    public static final String uuid = "ece77695-b296-4172-a7e4-fd1b96503534";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYyMDE4NDI4NDgxMiwKICAicHJvZmlsZUlkIiA6ICJlY2U3NzY5NWIyOTY0MTcyYTdlNGZkMWI5NjUwMzUzNCIsCiAgInByb2ZpbGVOYW1lIiA6ICJuZWtvbWF0YV9va2F5dSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS80NzlkZDZkNjBlYjM5MzQ1NjU4NzA5MmFjNDkwOGYzZTU2YWViY2I3NWRlMjE4NjgzNjUxMmI2OGI4ZjY0NTJkIiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0=";

    public static final char key = 'q';

    public OkayuCollection(){
        super(name, desc, theme, ign, uuid, key, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
