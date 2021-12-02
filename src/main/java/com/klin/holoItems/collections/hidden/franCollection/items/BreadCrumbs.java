package com.klin.holoItems.collections.hidden.franCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.interfaces.customMobs.Combinable;
import com.klin.holoItems.interfaces.customMobs.Spawnable;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashSet;
import java.util.Set;

public class BreadCrumbs extends Item implements Combinable, Spawnable {
    public static final String name = "breadCrumbs";
    public static final Set<Enchantment> accepted = null;

    private static final Material material = Material.BREAD;
    private static final int quantity = 1;
    private static final String lore =
            "Rename to set HP";
    private static final int durability = 0;
    public static final boolean stackable = true;
    private static final boolean shiny = true;
    public static final int cost = -1;

    public BreadCrumbs(){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost);
    }

    public void registerRecipes(){}

    public String processInfo(ItemStack item) {
        return ":"+item.getItemMeta().getDisplayName();
    }

    public void ability(LivingEntity entity, String info) {
        int speed;
        try{
            speed = Math.max(1, Math.min(Integer.parseInt(info.substring(0, Math.max(info.indexOf(" "), 1))), 32));
        } catch(NumberFormatException e){ return; }
        Material path = Material.getMaterial(info.substring(info.indexOf(" ")+1));
        if(path==null) {
            entity.damage(210);
            return;
        }
        entity.setAI(false);
        entity.getPersistentDataContainer().set(Utility.key, PersistentDataType.STRING, name);

        Location location = entity.getLocation();
        Set<Block> visited = new HashSet<>();
        new Task(HoloItems.getInstance(), 0, 1){
            Block curr = location.getWorld().getBlockAt(location).getRelative(BlockFace.DOWN);
            Block next;
            int increment = 0;
            int increments = 0;
            Location loc;
            
            public void run(){
                if(increment==0) {
                    visited.add(curr);
                    for (BlockFace face : Utility.cardinal.keySet()) {
                        Block block = curr.getRelative(face);
                        if (block.getType() == path && !visited.contains(block)) {
                            next = block;
                            loc = curr.getLocation().add(0.5, 1, 0.5).setDirection(Utility.cardinal.get(face));
                            entity.teleport(loc);
                        }
                    }
                }
                else if(increment==speed){
                    curr = next;
                    next = null;
                    increment = 0;
                    increments++;
                    return;
                }

                if(increments>1000 || next==null || !entity.isValid()){
                    if(entity.isValid()) {
                        entity.setAI(true);
                        entity.damage(1000);
                    }
                    cancel();
                    return;
                }

                entity.teleport(loc.clone().add(loc.getDirection().multiply((double) increment/speed)));
                increment++;
            }
        };
    }
}
