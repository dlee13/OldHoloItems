package com.klin.holoItems.collections.misc.ingredientsCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.misc.ingredientsCollection.items.CoalPetal;
import com.klin.holoItems.collections.misc.ingredientsCollection.items.EmeraldLeaf;
import com.klin.holoItems.collections.misc.ingredientsCollection.items.QuartzFragment;
import com.klin.holoItems.collections.misc.ingredientsCollection.items.SaintQuartz;
import org.bukkit.entity.Player;

import java.util.Map;

public class IngredientsCollection extends Collection {
    public static final String name = "Ingredients";
    public static final String desc = "";
    public static final String theme = "";
    public static final String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDVjNmRjMmJiZjUxYzM2Y2ZjNzcxNDU4NWE2YTU2ODNlZjJiMTRkNDdkOGZmNzE0NjU0YTg5M2Y1ZGE2MjIifX19";

    public IngredientsCollection(){
        super(name, desc, theme, base64);
        collection.add(new EmeraldLeaf());
        collection.add(new CoalPetal());
        collection.add(new QuartzFragment());
        collection.add(new SaintQuartz());
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
