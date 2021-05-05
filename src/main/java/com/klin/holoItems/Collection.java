package com.klin.holoItems;

import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Collection {
    public Map<Character, Item> collection = new LinkedHashMap<>();
    public String name;
    public String desc;
    public String theme;
    public String ign;
    public String uuid;
    public char key;
    public String base64;

    public Collection(String name, String desc, String theme, String ign, String uuid, char key, String base64){
        this.name = name;
        this.desc = desc;
        this.theme = theme;
        this.ign = ign;
        this.uuid = uuid;
        this.key = key;
        this.base64 = base64;
    }

    public abstract Map<String, Integer> getStat(Player player);
}
