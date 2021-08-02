package com.klin.holoItems.dungeons.inaDungeon;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class InaDungeon implements CommandExecutor, Listener {
    private static final String[][][][] builds = new String[][][][]{
            {{{"minecraft:spruce_slab[type=bottom,waterlogged=false]", "minecraft:spruce_planks", "minecraft:spruce_slab[type=bottom,waterlogged=false]"}, {"minecraft:air", "minecraft:spruce_planks", "minecraft:air"}, {"minecraft:air", "minecraft:cobblestone", "minecraft:air"}, {"minecraft:air", "minecraft:cobblestone_stairs[facing=east,half=bottom,shape=straight,waterlogged=false]", "minecraft:air"}, {"minecraft:air", "minecraft:spruce_stairs[facing=east,half=top,shape=straight,waterlogged=false]", "minecraft:air"}, {"minecraft:air", "minecraft:glass", "minecraft:air"}, {"minecraft:air", "minecraft:spruce_stairs[facing=east,half=bottom,shape=straight,waterlogged=false]", "minecraft:air"}, {"minecraft:air", "minecraft:air", "minecraft:air"}}, {{"minecraft:spruce_planks", "minecraft:spruce_planks", "minecraft:spruce_slab[type=double,waterlogged=false]"}, {"minecraft:spruce_planks", "minecraft:spruce_planks", "minecraft:spruce_planks"}, {"minecraft:cobblestone", "minecraft:cobblestone", "minecraft:cobblestone"}, {"minecraft:cobblestone_stairs[facing=south,half=bottom,shape=straight,waterlogged=false]", "minecraft:cobblestone", "minecraft:cobblestone_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]"}, {"minecraft:spruce_stairs[facing=south,half=top,shape=straight,waterlogged=false]", "minecraft:redstone_block", "minecraft:spruce_stairs[facing=north,half=top,shape=straight,waterlogged=false]"}, {"minecraft:glass", "minecraft:redstone_lamp[lit=true]", "minecraft:glass"}, {"minecraft:spruce_stairs[facing=south,half=bottom,shape=straight,waterlogged=false]", "minecraft:cobblestone", "minecraft:spruce_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]"}, {"minecraft:air", "minecraft:cobblestone_slab[type=bottom,waterlogged=false]", "minecraft:air"}}, {{"minecraft:spruce_slab[type=bottom,waterlogged=false]", "minecraft:spruce_planks", "minecraft:spruce_slab[type=bottom,waterlogged=false]"}, {"minecraft:air", "minecraft:spruce_planks", "minecraft:air"}, {"minecraft:air", "minecraft:cobblestone", "minecraft:air"}, {"minecraft:air", "minecraft:cobblestone_stairs[facing=west,half=bottom,shape=straight,waterlogged=false]", "minecraft:air"}, {"minecraft:air", "minecraft:spruce_stairs[facing=west,half=top,shape=straight,waterlogged=false]", "minecraft:air"}, {"minecraft:air", "minecraft:glass", "minecraft:air"}, {"minecraft:glass", "minecraft:spruce_stairs[facing=west,half=bottom,shape=straight,waterlogged=false]", "minecraft:air"}, {"minecraft:air", "minecraft:air", "minecraft:air"}}}
    };

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player))
            return true;
        Player player = (Player) sender;
        if(!player.isOp())
            return true;

        switch(cmd.getName().toLowerCase()){
            case "tostring":
                if(args.length<6) {
                    player.sendMessage("A pair of coordinates are required");
                    return true;
                }
                int[] arguments = new int[6];
                try{
                    Location loc = player.getLocation();
                    for(int i=0; i<6; i++)
                        arguments[i] = args[i].startsWith("~")?(i==0||i==3?loc.getBlockX():(i==1||i==4?loc.getBlockY():loc.getBlockZ()))+(args[i].substring(1).isEmpty()?0:Integer.parseInt(args[i].substring(1))):Integer.parseInt(args[i]);
                }catch(NumberFormatException e){
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
                for (int i=x1; i<=x2; i++) {
                    out += "{";
                    for (int j=y1; j<=y2; j++) {
                        out += "{";
                        for (int k=z1; k<=z2; k++) {
                            out += "\"" + world.getBlockAt(i, j, k).getBlockData().getAsString() + "\"";
                            if(k<z2)
                                out += ", ";
                        }
                        out += "}";
                        if(j<y2)
                            out += ", ";
                    }
                    out += "}";
                    if(i<x2)
                        out += ", ";
                }
                System.out.println(out + "}");
                return true;

            case "build":
                if(args.length<1)
                    return true;
                int index;
                try {
                    index = Integer.parseInt(args[0]);
                }catch(NumberFormatException e){
                    player.sendMessage("Invalid index");
                    return true;
                }
                if(index<0 || index>=builds.length){
                    player.sendMessage("Out of bounds indnex");
                    return true;
                }
                Location loc = player.getLocation();
                world = player.getWorld();
                for(int i=0; i<builds[index].length; i++){
                    for(int j=0; j<builds[index][i].length; j++){
                        for(int k=0; k<builds[index][i][j].length; k++){
                            world.getBlockAt(loc.clone().add(i, j, k)).setBlockData(Bukkit.createBlockData(builds[index][i][j][k]));
                        }
                    }
                }
        }
        return true;
    }
}
