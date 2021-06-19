package com.klin.holoItems.collections.gen5.lamyCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.gen5.lamyCollection.items.Condensation;
import com.klin.holoItems.collections.gen5.lamyCollection.items.CubeTray;
import com.klin.holoItems.collections.gen5.lamyCollection.items.FrostLayer;
import org.bukkit.entity.Player;

import java.util.Map;

public class LamyCollection extends Collection {
    public static final String name = "Lamy";
    public static final String desc = "";
    public static final String theme = "";
    public static final String ign = "lamy_yukihana";
    public static final String uuid = "23c163a1-e2c4-4790-a9ee-799e79a3cb56";

    public static final char key = 'F';

    public LamyCollection(){
        super(name, desc, theme, ign, uuid, key);
        collection.put(CubeTray.key, new CubeTray());
        collection.put(Condensation.key, new Condensation());
        collection.put(FrostLayer.key, new FrostLayer());
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
