package com.klin.holoItems.collections.gen2.chocoCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.Map;

public class ChocoCollection extends Collection {
    public static final String name = "Choco";
    public static final String desc = "";
    public static final String theme = "";
    public static final String ign = "yuzukichoco";
    public static final String uuid = "0c3e0035-d7ce-4efa-b3ad-f5a43e2a74f6";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYyMDE4NDY3ODk3MCwKICAicHJvZmlsZUlkIiA6ICIwYzNlMDAzNWQ3Y2U0ZWZhYjNhZGY1YTQzZTJhNzRmNiIsCiAgInByb2ZpbGVOYW1lIiA6ICJ5dXp1a2ljaG9jbyIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS82MWQ4YjQ2ODhkZTI0YWRkMWNhOTA2ZWQ4YTJiZWE5ZjNhNWNlMjQxNmQyOThlMjViZTA3MmNlMDNiNTBhNjUxIiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0=";

    public static final char key = 'n';

    public ChocoCollection(){
        super(name, desc, theme, ign, uuid, key, base64);
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
