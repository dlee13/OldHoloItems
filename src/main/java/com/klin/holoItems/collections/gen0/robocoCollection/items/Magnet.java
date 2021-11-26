package com.klin.holoItems.collections.gen0.robocoCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.abstractClasses.Enchant;
import com.klin.holoItems.interfaces.Extractable;
import com.klin.holoItems.utility.Utility;
import org.bukkit.*;
import org.bukkit.block.*;
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
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("aab","cde","fgd");
        recipe.setIngredient('a', Material.POWERED_RAIL);
        recipe.setIngredient('b', Material.IRON_PICKAXE);
        recipe.setIngredient('c', Material.HOPPER);
        recipe.setIngredient('d', Material.IRON_BLOCK);
        recipe.setIngredient('e', Material.REDSTONE);
        recipe.setIngredient('f', Material.DROPPER);
        recipe.setIngredient('g', Material.COMPARATOR);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);

        Utility.mirror(recipe, name, item);
    }

    public void ability(BlockBreakEvent event){
        if(!event.isDropItems())
            return;
        Block block = event.getBlock();
        Material type = block.getType();
        if(block.getType()==Material.PLAYER_HEAD)
            return;
        event.setDropItems(false);
        Player player = event.getPlayer();
        PlayerInventory inv = player.getInventory();
        World world = player.getWorld();
        Location loc = block.getLocation();
        if(exception.contains(type)){
            ItemStack item = new ItemStack(type);
            if(!inv.addItem(item).isEmpty())
                world.dropItemNaturally(loc, item);
            return;
        }
        List<ItemStack> items = new ArrayList<>(block.getDrops(inv.getItemInMainHand(), player));
        BlockState state = block.getState();
        if(state instanceof Container && !(state instanceof ShulkerBox)) {
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
            if(!inv.addItem(item).isEmpty())
                world.dropItemNaturally(loc, item);
        }
    }
}
