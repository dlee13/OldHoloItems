package com.klin.holoItems.dungeons.inaDungeon;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.dungeons.inaDungeon.classes.*;
import com.klin.holoItems.dungeons.inaDungeon.classes.Member;
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
    //select buildteam 885 110 -189
    //select world 78 119 -231
    private final Map<Player, Member> classes;
    private final Location center;
    private final Set<Block> source;
    private boolean ina;

    public ClassSelect(World world, int x, int y, int z){
        classes = new HashMap<>();
        center = new Location(world, x, y, z);
        source = Utility.vacuum(world.getBlockAt(center).getRelative(BlockFace.UP), Material.WATER, 500, false);
        ina = false;
        getServer().getPluginManager().registerEvents(this, HoloItems.getInstance());
    }

    @EventHandler
    public void select(EntityDamageEvent event){
        if(event.getCause()!=EntityDamageEvent.DamageCause.VOID)
            return;
        Entity entity = event.getEntity();
        if(!(entity instanceof Player))
            return;
        Player player = (Player) entity;
        Location loc = player.getLocation();
        Vector select = loc.subtract(center).toVector().setY(0).normalize();
        loc.setY(0);
        if(ina && center.distance(loc)<3)
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
    public void select(EntityBlockFormEvent event){
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

    public void freeze(){
        for(Block block : source)
            block.setType(Material.FROSTED_ICE);
    }

    public void reset(){
        EntityDamageEvent.getHandlerList().unregister(this);
        EntityBlockFormEvent.getHandlerList().unregister(this);
        InaDungeon.maintenance.classes = classes;
    }
}
