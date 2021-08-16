package com.klin.holoItems.collections.misc.klinCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.misc.klinCollection.KlinCollection;
import com.klin.holoItems.interfaces.Interactable;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class KFPGuide extends Item implements Interactable {
    public static final String name = "kfpGuide";
    public static final Set<Enchantment> accepted = null;

    private static final Material material = Material.EGG;
    private static final int quantity = 1;
    private static final String lore =
            "Escape the usual room";
    private static final int durability = -1;
    private static final boolean stackable = false;
    private static final boolean shiny = false;

    public static final int cost = -1;
    public static final char key = '4';

    public KFPGuide(){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost,
                ""+KlinCollection.key+key, key);
    }

    public void registerRecipes(){}

    public void ability(PlayerInteractEvent event, Action action){
        event.setCancelled(true);
        if(action!=Action.RIGHT_CLICK_BLOCK)
            return;
        if(Utility.onCooldown(event.getItem()))
            return;
        Utility.cooldown(event.getItem(), 20);

        ItemStack item = event.getItem();
        Block block = event.getClickedBlock();
        ItemMeta meta = item.getItemMeta();
        Player player = event.getPlayer();

        int[] coords = meta.getPersistentDataContainer().get(Utility.data, PersistentDataType.INTEGER_ARRAY);
        if(coords==null || event.getBlockFace()!=BlockFace.UP){
            coords = new int[]{block.getX(), block.getY(), block.getZ()};
            player.sendMessage("Finish set to: "+coords[0]+" "+coords[1]+" "+coords[2]);
            meta.getPersistentDataContainer().set(Utility.data, PersistentDataType.INTEGER_ARRAY, coords);
            item.setItemMeta(meta);
            return;
        }

        World world = block.getWorld();
        Block finish = world.getBlockAt(coords[0], block.getY()+1, coords[2]);
        Map<Block, Block> blocks = new HashMap<>();
        Queue<Block> queue = new LinkedList<>();
        Block first = block.getRelative(BlockFace.UP);
        blocks.put(first, null);
        queue.add(first);
        boolean find = false;
        while(!queue.isEmpty() && !find){
            if(blocks.size()>32768)
                break;
            Block current = queue.poll();
            for(Block check : new Block[]{
                    current.getRelative(BlockFace.NORTH),
                    current.getRelative(BlockFace.SOUTH),
                    current.getRelative(BlockFace.EAST),
                    current.getRelative(BlockFace.WEST)}){
                if(!blocks.containsKey(check) && check.isEmpty()) {
                    queue.add(check);
                    blocks.put(check, current);
                    if(check.equals(finish)) {
                        find = true;
                        break;
                    }
                }
            }
        }
        if(!find){
            player.sendMessage("No valid path found");
            return;
        }
        Stack<Location> path = new Stack<>();
        Block previous = finish;
        while(previous!=null){
            path.add(previous.getLocation().add(0.5, 0, 0.5));
            previous = blocks.get(previous);
        }

        Chicken payload = world.spawn(block.getLocation().add(0.5, 1, 0.5), Chicken.class);
        payload.setGravity(false);
        new Task(HoloItems.getInstance(), 1, 1){
            Location current;
            Location next = path.pop();
            double xInterval;
            double zInterval;
            int increment = 10;

            public void run(){
                if(!payload.isValid() || path.isEmpty() && increment==10) {
                    payload.setFireTicks(1);
                    payload.damage(8);
                    cancel();
                    return;
                }

                if(payload.getNearbyEntities(2, 2, 2).contains(player)) {
                    Location loc;
                    if(increment==10) {
                        current = next;
                        next = path.pop();
                        xInterval = (next.getX()-current.getX())/10;
                        zInterval = (next.getZ()-current.getZ())/10;
                        loc = current;
                        increment = 1;
                    }
                    else {
                        loc = current.clone().add(xInterval*increment, 0, zInterval*increment);
                        increment++;
                    }
                    loc.setDirection(player.getLocation().add(0, 1, 0).subtract(payload.getLocation()).toVector());
                    payload.teleport(loc);
                }
            }
        };
    }
}
