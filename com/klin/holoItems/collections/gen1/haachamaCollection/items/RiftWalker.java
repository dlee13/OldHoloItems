package com.klin.holoItems.collections.gen1.haachamaCollection.items;

import com.klin.holoItems.abstractClasses.BatteryPack;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.collections.gen1.haachamaCollection.HaachamaCollection;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ShapelessRecipe;

public class RiftWalker extends BatteryPack {
    public static final String name = "riftWalker";

    private static final Material material = Material.POPPY;
    private static final String lore =
            "§6Ability" +"/n"+
            "Right click to consume a charge and" +"/n"+
            "teleport yourself a maximum of 8" +"/n"+
            "blocks in the direction you're facing";
    private static final int durability = 0;
    private static final boolean shiny = true;

    public static final Material content = Material.ENDER_PEARL;
    public static final double perFuel = 0.5;
    public static final int cap = 72;

    public static final int cost = -1;
    public static final char key = '0';

    public RiftWalker(){
        super(name, material, lore, durability, shiny, cost, content, perFuel, cap,
                ""+HaachamaCollection.key+key, key);
    }

    public void registerRecipes(){
        ShapelessRecipe recipe = new ShapelessRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.addIngredient(Material.APPLE);
        recipe.addIngredient(Material.ELYTRA);
        recipe.addIngredient(Material.FERMENTED_SPIDER_EYE);
        recipe.addIngredient(Material.ENDER_EYE);
        recipe.addIngredient(Material.KELP);
        recipe.addIngredient(Material.NETHERITE_SCRAP);
        recipe.addIngredient(Material.SALMON);
        recipe.addIngredient(Material.SCUTE);
        recipe.addIngredient(Material.SLIME_BALL);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void effect(PlayerInteractEvent event){
        event.setCancelled(true);
        int charge = Utility.deplete(event.getItem());
        if(charge==-1)
            return;

        Player player = event.getPlayer();
        Block block = player.getTargetBlockExact(8);
        double dist = 8;
        Location loc = player.getLocation();
        if(block!=null) {
            dist = block.getLocation().add(0.5, 0.5, 0.5).
                    distance(player.getLocation()) - 1.5;
        }
        if(dist<=1)
            return;
        loc.add(loc.getDirection().multiply(dist));
        if(!player.getWorld().getBlockAt(loc.clone().add(0, -1.5, 0)).isEmpty())
            loc.add(0, 1.5, 0);
        player.teleport(loc);
        if(charge==24 || charge==8 || charge==0)
            player.sendMessage("§7"+charge+" remaining");
    }
}
