package com.klin.holoItems.collections.gen3.pekoraCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.interfaces.Placeable;
import com.klin.holoItems.utility.Task;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ShapedRecipe;

import java.util.HashSet;
import java.util.Set;

public class Detonator extends Item implements Placeable {
    public static final String name = "detonator";

    private static final Material material = Material.TNT;
    private static final int quantity = 1;
    private static final String lore =
            "Immediate chained explosions";
    private static final int durability = 0;
    public static final boolean stackable = false;
    private static final boolean shiny = true;
    public static final int cost = 0;

    public Detonator(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost);
    }

    public void registerRecipes(){
        ShapedRecipe recipe =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("aba","bab","aba");
        recipe.setIngredient('a', Material.GHAST_TEAR);
        recipe.setIngredient('b', Material.SOUL_SAND);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(BlockPlaceEvent event) {
        Location loc = event.getBlockPlaced().getLocation();
        Set<Location> temp = new HashSet<>();
        World world = loc.getWorld();
        TNTPrimed primed = world.spawn(loc, TNTPrimed.class);
        primed.setFuseTicks(0);
        temp.add(loc);
        new Task(HoloItems.getInstance(), 2, 1){
            int increment = 0;
            Set<Location> tnt = temp;
            public void run(){
                if(increment>120){
                    cancel();
                    return;
                }
                Set<Location> temp = new HashSet<>();
                for(Location primed : tnt){
                    for(Entity nearby : world.getNearbyEntities(primed, 5, 5, 5)) {
                        if (nearby instanceof TNTPrimed) {
                            TNTPrimed tnt = (TNTPrimed) nearby;
                            if(tnt.getFuseTicks()>0) {
                                tnt.setFuseTicks(0);
                                temp.add(tnt.getLocation());
                            }
                        }
                    }
                }
                tnt = temp;
                increment++;
            }
        };
    }
}