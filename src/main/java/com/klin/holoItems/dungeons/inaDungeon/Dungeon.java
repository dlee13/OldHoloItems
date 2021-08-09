package com.klin.holoItems.dungeons.inaDungeon;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.collections.misc.franCollection.items.DyeConcentrate;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;

import java.util.HashSet;

public class Dungeon implements CommandExecutor{
    public static final String[][][][] builds = new String[][][][]{
            {{{"minecraft:air", "minecraft:oak_trapdoor[facing=west,half=bottom,open=true,powered=false,waterlogged=false]", "minecraft:air", "minecraft:air", "minecraft:oak_trapdoor[facing=west,half=bottom,open=true,powered=false,waterlogged=false]", "minecraft:air", "minecraft:air"}, {"minecraft:air", "minecraft:spruce_trapdoor[facing=west,half=bottom,open=true,powered=false,waterlogged=false]", "minecraft:spruce_trapdoor[facing=west,half=bottom,open=true,powered=false,waterlogged=false]", "minecraft:air", "minecraft:air", "minecraft:air", "minecraft:air"}, {"minecraft:air", "minecraft:air", "minecraft:air", "minecraft:air", "minecraft:air", "minecraft:air", "minecraft:air"}}, {{"minecraft:dark_oak_slab[type=top,waterlogged=false]", "minecraft:grindstone[face=ceiling,facing=south]", "minecraft:spruce_slab[type=top,waterlogged=false]", "minecraft:spruce_slab[type=top,waterlogged=false]", "minecraft:grindstone[face=ceiling,facing=north]", "minecraft:dark_oak_slab[type=top,waterlogged=false]", "minecraft:spruce_fence_gate[facing=east,in_wall=false,open=false,powered=false]"}, {"minecraft:spruce_trapdoor[facing=north,half=bottom,open=true,powered=false,waterlogged=false]", "minecraft:barrel[facing=up,open=false]", "minecraft:air", "minecraft:air", "minecraft:gray_carpet", "minecraft:spruce_fence[east=false,north=false,south=false,waterlogged=false,west=false]", "minecraft:air"}, {"minecraft:air", "minecraft:spruce_fence[east=false,north=false,south=false,waterlogged=false,west=false]", "minecraft:air", "minecraft:air", "minecraft:air", "minecraft:air", "minecraft:air"}}, {{"minecraft:dark_oak_slab[type=top,waterlogged=false]", "minecraft:spruce_slab[type=top,waterlogged=false]", "minecraft:spruce_slab[type=top,waterlogged=false]", "minecraft:spruce_slab[type=top,waterlogged=false]", "minecraft:spruce_slab[type=top,waterlogged=false]", "minecraft:dark_oak_slab[type=top,waterlogged=false]", "minecraft:air"}, {"minecraft:spruce_trapdoor[facing=north,half=bottom,open=true,powered=false,waterlogged=false]", "minecraft:spruce_trapdoor[facing=north,half=bottom,open=false,powered=false,waterlogged=false]", "minecraft:barrel[facing=west,open=false]", "minecraft:air", "minecraft:gray_carpet", "minecraft:air", "minecraft:air"}, {"minecraft:air", "minecraft:air", "minecraft:barrel[facing=south,open=false]", "minecraft:air", "minecraft:air", "minecraft:air", "minecraft:air"}}, {{"minecraft:dark_oak_slab[type=top,waterlogged=false]", "minecraft:grindstone[face=ceiling,facing=south]", "minecraft:spruce_slab[type=top,waterlogged=false]", "minecraft:spruce_slab[type=top,waterlogged=false]", "minecraft:grindstone[face=ceiling,facing=north]", "minecraft:dark_oak_slab[type=top,waterlogged=false]", "minecraft:spruce_fence_gate[facing=west,in_wall=false,open=false,powered=false]"}, {"minecraft:spruce_trapdoor[facing=north,half=bottom,open=true,powered=false,waterlogged=false]", "minecraft:barrel[facing=up,open=false]", "minecraft:chest[facing=east,type=left,waterlogged=false]", "minecraft:chest[facing=east,type=right,waterlogged=false]", "minecraft:gray_carpet", "minecraft:spruce_fence[east=false,north=false,south=false,waterlogged=false,west=false]", "minecraft:air"}, {"minecraft:air", "minecraft:spruce_fence[east=false,north=false,south=false,waterlogged=false,west=false]", "minecraft:air", "minecraft:air", "minecraft:air", "minecraft:air", "minecraft:air"}}, {{"minecraft:air", "minecraft:oak_trapdoor[facing=east,half=bottom,open=true,powered=false,waterlogged=false]", "minecraft:air", "minecraft:air", "minecraft:oak_trapdoor[facing=east,half=bottom,open=true,powered=false,waterlogged=false]", "minecraft:air", "minecraft:air"}, {"minecraft:air", "minecraft:spruce_trapdoor[facing=east,half=bottom,open=true,powered=false,waterlogged=false]", "minecraft:spruce_trapdoor[facing=east,half=bottom,open=true,powered=false,waterlogged=false]", "minecraft:air", "minecraft:air", "minecraft:air", "minecraft:air"}, {"minecraft:air", "minecraft:air", "minecraft:air", "minecraft:air", "minecraft:air", "minecraft:air", "minecraft:air"}}}
    };

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player))
            return true;
        Player player = (Player) sender;
        if(!player.isOp())
            return true;

        switch(cmd.getName().toLowerCase()) {
            case "tostring":
                if (args.length < 6) {
                    player.sendMessage("A pair of coordinates are required");
                    return true;
                }
                int[] arguments = new int[6];
                try {
                    Location loc = player.getLocation();
                    for (int i = 0; i < 6; i++)
                        arguments[i] = args[i].startsWith("~") ? (i == 0 || i == 3 ? loc.getBlockX() : (i == 1 || i == 4 ? loc.getBlockY() : loc.getBlockZ())) + (args[i].substring(1).isEmpty() ? 0 : Integer.parseInt(args[i].substring(1))) : Integer.parseInt(args[i]);
                } catch (NumberFormatException e) {
                    player.sendMessage("Invalid coordinate(s)");
                    return true;
                }
                World world = player.getWorld();
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

            case "build":
                if (args.length < 1)
                    return true;
                int index;
                try {
                    index = Integer.parseInt(args[0]);
                } catch (NumberFormatException e) {
                    player.sendMessage("Invalid index");
                    return true;
                }
                if (index < 0 || index >= builds.length) {
                    player.sendMessage("Out of bounds indnex");
                    return true;
                }
                Location loc = player.getLocation();
                world = player.getWorld();
                for (int i = 0; i < builds[index].length; i++) {
                    for (int j = 0; j < builds[index][i].length; j++) {
                        for (int k = 0; k < builds[index][i][j].length; k++) {
                            world.getBlockAt(loc.clone().add(i, j, k)).setBlockData(Bukkit.createBlockData(builds[index][i][j][k]));
                        }
                    }
                }
                return true;

            case "spreadfire":
                if (args.length < 3)
                    return true;
                world = player.getWorld();
                try {
                    Attacks.spreadFire(BlockFace.UP, world.getBlockAt(new Location(world, Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]))), Utility.cardinal.keySet(), new HashSet<>(), new HashSet<>(), args.length>3&&args[3].equals("true"));
                }catch(NumberFormatException ignored){}
                return true;

            case "paintbomb":
                if (args.length < 3)
                    return true;
                world = player.getWorld();
                try {
                    loc = new Location(world, Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
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

            case "groundpound":
                Entity entity;
                if(args.length>0) {
                    try {
                        entity = player.getWorld().spawnEntity(player.getLocation(), EntityType.valueOf(args[0]));
                    }catch(IllegalArgumentException e){return true;}
                }
                else entity = player;
                Attacks.groundPound(entity);
        }
        return true;
    }
}
