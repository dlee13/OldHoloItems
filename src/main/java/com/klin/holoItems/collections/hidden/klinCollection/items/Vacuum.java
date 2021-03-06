package com.klin.holoItems.collections.hidden.klinCollection.items;

import com.klin.holoItems.Item;
import com.klin.holoItems.interfaces.Interactable;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class Vacuum extends Item implements Interactable {
    public static final String name = "vacuum";

    private static final Material material = Material.NETHERITE_HOE;
    private static final int quantity = 1;
    private static final String lore =
            "brrrrrrrrrr";
    private static final int durability = -1;
    private static final boolean stackable = false;
    private static final boolean shiny = false;
    public static final int cost = -1;

    public Vacuum(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost);
    }

    public void registerRecipes(){}

    public void ability(PlayerInteractEvent event, Action action){
        if(action!=Action.RIGHT_CLICK_BLOCK)
            return;
        Block block = event.getClickedBlock();
        Utility.vacuum(block, block.getType(), 1, 64);
    }
}