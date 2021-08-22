package com.klin.holoItems.dungeons.inaDungeon;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.dungeons.inaDungeon.classes.*;
import com.klin.holoItems.dungeons.inaDungeon.classes.Class;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.bukkit.Bukkit.getServer;

public class ClassSelect implements Listener {
    private static ClassSelect instance = null;
    //select buildteam 885 110 -189
    //select world 78 119 -231
    private static Map<Player, Class> classes;
    private static Location center;
    private static Set<Block> source;
    private static boolean ina;

    public static void setUp(World world, int x, int y, int z){
        classes = new HashMap<>();
        center = new Location(world, x, y, z);
        source = Utility.vacuum(world.getBlockAt(center).getRelative(BlockFace.UP), Material.WATER, 500, false);
        ina = false;
        instance = new ClassSelect();
        getServer().getPluginManager().registerEvents(instance, HoloItems.getInstance());
    }

    @EventHandler
    public static void select(EntityDamageEvent event){
        if(event.getCause()!=EntityDamageEvent.DamageCause.VOID)
            return;
        Entity entity = event.getEntity();
        if(!(entity instanceof Player))
            return;
        Player player = (Player) entity;
        Vector select = player.getLocation().subtract(center).toVector().setY(0).normalize();
        if(ina && center.toVector().distance(select)<3)
            classes.put(player, new Ina(player));
        else {
            double side = select.getX();
            double choice = select.getZ();
            if (side > 0.8) {
                if (choice < -0.2)
                    classes.put(player, new Gura(player));
                else if (choice < 0.2)
                    classes.put(player, new Watson(player));
                else
                    classes.put(player, new Kiara(player));
            } else if (side < -0.8) {
                if (choice < -0.2)
                    classes.put(player, new Calli(player));
                else if (choice < 0.2)
                    classes.put(player, new Enma(player));
                else
                    classes.put(player, new Irys(player));
            }
        }
        event.setCancelled(true);
        player.teleport(center);
    }

    @EventHandler
    public static void select(EntityBlockFormEvent event){
        Block ice = event.getBlock();
        new BukkitRunnable(){
            public void run(){
                if(ice.getType()==Material.FROSTED_ICE && source.contains(ice)){
                    for(Block block : source){
                        if(block.getType()!=Material.FROSTED_ICE)
                            return;
                    }
                    //spawn capsule at center
                    System.out.println("frosted");
                    ina = true;
                }
            }
        }.runTask(HoloItems.getInstance());
    }

    public static void freeze(){
        if(instance==null){
            System.out.println("Class select: uninitialized");
            return;
        }
        for(Block block : source)
            block.setType(Material.FROSTED_ICE);
    }

    public static void reset(){
        if(instance==null)
            return;
        else{
            EntityDamageEvent.getHandlerList().unregister(instance);
            EntityBlockFormEvent.getHandlerList().unregister(instance);
        }
        Maintenance.classes = classes;
    }
}
