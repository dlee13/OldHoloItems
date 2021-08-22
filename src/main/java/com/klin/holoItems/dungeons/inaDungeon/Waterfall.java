package com.klin.holoItems.dungeons.inaDungeon;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

import static org.bukkit.Bukkit.getServer;

public class Waterfall implements Listener {
    private static Waterfall instance = null;
    //waterfall buildteam 713 54 345
    //y:61 -> 721 70 361
    //waterfall world -6 60 -285
    private static Location center = null;
    private static final Set<Material> prohibited = Set.of(Material.PISTON, Material.STICKY_PISTON, Material.IRON_TRAPDOOR, Material.OAK_TRAPDOOR, Material.SPRUCE_TRAPDOOR, Material.BIRCH_TRAPDOOR, Material.JUNGLE_TRAPDOOR, Material.ACACIA_TRAPDOOR, Material.DARK_OAK_TRAPDOOR, Material.CRIMSON_TRAPDOOR, Material.WARPED_TRAPDOOR);
    private static Set<Block> pond = null;
    private static final Set<Block> rapids = new HashSet<>();

    public static void setUp(World world, int x, int y, int z){
        center = new Location(world, x, y, z);
        pond = Utility.vacuum(center.getBlock(), Material.WATER, 3000, true);
        for(int i=0; i<=8; i++){
            for(int j=7; j<=16; j++){
                for(int k=0; k<=16; k++){
                    Block block = world.getBlockAt(x+i, y+j, z+k);
                    if(block.getType()==Material.COBWEB) {
                        block.setType(Material.AIR);
                        rapids.add(block);
                    }
                }
            }
        }
        instance = new Waterfall();
        getServer().getPluginManager().registerEvents(instance, HoloItems.getInstance());
    }

    @EventHandler
    public static void fill(WeatherChangeEvent event){
        new BukkitRunnable(){
            public void run(){
                if(event.getWorld().hasStorm())
                    reset();
            }
        }.runTask(HoloItems.getInstance());
    }

    @EventHandler
    public static void decay(BlockPlaceEvent event){
        if(!event.isCancelled() && instance!=null) {
            Block block = event.getBlock();
            if(prohibited.contains(block.getType()) && center.distance(block.getLocation())<24)
                event.setCancelled(true);
        }
    }

    public static void reset(){
        if(instance!=null) {
            WeatherChangeEvent.getHandlerList().unregister(instance);
            BlockPlaceEvent.getHandlerList().unregister(instance);
            instance = null;
        }
        for(Block block : pond)
            block.setType(Material.WATER);
        pond = null;
        Block block = center.getBlock();
        center = null;
        new Task(HoloItems.getInstance(), 20, 20){
            int increment = 0;
            public void run(){
                if(increment>=20 || block.getType()==Material.WATER){
                    new Task(HoloItems.getInstance(), 0, 2){
                        int y = block.getY()+7;
                        final int increment = y+16;
                        public void run(){
                            if(y>increment || rapids.isEmpty()){
                                for(Block block : rapids)
                                    block.setType(Material.COBWEB);
                                rapids.clear();
                                cancel();
                                return;
                            }
                            Set<Block> remove = new HashSet<>();
                            for(Block block : rapids) {
                                if(y==block.getY()) {
                                    block.setType(Material.COBWEB);
                                    remove.add(block);
                                }
                            }
                            rapids.removeAll(remove);
                            y++;
                        }
                    };
                    cancel();
                    return;
                }
                increment++;
            }
        };
    }
}
