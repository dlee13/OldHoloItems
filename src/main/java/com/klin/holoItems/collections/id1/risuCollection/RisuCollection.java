package com.klin.holoItems.collections.id1.risuCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class RisuCollection extends Collection {
    public static final String name = "Risu";
    public static final String desc = "";
    public static final String theme = "";
    public static final String ign = "ayunda_risu";
    public static final String uuid = "8b06684f-7615-489e-a1b6-e84838d398f2";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYyMDE4NTY1NDA2MiwKICAicHJvZmlsZUlkIiA6ICI4YjA2Njg0Zjc2MTU0ODllYTFiNmU4NDgzOGQzOThmMiIsCiAgInByb2ZpbGVOYW1lIiA6ICJBeXVuZGFfUmlzdSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS83NjllN2ZlODEyNjkyMzkzNTU0MjQ1YjFmOTNhOTU1MGRkYjRjYzU3NzUyMTFiZjhlMTY3MjI1N2Q5MDZlZThjIiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0=";

    public static final char key = 'C';

    public RisuCollection(){
        super(name, desc, theme, ign, uuid, key, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
