package com.klin.holoItems.collections.misc.franCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.interfaces.Consumable;
import com.klin.holoItems.interfaces.Interactable;
import com.klin.holoItems.interfaces.customMobs.Combinable;
import com.klin.holoItems.interfaces.customMobs.Spawnable;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.*;

public class KFPGuide extends Item implements Interactable, Combinable, Spawnable, Consumable {
    public static final String name = "kfpGuide";

    private static final Material material = Material.COOKED_CHICKEN;
    private static final int quantity = 1;
    private static final String lore =
            "Escape the usual room";
    private static final int durability = -1;
    private static final boolean stackable = true;
    private static final boolean shiny = true;
    public static final int cost = -1;

    public KFPGuide(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost);
    }

    public void registerRecipes(){}

    public void ability(PlayerInteractEvent event, Action action){
        if(action!=Action.LEFT_CLICK_BLOCK)
            return;
        event.setCancelled(true);
        ItemStack item = event.getItem();
        if(Utility.onCooldown(item))
            return;
        Utility.cooldown(item, 10);

        Block block = event.getClickedBlock();
        String cords = block.getX() + " " + block.getZ();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(cords);
        item.setItemMeta(meta);

        event.getPlayer().sendMessage("Finish set to: " + cords);
    }

    public String processInfo(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if(meta.hasDisplayName())
            return ":"+meta.getDisplayName();
        else
            return ":";
    }

    public void ability(LivingEntity entity, String info) {
        Block first = entity.getLocation().getBlock();
        Block finish;
        try {
            String[] cords = info.split(" ");
            finish = entity.getWorld().getBlockAt(Integer.parseInt(cords[0]), first.getY(), Integer.parseInt(cords[1]));
        } catch (NumberFormatException e) {
            finish = null;
        }
        solve(first, finish, entity);
    }

    public void ability(PlayerItemConsumeEvent event, ItemStack item) {
        Player player = event.getPlayer();
        Block first = player.getLocation().getBlock();
        Block finish;
        ItemMeta meta = item.getItemMeta();
        if(meta.hasDisplayName()) {
            try {
                String[] cords = meta.getDisplayName().split(" ");
                finish = player.getWorld().getBlockAt(Integer.parseInt(cords[0]), first.getY(), Integer.parseInt(cords[1]));
            } catch (NumberFormatException e) {
                finish = null;
            }
        } else
            finish = null;
        solve(first, finish, player);
    }

    private void solve(Block first, Block finish, LivingEntity payload){
        Player temp = null;
        if(!(payload instanceof Player)) {
            for(Entity entity : payload.getNearbyEntities(2, 2, 2)){
                if(entity instanceof Player) {
                    temp = (Player) entity;
                    break;
                }
            }
        }
        Player player = temp;

        Map<Block, Block> blocks = new HashMap<>();
        Queue<Block> queue = new LinkedList<>();
        blocks.put(first, null);
        queue.add(first);
        boolean find = false;

        if (finish == null) {
            while (!queue.isEmpty() && !find) {
                if (blocks.size() > 32768)
                    break;
                Block current = queue.poll();
                for (BlockFace face : Utility.cardinal.keySet()) {
                    Block check = current.getRelative(face);
                    if (!blocks.containsKey(check) && check.isPassable()) {
                        queue.add(check);
                        blocks.put(check, current);
                        if (check.getRelative(BlockFace.DOWN).isEmpty()) {
                            finish = check;
                            find = true;
                            break;
                        }
                    }
                }
            }
        } else {
            while (!queue.isEmpty() && !find) {
                if (blocks.size() > 32768)
                    break;
                Block current = queue.poll();
                for (BlockFace face : Utility.cardinal.keySet()) {
                    Block check = current.getRelative(face);
                    if (!blocks.containsKey(check) && check.isPassable()) {
                        queue.add(check);
                        blocks.put(check, current);
                        if (check.equals(finish)) {
                            find = true;
                            break;
                        }
                    }
                }
            }
        }
        if(!find) {
            if(payload instanceof Player)
                payload.sendMessage("No valid path found");
            else
                payload.remove();
            if(player!=null)
                player.sendMessage("No valid path found");
            return;
        }

        Stack<Location> path = new Stack<>();
        Block previous = finish;
        while(previous!=null){
            path.add(previous.getLocation().add(0.5, 0, 0.5));
            previous = blocks.get(previous);
        }
        new Task(HoloItems.getInstance(), 1, 1){
            Location current;
            Location next = path.pop();
            double xInterval;
            double zInterval;
            int increment = 4;

            public void run(){
                if(!payload.isValid() || path.isEmpty() && increment==4) {
                    if(!(payload instanceof Player)) {
                        payload.setFireTicks(1);
                        payload.damage(1000);
                    }
                    cancel();
                    return;
                }

                if(player==null || payload.getNearbyEntities(2, 2, 2).contains(player)) {
                    Location loc;
                    if(increment==4) {
                        current = next;
                        next = path.pop();
                        xInterval = (next.getX()-current.getX())/4;
                        zInterval = (next.getZ()-current.getZ())/4;
                        loc = current;
                        increment = 1;
                    }
                    else {
                        loc = current.clone().add(xInterval*increment, 0, zInterval*increment);
                        increment++;
                    }
                    if(player==null)
                        loc.setDirection(new Vector(xInterval*increment, 0, zInterval*increment));
                    else
                        loc.setDirection(player.getEyeLocation().subtract(payload.getEyeLocation()).toVector());
                    payload.teleport(loc);
                }
            }
        };
    }
}
