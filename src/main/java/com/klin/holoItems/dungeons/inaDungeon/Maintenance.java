package com.klin.holoItems.dungeons.inaDungeon;

import com.klin.holoItems.Collections;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.collections.gen1.haachamaCollection.items.RiftWalker;
import com.klin.holoItems.utility.Task;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;

import static org.bukkit.Bukkit.getServer;

public class Maintenance implements Listener {
    private static Maintenance instance;
    private static final Material[][] seal = new Material[][]{
            {Material.PURPLE_STAINED_GLASS, Material.PURPLE_STAINED_GLASS, Material.WHITE_STAINED_GLASS, Material.PURPLE_STAINED_GLASS, Material.PURPLE_STAINED_GLASS},
            {Material.PURPLE_STAINED_GLASS, Material.PURPLE_STAINED_GLASS, Material.WHITE_STAINED_GLASS, Material.PURPLE_STAINED_GLASS, Material.PURPLE_STAINED_GLASS},
            {Material.WHITE_STAINED_GLASS, Material.WHITE_STAINED_GLASS, Material.PURPLE_STAINED_GLASS, Material.WHITE_STAINED_GLASS, Material.WHITE_STAINED_GLASS},
            {Material.PURPLE_STAINED_GLASS, Material.PURPLE_STAINED_GLASS, Material.WHITE_STAINED_GLASS, Material.PURPLE_STAINED_GLASS, Material.PURPLE_STAINED_GLASS},
            {Material.PURPLE_STAINED_GLASS, Material.PURPLE_STAINED_GLASS, Material.WHITE_STAINED_GLASS, Material.PURPLE_STAINED_GLASS, Material.PURPLE_STAINED_GLASS}
    };
    //maintain -41 -292 -27 -267
    private static int[] cage = null;
    private static final Set<Block> decay = new HashSet<>();
    public static final Set<Player> knockBack = new HashSet<>();

    public static void setUp(int x1, int z1, int x2, int z2){
        Collections.disabled.add(RiftWalker.id);
        cage = new int[]{x1, z1, x2, z2};
        instance = new Maintenance();
        getServer().getPluginManager().registerEvents(instance, HoloItems.getInstance());
    }

    @EventHandler
    public static void cage(PlayerMoveEvent event){
        if(event.isCancelled())
            return;
        Player player = event.getPlayer();
        if(knockBack.contains(player))
            return;
        Location location = player.getLocation();
        int x = location.getBlockX();
        int z = location.getBlockZ();
        Vector velocity = player.getVelocity();
        double difference = 2 + Math.pow(velocity.getX(), 2);
        boolean shatter;
        boolean axis = true;
        if(x-difference<=cage[0]) {
            shatter = x<cage[0];
            x = cage[0];
            z -= 2;
            velocity.setX(1);
            velocity.setZ(0);
        }
        else if(x+difference>=cage[2]) {
            shatter = x>cage[2];
            x = cage[2];
            z -= 2;
            velocity.setX(-1);
            velocity.setZ(0);
        }
        else {
            axis = false;
            difference = 2 + Math.pow(player.getVelocity().getZ(), 2);
            if(z-difference<=cage[1]) {
                shatter = z<cage[1];
                z = cage[1];
                x -= 2;
                velocity.setZ(1);
                velocity.setX(0);
            }
            else if (z+difference>=cage[3]) {
                shatter = z>cage[3];
                z = cage[3];
                x -= 2;
                velocity.setZ(-1);
                velocity.setX(0);
            }
            else return;
        }
        knockBack.add(player);
        if(shatter)
            velocity.multiply(2);
        else{
            World world = player.getWorld();
            int y = location.getBlockY();
            Location loc = new Location(world, x, y - 1, z);
            for (int i = 0; i < seal.length; i++) {
                if (y + i > 256)
                    break;
                for (int j = 0; j < seal[i].length; j++) {
                    Block block = world.getBlockAt(loc.clone().add(!axis ? j : 0, i, axis ? j : 0));
                    if (block.isPassable() || decay.remove(block)) {
                        block.setType(seal[i][j]);
                        decay(block, 4 + 2 * (Math.abs(2 - i) + Math.abs(2 - j)));
                    }
                }
            }
        }
        player.setGliding(false);
        velocity.setY(0.5);
        player.setVelocity(velocity);
        new BukkitRunnable(){
            public void run(){
                knockBack.remove(player);
            }
        }.runTaskLater(HoloItems.getInstance(), 8);
    }

    @EventHandler
    public static void track(BlockPlaceEvent event){
        if(!event.isCancelled())
            decay(event.getBlock(), 80);
    }

    private static void decay(Block block, int duration){
        decay.add(block);
        new Task(HoloItems.getInstance(), 2, 1){
            int increment = 0;
            public void run(){
                if(increment>=duration || !decay.contains(block)) {
                    block.setType(Material.AIR);
                    cancel();
                    return;
                }
                increment++;
            }
        };
    }

    @EventHandler
    public static void prevent(BlockBreakEvent event){
        if(!event.isCancelled()) {
            if(decay.remove(event.getBlock()))
                event.setDropItems(false);
            else
                event.setCancelled(true);
        }
    }

    public static void reset(){
        Collections.disabled.remove(RiftWalker.id);
        if(instance!=null) {
            PlayerMoveEvent.getHandlerList().unregister(instance);
            BlockPlaceEvent.getHandlerList().unregister(instance);
            BlockBreakEvent.getHandlerList().unregister(instance);
            instance = null;
        }
        cage = null;
        for(Block block : decay)
            block.breakNaturally();
        decay.clear();
        knockBack.clear();
    }
}
