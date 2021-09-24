package com.klin.holoItems.collections.misc.opCollection.items;

import com.klin.holoItems.Item;
import com.klin.holoItems.collections.misc.opCollection.OpCollection;
import com.klin.holoItems.interfaces.Interactable;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Set;

public class Sokoban extends Item implements Interactable {
    public static final String name = "sokoban";
    public static final Set<Enchantment> accepted = null;

    private static final Material material = Material.STONE_HOE;
    private static final int quantity = 1;
    private static final String lore =
            "Push a block by a block";
    private static final int durability = -1;
    private static final boolean stackable = false;
    private static final boolean shiny = false;
    public static final int cost = -1;

    public Sokoban(){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost);
    }

    public void registerRecipes(){}

    public void ability(PlayerInteractEvent event, Action action){
        if(action!=Action.RIGHT_CLICK_BLOCK)
            return;
        Block block = event.getClickedBlock();
        Block target = block.getRelative(Utility.opposites.get(event.getBlockFace()));
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