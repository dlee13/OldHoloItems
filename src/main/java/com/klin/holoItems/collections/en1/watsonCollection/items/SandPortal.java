package com.klin.holoItems.collections.en1.watsonCollection.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Container;
import org.bukkit.block.data.type.Dispenser;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.scheduler.BukkitRunnable;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.interfaces.Dispensable;
import com.klin.holoItems.interfaces.Placeable;
import com.klin.holoItems.utility.Utility;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;

public class SandPortal extends Item implements Dispensable, Placeable {
    public static final String name = "sandPortal";
    private static final Material[] types = new Material[] { Material.SAND, Material.RED_SAND, Material.GRAVEL,
            Material.WHITE_CONCRETE_POWDER, Material.ORANGE_CONCRETE_POWDER, Material.MAGENTA_CONCRETE_POWDER,
            Material.LIGHT_BLUE_CONCRETE_POWDER, Material.YELLOW_CONCRETE_POWDER, Material.LIME_CONCRETE_POWDER,
            Material.PINK_CONCRETE_POWDER, Material.GRAY_CONCRETE_POWDER, Material.LIGHT_GRAY_CONCRETE_POWDER,
            Material.CYAN_CONCRETE_POWDER, Material.PURPLE_CONCRETE_POWDER, Material.BLUE_CONCRETE_POWDER,
            Material.BROWN_CONCRETE_POWDER, Material.GREEN_CONCRETE_POWDER, Material.RED_CONCRETE_POWDER,
            Material.BLACK_CONCRETE_POWDER };

    private static final Material material = Material.SAND;
    private static final int quantity = 1;
    private static final String lore = "Dispense to create a physical block\n" +
            "Can also be placed infinitely";
    private static final int durability = 0;
    public static final boolean stackable = true;
    private static final boolean shiny = false;
    public static final int cost = 19200;

    public SandPortal() {
        super(name, material, quantity, lore, durability, stackable, shiny, cost);
    }

    @Override
    public void registerRecipes() {
        final var style = Style.style().decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GOLD).build();
        for (final var material : types) {
            final var materialName = material.key().value().split("_");
            final var builder = new StringBuilder();
            for (final var s : materialName) {
                builder.append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).append(' ');
            }
            builder.append("Portal");
            final var displayName = builder.toString();
            builder.setCharAt(0, Character.toLowerCase(builder.charAt(0)));
            var i = 0;
            for (final var s : materialName) {
                i += s.length();
                builder.deleteCharAt(i);
            }
            final var key = builder.toString();
            final var itemStack = Utility.process(name, material, quantity, lore, durability, shiny);
            final var itemMeta = itemStack.getItemMeta();
            itemMeta.displayName(Component.text(displayName, style));
            itemStack.setItemMeta(itemMeta);
            final var recipe = new ShapelessRecipe(new NamespacedKey(HoloItems.getInstance(), key), itemStack);
            recipe.addIngredient(Material.NETHERITE_BLOCK);
            recipe.addIngredient(material);
            recipe.setGroup(name);
            Bukkit.getServer().addRecipe(recipe);
        }
    }

    @Override
    public void ability(BlockDispenseEvent event) {
        event.setCancelled(true);
        final var block = event.getBlock();
        final var type = event.getItem().getType();
        final var facedBlock = block.getRelative(((Dispenser) block.getBlockData()).getFacing());
        if (facedBlock.getType().isAir()) {
            facedBlock.setType(type);
        } else if (facedBlock.getState() instanceof Container container) {
            container.getInventory().addItem(new ItemStack(type));
        }
    }

    @Override
    public void ability(BlockPlaceEvent event) {
        // event is cancelled in Events.java
        final var block = event.getBlockPlaced();
        final var type = block.getType();
        new BukkitRunnable() {
            @Override
            public void run() {
                block.setType(type);
            }
        }.runTask(HoloItems.getInstance());
    }
}
