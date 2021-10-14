package com.klin.holoItems.collections.gen5.lamyCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.abstractClasses.BatteryPack;
import com.klin.holoItems.collections.gen5.lamyCollection.LamyCollection;
import com.klin.holoItems.interfaces.Placeable;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class FrostLayer extends BatteryPack implements Placeable {
    public static final String name = "frostLayer";
    private static final ItemStack snowBlock = new ItemStack(Material.SNOW_BLOCK);

    private static final Material material = Material.SNOW;
    private static final String lore =
            "Spreads snow";
    private static final int durability = 0;
    private static final boolean shiny = true;

    public static final Material content = Material.SNOW_BLOCK;
    public static final double perCharge = 1;
    public static final int cap = 576;
    public static final int cost = 0;

    public FrostLayer(){
        super(name, material, lore, durability, shiny, cost, content, perCharge, cap);
    }

    public void registerRecipes(){
        ShapedRecipe recipe =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape(" % ","%*%","&&&");
        recipe.setIngredient('*', Material.HEART_OF_THE_SEA);
        recipe.setIngredient('%', Material.STICK);
        recipe.setIngredient('&', Material.BLUE_ICE);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(BlockPlaceEvent event) {
        ItemStack item = event.getItemInHand();
        ItemMeta meta = item.getItemMeta();
        Integer currCharge = meta.getPersistentDataContainer().
                get(Utility.pack, PersistentDataType.INTEGER);
        if(currCharge==null || currCharge==0)
            return;
        Block start = event.getBlockAgainst();
        Material material = item.getType();
        if(material==Material.SNOW)
            material = Material.SNOW_BLOCK;
        if(start.getType()!=material)
            return;
        if(event.getPlayer().getGameMode()!=GameMode.CREATIVE) {
            meta.getPersistentDataContainer().set(Utility.pack, PersistentDataType.INTEGER, 0);
            item.setItemMeta(meta);
        }

        Queue<Block> platform = new LinkedList<>();
        Set<Block> checked = new HashSet<>();
        platform.add(start);

        Player player = event.getPlayer();
        Material type = material;
        new Task(HoloItems.getInstance(), 0, 1){
            int charge = currCharge;

            public void run(){
                if(platform.isEmpty() || charge<0){
                    if(event.getPlayer().getGameMode()!=GameMode.CREATIVE) {
                        meta.getPersistentDataContainer().set(Utility.pack, PersistentDataType.INTEGER, charge);
                        item.setItemMeta(meta);
                    }
                    cancel();
                    return;
                }

                for(int i=0; i<4; i++) {
                    Block center = platform.poll();
                    checked.add(center);
                    if(center==null || charge<0)
                        break;

                    for (Block block : new Block[]{
                            center.getRelative(BlockFace.NORTH),
                            center.getRelative(BlockFace.SOUTH),
                            center.getRelative(BlockFace.EAST),
                            center.getRelative(BlockFace.WEST)}) {
                        if ((block.getType().isAir() || block.getType()==type) &&
                                !checked.contains(block) && !platform.contains(block)) {
                            BlockPlaceEvent event = new BlockPlaceEvent(block, center.getState(), center,
                                    snowBlock, player, true, EquipmentSlot.HAND);
                            Bukkit.getServer().getPluginManager().callEvent(event);
                            if(!event.isCancelled() && block.getType()!=type) {
                                block.setType(type);
                                charge--;
                            }
                            platform.add(block);
                        }
                    }
                }
            }
        };
    }

    public void effect(PlayerInteractEvent event){}
}