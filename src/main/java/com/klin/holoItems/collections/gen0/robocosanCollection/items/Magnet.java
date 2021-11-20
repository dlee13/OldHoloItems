package com.klin.holoItems.collections.gen0.robocosanCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.abstractClasses.Enchant;
import com.klin.holoItems.interfaces.Extractable;
import com.klin.holoItems.utility.Utility;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.data.type.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.*;

import java.util.*;

public class Magnet extends Enchant implements Extractable {
    public static final String name = "magnet";
    private static final Set<Material> exception = Set.of(Material.WHITE_BED, Material.ORANGE_BED, Material.MAGENTA_BED, Material.LIGHT_BLUE_BED, Material.YELLOW_BED, Material.LIME_BED, Material.PINK_BED, Material.GRAY_BED, Material.LIGHT_GRAY_BED, Material.CYAN_BED, Material.PURPLE_BED, Material.BLUE_BED, Material.BROWN_BED, Material.GREEN_BED, Material.RED_BED, Material.BLACK_BED, Material.ACACIA_DOOR, Material.BIRCH_DOOR, Material.IRON_DOOR, Material.DARK_OAK_DOOR, Material.CRIMSON_DOOR, Material.JUNGLE_DOOR, Material.OAK_DOOR, Material.SPRUCE_DOOR, Material.WARPED_DOOR);

    public static final Set<Enchantment> accepted = Set.of(Enchantment.DURABILITY, Enchantment.MENDING, Enchantment.SILK_TOUCH);
    public static final Set<String> acceptedIds = null;
    public static final Set<Material> acceptedTypes = new HashSet<>();
    public static final int expCost = 39;

    private static final Material material = Material.WOODEN_PICKAXE;
    private static final String lore =
            "Collect block drops immediately\n"+
            "upon breaking";
    private static final int durability = 3136;
    private static final boolean shiny = false;
    public static final int cost = 0;

    public Magnet(){
        super(name, accepted, material, lore, durability, shiny, cost, acceptedIds, acceptedTypes, expCost);
        acceptedTypes.addAll(Utility.pickaxes);
        acceptedTypes.addAll(Utility.axes);
        acceptedTypes.addAll(Utility.shovels);
        acceptedTypes.addAll(Utility.hoes);
    }

    public void registerRecipes(){
        ShapedRecipe recipe0 = new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"0"), item);
        recipe0.shape("abc","dac","aef");
        recipe0.setIngredient('a', Material.IRON_BLOCK);
        recipe0.setIngredient('b', Material.COMPARATOR);
        recipe0.setIngredient('c', Material.HOPPER);
        recipe0.setIngredient('d', Material.REDSTONE);
        recipe0.setIngredient('e', Material.REDSTONE_TORCH);
        recipe0.setIngredient('f', Material.BARREL);
        recipe0.setGroup(name);
        Bukkit.getServer().addRecipe(recipe0);

        ShapedRecipe recipe1 = new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"1"), item);
        recipe1.shape("cba","cad","fea");
        recipe1.setIngredient('a', Material.IRON_BLOCK);
        recipe1.setIngredient('b', Material.COMPARATOR);
        recipe1.setIngredient('c', Material.HOPPER);
        recipe1.setIngredient('d', Material.REDSTONE);
        recipe1.setIngredient('e', Material.REDSTONE_TORCH);
        recipe1.setIngredient('f', Material.BARREL);
        recipe1.setGroup(name);
        Bukkit.getServer().addRecipe(recipe1);
    }

    public void ability(BlockBreakEvent event){
        event.setDropItems(false);
        Player player = event.getPlayer();
        PlayerInventory inv = player.getInventory();
        World world = null;
        Block block = event.getBlock();
        Location loc = block.getLocation();
        Material type = block.getType();
        if(exception.contains(type)){
            ItemStack item = new ItemStack(type);
            if(!inv.addItem(item).isEmpty())
                loc.getWorld().dropItemNaturally(loc, item);
            return;
        }
        List<ItemStack> items = new ArrayList<>(block.getDrops(inv.getItemInMainHand(), player));
        BlockState state = block.getState();
        if(state instanceof Container) {
            Inventory container = ((Container) state).getInventory();
            if(container.getHolder() instanceof DoubleChest){
                Chest chest = (Chest) block.getBlockData();
                if(chest.getType()==Chest.Type.LEFT)
                    items.addAll(Arrays.asList(((DoubleChestInventory) container).getRightSide().getStorageContents()));
                else
                    items.addAll(Arrays.asList(((DoubleChestInventory) container).getLeftSide().getStorageContents()));
            } else
                items.addAll(Arrays.asList(container.getStorageContents()));
            items.removeIf(Objects::isNull);
        }
        for(ItemStack item : items){
            if(world!=null)
                world.dropItemNaturally(loc, item);
            else if(!inv.addItem(item).isEmpty()){
                world = loc.getWorld();
                world.dropItemNaturally(loc, item);
            }
        }
    }
}
