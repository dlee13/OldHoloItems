package com.klin.holoItems.collections.misc.hiddenCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.gen5.lamyCollection.LamyCollection;
import com.klin.holoItems.interfaces.Shootable;
import com.klin.holoItems.utility.Task;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class FlashFreeze extends Item implements Shootable {
    public static final String name = "flashFreeze";
    public static final Set<Enchantment> accepted = null;

    private static final Material material = Material.SNOWBALL;
    private static final int quantity = 1;
    private static final String lore =
            "§6Ability" +"/n"+
                "Instant ice rink";
    private static final int durability = 0;
    public static final boolean stackable = false;
    private static final boolean shiny = true;

    public static final int cost = -1;
    public static final char key = '3';

    public FlashFreeze(){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost,
                ""+LamyCollection.key+key, key);
    }

    public void registerRecipes(){}

    public void cause(ProjectileLaunchEvent event, ItemStack item){
        Projectile snowball = event.getEntity();
        new Task(HoloItems.getInstance(), 1, 1) {
            int increment = 1200;

            public void run() {
                if(!snowball.isValid() || increment<0) {
                    ((Player) snowball.getShooter()).getInventory().addItem(item);
                    cancel();
                    return;
                }

                if(snowball.getLocation().getBlock().getType()==Material.WATER) {
                    Queue<Block> water = new LinkedList<>();
                    Set<Block> checked = new HashSet<>();
                    Block block = snowball.getLocation().getBlock();
                    water.add(block);
                    new Task(HoloItems.getInstance(), 0, 1){
                        int charge = 128;

                        public void run(){
                            if(water.isEmpty() || charge<0){
                                cancel();
                                return;
                            }

                            for(int i=0; i<4; i++) {
                                Block center = water.poll();
                                checked.add(center);
                                if(center==null || charge<0)
                                    break;

                                if(charge<80)
                                    center.setType(Material.ICE);
                                else if(charge<112)
                                    center.setType(Material.PACKED_ICE);
                                else
                                    center.setType(Material.BLUE_ICE);
                                charge--;
                                for (Block block : new Block[]{
                                        center.getRelative(BlockFace.UP),
                                        center.getRelative(BlockFace.NORTH),
                                        center.getRelative(BlockFace.SOUTH),
                                        center.getRelative(BlockFace.EAST),
                                        center.getRelative(BlockFace.WEST),
                                        center.getRelative(BlockFace.DOWN)}) {
                                    if (block.getType()==Material.WATER &&
                                            !checked.contains(block) && !water.contains(block))
                                        water.add(block);
                                }
                            }
                        }
                    };
                    cancel();
                    return;
                }

                increment--;
            }
        };
    }

    public void effect(ProjectileHitEvent event){}
}

//public class Condensation extends Item implements Shootable {
//    public static final String name = "condensation";
//    private static ItemStack copy;
//
//    public static final Set<Enchantment> accepted = null;
//
//    private static final Material material = Material.SNOWBALL;
//    private static final int quantity = 64;
//    private static final String lore =
//            "§6Ability" +"/n"+
//                    "The lava putter outer";
//    private static final int durability = 0;
//    public static final boolean stackable = true;
//    private static final boolean shiny = true;
//
//    public static final int cost = 0;
//    public static final char key = '1';
//
//    public Condensation(){
//        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost,
//                ""+LamyCollection.key+key, key);
//
//        copy = item.clone();
//        copy.setAmount(1);
//    }
//
//    public void registerRecipes(){
//        ShapedRecipe recipe =
//                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
//        recipe.shape("&%&","%*%","&%&");
//        recipe.setIngredient('*', Material.WET_SPONGE);
//        recipe.setIngredient('%', Material.SNOW_BLOCK);
//        recipe.setIngredient('&', Material.SNOWBALL);
//        recipe.setGroup(name);
//        Bukkit.getServer().addRecipe(recipe);
//    }
//
//    public void cause(ProjectileLaunchEvent event){
//        Projectile snowball = event.getEntity();
//        new Task(HoloItems.getInstance(), 1, 1) {
//            int increment = 1200;
//
//            public void run() {
//                if(!snowball.isValid() || increment<0) {
//                    ((Player) snowball.getShooter()).getInventory().addItem(copy);
//                    cancel();
//                    return;
//                }
//
//                if(snowball.getLocation().getBlock().getType()==Material.LAVA) {
//                    Queue<Block> lava = new LinkedList<>();
//                    Set<Block> checked = new HashSet<>();
//                    Block block = snowball.getLocation().getBlock();
//                    snowball.remove();
//
//                    lava.add(block);
//                    new Task(HoloItems.getInstance(), 0, 1){
//                        int charge = 128;
//
//                        public void run(){
//                            if(lava.isEmpty() || charge<0){
//                                cancel();
//                                return;
//                            }
//
//                            for(int i=0; i<4; i++) {
//                                Block center = lava.poll();
//                                checked.add(center);
//                                if(center==null || charge<0)
//                                    break;
//
//                                center.setType(Material.AIR);
//                                charge--;
//                                for (Block block : new Block[]{
//                                        center.getRelative(BlockFace.UP),
//                                        center.getRelative(BlockFace.NORTH),
//                                        center.getRelative(BlockFace.SOUTH),
//                                        center.getRelative(BlockFace.EAST),
//                                        center.getRelative(BlockFace.WEST),
//                                        center.getRelative(BlockFace.DOWN)}) {
//                                    if (block.getType()==Material.LAVA &&
//                                            !checked.contains(block) && !lava.contains(block))
//                                        lava.add(block);
//                                }
//                            }
//                        }
//                    };
//                    cancel();
//                    return;
//                }
//
//                increment--;
//            }
//        };
//    }
//
//    public void effect(ProjectileHitEvent event){}
//}
