package com.klin.holoItems;

import org.bukkit.entity.Player;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public abstract class Collection {
    public Set<Item> collection = new LinkedHashSet<>();
    public String name;
    public String desc;
    public String theme;
    public String base64;

    public Collection(String name, String desc, String theme, String base64){
        this.name = name;
        this.desc = desc;
        this.theme = theme;
        this.base64 = base64;
    }

    public abstract Map<String, Integer> getStat(Player player);
}
