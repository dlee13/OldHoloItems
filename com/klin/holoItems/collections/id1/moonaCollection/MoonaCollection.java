package com.klin.holoItems.collections.id1.moonaCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.id1.moonaCollection.items.Lightbulb;
import org.bukkit.entity.Player;

import java.util.Map;

public class MoonaCollection extends Collection {
    public static final String name = "Moona";
    public static final String desc = "";
    public static final String theme = "";
    public static final String ign = "moonahoshinova";
    public static final String uuid = "3800f421-5195-478b-b8f2-c3224cc7e041";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYyMDE4NTYxOTQxMCwKICAicHJvZmlsZUlkIiA6ICIzODAwZjQyMTUxOTU0NzhiYjhmMmMzMjI0Y2M3ZTA0MSIsCiAgInByb2ZpbGVOYW1lIiA6ICJNb29uYUhvc2hpbm92YSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9jN2NjNzhjM2UyYjlkNzNiYmIxMzc3MWM0ZjVjNWVjMmViMWYwYTMzMjQ4N2NjODFjYjAwMjk2OGMwYTYzNTM5IiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0=";

    public static final char key = 'D';

    public MoonaCollection(){
        super(name, desc, theme, ign, uuid, key, base64);
        collection.put(Lightbulb.key, new Lightbulb());
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}
