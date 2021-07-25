package com.klin.holoItems.collections.en.inaCollection.items;

import com.klin.holoItems.abstractClasses.BatteryPack;
import com.klin.holoItems.interfaces.Placeable;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.collections.en.inaCollection.InaCollection;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class CorruptedWheat extends BatteryPack implements Placeable {
    public static final String name = "corruptedWheat";
    private static final Material material = Material.WHEAT_SEEDS;
    private static final String lore =
            "§6Ability" +"/n"+
            "Spreads to surrounding farmland" +"/n"+
            "up to the quantity stored";
    private static final int durability = 0;
    private static final boolean shiny = true;

    public static final Material content = null;
    public static final double perCharge = 1;
    public static final int cap = 256;

    public static final int cost = 8860;
    public static final char key = '0';

    public CorruptedWheat(){
        super(name, material, lore, durability, shiny, cost, content, perCharge, cap,
                ""+InaCollection.key+key, key);
    }

    public void registerRecipes(){
        for(Material crop : Utility.ageable.keySet()) {
            item.setType(crop);
            String key = crop.toString().toLowerCase();
            int index = key.indexOf("_");
            if(index>=0){
                key = key.substring(0, index)+key.substring(index+1, index+2).toUpperCase()+
                        key.substring(index+2);
            }
            key = "corrupted"+key.substring(0, 1).toUpperCase()+key.substring(1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§6"+Utility.formatName(key));
            item.setItemMeta(meta);
            ShapedRecipe recipe =
                    new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), key), item);
            recipe.shape("%%%","%*%","%%%");
            recipe.setIngredient('*', crop);
            recipe.setIngredient('%', Material.LEATHER);
            recipe.setGroup(name);
            Bukkit.getServer().addRecipe(recipe);
        }
    }

    public void ability(BlockPlaceEvent event) {
        ItemStack item = event.getItemInHand();
        Material type = item.getType();
        Integer currCharge = item.getItemMeta().getPersistentDataContainer().
                get(Utility.pack, PersistentDataType.INTEGER);
        if(currCharge==null || currCharge==0)
            return;

        if(event.getPlayer().getGameMode()!=GameMode.CREATIVE)
            item.setAmount(item.getAmount()-1);

        Block start = event.getBlockAgainst();
        spread(start, type, currCharge, event.getPlayer().getLocation());
    }

    public static void spread(Block start, Material type, int currCharge, Location loc){
        Queue<Block> farmland = new LinkedList<>();
        Set<Block> checked = new HashSet<>();
        World world = start.getWorld();
        farmland.add(start);

        Material crop = Utility.ageable.get(type);
        Material ground = Material.FARMLAND;
        if(type==Material.NETHER_WART)
            ground = Material.SOUL_SAND;
        Material soil = ground;
        new Task(HoloItems.getInstance(), 0, 1){
            int charge = currCharge;

            public void run(){
                if(farmland.isEmpty() || charge<0){
                    if(charge>0)
                        world.dropItemNaturally(loc, new ItemStack(type, charge));
                    cancel();
                    return;
                }

                for(int i=0; i<2; i++) {
                    Block center = farmland.poll();
                    checked.add(center);
                    if(center==null || charge<0)
                        break;
                    Block air = center.getRelative(BlockFace.UP);
                    if(air.isEmpty()) {
                        air.setType(crop);
                        charge--;
                    }
                    else {
                        i--;
                        continue;
                    }
                    for (Block check : new Block[]{
                            center.getRelative(BlockFace.NORTH),
                            center.getRelative(BlockFace.SOUTH),
                            center.getRelative(BlockFace.EAST),
                            center.getRelative(BlockFace.WEST)}) {
                        if (check.getType()==soil &&
                                !checked.contains(check) && !farmland.contains(check))
                            farmland.add(check);
                    }
                }
            }
        };
    }

    public void effect(PlayerInteractEvent event){}
}
