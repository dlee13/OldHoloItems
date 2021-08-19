package com.klin.holoItems.dungeons.inaDungeon;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.collections.misc.franCollection.items.DyeConcentrate;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class InaDungeon implements CommandExecutor{
    public static final String[][][][] builds = new String[][][][]{
            {{{"minecraft:air", "minecraft:oak_trapdoor[facing=west,half=bottom,open=true,powered=false,waterlogged=false]", "minecraft:air", "minecraft:air", "minecraft:oak_trapdoor[facing=west,half=bottom,open=true,powered=false,waterlogged=false]", "minecraft:air", "minecraft:air"}, {"minecraft:air", "minecraft:spruce_trapdoor[facing=west,half=bottom,open=true,powered=false,waterlogged=false]", "minecraft:spruce_trapdoor[facing=west,half=bottom,open=true,powered=false,waterlogged=false]", "minecraft:air", "minecraft:air", "minecraft:air", "minecraft:air"}, {"minecraft:air", "minecraft:air", "minecraft:air", "minecraft:air", "minecraft:air", "minecraft:air", "minecraft:air"}}, {{"minecraft:dark_oak_slab[type=top,waterlogged=false]", "minecraft:grindstone[face=ceiling,facing=south]", "minecraft:spruce_slab[type=top,waterlogged=false]", "minecraft:spruce_slab[type=top,waterlogged=false]", "minecraft:grindstone[face=ceiling,facing=north]", "minecraft:dark_oak_slab[type=top,waterlogged=false]", "minecraft:spruce_fence_gate[facing=east,in_wall=false,open=false,powered=false]"}, {"minecraft:spruce_trapdoor[facing=north,half=bottom,open=true,powered=false,waterlogged=false]", "minecraft:barrel[facing=up,open=false]", "minecraft:air", "minecraft:air", "minecraft:gray_carpet", "minecraft:spruce_fence[east=false,north=false,south=false,waterlogged=false,west=false]", "minecraft:air"}, {"minecraft:air", "minecraft:spruce_fence[east=false,north=false,south=false,waterlogged=false,west=false]", "minecraft:air", "minecraft:air", "minecraft:air", "minecraft:air", "minecraft:air"}}, {{"minecraft:dark_oak_slab[type=top,waterlogged=false]", "minecraft:spruce_slab[type=top,waterlogged=false]", "minecraft:spruce_slab[type=top,waterlogged=false]", "minecraft:spruce_slab[type=top,waterlogged=false]", "minecraft:spruce_slab[type=top,waterlogged=false]", "minecraft:dark_oak_slab[type=top,waterlogged=false]", "minecraft:air"}, {"minecraft:spruce_trapdoor[facing=north,half=bottom,open=true,powered=false,waterlogged=false]", "minecraft:spruce_trapdoor[facing=north,half=bottom,open=false,powered=false,waterlogged=false]", "minecraft:barrel[facing=west,open=false]", "minecraft:air", "minecraft:gray_carpet", "minecraft:air", "minecraft:air"}, {"minecraft:air", "minecraft:air", "minecraft:barrel[facing=south,open=false]", "minecraft:air", "minecraft:air", "minecraft:air", "minecraft:air"}}, {{"minecraft:dark_oak_slab[type=top,waterlogged=false]", "minecraft:grindstone[face=ceiling,facing=south]", "minecraft:spruce_slab[type=top,waterlogged=false]", "minecraft:spruce_slab[type=top,waterlogged=false]", "minecraft:grindstone[face=ceiling,facing=north]", "minecraft:dark_oak_slab[type=top,waterlogged=false]", "minecraft:spruce_fence_gate[facing=west,in_wall=false,open=false,powered=false]"}, {"minecraft:spruce_trapdoor[facing=north,half=bottom,open=true,powered=false,waterlogged=false]", "minecraft:barrel[facing=up,open=false]", "minecraft:chest[facing=east,type=left,waterlogged=false]", "minecraft:chest[facing=east,type=right,waterlogged=false]", "minecraft:gray_carpet", "minecraft:spruce_fence[east=false,north=false,south=false,waterlogged=false,west=false]", "minecraft:air"}, {"minecraft:air", "minecraft:spruce_fence[east=false,north=false,south=false,waterlogged=false,west=false]", "minecraft:air", "minecraft:air", "minecraft:air", "minecraft:air", "minecraft:air"}}, {{"minecraft:air", "minecraft:oak_trapdoor[facing=east,half=bottom,open=true,powered=false,waterlogged=false]", "minecraft:air", "minecraft:air", "minecraft:oak_trapdoor[facing=east,half=bottom,open=true,powered=false,waterlogged=false]", "minecraft:air", "minecraft:air"}, {"minecraft:air", "minecraft:spruce_trapdoor[facing=east,half=bottom,open=true,powered=false,waterlogged=false]", "minecraft:spruce_trapdoor[facing=east,half=bottom,open=true,powered=false,waterlogged=false]", "minecraft:air", "minecraft:air", "minecraft:air", "minecraft:air"}, {"minecraft:air", "minecraft:air", "minecraft:air", "minecraft:air", "minecraft:air", "minecraft:air", "minecraft:air"}}}
    };

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player && args.length>0){
            Location loc = ((Player) sender).getLocation();
            for(int i=0; i<args.length; i++){
                if(args[i].startsWith("~")){
                    boolean update = true;
                    for(int j=1; j<3; j++){
                        if (i + j >= args.length || !args[i + j].startsWith("~")) {
                            update = false;
                            break;
                        }
                    }
                    if(update){
                        int x;
                        try{
                            x=Integer.parseInt(args[i].substring(1));
                        }catch (NumberFormatException e){x=0;}
                        args[i] = loc.getBlockX() + x +"";
                        int y;
                        try{
                            y=Integer.parseInt(args[i+1].substring(1));
                        }catch (NumberFormatException e){y=0;}
                        args[i+1] = loc.getBlockY() + y +"";
                        int z;
                        try{
                            z=Integer.parseInt(args[i+2].substring(1));
                        }catch (NumberFormatException e){z=0;}
                        args[i+2] = loc.getBlockX() + z +"";
                    }
                }
            }
        }

        switch(cmd.getName().toLowerCase()) {
            case "build":
                if (args.length < 5)
                    return false;
                int index;
                try {
                    index = Integer.parseInt(args[0]);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid index");
                    return true;
                }
                if (index < 0 || index >= builds.length) {
                    System.out.println("Out of bounds index");
                    return true;
                }
                World world = Bukkit.getWorld(args[1]);
                if(world==null) {
                    System.out.println("Invalid world name");
                    return true;
                }
                Location loc;
                try {
                    loc = new Location(world, Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
                }catch(NumberFormatException e){
                    System.out.println("Invalid coordinates");
                    return true;
                }
                build(index, world, loc);
                return true;

            case "tostring":
                if (args.length < 7)
                    return false;
                world = Bukkit.getWorld(args[0]);
                if(world==null){
                    System.out.println("Invalid world name");
                    return true;
                }
                int[] arguments = new int[6];
                try {
                    for (int i = 1; i <= 6; i++)
                        arguments[i-1] = Integer.parseInt(args[i]);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid coordinate(s)");
                    return true;
                }
                String out = "{";
                int x1 = Math.min(arguments[0], arguments[3]);
                int x2 = Math.max(arguments[0], arguments[3]);
                int y1 = Math.min(arguments[1], arguments[4]);
                int y2 = Math.max(arguments[1], arguments[4]);
                int z1 = Math.min(arguments[2], arguments[5]);
                int z2 = Math.max(arguments[2], arguments[5]);
                for (int i = x1; i <= x2; i++) {
                    out += "{";
                    for (int j = y1; j <= y2; j++) {
                        out += "{";
                        for (int k = z1; k <= z2; k++) {
                            out += "\"" + world.getBlockAt(i, j, k).getBlockData().getAsString() + "\"";
                            if (k < z2)
                                out += ", ";
                        }
                        out += "}";
                        if (j < y2)
                            out += ", ";
                    }
                    out += "}";
                    if (i < x2)
                        out += ", ";
                }
                System.out.println(out + "}");
                return true;
            //attacks
            case "groundpound":
                if(!(sender instanceof Player)){
                    System.out.println("Player only command");
                    return true;
                }
                Entity entity;
                Player player = (Player) sender;
                if(args.length>0) {
                    try {
                        entity = player.getWorld().spawnEntity(player.getLocation(), EntityType.valueOf(args[0]));
                    }catch(IllegalArgumentException e){return true;}
                }
                else entity = player;
                Attacks.groundPound(entity);
                return  true;

            case "paintbomb":
                if (args.length < 4)
                    return false;
                world = Bukkit.getWorld(args[0]);
                if(world==null){
                    System.out.println("Invalid world name");
                    return true;
                }
                try {
                    loc = new Location(world, Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
                }catch(NumberFormatException e){return true;}
                Block bloc = world.getBlockAt(loc);
                Block drop = bloc;
                while(drop.getRelative(BlockFace.UP).isEmpty() && drop.getY()<256)
                    drop = drop.getRelative(BlockFace.UP);
                for(int i=0; i<4; i++)
                    drop = drop.getRelative(BlockFace.DOWN);
                DyeColor color = Utility.getRandom(Utility.colors.keySet()).get();
                Material type = Utility.getRandom(Utility.colors.get(color)).get();
                FallingBlock fallingBlock = world.spawnFallingBlock(drop.getLocation().add(0.5, 0, 0.5), Bukkit.createBlockData(type));
                fallingBlock.setVelocity(new Vector(0, 1, 0));
                new Task(HoloItems.getInstance(), 20, 1){
                    int increment = 0;
                    Block block = bloc;
                    public void run(){
                        if(increment>=40 || !fallingBlock.isValid()){
                            if(fallingBlock.isValid())
                                fallingBlock.remove();
                            while(block.getType()!=type && !block.isEmpty())
                                block = block.getRelative(BlockFace.UP);
                            block.setType(Material.AIR);
                            Utility.spawn(block.getLocation().add(0.5, 0, 0.5), world, EntityType.SHULKER, DyeConcentrate.id+":"+color);
                            cancel();
                        }
                        fallingBlock.setVelocity(fallingBlock.getVelocity().add(new Vector(0, -0.5, 0)));
                        increment++;
                    }
                };
                return true;

            case "spreadfire":
                if (args.length<4)
                    return false;
                world = Bukkit.getWorld(args[0]);
                if(world==null){
                    System.out.println("Invalid world name");
                    return true;
                }
                try {
                    Attacks.spreadFire(BlockFace.UP, world.getBlockAt(new Location(world, Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]))), Utility.cardinal.keySet(), new HashSet<>(), new HashSet<>(), args[3].equals("true"));
                }catch(NumberFormatException e){return false;}
                return true;
            //conduit
            case "conduit":
                if(args.length<4)
                    return false;
                world = Bukkit.getWorld(args[0]);
                if(world==null){
                    System.out.println("Invalid world name");
                    return true;
                }
                try {
                    Conduit.setUp(world, Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
                }catch (NumberFormatException e){return false;}
                System.out.println("Conduit [ON]");
                return true;

            case "resetconduit":
                Conduit.water.setType(Material.AIR);
                Conduit.reset();
                System.out.println("Conduit [OFF]");
                return true;

            case "rotate":
                if(args.length<6)
                    return false;
                world = Bukkit.getWorld(args[0]);
                if(world==null){
                    System.out.println("Invalid world name");
                    return true;
                }
                try {
                    int x = Integer.parseInt(args[2]);
                    x1 = Integer.parseInt(args[1]);
                    if(x1>x) {
                        x2 = x1;
                        x1 = x;
                    }
                    else
                        x2 = x;
                    int z = Integer.parseInt(args[4]);
                    z1 = Integer.parseInt(args[3]);
                    if(z1>z) {
                        z2 = z1;
                        z1 = z;
                    }
                    else
                        z2 = z;
                    Map<Block[], BlockFace> joints = Conduit.joint(world, x1, x2, z1, z2, Integer.parseInt(args[5]), null);
                    if(joints==null){
                        System.out.println("Invalid torch arrangement");
                        return true;
                    }
                    for(Block[] torches : joints.keySet())
                        Conduit.rotate(torches, joints.get(torches));
                }catch(NumberFormatException e){return false;}
                return true;
            //gettingWood
            case "plant":
                if(args.length<5)
                    return false;
                try {
                    index = Integer.parseInt(args[0]);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid index");
                    return true;
                }
                if (index < 0 || index >= builds.length) {
                    System.out.println("Out of bounds index");
                    return true;
                }
                world = Bukkit.getWorld(args[1]);
                if(world==null) {
                    System.out.println("Invalid world name");
                    return true;
                }
                try {
                    loc = new Location(world, Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
                }catch(NumberFormatException e){
                    System.out.println("Invalid coordinates");
                    return true;
                }
                GettingWood.plant(index, world, loc);
                return true;

            case "resetGettingWood":
                GettingWood.reset();
                System.out.println("Getting Wood [OFF]");
                return true;
            //maintenance
            case "maintain":
                if(args.length<4)
                    return false;
                try {
                    Maintenance.setUp(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
                }catch (NumberFormatException e){return false;}
                System.out.println("Maintenance [ON]");
                return true;

            case "resetmaintenance":
                Maintenance.reset();
                System.out.println("Maintenance [OFF]");
                return true;
            //minesweeper
            case "minesweeper":
                if(args.length < 6)
                    return false;
                world = Bukkit.getWorld(args[0]);
                if(world==null){
                    System.out.println("Invalid world name");
                    return true;
                }
                try {
                    int x = Integer.parseInt(args[2]);
                    x1 = Integer.parseInt(args[1]);
                    if(x1>x) {
                        x2 = x1;
                        x1 = x;
                    }
                    else
                        x2 = x;
                    int z = Integer.parseInt(args[4]);
                    z1 = Integer.parseInt(args[3]);
                    if(z1>z) {
                        z2 = z1;
                        z1 = z;
                    }
                    else
                        z2 = z;
                    Minesweeper.setUp(world, x1, x2, z1, z2, Integer.parseInt(args[5]));
                    System.out.println("Minesweeper [ON]");
                }catch (NumberFormatException e){System.out.println("Invalid coordinates");}
                return  true;

            case "resetMinesweeper":
                Minesweeper.reset();
                System.out.println("Minesweeper [OFF]");
                return true;
            //payload
            case "payload":
                if(args.length>10)
                    Payload.payload(args);
                return true;

            case "spawn":
                if(args.length < 5)
                    return false;
                try {
                    index = Integer.parseInt(args[0]);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid index");
                    return true;
                }
                if (index < 0 || index >= builds.length) {
                    System.out.println("Out of bounds index");
                    return true;
                }
                world = Bukkit.getWorld(args[1]);
                if(world==null){
                    System.out.println("Invalid world name");
                    return true;
                }
                try {
                    Payload.spawn(index, world, Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]), BlockFace.UP);
                }catch (NumberFormatException e){ System.out.println("Invalid argument(s)"); }
                return true;
            //waterfall
            case "waterfall":
                if(args.length<4)
                    return false;
                world = Bukkit.getWorld(args[0]);
                if(world==null){
                    System.out.println("Invalid world name");
                    return true;
                }
                try {
                    Waterfall.setUp(world, Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
                }catch (NumberFormatException e){return false;}
                System.out.println("Waterfall [ON]");
                return true;

            case "resetwaterfall":
                Waterfall.reset();
                System.out.println("Waterfall [OFF]");
                return true;
        }
        return true;
    }

    public static Map<Block, BlockData> build(int index, World world, Location loc){
        Map<Block, BlockData> blocks = new HashMap<>();
        for (int i = 0; i < builds[index].length; i++) {
            for (int j = 0; j < builds[index][i].length; j++) {
                for (int k = 0; k < builds[index][i][j].length; k++) {
                    Block block = world.getBlockAt(loc.clone().add(i, j, k));
                    blocks.put(block, block.getBlockData());
                    block.setBlockData(Bukkit.createBlockData(builds[index][i][j][k]));
                }
            }
        }
        return blocks;
    }
}
