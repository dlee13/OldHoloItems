package com.klin.holoItems.collections.gen3.flareCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.abstractClasses.Enchant;
import com.klin.holoItems.collections.gen0.suiseiCollection.items.Comet;
import com.klin.holoItems.collections.gen3.flareCollection.FlareCollection;
import com.klin.holoItems.interfaces.Extractable;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Splinter extends Enchant implements Extractable {
    public static final String name = "splinter";
    public static final Set<Enchantment> accepted = Set.of(Enchantment.DURABILITY, Enchantment.MENDING, Enchantment.SILK_TOUCH);
    public static final Set<String> acceptedIds = Stream.of(Comet.name).collect(Collectors.toCollection(HashSet::new));
    public static final Set<Material> acceptedTypes = Utility.axes;
    public static final int expCost = 39;

    private static final Material material = Material.WOODEN_AXE;
    private static final String lore =
            "Split apart logs, leaves, or mushrooms\n"+
            "adjacent to the one you broke";
    private static final int durability = 3136;
    private static final boolean shiny = false;
    public static final int cost = 4200;

    public Splinter(){
        super(name, accepted, material, lore, durability, shiny, cost, acceptedIds, acceptedTypes, expCost);
    }

    public void registerRecipes(){
        ShapedRecipe recipe0 =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"0"), item);
        recipe0.shape("**","*&"," %");
        recipe0.setIngredient('*', Material.SCUTE);
        recipe0.setIngredient('&', Material.CONDUIT);
        recipe0.setIngredient('%', Material.STICK);
        recipe0.setGroup(name);
        Bukkit.getServer().addRecipe(recipe0);

        ShapedRecipe recipe1 =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"1"), item);
        recipe1.shape("**","&*","% ");
        recipe1.setIngredient('*', Material.SCUTE);
        recipe1.setIngredient('&', Material.CONDUIT);
        recipe1.setIngredient('%', Material.STICK);
        recipe1.setGroup(name);
        Bukkit.getServer().addRecipe(recipe1);
    }

    public void ability(BlockBreakEvent event){
        Block block = event.getBlock();
        Material wood = block.getType();
        if(!Utility.logs.contains(wood))
            return;

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if(Utility.onCooldown(item))
            return;
        Utility.cooldown(item);
        Utility.addDurability(item, 0.5, player);

        Queue<Block> log = new LinkedList<>();
        Set<Block> checked = new HashSet<>();
        log.add(block);
        World world = player.getWorld();
        new Task(HoloItems.getInstance(), 0, 1){
            int charge = 32;
            public void run(){
                if(log.isEmpty() || charge<0){
                    Utility.removeCooldown(item);
                    cancel();
                    return;
                }
                for(int i=0; i<4; i++) {
                    Block center = log.poll();
                    //NECESSARY: loops if center can't broken
                    checked.add(center);
                    if(center==null || charge<0)
                        break;
                    player.breakBlock(center);
                    charge--;
                    Location loc = center.getLocation();
                    for (int j=-1; j<=1; j++) {
                        for(int k=-1; k<=1; k++) {
                            for(int l=-1; l<=1; l++) {
                                Block block = world.getBlockAt(loc.clone().add(j, k, l));
                                if (block.getType()==wood && !checked.contains(block) && !log.contains(block))
                                    log.add(block);
                            }
                        }
                    }
                }
            }
        };
    }
}
