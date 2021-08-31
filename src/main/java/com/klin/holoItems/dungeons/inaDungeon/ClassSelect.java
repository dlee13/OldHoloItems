package com.klin.holoItems.dungeons.inaDungeon;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.dungeons.Resetable;
import com.klin.holoItems.dungeons.inaDungeon.classes.*;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.bukkit.Bukkit.getServer;

public class ClassSelect implements Listener, Resetable {
    //select buildteam 885 110 -189
    //select world 78 120 -231
    private final Map<Player, Member> classes;
    private final Location center;
    private final List<Block> source;
    private boolean ina;

    public ClassSelect(World world, int x, int y, int z){
        classes = new HashMap<>();
        center = new Location(world, x, y, z);
        source = Utility.vacuum(world.getBlockAt(center), Material.WATER, 500, false);
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
        Vector select = loc.clone().subtract(center).toVector().setY(0).normalize();
        loc.setY(center.getY());
        if(ina && center.distance(loc)<4.25)
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
        player.setFallDistance(0);
        player.teleport(center);
    }

    @EventHandler
    public void select(EntityBlockFormEvent event){
        Block ice = event.getBlock();
        if(source.contains(ice)) {
            new BukkitRunnable() {
                public void run() {
                    if(ice.getType()!=Material.FROSTED_ICE)
                        return;
                    boolean melt = false;
                    for (Block block : source) {
                        if (block.getType()!=Material.FROSTED_ICE) {
                            melt = true;
                            break;
                        }
                    }
                    if(melt){
                        new BukkitRunnable(){
                            public void run(){
                                ice.setType(Material.WATER);
                            }
                        }.runTaskLater(HoloItems.getInstance(), 120);
                        return;
                    }
                    new Task(HoloItems.getInstance(), 1, 1){
                        public void run(){
                            for(int i=0; i<4; i++){
                                Block block = source.remove(0);
                                block.setType(Material.ICE);
                                if(source.isEmpty()){
                                    cancel();
                                    return;
                                }
                            }
                        }
                    };
                    ina = true;
                }
            }.runTask(HoloItems.getInstance());
        }
    }

    @EventHandler
    public void melt(BlockFadeEvent event){
        if(!event.isCancelled() && event.getNewState().getType()==Material.WATER)
            event.setCancelled(true);
    }

    public void freeze(){
        for(Block block : source)
            block.setType(Material.FROSTED_ICE);
    }

    public void reset(){
        EntityDamageEvent.getHandlerList().unregister(this);
        EntityBlockFormEvent.getHandlerList().unregister(this);
        BlockFadeEvent.getHandlerList().unregister(this);
        ((Maintenance) InaDungeon.presets.computeIfAbsent("maintenance", k -> new Maintenance())).classes = classes;
    }
}
