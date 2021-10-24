package com.klin.holoItems.collections.misc.franCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.interfaces.customMobs.Combinable;
import com.klin.holoItems.interfaces.customMobs.Spawnable;
import com.klin.holoItems.interfaces.customMobs.Targetable;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NinjaCloak extends Item implements Combinable, Spawnable {
    public static final String name = "ninjaCloak";

    private static final Material material = Material.DRIED_KELP;
    private static final int quantity = 1;
    private static final String lore =
            "Drop from above";
    private static final int durability = 0;
    public static final boolean stackable = true;
    private static final boolean shiny = true;
    public static final int cost = -1;

    public NinjaCloak(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost);
    }

    public void registerRecipes(){}

    public void ability(LivingEntity entity, String info) {
        entity.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 99999, 127));
        entity.setCustomName("Dinnerbone");
        new Task(HoloItems.getInstance(), 1, 1){
            int increment = 0;
            public void run(){
                if(increment>=1200){
                    entity.remove();
                    cancel();
                    return;
                }
                for(Entity nearby : entity.getNearbyEntities(0.7, 96, 0.7)) {
                    if(!(nearby instanceof Player))
                        continue;
                    entity.removePotionEffect(PotionEffectType.LEVITATION);
                    new Task(HoloItems.getInstance(), 1, 1){
                        int increment = 0;
                        boolean parachute = false;
                        public void run(){
                            if(increment>=120 || entity.isOnGround()) {
                                entity.setCustomName(null);
                                cancel();
                                return;
                            }
                            increment++;
                            if(parachute)
                                return;
                            Block block = entity.getLocation().getBlock().getRelative(BlockFace.DOWN);
                            Block relative = block.getRelative(BlockFace.DOWN);
                            for(Block ground : new Block[]{block, relative, relative.getRelative(BlockFace.DOWN)}) {
                                if (!ground.isPassable()) {
                                    entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 2, 1));
                                    parachute = true;
                                    System.out.println("SLOW");
                                    return;
                                }
                            }
                        }
                    };
                    cancel();
                    return;
                }
                increment++;
            }
        };
    }
}