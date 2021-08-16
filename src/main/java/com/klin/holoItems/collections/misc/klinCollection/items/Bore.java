package com.klin.holoItems.collections.misc.klinCollection.items;

import com.klin.holoItems.interfaces.Extractable;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.misc.klinCollection.KlinCollection;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class Bore extends Item implements Extractable {
    public static final String name = "bore";
    public static final Set<Enchantment> accepted = new HashSet<>(){{
        add(Enchantment.DIG_SPEED);
        add(Enchantment.SILK_TOUCH);
    }};

    private static final Material material = Material.NETHERITE_PICKAXE;
    private static final int quantity = 1;
    private static final String lore =
            "brrrrrrrrrr";
    private static final int durability = -1;
    public static final boolean stackable = false;
    private static final boolean shiny = false;

    public static final int cost = -1;
    public static final char key = '0';

    public Bore(){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost,
                ""+KlinCollection.key+key, key);
    }

    public void registerRecipes(){}

    public void ability(BlockBreakEvent event){
        Player player = event.getPlayer();
        BlockFace direction = player.getFacing();
        boolean axis;
        switch(direction){
            case NORTH:
            case SOUTH:
                axis = true;
                break;
            case EAST:
            case WEST:
                axis = false;
                break;
            default:
                player.sendMessage("Face a cardinal direction: "+direction);
                return;
        }

        Location center = event.getBlock().getLocation();
        World world = center.getWorld();
        ItemStack bore = player.getInventory().getItemInMainHand();
        for(int i=-4; i<5; i++){
            for(int j=-2; j<4; j++){
                world.getBlockAt(center.clone().
                        add(axis ? i : 0, j, !axis ? i : 0)).breakNaturally(bore);
                //probably a good idea to take item as parameter since i reckon
                //.breakNaturally(ItemStack item) would be a pretty common usage, maybe
            }
        }
    }
}
