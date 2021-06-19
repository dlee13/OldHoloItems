package com.klin.holoItems;

import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public abstract class Collection {
    public Map<Character, Item> collection = new LinkedHashMap<>();
    public String name;
    public String desc;
    public String theme;
    public String ign;
    public UUID uuid;
    public char key;

    public Collection(String name, String desc, String theme, String ign, String uuid, char key){
        this.name = name;
        this.desc = desc;
        this.theme = theme;
        this.ign = ign;
        this.uuid = uuidFromString(uuid);
        this.key = key;
    }

    private static UUID uuidFromString(String uuid) {
        try {
            return UUID.fromString(uuid);
        } catch (Exception e) {
            return null;
        }
    }

    public abstract Map<String, Integer> getStat(Player player);
}
