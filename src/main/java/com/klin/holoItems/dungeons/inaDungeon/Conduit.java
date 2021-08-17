package com.klin.holoItems.dungeons.inaDungeon;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;

import java.util.*;

import static org.bukkit.Bukkit.getServer;

public class Conduit implements Listener {
    private static Conduit instance = null;
    //rotate world -65 -63 -205 -203 72
    //19x19: y+=1 (-10 -203)
    //conduit world -28 58 -221
    private static final Map<Material, Set<Block[]>> buttons = new HashMap<>();
    private static final Map<Block[], BlockFace> joints = new HashMap<>();
    private static final Map<Block, BlockData> reset = new HashMap<>();

    //water off until after set-up
    public static void setUp(World world, int x, int y, int z){
        if(joints!=null)
            reset();
        for(int i=-0; i<3; i++){
            for(int j=0; j<2; j++){
                if(joint(world, x+8*i, x+8*i+2, z+16*j, z+16*j+2, y+i, joints)==null) {
                    joints.clear();
                    System.out.println("Invalid conduit arrangement");
                    return;
                }
            }
        }
        buttons.put(Material.RED_GLAZED_TERRACOTTA, new HashSet<>());
        buttons.put(Material.LIME_GLAZED_TERRACOTTA, new HashSet<>());
        buttons.put(Material.LIGHT_BLUE_GLAZED_TERRACOTTA, new HashSet<>());
        Map<Block[], Set<BlockData>> links = new HashMap<>();
        BlockData fireCoral = Bukkit.createBlockData(Material.FIRE_CORAL);
        ((Waterlogged) fireCoral).setWaterlogged(false);
        BlockData sugarCane = Bukkit.createBlockData(Material.SUGAR_CANE);
        BlockData tubeCoral = Bukkit.createBlockData(Material.TUBE_CORAL);
        ((Waterlogged) tubeCoral).setWaterlogged(false);
        for(Block[] torches : joints.keySet()) {
            Set<BlockData> types = new HashSet<>();
            while(types.isEmpty()){
                for(Material type : buttons.keySet()){
                    if(Math.random()<0.334){
                        buttons.get(type).add(torches);
                        BlockData plant;
                        if(type==Material.RED_GLAZED_TERRACOTTA)
                            plant = fireCoral;
                        else if(type==Material.LIME_GLAZED_TERRACOTTA)
                            plant = sugarCane;
                        else
                            plant = tubeCoral;
                        types.add(plant);
                    }
                }
            }
            links.put(torches, types);
        }
        int rotations = 5 + (int) (Math.random()*10+1);
        new Task(HoloItems.getInstance(), 10, 10){
            int increment = 0;
            public void run(){
                if(increment>rotations){
                    for(Block[] torches : joints.keySet()){
                        for(BlockData plant : links.get(torches)){
                            Block block = null;
                            while(block==null || !block.isEmpty())
                                block = world.getBlockAt(torches[2].getLocation().add((int) (Math.random() * 2 + 1) * Math.random() < 0.5 ? 1 : -1, 4, (int) (Math.random() * 2 + 1) * Math.random() < 0.5 ? 1 : -1));
                            reset.put(block, block.getBlockData());
                            block.setBlockData(plant);
                        }
                    }
                    cancel();
                    return;
                }
                int random = (int) (Math.random()*3);
                Material type;
                if(random==0)
                    type = Material.RED_GLAZED_TERRACOTTA;
                else if(random==1)
                    type = Material.LIME_GLAZED_TERRACOTTA;
                else
                    type = Material.LIGHT_BLUE_GLAZED_TERRACOTTA;
                for(Block[] torches : buttons.get(type))
                    rotate(torches, joints.get(torches));
                increment++;
            }
        };
        instance = new Conduit();
        getServer().getPluginManager().registerEvents(instance, HoloItems.getInstance());
    }

    @EventHandler
    public static void press(PlayerToggleSneakEvent event){
        Player player = event.getPlayer();
        if(player.getGameMode()==GameMode.SPECTATOR || ((Entity) player).isOnGround())
            return;
        Block button = player.getWorld().getBlockAt(player.getLocation());
        for(int i=0; i<10; i++){
            if(button.isEmpty())
                button = button.getRelative(BlockFace.DOWN);
            else
                break;
        }
        if(button.isEmpty())
            return;
        Material type = button.getType();
        if(!buttons.containsKey(type))
            return;
        new Task(HoloItems.getInstance(), 1, 1){
            int increment = 0;
            public void run(){
                if(increment>120 || ((Entity) player).isOnGround()){
                    rotate(type);
                    boolean solved = true;
                    for(Block block : reset.keySet()){
                        Material type = block.getType();
                        if(!Set.of(Material.FIRE_CORAL, Material.SUGAR_CANE, Material.TUBE_CORAL).contains(type)
                                && !type.equals(reset.get(block).getMaterial())){
                            solved = false;
                            break;
                        }
                    }
                    if(solved){
                        reset();
                        player.sendMessage("Congratulations: solved");
                    }
                    cancel();
                    return;
                }
                player.setVelocity(player.getVelocity().add(new Vector(0, -0.2, 0)));
                increment++;
            }
        };
    }

    @EventHandler
    public static void plant(BlockFadeEvent event){
        if(event.isCancelled())
            return;
        if(Set.of(Material.DEAD_FIRE_CORAL, Material.DEAD_TUBE_CORAL).contains(event.getNewState().getType()))
            event.setCancelled(true);
    }

    public static void reset(){
        if(instance!=null) {
            PlayerToggleSneakEvent.getHandlerList().unregister(instance);
            BlockFadeEvent.getHandlerList().unregister(instance);
            instance = null;
        }
        for(Block block : reset.keySet())
            block.setBlockData(reset.get(block));
        reset.clear();
        buttons.clear();
        joints.clear();
    }

    public static void rotate(Block[] torches, BlockFace flip){
        if(flip==null){
            for(Block block : torches) {
                if(block.getType()!=Material.STONE_BUTTON)
                    block.setType(block.isEmpty() ? Material.REDSTONE_TORCH:Material.AIR);
            }
        }
        else{
            Block block = torches[2];
            block.getRelative(flip).setType(Material.REDSTONE_TORCH);
            block.getRelative(Utility.opposites.get(flip)).setType(Material.AIR);
            joints.replace(torches, Utility.left.get(flip));
        }
    }

    private static void rotate(Material type){
        if(!buttons.containsKey(type))
            return;
        for(Block[] torches : buttons.get(type))
            rotate(torches, joints.get(torches));
    }

    public static Map<Block[], BlockFace> joint(World world, int x1, int x2, int z1, int z2, int y, Map<Block[], BlockFace> joints){
        Block[] torches = new Block[5];
        int increment = 0;
        int check = 0;
        int index = -1;
        BlockFace flip = null;
        for(int x=x1; x<=x2; x++){
            for(int z=z1; z<=z2; z++){
                Block block = world.getBlockAt(x, y, z);
                if(!block.isPassable())
                    continue;
                try {
                    torches[increment] = block;
                    reset.put(block, block.getBlockData());
                }catch (IndexOutOfBoundsException e){return null;}
                if(check>-1 && index>-1 && block.isEmpty()){
                    switch (check){
                        case 1:
                            if(index==0)
                                flip = BlockFace.NORTH;
                            break;
                        case 3:
                            if(index==0)
                                flip = BlockFace.WEST;
                            break;
                        case 4:
                            if(index==1)
                                flip = BlockFace.EAST;
                            else if(index==3)
                                flip = BlockFace.SOUTH;
                    }
                    check = -1;
                }
                else if(block.isEmpty())
                    index = increment;
                check++;
                increment++;
            }
        }
        if(torches[4]==null)
            return null;
        if(joints==null)
            joints = new HashMap<>(Collections.singletonMap(torches, flip));
        else
            joints.put(torches, flip);
        return joints;
    }
}

