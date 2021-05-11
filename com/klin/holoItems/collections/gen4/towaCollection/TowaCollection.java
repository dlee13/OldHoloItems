package com.klin.holoItems.collections.gen4.towaCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.Map;

public class TowaCollection extends Collection {
    public static final String name = "Towa";
    public static final String desc = "";
    public static final String theme = "";
    public static final String ign = "towasama";
    public static final String uuid = "8d257d00-57cd-4bb5-a75a-4ee476f72953";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYyMDE4NTAwMzM1OSwKICAicHJvZmlsZUlkIiA6ICI4ZDI1N2QwMDU3Y2Q0YmI1YTc1YTRlZTQ3NmY3Mjk1MyIsCiAgInByb2ZpbGVOYW1lIiA6ICJUb3dhc2FtYSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9kZjk1ZmMyMzM5MmFmN2U1YzVjMjBjNzZhMjIwOGRmNDkyMmIxMDhmMmM4OWUzMzUyMjQwMjYxMmM1OGRiNTA4IiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0=";

    public static final char key = 'A';

    public TowaCollection(){
        super(name, desc, theme, ign, uuid, key, base64);
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
