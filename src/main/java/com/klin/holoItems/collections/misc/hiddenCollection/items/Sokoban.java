package com.klin.holoItems.collections.misc.hiddenCollection.items;

import com.klin.holoItems.Item;
import com.klin.holoItems.collections.misc.hiddenCollection.HiddenCollection;
import com.klin.holoItems.interfaces.Interactable;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Sokoban extends Item implements Interactable {
    public static final String name = "sokoban";
    public static final Set<Enchantment> accepted = null;

    Map<BlockFace, BlockFace> opposites = Stream.of(new BlockFace[][] {
            { BlockFace.UP, BlockFace.DOWN },
            { BlockFace.DOWN, BlockFace.UP },
            { BlockFace.NORTH, BlockFace.SOUTH },
            { BlockFace.SOUTH, BlockFace.NORTH },
            { BlockFace.EAST, BlockFace.WEST },
            { BlockFace.WEST, BlockFace.EAST },
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

    private static final Material material = Material.STONE_HOE;
    private static final int quantity = 1;
    private static final String lore =
            "§6Ability" +"/n"+
                "Push a block by a block";
    private static final int durability = -1;
    private static final boolean stackable = false;
    private static final boolean shiny = false;

    public static final int cost = -1;
    public static final char key = '0';

    public Sokoban(){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost,
                ""+HiddenCollection.key+key, key);
    }

    public void registerRecipes(){}

    public void ability(PlayerInteractEvent event, Action action){
        if(action!=Action.RIGHT_CLICK_BLOCK)
            return;
        Block block = event.getClickedBlock();
        Block target = block.getRelative(opposites.get(event.getBlockFace()));
        if(!target.isEmpty())
            return;

//        Location loc = block.getLocation();
//        Location dest = target.getLocation();
//        double x = 0.05*(dest.getBlockX()-loc.getBlockX());
//        double y = 0.05*(dest.getBlockY()-loc.getBlockY());
//        double z = 0.05*(dest.getBlockZ()-loc.getBlockZ());

//        Material type = block.getType();
        BlockData data = block.getBlockData();
        block.setType(Material.AIR);
        target.setBlockData(data);

//        FallingBlock fallingBlock = block.getWorld().spawnFallingBlock(loc.add(0.5, 0, 0.5), data);
//        fallingBlock.setGravity(false);
//
//        new Task(HoloItems.getInstance(), 0, 1){
//            double charge = 0;
//
//            public void run() {
//                charge += 1;
//                if(charge>=20){
//                    fallingBlock.remove();
//                    target.setType(data.getMaterial());
//                    cancel();
//                    return;
//                }
//                fallingBlock.teleport(fallingBlock.getLocation().add(x, y, z));
//
//                //test
//                System.out.println(fallingBlock.getLocation().toString());
//            }
//        };
    }
}