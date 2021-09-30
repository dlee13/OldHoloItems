package com.klin.holoItems.collections.gen0.azkiCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class AzkiCollection extends Collection {
    public static final String name = "Azki";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "azki_";
//    public static final String uuid = "031fd87e-83a4-43ad-af62-71a3e4dc5b34";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYyMDE4NDMxMTEzNSwKICAicHJvZmlsZUlkIiA6ICIwMzFmZDg3ZTgzYTQ0M2FkYWY2MjcxYTNlNGRjNWIzNCIsCiAgInByb2ZpbGVOYW1lIiA6ICJBWktpXyIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS80NWE2NTRmMzgzMDJkMjQ1ZTU5ZWM1ZjlmNmNiNDY3NDhjODM0MmNiNTUyZDc5NjUzZjExOThhNWZhYTBhNDY4IiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0=";

    public AzkiCollection(){
        super(name, desc, theme, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
