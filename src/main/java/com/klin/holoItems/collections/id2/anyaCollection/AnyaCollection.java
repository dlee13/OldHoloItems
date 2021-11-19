package com.klin.holoItems.collections.id2.anyaCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.id2.anyaCollection.items.UnlimitedKrisWorks;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class AnyaCollection extends Collection {
    public static final String name = "Anya";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "anyaaaaam3lfi";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYzNjQ0ODM4MDI4OCwKICAicHJvZmlsZUlkIiA6ICJjMGI3Mjk3NzBkZTE0ZmYyODE4MWRlMDRiN2UxZGFkYyIsCiAgInByb2ZpbGVOYW1lIiA6ICJhbnlhYWFhYW0zbGZpIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzQ1NDdmMGViOGYzNjQ0ZTkxMTFhOTU3ZTExMTkyNDVkN2E0MDMyZGUyMDA5NmUwMzg4ODllMmQ5NjNhYjc0ZjYiLAogICAgICAibWV0YWRhdGEiIDogewogICAgICAgICJtb2RlbCIgOiAic2xpbSIKICAgICAgfQogICAgfQogIH0KfQ==";

    public AnyaCollection(){
        super(name, desc, theme, base64);
        collection.add(new UnlimitedKrisWorks());
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
