package com.klin.holoItems.collections.en1.inaCollection.items;

import com.klin.holoItems.abstractClasses.BatteryPack;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.utility.Utility;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class WateringCan extends BatteryPack {
    public static final String name = "wateringCan";

    private static final Material material = Material.BUCKET;
    private static final String lore =
            "Right click to consume a charge and\n" +
            "advance the growth stage of all\n" +
            "vegetation around you";
    private static final int durability = 0;
    private static final boolean shiny = true;

    public static final Material content = Material.BONE_MEAL;
    public static final double perFuel = 1;
    public static final int cap = 576;
    public static final int cost = 4430;

    public WateringCan(){
        super(name, material, lore, durability, shiny, cost, content, perFuel, cap);
    }

    public void registerRecipes(){
        ShapedRecipe recipe0 = new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"0"), item);
        recipe0.shape(" * ","/&%"," % ");
        recipe0.setIngredient('*', Material.IRON_TRAPDOOR);
        recipe0.setIngredient('/', Material.TRIPWIRE_HOOK);
        recipe0.setIngredient('&', Material.HOPPER);
        recipe0.setIngredient('%', Material.IRON_INGOT);
        recipe0.setGroup(name);
        Bukkit.getServer().addRecipe(recipe0);

        ShapedRecipe recipe1 = new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"1"), item);
        recipe1.shape(" * ","%&/"," % ");
        recipe1.setIngredient('*', Material.IRON_TRAPDOOR);
        recipe1.setIngredient('/', Material.TRIPWIRE_HOOK);
        recipe1.setIngredient('&', Material.HOPPER);
        recipe1.setIngredient('%', Material.IRON_INGOT);
        recipe1.setGroup(name);
        Bukkit.getServer().addRecipe(recipe1);
    }

    public int ability(Inventory inv, ItemStack item, Player player){
        int by = super.ability(inv, item, player);
        if (by>0)
            item.setType(Material.WATER_BUCKET);
        else
            item.setType(Material.BUCKET);
        return by;
    }

    public void effect(PlayerInteractEvent event){
        event.setCancelled(true);
        Player player = event.getPlayer();
        int charge = Utility.deplete(event.getItem(), player, cap);
        if(charge==-1)
            return;
        World world = player.getWorld();
        int farmland = world.getBlockAt(player.getLocation()).isEmpty() ? 0 : 1;
        Location center = player.getLocation();
        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                Location loc = center.clone().add(i, farmland, j);
                Block block = world.getBlockAt(loc);
                BlockData blockData = block.getBlockData();
                if (blockData instanceof Ageable) {
                    Ageable crop = (Ageable) blockData;
                    int age = crop.getAge();
                    if(age==crop.getMaximumAge())
                        continue;
                    crop.setAge(crop.getAge()+1);
                    block.setBlockData(crop);

                    world.spawnParticle(Particle.VILLAGER_HAPPY, loc, 1, 0.5, 0.5, 0.5);
                }
            }
        }
        world.spawnParticle(Particle.WATER_SPLASH, player.getLocation(), 100, 2.5, 0.25, 2.5);
        if(charge==0)
            event.getItem().setType(Material.BUCKET);
    }
}

