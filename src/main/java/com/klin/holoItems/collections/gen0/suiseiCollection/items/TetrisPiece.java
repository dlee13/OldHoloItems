package com.klin.holoItems.collections.gen0.suiseiCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.gen0.suiseiCollection.SuiseiCollection;
import com.klin.holoItems.interfaces.Interactable;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TetrisPiece extends Item implements Interactable {
    public static final String name = "TetrisPiece";
    public static final Set<Enchantment> accepted = null;
    private static final Map<Material, Material[][]> pieces = new LinkedHashMap<>(){{
        put(Material.BLUE_CONCRETE_POWDER, new Material[][]{
                {Material.BLUE_CONCRETE_POWDER, Material.BLUE_CONCRETE_POWDER, Material.BLUE_CONCRETE_POWDER},
                {Material.BLUE_CONCRETE_POWDER}
        });
        put(Material.ORANGE_CONCRETE_POWDER, new Material[][]{
                {Material.ORANGE_CONCRETE_POWDER, Material.ORANGE_CONCRETE_POWDER, Material.LIGHT_BLUE_CONCRETE_POWDER},
                {null, null, Material.LIGHT_BLUE_CONCRETE_POWDER}
        });
        put(Material.YELLOW_CONCRETE_POWDER, new Material[][]{
                {Material.YELLOW_CONCRETE_POWDER, Material.YELLOW_CONCRETE_POWDER},
                {Material.YELLOW_CONCRETE_POWDER, Material.YELLOW_CONCRETE_POWDER}
        });
        put(Material.LIME_CONCRETE_POWDER, new Material[][]{
                {Material.LIME_CONCRETE_POWDER, Material.LIME_CONCRETE_POWDER},
                {null, Material.LIME_CONCRETE_POWDER, Material.LIME_CONCRETE_POWDER}
        });
        put(Material.PURPLE_CONCRETE_POWDER, new Material[][]{
                {Material.PURPLE_CONCRETE_POWDER, Material.PURPLE_CONCRETE_POWDER, Material.PURPLE_CONCRETE_POWDER},
                {null, Material.PURPLE_CONCRETE_POWDER}
        });
        put(Material.RED_CONCRETE_POWDER, new Material[][]{
                {null, Material.RED_CONCRETE_POWDER, Material.RED_CONCRETE_POWDER},
                {Material.RED_CONCRETE_POWDER, Material.RED_CONCRETE_POWDER}
        });
        put(Material.LIGHT_BLUE_CONCRETE_POWDER, new Material[][]{
                {Material.LIGHT_BLUE_CONCRETE_POWDER, Material.LIGHT_BLUE_CONCRETE_POWDER, Material.LIGHT_BLUE_CONCRETE_POWDER, Material.LIGHT_BLUE_CONCRETE_POWDER}
        });
    }};
    public static final Map<Material, Material> tetris = Stream.of(new Material[][] {
            { Material.BLUE_CONCRETE_POWDER, Material.BLUE_CONCRETE },
            { Material.ORANGE_CONCRETE_POWDER, Material.ORANGE_CONCRETE },
            { Material.YELLOW_CONCRETE_POWDER, Material.YELLOW_CONCRETE },
            { Material.LIME_CONCRETE_POWDER, Material.LIME_CONCRETE },
            { Material.PURPLE_CONCRETE_POWDER, Material.PURPLE_CONCRETE },
            { Material.RED_CONCRETE_POWDER, Material.RED_CONCRETE },
            { Material.LIGHT_BLUE_CONCRETE_POWDER, Material.LIGHT_BLUE_CONCRETE }
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));
    private static Map<Player, String> players = new HashMap<>();

    private static final Material material = Material.LIGHT_BLUE_CONCRETE_POWDER;
    private static final int quantity = 1;
    private static final String lore =
            "§6Ability" +"/n"+
                "Spin";
    private static final int durability = 0;
    private static final boolean stackable = false;
    private static final boolean shiny = false;

    public static final int cost = -1;
    public static final char key = '4';

    public TetrisPiece(){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost,
                ""+SuiseiCollection.key+key, key);
    }

    public void registerRecipes(){
        for(Material type : pieces.keySet()) {
            item.setType(type);
            String key = type.toString();
            key = key.substring(0, key.indexOf("_CONCRETE_POWDER")).toLowerCase();
            int index = key.indexOf("_");
            if(index>=0)
                key = key.substring(0, index)+key.substring(index+1, index+2).toUpperCase()+key.substring(index+2);
            key += name;
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§6"+Utility.formatName(key));
            item.setItemMeta(meta);
            ShapedRecipe recipe =
                    new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), key), item);
            recipe.shape("%%%","%*%","%%%");
            recipe.setIngredient('*', type);
            recipe.setIngredient('%', Material.LEATHER);
            recipe.setGroup(name);
            Bukkit.getServer().addRecipe(recipe);
        }
    }

    public void ability(PlayerInteractEvent event, Action action){
        Player player = event.getPlayer();
        BlockFace face = player.getFacing();
        BlockFace left = Utility.left.get(face);
        if(left==null)
            return;
        int x = 0;
        int z = 0;
        BlockFace right = Utility.opposites.get(left);
        switch(right){
            case NORTH:
                z = -1;
            case SOUTH:
                if(z==0)
                    z = 1;
                break;
            case EAST:
                x = 1;
            case WEST:
                if(x==0)
                    x = -1;
                break;
            default:
                return;
        }
        World world = player.getWorld();
        Location loc = player.getEyeLocation();
        Vector dir = loc.getDirection();
        RayTraceResult result = world.rayTraceBlocks(loc, dir, 100);
        if(result==null)
            return;
        Block block = result.getHitBlock();
        if(block==null)
            return;
        while(!block.isEmpty()) {
            if(block.getY()<254)
                block = block.getRelative(BlockFace.UP);
            else return;
        }
        Location drop = block.getRelative(Utility.opposites.get(face)).getRelative(left).getRelative(BlockFace.UP).getRelative(BlockFace.UP).getLocation();
        Material type = event.getItem().getType();
        Material[][] piece = pieces.get(type);
        if(piece==null)
            return;
        Set<FallingBlock> blocks = new HashSet<>();
        for(int i=0; i<piece.length; i++){
            for(int j=0; j<piece[i].length; j++) {
                if(piece[i][j]==null)
                    continue;
                FallingBlock fallingBlock = world.spawnFallingBlock(drop.clone().add(j*x+0.5, i, j*z+0.5), Bukkit.createBlockData(type));
                fallingBlock.setGravity(false);
                blocks.add(fallingBlock);
            }
        }

        drop.setY(0);
        int xCopy = x;
        int zCopy = z;
        new Task(HoloItems.getInstance(), 2, 2){
            int increment = 0;
            Location dropCopy = drop;
            public void run(){
                if(increment>=600){
                    cancel();
                    return;
                }
                if(player.isSneaking()){
                    Location loc = player.getEyeLocation();
                    RayTraceResult result = world.rayTraceBlocks(loc, loc.getDirection(), 100);
                    if(result==null)
                        return;
                    Block block = result.getHitBlock();
                    if(block==null)
                        return;
                    loc = block.getLocation();
                    Location direction = loc.subtract(dropCopy);
                    Vector vector = new Vector(xCopy==0?0:Math.signum(direction.getX()), 0, zCopy==0?0:Math.signum(direction.getZ()));
                    dropCopy = dropCopy.add(vector);
                    vector = vector.multiply(0.5);
                    for(FallingBlock fallingBlock : blocks)
                        fallingBlock.setVelocity(vector);
                    new BukkitRunnable(){
                        public void run(){
                            for(FallingBlock fallingBlock : blocks)
                                fallingBlock.setVelocity(new Vector());
                        }
                    }.runTaskLater(HoloItems.getInstance(), 2);
                }
                increment++;
                if(increment%10!=0)
                    return;
                for(FallingBlock fallingBlock : blocks) {
                    Location loc = fallingBlock.getLocation().subtract(0, 1, 0);
                    if(!world.getBlockAt(loc).isEmpty()){
                        Material concrete = tetris.get(type);
                        Map<Integer, Block> check = new HashMap<>();
                        for(FallingBlock fBlock : blocks){
                            Location location = fBlock.getLocation();
                            Block block = world.getBlockAt(location);
                            block.setType(concrete);
                            if(!check.containsKey(block.getY()))
                                check.put(block.getY(), block);
                            fBlock.remove();
                        }
                        for(Block block : check.values()){
                            Block front = block.getRelative(left);
                            while(tetris.containsValue(front.getType()))
                                front = front.getRelative(left);
                            if(front.isEmpty())
                                continue;
                            Block back = block.getRelative(right);
                            while(tetris.containsValue(back.getType()))
                                back = back.getRelative(right);
                            if(back.isEmpty())
                                continue;
                            Block vanguard = front;
                            Block rearguard = back;
                            new Task(HoloItems.getInstance(), 2, 2){
                                Block foremost = block;
                                Block fore = null;
                                Block hindmost = block;
                                Block hind = null;
                                public void run(){
                                    boolean cancel = true;
                                    if(!vanguard.equals(fore)) {
                                        if(!vanguard.equals(foremost))
                                            foremost.setType(Material.WHITE_CONCRETE);
                                        if (fore != null)
                                            fore.setType(Material.AIR);
                                        fore = foremost;
                                        foremost = foremost.getRelative(left);
                                        cancel = false;
                                    }
                                    if(!rearguard.equals(hind)) {
                                        if(!rearguard.equals(hindmost))
                                            hindmost.setType(Material.WHITE_CONCRETE);
                                        if (hind != null)
                                            hind.setType(Material.AIR);
                                        hind = hindmost;
                                        hindmost = hindmost.getRelative(right);
                                        cancel = false;
                                    }
                                    if(cancel)
                                        cancel();
                                }
                            };
                        }
                        cancel();
                        return;
                    }
                }
                for(FallingBlock fallingBlock : blocks)
                    fallingBlock.teleport(fallingBlock.getLocation().subtract(0, 1, 0));
            }
        };
    }
}