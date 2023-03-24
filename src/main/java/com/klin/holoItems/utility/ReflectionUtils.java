//authored by StrangeOne101
package com.klin.holoItems.utility;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ReflectionUtils {

    public static final String nms = "net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().substring(23);
    public static final String craft = "org.bukkit.craftbukkit." + Bukkit.getServer().getClass().getPackage().getName().substring(23);

    private static boolean setup = false;

    private static Field repairCost;

    private static Method NMSifyItemStack;
    private static Class<?> craftWorldClass;
    private static Method getWorldHandleMethod;
    private static Class<?> craftBukkitLivingEntityClass;
    private static Method getLivingEntityHandleMethod;
    private static Class<?> minecraftEntityLivingClass;
    private static Method finishUsingItemMethod;

    private static void setup() {
        System.out.println("Invoking setup()");
        try {
            /*
            String[] classes = "CraftArt,CraftChunk,CraftChunkSnapshot,CraftCrashReport,CraftEffect,CraftEquipmentSlot,CraftIpBanEntry,CraftIpBanList,CraftOfflinePlayer,CraftParticle,CraftProfileBanEntry,CraftProfileBanList,CraftServer,CraftSound,CraftStatistic,CraftWorld,CraftWorldBorder,LoggerOutputStream,Main,Overridden,SpigotTimings,TrigMath".split(",");
            for(String name : classes){
                final Class<?> testClass = Class.forName(craft + "." + name);
                for(Method m:testClass.getMethods()){
                    if(m.toString().contains("net.minecraft.world.Level")){
                        System.out.println(name + " contains " + m);
                    }
                }
            }

             */
            System.out.println("For-loop done");
            Class<?> craftStackClass = Class.forName(craft + ".inventory.CraftMetaItem");
            repairCost = craftStackClass.getDeclaredField("repairCost");
            repairCost.setAccessible(true);

            final Class<?> craftItemStackClass = Class.forName(craft + ".inventory.CraftItemStack", false, Bukkit.getServer().getClass().getClassLoader());
            NMSifyItemStack = craftItemStackClass.getMethod("asNMSCopy", ItemStack.class);

            craftWorldClass = Class.forName(craft + ".CraftWorld", false, Bukkit.getServer().getClass().getClassLoader());
            getWorldHandleMethod = craftWorldClass.getMethod("getHandle");
            Class<?> test = getWorldHandleMethod.getReturnType();
            while(test != Object.class){
                System.out.println("Class: " + test);
                System.out.println("Superclass: " + test.getSuperclass());
                System.out.println("Interfaces: " + Arrays.toString(test.getInterfaces()));
                test = test.getSuperclass();
            }
            // System.out.println(Arrays.toString(getWorldHandleMethod.getReturnType().getMethods()));

            craftBukkitLivingEntityClass = Class.forName(craft + ".entity.CraftEntity");
            getLivingEntityHandleMethod = craftBukkitLivingEntityClass.getMethod("getHandle");

            minecraftEntityLivingClass = Class.forName("net.minecraft.world.entity.EntityLiving");

            final Class<?> itemClass = Class.forName("net.minecraft.world.item.Item", false, Bukkit.getServer().getClass().getClassLoader());
            finishUsingItemMethod = itemClass.getMethod("finishUsingItem", NMSifyItemStack.getReturnType(), getWorldHandleMethod.getReturnType(), minecraftEntityLivingClass);
            System.out.println("finishUsingItemMethod: " + finishUsingItemMethod);

            setup = true;
        } catch (ClassNotFoundException | NoSuchFieldException | NoSuchMethodException e) {
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

    /**
     * Calls the "finishUsingItem" method. This invokes Reflection.
     * TODO: Find out if finishUsingItem decrements the itemStack by 1 or not.
     * @param item Equivalent to the ItemStack in finishUsingItem
     * @param world Equivalent to the Level in finishUsingItem
     * @param entity Equivalent to EntityLiving in finishUsingItem
     * @return Equivalent to the result of FinishUsingItem
     */
    public static ItemStack finishUsingItem(ItemStack item, World world, LivingEntity entity){
        if(!setup){
            setup();
        }

        return null;
    }
}