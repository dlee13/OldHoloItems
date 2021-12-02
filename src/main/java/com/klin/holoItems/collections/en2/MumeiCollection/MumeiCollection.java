package com.klin.holoItems.collections.en2.MumeiCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class MumeiCollection extends Collection {
    public static final String name = "Mumei";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "nana_mumei";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYzMzEzMTcwNjMyNywKICAicHJvZmlsZUlkIiA6ICIyM2YxYTU5ZjQ2OWI0M2RkYmRiNTM3YmZlYzEwNDcxZiIsCiAgInByb2ZpbGVOYW1lIiA6ICIyODA3IiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzlkNzZhZDIzNWMwMzkxZWJiZGU3MjU5ZWI5Y2QwZWFkNzc2ODMzZGUyNzIyNzdiZTQ3NzUyOGYwYzhiYjJmNyIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9";

    public MumeiCollection(){
        super(name, desc, theme, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
