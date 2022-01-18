package com.klin.holoItems.collections.gamers.koroneCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.gamers.koroneCollection.items.Radar;
import com.klin.holoItems.collections.gen4.watameCollection.items.UberSheepPackage;
import com.klin.holoItems.collections.gen4.watameCollection.items.LaunchPad;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class KoroneCollection extends Collection {
    public static final String name = "Korone";
    public static final String desc = "";
    public static final String theme = "Pastry baked";
//    public static final String ign = "inugamikorone";
//    public static final String uuid = "10752967-5daf-47a2-be31-01f70f0b3294";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTJlN2NkZDkwYzEyZGFkOWFmYzAwYjkzYTZlYjA4ZmNhZTkzNmM0ZDlmOTE1ZTc0MGQ0NWQ3MzAyMjkyNTczNCJ9fX0=";

    public KoroneCollection(){
        super(name, desc, theme, base64);
        collection.add(new Radar());
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
