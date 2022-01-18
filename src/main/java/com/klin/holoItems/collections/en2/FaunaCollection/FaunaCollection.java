package com.klin.holoItems.collections.en2.FaunaCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class FaunaCollection extends Collection {
    public static final String name = "Fauna";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "faunaceres";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTBjZDNlMTAyNWM1NGNmNTg4Zjc5NmRjZDBmYTNjNWRkODRjZmZiMTE1OGQ0ZTMxZDljNDlhOWFhMDdmZDAzMCJ9fX0=";

    public FaunaCollection(){
        super(name, desc, theme, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
