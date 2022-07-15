package com.klin.holoItems.collections.en1.watsonCollection.items;

import com.klin.holoItems.abstractClasses.BatteryPack;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;

public class GemKnife extends BatteryPack {
    public static final String name = "gemKnife";

    private static final Map<Material, Material> COMPATIBLE_MATERIALS = Map.ofEntries(
        Map.entry(Material.COAL_ORE, Material.COAL),
        Map.entry(Material.DEEPSLATE_COAL_ORE, Material.COAL),
        Map.entry(Material.IRON_ORE, Material.IRON_NUGGET),
        Map.entry(Material.DEEPSLATE_IRON_ORE, Material.IRON_NUGGET),
        Map.entry(Material.GOLD_ORE, Material.GOLD_NUGGET),
        Map.entry(Material.DEEPSLATE_GOLD_ORE, Material.GOLD_NUGGET),
        Map.entry(Material.NETHER_GOLD_ORE, Material.GOLD_NUGGET),
        Map.entry(Material.GILDED_BLACKSTONE, Material.GOLD_NUGGET),
        Map.entry(Material.REDSTONE_ORE, Material.REDSTONE),
        Map.entry(Material.DEEPSLATE_REDSTONE_ORE, Material.REDSTONE),
        Map.entry(Material.LAPIS_ORE, Material.LAPIS_LAZULI),
        Map.entry(Material.DEEPSLATE_LAPIS_ORE, Material.LAPIS_LAZULI),
        Map.entry(Material.NETHER_QUARTZ_ORE, Material.QUARTZ),
        Map.entry(Material.GLOWSTONE, Material.GLOWSTONE_DUST)
    );

    private static final Material material = Material.EMERALD;
    private static final String lore = "Right click to consume a charge an\n" +
            "break a piece off any ore softer than\n" +
            "an emerald";
    private static final int durability = 0;
    private static final boolean shiny = true;

    public static final Material content = Material.EMERALD;
    public static final double perFuel = 2;
    public static final int cap = 1152;
    public static final int cost = 24000;

    public GemKnife() {
        super(name, material, lore, durability, shiny, cost, content, perFuel, cap);
    }

    public void registerRecipes() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("a", "b");
        recipe.setIngredient('a', Material.EMERALD_BLOCK);
        recipe.setIngredient('b', Material.STICK);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    // weird implementation for setting the charge, but i don't feel like fixing it until we reimplement it in the rewrite
    // it would be better to set perFuel to 1 and base the charge cost on what material is being farmed
    // and getting 2 charges per 1 emeralds is kind of unbalanced
    public void effect(PlayerInteractEvent event) {
        event.setCancelled(true);
        if (event.getClickedBlock() == null) {
            return;
        }

        final var block = event.getClickedBlock();
        final var material = COMPATIBLE_MATERIALS.get(block.getType());
        if (material == null) {
            return;
        }

        final var gemKnifeItemStack = event.getItem();
        int charge = Utility.deplete(gemKnifeItemStack, event.getPlayer(), cap);
        if (charge == -1) {
            return;
        }

        int amount;
        if (charge >= 63) {
            final var gemKnifeItemMeta = gemKnifeItemStack.getItemMeta();
            gemKnifeItemMeta.getPersistentDataContainer().set(Utility.pack, PersistentDataType.INTEGER, charge - 63);
            gemKnifeItemStack.setItemMeta(gemKnifeItemMeta);
            amount = material.getMaxStackSize();
        } else {
            amount = 1;
        }

        block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(material, amount));
    }
}
