package com.klin.holoItems.collections.en1.irysCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class IrysCollection extends Collection {
    public static final String name = "Irys";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "IRySuperGlue";
//    public static final String uuid = "1cbbf835-c971-40a4-9b4e-2bd410a0a8e0";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWQ2YjMxNTcxNGE2YmUzZTlmMzVhNWNhZWE0NDQ1MTE0Y2JkYTYxMTBjNWUwMzliNGIzODJkNDI5NTg3MGVkMiJ9fX0=";

    public IrysCollection(){
        super(name, desc, theme, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
