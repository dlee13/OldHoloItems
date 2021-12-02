package com.klin.holoItems.collections.en1.irysCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class IrysCollection extends Collection {
    public static final String name = "Irys";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "IRySuperGlue";
//    public static final String uuid = "1cbbf835-c971-40a4-9b4e-2bd410a0a8e0";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYyNjI2NzA0OTc4MywKICAicHJvZmlsZUlkIiA6ICIyMWUzNjdkNzI1Y2Y0ZTNiYjI2OTJjNGEzMDBhNGRlYiIsCiAgInByb2ZpbGVOYW1lIiA6ICJHZXlzZXJNQyIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9lZDZiMzE1NzE0YTZiZTNlOWYzNWE1Y2FlYTQ0NDUxMTRjYmRhNjExMGM1ZTAzOWI0YjM4MmQ0Mjk1ODcwZWQyIgogICAgfQogIH0KfQ==";

    public IrysCollection(){
        super(name, desc, theme, base64);
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
