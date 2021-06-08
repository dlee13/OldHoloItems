package com.klin.holoItems.collections.gen1.matsuriCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.Map;

public class MatsuriCollection extends Collection {
    public static final String name = "Matsuri";
    public static final String desc = "";
    public static final String theme = "";
    public static final String ign = "natsuiromatsuri";
    public static final String uuid = "5d4814bd-54ec-4461-952a-3557b9db9a51";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYyMDE4NDU1OTIxNywKICAicHJvZmlsZUlkIiA6ICI1ZDQ4MTRiZDU0ZWM0NDYxOTUyYTM1NTdiOWRiOWE1MSIsCiAgInByb2ZpbGVOYW1lIiA6ICJuYXRzdWlyb21hdHN1cmkiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDhlN2UzYjA4YmExN2M4OWRiYzJiODVhYjVmYTViNDk5MTZlZmVmMTQ5YzgwMzViOTZjNjJmMjE1Y2U5YzJjMSIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9=";

    public static final char key = 'h';

    public MatsuriCollection(){
        super(name, desc, theme, ign, uuid, key, base64);
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
