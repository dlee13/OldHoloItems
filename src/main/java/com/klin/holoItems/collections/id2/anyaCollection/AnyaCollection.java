package com.klin.holoItems.collections.id2.anyaCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class AnyaCollection extends Collection {
    public static final String name = "Anya";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "anya_melfissa";
//    public static final String uuid = "a39a7d00-0ad5-42f3-903d-ccb553b682d7";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYyMDE4NTY5MjMzMCwKICAicHJvZmlsZUlkIiA6ICJhMzlhN2QwMDBhZDU0MmYzOTAzZGNjYjU1M2I2ODJkNyIsCiAgInByb2ZpbGVOYW1lIiA6ICJBbnlhX01lbGZpc3NhIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzZjOTNiODA1ODM0Njc0NDUxNDRlNWQyZGYyN2QzMGYwYmNiYTIxNGU1OTMzOWY5NzE2ODljYzkyZTliNjI3N2YiLAogICAgICAibWV0YWRhdGEiIDogewogICAgICAgICJtb2RlbCIgOiAic2xpbSIKICAgICAgfQogICAgfQogIH0KfQ==";

    public AnyaCollection(){
        super(name, desc, theme, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
