package com.klin.holoItems.collections.gen3.flareCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.abstractClasses.Enchant;
import com.klin.holoItems.collections.gen3.flareCollection.FlareCollection;
import com.klin.holoItems.interfaces.Extractable;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Statistic;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Splinter extends Enchant implements Extractable {
    public static final String name = "splinter";
    public static final Set<Enchantment> accepted = new HashSet<Enchantment>(){{
        add(Enchantment.DURABILITY);
        add(Enchantment.MENDING);
    }};
    public static final Set<String> acceptedIds = new HashSet<String>(){{
        add("a2");
    }};
    public static final Set<Material> acceptedTypes = Utility.axes;
    public static final int expCost = 39;

    private static final Material material = Material.WOODEN_AXE;
    private static final String lore =
            "§6Ability" +"/n"+
                "Split apart logs of the same type" +"/n"+
                "adjacent to the one you broke";
    private static final int durability = 3136;
    private static final boolean shiny = false;

    public static final int cost = 4200;
    public static final char key = '1';

    public Splinter(){
        super(name, accepted, material, lore, durability, shiny, cost,
                ""+FlareCollection.key+key, key, acceptedIds, acceptedTypes, expCost);
    }

    public void registerRecipes(){
        ShapedRecipe recipe0 =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"0"), item);
        recipe0.shape("** ","*& "," % ");
        recipe0.setIngredient('*', Material.SCUTE);
        recipe0.setIngredient('&', Material.CONDUIT);
        recipe0.setIngredient('%', Material.STICK);
        recipe0.setGroup(name);
        Bukkit.getServer().addRecipe(recipe0);

        ShapedRecipe recipe1 =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"1"), item);
        recipe1.shape("** ","&* ","%  ");
        recipe1.setIngredient('*', Material.SCUTE);
        recipe1.setIngredient('&', Material.CONDUIT);
        recipe1.setIngredient('%', Material.STICK);
        recipe1.setGroup(name);
        Bukkit.getServer().addRecipe(recipe1);

        ShapedRecipe recipe2 =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"2"), item);
        recipe2.shape("** ","*& "," % ");
        recipe2.setIngredient('*', Material.SCUTE);
        recipe2.setIngredient('&', Material.CONDUIT);
        recipe2.setIngredient('%', Material.STICK);
        recipe2.setGroup(name);
        Bukkit.getServer().addRecipe(recipe2);

        ShapedRecipe recipe3 =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"3"), item);
        recipe3.shape(" **"," &*"," % ");
        recipe3.setIngredient('*', Material.SCUTE);
        recipe3.setIngredient('&', Material.CONDUIT);
        recipe3.setIngredient('%', Material.STICK);
        recipe3.setGroup(name);
        Bukkit.getServer().addRecipe(recipe3);
    }

    public void ability(BlockBreakEvent event){
        Block block = event.getBlock();
        Material wood = block.getType();
        if(!Utility.logs.contains(wood))
            return;

        Queue<Block> log = new LinkedList<>();
        Set<Block> checked = new HashSet<>();
        log.add(block);

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        Utility.addDurability(item, 0.5, player);
        new Task(HoloItems.getInstance(), 0, 1){
            int charge = 32;

            public void run(){
                if(log.isEmpty() || charge<0){
                    cancel();
                    return;
                }

                for(int i=0; i<4; i++) {
                    Block center = log.poll();
                    checked.add(center);
                    if(center==null || charge<0)
                        break;

                    center.breakNaturally();
                    Utility.addDurability(item, -1, player);
                    player.setStatistic(Statistic.MINE_BLOCK, wood,
                            player.getStatistic(Statistic.MINE_BLOCK, wood)+1);
                    charge--;
                    for (Block block : new Block[]{
                            center.getRelative(BlockFace.UP),
                            center.getRelative(BlockFace.NORTH),
                            center.getRelative(BlockFace.SOUTH),
                            center.getRelative(BlockFace.EAST),
                            center.getRelative(BlockFace.WEST),
                            center.getRelative(BlockFace.DOWN)}) {
                        if (block.getType()==wood &&
                                !checked.contains(block) && !log.contains(block))
                            log.add(block);
                    }
                }
            }
        };
    }
}
