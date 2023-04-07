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
            Class<?> craftStackClass = Class.forName(craft + ".inventory.CraftMetaItem");
            repairCost = craftStackClass.getDeclaredField("repairCost");
            repairCost.setAccessible(true);

            final Class<?> craftItemStackClass = Class.forName(craft + ".inventory.CraftItemStack", false, Bukkit.getServer().getClass().getClassLoader());
            // asNMSCopy: Converts from Bukkit ItemStack to net.minecraft.world.item.ItemStack
            NMSifyItemStack = craftItemStackClass.getMethod("asNMSCopy", ItemStack.class);

            // According to this bukkit.org forum link:
            // https://bukkit.org/threads/cast-org-bukkit-world-to-net-minecraft-server-v1_8_r3-world.431984/
            // Apparently you can cast org.bukkit.World to CraftWorld then use getHandle to get the NMS.World?
            craftWorldClass = Class.forName(craft + ".CraftWorld", false, Bukkit.getServer().getClass().getClassLoader());
            // The following is reassurance, but not a guarantee, that World might be able to cast to CraftWorld.
            // System.out.println("Is Bukkit world a superclass of craftWorld? " + World.class.isAssignableFrom(craftWorldClass));
            // This gives a NMS.level.WorldServer class
            getWorldHandleMethod = craftWorldClass.getMethod("getHandle");

            // worldClass is a superclass of NMS.level.WorldServer
            // so you can cast from NMS.level.worldServer to worldClass
            Class<?> worldClass = Class.forName("net.minecraft.world.level.World", false, Bukkit.getServer().getClass().getClassLoader());
            // proof that worldClass is a superclass if NMS.level.worldServer
            // System.out.println("Can you cast from getWorldHandleMethod returnType to worldClass?" + worldClass.isAssignableFrom(getWorldHandleMethod.getReturnType()));

            // I can't find my notes on how to convert from Bukkit entity to CraftEntity?
            // And google isn't being particularly helpful, except giving me something which I have no idea if it'll work.
            // Apparantly I should just cast from Bukkit LivingEntity to CraftEntity and ... well, it'll work
            // So craftBukkitLivingEntityClass does the conversion
            craftBukkitLivingEntityClass = Class.forName(craft + ".entity.CraftEntity");
            // getHandle can get a net.minecraft.world.entity.Entity
            getLivingEntityHandleMethod = craftBukkitLivingEntityClass.getMethod("getHandle");

            // And then I just have to pray that we can cast THAT to EntityLiving
            minecraftEntityLivingClass = Class.forName("net.minecraft.world.entity.EntityLiving");

            final Class<?> itemClass = Class.forName("net.minecraft.world.item.Item", false, Bukkit.getServer().getClass().getClassLoader());
            System.out.println(NMSifyItemStack.getReturnType());
            //System.out.println(getWorldHandleMethod.getReturnType());
            System.out.println(worldClass);
            System.out.println(minecraftEntityLivingClass);
            finishUsingItemMethod = itemClass.getMethod("a", NMSifyItemStack.getReturnType(), worldClass, minecraftEntityLivingClass);
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