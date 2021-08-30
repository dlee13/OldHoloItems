package com.klin.holoItems.dungeons.inaDungeon;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.collections.misc.franCollection.items.DyeConcentrate;
import com.klin.holoItems.dungeons.Resetable;
import com.klin.holoItems.dungeons.inaDungeon.classes.Member;
import com.klin.holoItems.dungeons.inaDungeon.classes.*;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class InaDungeon implements CommandExecutor{
    public static Map<String, Resetable> presets;

    public InaDungeon(){
        presets = new HashMap<>();
    }

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
                World world = Bukkit.getWorld(args[1]);
                if(world==null) {
                    System.out.println("Invalid world name");
                    return true;
                }
                Location loc;
                try {
                    loc = new Location(world, Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
                }catch(NumberFormatException e){ return false; }
                build(args[0], loc, null, null);
                return true;

            case "tostring":
                if (args.length < 8)
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
                int x1 = Math.min(arguments[0], arguments[3]);
                int x2 = Math.max(arguments[0], arguments[3]);
                int y1 = Math.min(arguments[1], arguments[4]);
                int y2 = Math.max(arguments[1], arguments[4]);
                int z1 = Math.min(arguments[2], arguments[5]);
                int z2 = Math.max(arguments[2], arguments[5]);
                String wall = "";
                for (int i = x1; i <= x2; i++) {
                    String fence = "";
                    for (int j = y1; j <= y2; j++) {
                        String tile = "";
                        for (int k = z1; k <= z2; k++)
                            tile += " " + world.getBlockAt(i, j, k).getBlockData().getAsString();
                        fence += "," + tile;
                    }
                    wall += "\n" + fence.substring(2);
                }
                try {
                    File file = new File(HoloItems.getInstance().getDataFolder(), args[7]+".txt");
                    if (file.createNewFile()) {
                        FileWriter writer = new FileWriter(file);
                        writer.write(wall.substring(1));
                        writer.close();
                        System.out.println("File created: " + args[7]);
                    } else {
                        System.out.println("File already exists");
                    }
                } catch (IOException e) { e.printStackTrace(); }
                return true;

            case "reset":
                if(args.length<1) {
                    System.out.println(presets.keySet());
                    return true;
                }
                Resetable preset = presets.remove(args[0].toLowerCase());
                if(preset!=null) {
                    preset.reset();
                    System.out.println(preset.getClass().getSimpleName() + " [OFF]");
                    return true;
                }
                return false;

            case "shop":
                if(presets.get("aoshop")!=null){
                    System.out.println("Ao-Shop already ON");
                    return true;
                }
                if (args.length<4)
                    return false;
                world = Bukkit.getWorld(args[0]);
                if(world==null){
                    System.out.println("Invalid world name");
                    return true;
                }
                try {
                    presets.put("aoshop", new AoShop(world, new Location(world, Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]))));
                    System.out.println("Ao-Shop [ON]");
                }catch(NumberFormatException e){return false;}
                return true;

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

            case "freeze":
                ClassSelect classSelect = (ClassSelect) presets.get("classselect");
                if(classSelect!=null)
                    classSelect.freeze();
                return true;

            case "select":
                if(presets.get("classselect")!=null){
                    System.out.println("Class Select already ON");
                    return true;
                }
                if(args.length<4)
                    return false;
                world = Bukkit.getWorld(args[0]);
                if(world==null){
                    System.out.println("Invalid world name");
                    return true;
                }
                try {
                    presets.put("classselect", new ClassSelect(world, Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3])));
                    System.out.println("Class Select [ON]");
                }catch (NumberFormatException e){return false;}
                return true;

            case "conduit":
                if(presets.get("conduit")!=null){
                    System.out.println("Conduit already ON");
                    return true;
                }
                if(args.length<4)
                    return false;
                world = Bukkit.getWorld(args[0]);
                if(world==null){
                    System.out.println("Invalid world name");
                    return true;
                }
                try {
                    presets.put("conduit", new Conduit(world, Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3])));
                    System.out.println("Conduit [ON]");
                }catch (NumberFormatException e){return false;}
                return true;

            case "cookie":
                if(presets.get("cookie")!=null){
                    System.out.println("Cookie already ON");
                    return true;
                }
                presets.put("cookie", new Cookie());
                System.out.println("Cookie [ON]");
                return true;

            case "plant":
                if(presets.get("gettingwood")!=null){
                    System.out.println("Getting Wood already ON");
                    return true;
                }
                if(args.length<4)
                    return false;
                world = Bukkit.getWorld(args[0]);
                if(world==null){
                    System.out.println("Invalid world name");
                    return true;
                }
                try {
                    presets.put("gettingwood", new GettingWood(new Location(world, Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]))));
                    System.out.println("Getting Wood [ON]");
                }catch (NumberFormatException e){return false;}
                return true;

            case "class":
                if(!(sender instanceof Player)){
                    if(args.length<2) {
                        System.out.println("Specify player");
                        return true;
                    }
                    else {
                        player = Bukkit.getPlayer(args[1]);
                        if(player==null) {
                            System.out.println("Unknown player");
                            return true;
                        }
                    }
                }
                else if(args.length>0) {
                    if(args.length!=1) {
                        player = Bukkit.getPlayer(args[1]);
                        if(player==null){
                            sender.sendMessage("Unknown player");
                            return true;
                        }
                    }
                    else
                        player = (Player) sender;
                }
                else
                    return false;
                Member member;
                switch (args[0].toLowerCase()){
                    case "calli":
                        member = new Calli(player); break;
                    case "enma":
                        member = new Enma(player); break;
                    case "gura":
                        member = new Gura(player); break;
                    case "ina":
                        member = new Ina(player); break;
                    case "irys":
                        member = new Irys(player); break;
                    case "kiara":
                        member = new Kiara(player); break;
                    case "watson":
                        member = new Watson(player); break;
                    default:
                        return false;
                }
                ((Maintenance) presets.computeIfAbsent("maintenance", k -> new Maintenance())).classes.put(player, member);
                return true;

            case "maintain":
                if(presets.get("maintenance")!=null){
                    System.out.println("Maintenance already ON");
                    return true;
                }
                if(args.length<4)
                    return false;
                try {
                    presets.put("maintenance", new Maintenance(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3])));
                    System.out.println("Maintenance [ON]");
                }catch (NumberFormatException e){return false;}
                return true;

            case "minesweeper":
                if(presets.get("minesweeper")!=null){
                    System.out.println("Minesweeper already ON");
                    return true;
                }
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
                    presets.put("minesweeper", new Minesweeper(world, x1, x2, z1, z2, Integer.parseInt(args[5])));
                    System.out.println("Minesweeper [ON]");
                }catch (NumberFormatException e){System.out.println("Invalid coordinates");}
                return  true;

            case "payload":
                if(presets.get("payload")!=null){
                    System.out.println("Payload already ON");
                    return true;
                }
                if(args.length < 5)
                    return false;
                world = Bukkit.getWorld(args[0]);
                if(world==null){
                    System.out.println("Invalid world name");
                    return true;
                }
                try {
                    presets.put("payload", new Payload(world, Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4])));
                    System.out.println("Payload [ON]");
                }catch(NumberFormatException e){ return false; }
                return true;

            case "waterfall":
                if(presets.get("waterfall")!=null){
                    System.out.println("Waterfall already ON");
                    return true;
                }
                if(args.length<4)
                    return false;
                world = Bukkit.getWorld(args[0]);
                if(world==null){
                    System.out.println("Invalid world name");
                    return true;
                }
                try {
                    presets.put("waterfall", new Waterfall(world, Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3])));
                    System.out.println("Waterfall [ON]");
                }catch (NumberFormatException e){return false;}
                return true;
        }
        return true;
    }

    public static Map<Location, BlockData> build(String fileName, Location loc, Map<Location, BlockData> build, Set<Material> overwrite){
        File file = new File(HoloItems.getInstance().getDataFolder(), fileName+".txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String wall;
            World world = loc.getWorld();
            int increment = 0;
            if(build==null)
                build = new HashMap<>();
            while ((wall = reader.readLine()) != null) {
                String[] fence = wall.split(", ");
                for (int i = 0; i < fence.length; i++) {
                    String[] tile = fence[i].split(" ");
                    for (int j = 0; j < tile.length; j++) {
                        Block block = world.getBlockAt(loc.clone().add(increment, i, j));
                        BlockData data = Bukkit.createBlockData(tile[j]);
                        //overwrite: null->skip air, new->replace all
                        if((overwrite==null || !overwrite.contains(block.getType())) && data.getMaterial().isAir())
                            continue;
                        build.putIfAbsent(block.getLocation(), block.getBlockData());
                        block.setBlockData(data);
                    }
                }
                increment++;
            }
            return build;
        } catch (IOException e) {
//            System.out.println("Invalid file");
            return null;
        }
    }

    public static Map<Location, BlockData> build(String fileName, Location loc, Map<Location, BlockData> build, Set<Material> overwrite, BlockFace face){
        File file = new File(HoloItems.getInstance().getDataFolder(), fileName+".txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String wall;
            World world = loc.getWorld();
            boolean flip;
            boolean turn;
            Map<BlockFace, BlockFace> rotate;
            switch (face){
                case NORTH:
                    loc = loc.clone().add(0, 0, -1);
                    flip = true;
                    turn = false;
                    rotate = Utility.opposites;
                    break;
                case EAST:
                    flip = false;
                    turn = true;
                    rotate = Utility.left;
                    break;
                case WEST:
                    loc = loc.clone().add(-1, 0, 0);
                    flip = true;
                    turn = true;
                    rotate = new HashMap<>();
                    for(BlockFace blockFace: Utility.left.keySet())
                        rotate.put(blockFace, Utility.opposites.get(Utility.left.get(blockFace)));
                    break;
                default:
                    flip = false;
                    turn = false;
                    rotate = null;
            }
            int increment = 0;
            if(build==null)
                build = new HashMap<>();
            while ((wall = reader.readLine()) != null) {
                String[] fence = wall.split(", ");
                for (int i = 0; i < fence.length; i++) {
                    String[] tile = fence[i].split(" ");
                    for (int j = 0; j < tile.length; j++) {
                        int x = increment;
                        int z = (flip?tile.length:0) + j*(flip?-1:1);
                        if(turn){
                            int temp = x;
                            x = z;
                            z = temp;
                        }
                        Block block = world.getBlockAt(loc.clone().add(x, i ,z));
                        BlockData data = Bukkit.createBlockData(tile[j]);
                        //overwrite: null->skip air, new->replace all
                        if((overwrite==null || !overwrite.contains(block.getType())) && data.getMaterial().isAir())
                            continue;
                        build.putIfAbsent(block.getLocation(), block.getBlockData());
                        if(rotate!=null && data instanceof Directional) {
                            Directional directional = (Directional) data;
                            directional.setFacing(directional.getFacing());
                        }
                        block.setBlockData(data);
                    }
                }
                increment++;
            }
            return build;
        } catch (IOException e) { return null; }
    }
}
