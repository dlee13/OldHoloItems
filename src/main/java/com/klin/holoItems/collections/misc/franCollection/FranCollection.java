package com.klin.holoItems.collections.misc.franCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.misc.franCollection.items.*;
import org.bukkit.entity.Player;

import java.util.Map;

public class FranCollection extends Collection {
    public static final String name = null;
    public static final String desc = null;
    public static final String theme = null;
    public static final String base64 = null;

    public FranCollection(){
        super(name, desc, theme, base64);
        collection.add(new TotemPole());
        collection.add(new SteinsEgg());
        collection.add(new LifeCrystal());
        collection.add(new BreadCrumbs());
        collection.add(new LaserPointer());
        collection.add(new SlimeJelly());
        collection.add(new DyeConcentrate());
        collection.add(new SharpenedFangs());
        collection.add(new NinjaCloak());
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
