//authored by StrangeOne101
package com.klin.holoItems.utility;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;

public class ReflectionUtils {

    public static final String nms = "net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().substring(23);
    public static final String craft = "org.bukkit.craftbukkit." + Bukkit.getServer().getClass().getPackage().getName().substring(23);

    private static boolean setup = false;

    private static Field repairCost;

    private static void setup() {
        try {
            Class<?> craftStackClass = Class.forName(craft + ".inventory.CraftMetaItem");
            repairCost = craftStackClass.getDeclaredField("repairCost");
            repairCost.setAccessible(true);
            setup = true;
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static int getRepairCost(ItemStack stack) {
        if (!setup) setup();

        try {
            return (int) repairCost.get(stack.getItemMeta());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return 0;
    }
}