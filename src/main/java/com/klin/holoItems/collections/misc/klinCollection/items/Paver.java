package com.klin.holoItems.collections.misc.klinCollection.items;

import com.klin.holoItems.Collections;
import com.klin.holoItems.abstractClasses.SlidingPack;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.misc.klinCollection.KlinCollection;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.persistence.PersistentDataType;

import java.util.Set;

public class Paver extends SlidingPack {
    public static final String name = "paver";
    public static final Set<Enchantment> accepted = null;

    private static final Material material = Material.NETHERITE_SWORD;
    private static final String lore =
            "brrrrrrrrrr";
    private static final int durability = -1;
    public static final boolean stackable = false;
    private static final boolean shiny = false;
    public static final int cost = -1;

    private static final ItemStack content = new ItemStack(Material.DARK_OAK_WOOD);

    public Paver(){
        super(name, accepted, material, lore, durability, shiny, cost, content);
    }

    public void registerRecipes(){}

    protected void effect(PlayerInteractEvent event){
        ItemStack paver = event.getItem();
        if(Utility.onCooldown(paver))
            return;
        Utility.cooldown(paver, 20);

        Player player = event.getPlayer();
        PlayerInventory inv = player.getInventory();
        int heldItemSlot = inv.getHeldItemSlot();
        if(!event.getItem().equals(inv.getItem(heldItemSlot)))
            return;

        BlockFace direction = player.getFacing();
        int x = 0;
        int z = 0;
        switch(direction){
            case NORTH:
                z = -1;
                break;
            case SOUTH:
                z = 1;
                break;
            case EAST:
                x = 1;
                break;
            case WEST:
                x = -1;
                break;
            default:
                player.sendMessage("Face a cardinal direction: "+direction);
                return;
        }
        Block block = event.getClickedBlock();
        if(block==null)
            return;
        World world = block.getWorld();
        Integer recurse = paver.getItemMeta().getPersistentDataContainer().
                get(Utility.pack, PersistentDataType.INTEGER);
        if(recurse==null)
            recurse = 1;
        else
            recurse *= 2;
        for(int j=0; j<recurse; j++) {
            for (int i = heldItemSlot + 1; i < 9; i++) {
                ItemStack item = inv.getItem(i);
                if (item == null || item.getItemMeta() == null)
                    break;
                String id = item.getItemMeta().getPersistentDataContainer().
                        get(Utility.key, PersistentDataType.STRING);
                if (id == null)
                    break;
                Item generic = Collections.findItem(id);
                if (!(generic instanceof Trowel))
                    break;

                ((Trowel) generic).ability(new PlayerInteractEvent(player, Action.RIGHT_CLICK_BLOCK,
                        item, block, BlockFace.UP, EquipmentSlot.HAND), Action.RIGHT_CLICK_BLOCK);
                Location loc = block.getLocation().add(x, 0, z);
                Chunk chunk = world.getChunkAt(loc);
                if (!chunk.isLoaded())
                    chunk.load();
                block = world.getBlockAt(loc);
            }
        }
    }
}
