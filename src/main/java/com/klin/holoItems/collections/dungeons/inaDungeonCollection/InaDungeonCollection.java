package com.klin.holoItems.collections.dungeons.inaDungeonCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.dungeons.inaDungeonCollection.items.*;
import org.bukkit.entity.Player;

import java.util.Map;

public class InaDungeonCollection extends Collection {
    public static final String name = null;
    public static final String desc = null;
    public static final String theme = null;
    public static final String base64 = null;

    public InaDungeonCollection(){
        super(name, desc, theme, base64);
        collection.add(new SteelRail());
        collection.add(new Torrent());
        collection.add(new CookieJar());

        collection.add(new AshWood());
        collection.add(new BoneFragment());
        collection.add(new BoneCrystal());
        collection.add(new BlackPowder());
        collection.add(new CoarseSand());
        collection.add(new DepthCharge());
        collection.add(new PieceOfEight());
        collection.add(new TemperedBottle());
        collection.add(new TemperedBottle());
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
