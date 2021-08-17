package com.klin.holoItems.dungeons.inaDungeon;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.collections.misc.franCollection.items.SharpenedFangs;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.bukkit.Bukkit.getServer;

public class Minesweeper implements Listener {
    private static Minesweeper instance = null;
    //minesweeper -27 -9 -294 -262 59
    private static final Set<Material> sand = Stream.of(Material.SAND, Material.SANDSTONE_SLAB, Material.SANDSTONE_STAIRS, Material.SMOOTH_SANDSTONE_SLAB, Material.SMOOTH_SANDSTONE).collect(Collectors.toCollection(HashSet::new));
    public static final Map<Integer, Material> flags = new HashMap<>() {{
        put(0, Material.ORANGE_STAINED_GLASS);
        put(1, Material.RED_SAND);
        put(2, Material.RED_SANDSTONE_SLAB);
        put(3, Material.RED_SANDSTONE_STAIRS);
        put(4, Material.RED_SANDSTONE);
    }};
    private static Map<AbstractMap.SimpleEntry<Integer, Integer>, Boolean> squares = new HashMap<>();
    private static int y;
    private static Map<Block, BlockData> reset = new HashMap<>();

    public static void setUp(World world, int x1, int x2, int z1, int z2, int y1){
        for(int x=x1; x<=x2; x++){
            for(int z=z1; z<z2; z++){
                Block block = world.getBlockAt(x, y1, z);
                do{
                    if(sand.contains(block.getType())) {
                        squares.put(new AbstractMap.SimpleEntry<>(x, z), Math.random() < 0.25);
                        break;
                    }
                    block = block.getRelative(BlockFace.UP);
                }while(!block.isEmpty());
            }
        }
        y = y1;
        instance = new Minesweeper();
        getServer().getPluginManager().registerEvents(instance, HoloItems.getInstance());
    }

    @EventHandler
    public static void mine(BlockBreakEvent event){
        event.setCancelled(true);
        Block block = event.getBlock();
        AbstractMap.SimpleEntry<Integer, Integer> key = new AbstractMap.SimpleEntry<>(block.getX(), block.getZ());
        Boolean mine = squares.get(key);
        Material material = block.getType();
        if(mine==null || !sand.contains(material))
            return;
        reset.putIfAbsent(block, block.getBlockData());
        if(mine){
            block.setType(Material.ORANGE_GLAZED_TERRACOTTA);
            World world = block.getWorld();
            Player player = event.getPlayer();
            Location loc = player.getLocation();
            if(reset.size()<6) {
                world.playSound(loc, Sound.BLOCK_GLASS_BREAK, 4, 1);
                return;
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10, 8));
            Entity entity = world.spawnEntity(loc, EntityType.EVOKER_FANGS);
            entity.getPersistentDataContainer().set(Utility.pack, PersistentDataType.STRING, SharpenedFangs.id+":20");
            world.playSound(loc, Sound.AMBIENT_CAVE, 4, 1);
            return;
        }
        block.getWorld().dropItemNaturally(block.getLocation().add(0.5, 0.5, 0.5), new ItemStack(Material.SAND));
        int adjacent = 0;
        for(int i=-1; i<=1; i++){
            for(int j=-1; j<=1; j++){
                AbstractMap.SimpleEntry<Integer, Integer> clone = new AbstractMap.SimpleEntry<>(key.getKey()+i, key.getValue()+j);
                Boolean present = squares.get(clone);
                if(present!=null && present) {
                    if(adjacent>=4){
                        Random random = new Random();
                        AbstractMap.SimpleEntry<Integer, Integer> shift = key;
                        while(squares.get(shift)==null || !squares.get(shift))
                            shift = new AbstractMap.SimpleEntry<>(key.getKey()+random.nextInt(3) - 1, key.getValue()+random.nextInt(3) - 1);
                        squares.replace(shift, false);
                        Block center = block.getWorld().getBlockAt(key.getKey(), y, key.getValue());
                        for(BlockFace face : new BlockFace[]{BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH_EAST, BlockFace.SOUTH_EAST, BlockFace.NORTH_WEST, BlockFace.SOUTH_WEST}){
                            Block check = center.getRelative(face);
                            do{
                                Material type = check.getType();
                                if(flags.containsValue(type)) {
                                    //reverse map
                                    int integer = 0;
                                    for(Integer flag : flags.keySet()){
                                        if(flags.get(flag)==type)
                                            integer = flag;
                                    }
                                    check.setType(flags.get(integer==0?0:integer-1));
                                }
                                check = check.getRelative(BlockFace.UP);
                            }while(!check.isEmpty());
                        }
                    }
                    else
                        adjacent++;
                }
            }
        }
        block.setType(flags.get(adjacent));
    }

    public static void reset(){
        if(instance!=null) {
            BlockBreakEvent.getHandlerList().unregister(instance);
            instance = null;
        }
        for(Block block : reset.keySet())
            block.setBlockData(reset.get(block));
        reset.clear();
        squares.clear();
    }
}
