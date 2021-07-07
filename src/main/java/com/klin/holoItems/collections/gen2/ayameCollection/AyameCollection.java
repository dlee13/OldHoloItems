package com.klin.holoItems.collections.gen2.ayameCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class AyameCollection extends Collection {
    public static final String name = "Ayame";
    public static final String desc = "";
    public static final String theme = "";
    public static final String ign = "nakiriayame";
    public static final String uuid = "26c349f8-d2ac-45d9-be08-02d00be7ffd4";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYyMDE4NDY1NTAxMywKICAicHJvZmlsZUlkIiA6ICIyNmMzNDlmOGQyYWM0NWQ5YmUwODAyZDAwYmU3ZmZkNCIsCiAgInByb2ZpbGVOYW1lIiA6ICJuYWtpcmlheWFtZSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8yYTdjYmIzYWFjZDZlM2QwODI2NDc4YmUzYjZmNDI1OTA5ZDdlMDIzYjMwY2Y3MzMyZjNjYTFmOTk1ZWZhODY4IiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0=";

    public static final char key = 'm';

    public AyameCollection(){
        super(name, desc, theme, ign, uuid, key, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
