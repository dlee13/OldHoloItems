package com.klin.holoItems.collections.misc.franCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.misc.franCollection.items.*;
import org.bukkit.entity.Player;

import java.util.Map;

public class FranCollection extends Collection {
    public static final String name = "Fran";
    public static final String desc = null;
    public static final String theme = null;
    public static final String ign = null;
    public static final String uuid = null;
    public static final String base64 = null;

    public static final char key = 'X';

    public FranCollection(){
        super(name, desc, theme, ign, uuid, key, base64);
        collection.put(TotemPole.key, new TotemPole());
        collection.put(SteinsEgg.key, new SteinsEgg());
        collection.put(LifeCrystal.key, new LifeCrystal());
        collection.put(BreadCrumbs.key, new BreadCrumbs());
        collection.put(LaserPointer.key, new LaserPointer());
        collection.put(SlimeJelly.key, new SlimeJelly());
        collection.put(DyeConcentrate.key, new DyeConcentrate());
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
