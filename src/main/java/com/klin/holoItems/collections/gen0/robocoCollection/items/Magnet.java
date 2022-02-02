package com.klin.holoItems.collections.gen0.robocoCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.abstractClasses.Enchant;
import com.klin.holoItems.collections.gen0.suiseiCollection.items.Comet;
import com.klin.holoItems.interfaces.Extractable;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.*;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Magnet extends Enchant implements Extractable {
    public static final String name = "magnet";

    public static final Set<Enchantment> accepted = Set.of(Enchantment.DURABILITY, Enchantment.MENDING, Enchantment.SILK_TOUCH);
    public static final Set<String> acceptedIds = Stream.of(Comet.name).collect(Collectors.toCollection(HashSet::new));
    public static final Set<Material> acceptedTypes = new HashSet<>();
    public static final int expCost = 39;

    private static final Material material = Material.WOODEN_PICKAXE;
    private static final String lore =
            "Collect block drops immediately\n"+
            "upon breaking";
    private static final int durability = 3136;
    private static final boolean shiny = false;
    public static final int cost = 0;

    public Magnet() {
        super(name, accepted, material, lore, durability, shiny, cost, acceptedIds, acceptedTypes, expCost);
        acceptedTypes.addAll(Utility.pickaxes);
        acceptedTypes.addAll(Utility.axes);
        acceptedTypes.addAll(Utility.shovels);
        acceptedTypes.addAll(Utility.hoes);
    }

    public void registerRecipes() {
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

    public void ability(BlockBreakEvent event) {
        final var location = event.getBlock().getLocation().toCenterLocation();
        final var player = event.getPlayer();

        new BukkitRunnable() {
            public void run() {
                final var items = location.getNearbyEntitiesByType(Item.class, 1.5, item -> item.canPlayerPickup());
                final var itemStacks = items.stream().map(item -> item.getItemStack()).toArray(ItemStack[]::new);
                final var excess = player.getInventory().addItem(itemStacks);
                excess.values().forEach(itemStack -> player.getWorld().dropItemNaturally(player.getLocation(), itemStack));
                items.forEach(item -> item.remove());
            }
        }.runTask(HoloItems.getInstance());
    }
}
