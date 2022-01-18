package com.klin.holoItems.collections.gen1.fubukiCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.gen1.fubukiCollection.items.Plow;
import com.klin.holoItems.collections.gen1.fubukiCollection.items.VerificationSeal;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class FubukiCollection extends Collection {
    public static final String name = "Fubuki";
    public static final String desc = "";
    public static final String theme = "Damage dealt";
//    public static final String ign = "shirakamifubuki";
//    public static final String uuid = "703a3d75-8988-4d96-a24e-1f2cc83c5439";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzVjOTc1ZGViMjhkZTMzNjQ3ODc5MDhmYjBmNjkzYjIwMGViMTZjNTBmZmQ3ZmNmNjJjZGJlMjM2NjViOGYzOSJ9fX0=";

    public FubukiCollection(){
        super(name, desc, theme, base64);
        collection.add(new VerificationSeal());
        collection.add(new Plow());
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
