package com.klin.holoItems.collections.dungeons.inaDungeonCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.dungeons.inaDungeonCollection.InaDungeonCollection;
import com.klin.holoItems.dungeons.inaDungeon.InaDungeon;
import com.klin.holoItems.interfaces.Dropable;
import com.klin.holoItems.interfaces.Hitable;
import com.klin.holoItems.interfaces.Launchable;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.projectiles.ProjectileSource;

import java.util.Map;
import java.util.Set;

public class Torrent extends Item implements Launchable, Hitable, Dropable {
    public static final String name = "torrent";
    private final Set<Material> overwrite;

    private static final Material material = Material.BOW;
    private static final int quantity = 1;
    private static final String lore =
            "Splash";
    private static final int durability = 0;
    public static final boolean stackable = false;
    private static final boolean shiny = false;

    public static final int cost = -1;
    public static final char key = '1';
    public static final String id = ""+ InaDungeonCollection.key+key;

    public Torrent(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost, id, key);
        overwrite = Set.of(Material.BLUE_WOOL, Material.RED_WOOL, Material.RED_CONCRETE, Material.QUARTZ_BLOCK, Material.QUARTZ_STAIRS, Material.WHITE_CONCRETE);
    }

    public void registerRecipes() {}

    public void ability(ProjectileLaunchEvent event, ItemStack item){
        event.getEntity().getPersistentDataContainer().set(Utility.key, PersistentDataType.STRING, id);
    }

    public void ability(ProjectileHitEvent event) {
        Projectile projectile = event.getEntity();
        Block block = projectile.getLocation().getBlock();
        while(block.isPassable() && block.getY()>0)
            block = block.getRelative(BlockFace.DOWN);
        ProjectileSource shooter = projectile.getShooter();
        if(!(shooter instanceof Player))
            return;
        BlockFace face = ((Player) shooter).getFacing();
        Location loc = block.getLocation();
        if(face==BlockFace.EAST || face==BlockFace.WEST)
            loc.add(-6, 0, -3);
        else
            loc.add(-3, 0, -6);
        new Task(HoloItems.getInstance(), 10, 1){
            int increment = 0;
            int frame = 0;
            Map<Location, BlockData> build = null;
            public void run(){
                if(frame>2){
                    for(Location loc : build.keySet())
                        loc.getBlock().setBlockData(build.get(loc));
                    cancel();
                    return;
                }
                increment++;
                if(frame==0 && increment>=8 || frame==1 && increment>=3 || frame==2 && increment>=5) {
                    build = InaDungeon.build("shark" + frame, loc, build, overwrite, face);
                    frame++;
                    increment = 0;
                }
            }
        };
    }

    public void ability(PlayerDropItemEvent event){
        event.setCancelled(true);
    }
}

//    spiral = Map.of(
//            BlockFace.NORTH, BlockFace.SOUTH,
//            BlockFace.NORTH_EAST, BlockFace.SOUTH_WEST,
//            BlockFace.EAST, BlockFace.WEST,
//            BlockFace.SOUTH_EAST, BlockFace.NORTH_WEST,
//            BlockFace.SOUTH, BlockFace.NORTH,
//            BlockFace.SOUTH_WEST, BlockFace.NORTH_EAST,
//            BlockFace.WEST, BlockFace.EAST,
//            BlockFace.NORTH_WEST, BlockFace.SOUTH_EAST
//            );

//    public void ability(ProjectileHitEvent event) {
//        Projectile projectile = event.getEntity();
//        Location location = projectile.getLocation();
//        Block block = location.getBlock();
//        while(block.isPassable())
//            block = block.getRelative(BlockFace.DOWN);
//        Block origin = block;
//        BlockFace face = projectile.getFacing();
//
//        List<BlockFace> keys = List.of(BlockFace.NORTH_WEST, BlockFace.WEST, BlockFace.SOUTH_WEST, BlockFace.SOUTH, BlockFace.SOUTH_EAST, BlockFace.EAST, BlockFace.NORTH_EAST, BlockFace.NORTH);
//        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
//        BlockData water = Bukkit.createBlockData(Material.WATER);
//        BlockData bubble = Bukkit.createBlockData(Material.WHITE_STAINED_GLASS);
//        Map<Location, BlockData> reset = new HashMap<>();
//        location = block.getLocation();
//        for(int x=-1; x<=1; x++){
//            for(int y=0; y<20; y++){
//                for(int z=-1; z<=1; z++){
//                    Location loc = location.clone().add(x, y, z);
//                    reset.put(loc, loc.getBlock().getBlockData());
//                }
//            }
//        }
//        new Task(HoloItems.getInstance(), 1, 1){
//            double increment = 0;
//            Map<Location, BlockData> vapor = new LinkedHashMap<>();
//            Block torrent = origin;
//            int index = keys.indexOf(face);
//            public void run(){
//                if(increment>=20){
//                    for(Location loc : reset.keySet()){
//                        BlockData data = reset.get(loc);
//                        for(Player player : players)
//                            player.sendBlockChange(loc, data);
//                    }
//                    cancel();
//                    return;
//                }
//                Map<Location, BlockData> temp = new HashMap<>();
//                for (Location loc : vapor.keySet()) {
//                    BlockData data = vapor.get(loc);
//                    loc.add(0, 1, 0);
//                    temp.put(loc, data);
//                    for (Player player : players)
//                        player.sendBlockChange(loc, data);
//                }
//                vapor = temp;
//                BlockFace bubbles = keys.get(index);
//                Set<BlockFace> faces = Set.of(bubbles, spiral.get(bubbles));
//                for (BlockFace face : spiral.keySet()) {
//                    Location loc = origin.getRelative(face).getLocation();
//                    BlockData data = faces.contains(face) ? bubble : water;
//                    vapor.put(loc, data);
//                    for (Player player : players)
//                        player.sendBlockChange(loc, data);
//                }
//                if (index > 6)
//                    index = 0;
//                else
//                    index++;
//                torrent = torrent.getRelative(BlockFace.UP);
//                increment++;
//            }
//        };
//    }

//    private static boolean alternate(Map<Location, BlockData> vapor, Collection<? extends Player> players, BlockData water, boolean alternate){
//        for(Location loc : vapor.keySet()){
//            for(Player player : players) {
//                if(alternate)
//                    player.sendBlockChange(loc, water);
//                else
//                    player.sendBlockChange(loc, vapor.get(loc));
//                alternate = !alternate;
//            }
//        }
//        return alternate;
//    }