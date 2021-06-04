package com.klin.holoItems.collections.misc.klinCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.misc.klinCollection.KlinCollection;
import com.klin.holoItems.interfaces.Interactable;
import com.klin.holoItems.utility.Task;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Vacuum extends Item implements Interactable {
    public static final String name = "vacuum";
    public static final Set<Enchantment> accepted = null;

    private static final Material material = Material.NETHERITE_HOE;
    private static final int quantity = 1;
    private static final String lore =
            "§6Ability" +"/n"+
                    "brrrrrrrrrr";
    private static final int durability = -1;
    private static final boolean stackable = false;
    private static final boolean shiny = false;

    public static final int cost = -1;
    public static final char key = '3';

    public Vacuum(){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost,
                ""+KlinCollection.key+key, key);
    }

    public void registerRecipes(){}

    public void ability(PlayerInteractEvent event, Action action){
        if(!(action==Action.RIGHT_CLICK_AIR || action==Action.RIGHT_CLICK_BLOCK))
            return;
        Block block = event.getClickedBlock();
        if(block==null)
            return;
        Material type = block.getType();
        Queue<Block> clear = new LinkedList<>();
        Set<Block> checked = new HashSet<>();
        clear.add(block);

        new Task(HoloItems.getInstance(), 0, 1){
            int charge = 128;

            public void run(){
                if(clear.isEmpty() || charge<0){
                    cancel();
                    return;
                }

                for(int i=0; i<4; i++) {
                    Block center = clear.poll();
                    checked.add(center);
                    if(center==null || charge<0)
                        break;

                    center.setType(Material.AIR);
                    charge--;
                    for (Block block : new Block[]{
                            center.getRelative(BlockFace.UP),
                            center.getRelative(BlockFace.NORTH),
                            center.getRelative(BlockFace.SOUTH),
                            center.getRelative(BlockFace.EAST),
                            center.getRelative(BlockFace.WEST),
                            center.getRelative(BlockFace.DOWN)}) {
                        if (block.getType()==type && !checked.contains(block) && !clear.contains(block))
                            clear.add(block);
                    }
                }
            }
        };
    }
}