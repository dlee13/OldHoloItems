package com.klin.holoItems.collections.holoCouncil.FaunaCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class FaunaCollection extends Collection {
    public static final String name = "Fauna";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "faunaceres";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYyOTcwNDc0NzM0NSwKICAicHJvZmlsZUlkIiA6ICJkZTE0MGFmM2NmMjM0ZmM0OTJiZTE3M2Y2NjA3MzViYiIsCiAgInByb2ZpbGVOYW1lIiA6ICJTUlRlYW0iLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTBjZDNlMTAyNWM1NGNmNTg4Zjc5NmRjZDBmYTNjNWRkODRjZmZiMTE1OGQ0ZTMxZDljNDlhOWFhMDdmZDAzMCIKICAgIH0KICB9Cn0=";

    public FaunaCollection(){
        super(name, desc, theme, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
