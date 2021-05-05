package com.klin.holoItems.collections.en.inaCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.abstractClasses.BatteryPack;
import com.klin.holoItems.collections.en.inaCollection.InaCollection;
import com.klin.holoItems.interfaces.Placeable;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.*;
import org.bukkit.block.Block;
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

        Queue<Location> farmland = new LinkedList<>();
        Set<Location> checked = new HashSet<>();
        Location start = event.getBlockAgainst().getLocation();
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
                        world.dropItemNaturally(event.getPlayer().getLocation(),
                                new ItemStack(type, charge));
                    cancel();
                    return;
                }

                for(int i=0; i<2; i++) {
                    Location center = farmland.poll();
                    checked.add(center);
                    if(center==null || charge<0)
                        break;
                    Block air = world.getBlockAt(center.clone().add(0, 1, 0));
                    if(air.isEmpty()) {
                        air.setType(crop);
                        charge--;
                    }
                    else {
                        i--;
                        continue;
                    }
                    for (Location loc : new Location[]{
                            center.clone().add(1, 0, 0),
                            center.clone().add(-1, 0, 0),
                            center.clone().add(0, 0, 1),
                            center.clone().add(0, 0, -1)}) {
                        Block check = world.getBlockAt(loc);
                        if (check.getType()==soil &&
                                !checked.contains(loc) && !farmland.contains(loc))
                            farmland.add(loc);
                    }
                }
            }
        };
    }

    public void effect(PlayerInteractEvent event){}
}
