package com.klin.holoItems.collections.misc.achanCollection.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.ShulkerBox;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.jetbrains.annotations.NotNull;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.interfaces.Clickable;
import com.klin.holoItems.interfaces.Closeable;
import com.klin.holoItems.interfaces.Holdable;
import com.klin.holoItems.interfaces.Placeable;

public class ShoulderBagStrap extends Item implements Placeable, Holdable, Closeable, Clickable {
    public static final String name = "shoulderBagStrap";

    private static final Material material = Material.CHAIN;
    private static final int quantity = 1;
    private static final String lore = "Easy carry shulkers";
    private static final int durability = 0;
    public static final boolean stackable = false;
    private static final boolean shiny = false;
    public static final int cost = 0;

    public ShoulderBagStrap() {
        super(name, material, quantity, lore, durability, stackable, shiny, cost);
    }

    public void registerRecipes() {
        final var recipe = new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape(" ab", "ab ", " ab");
        recipe.setIngredient('a', Material.IRON_INGOT);
        recipe.setIngredient('b', Material.LEATHER);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    @Override
    public void ability(BlockPlaceEvent event) {
        final var itemStack = event.getItemInHand();
        if (itemStack.getItemMeta() instanceof BlockStateMeta blockStateMeta
                && blockStateMeta.getBlockState() instanceof ShulkerBox shulkerBox) {
            event.setCancelled(true);
            final var shoulderBag = new ShoulderBag(itemStack.getType(), shulkerBox);
            final var player = event.getPlayer();
            player.getInventory().removeItem(itemStack);
            player.openInventory(shoulderBag.getInventory());
        }
    }

    @Override
    public void ability(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() instanceof ShoulderBag shoulderBag) {
            final var itemStack = shoulderBag.getItemStack();
            final var player = event.getPlayer();
            final var excess = player.getInventory().addItem(itemStack);
            if (!excess.isEmpty()) {
                player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
            }
        }
    }

    @Override
    public void ability(InventoryClickEvent event, boolean current) {
        if (event.getInventory().getHolder() instanceof ShoulderBag && event.getClick() == ClickType.SWAP_OFFHAND) {
            event.setCancelled(true);
        }
    }

    private static class ShoulderBag implements InventoryHolder {

        final Material material;
        final ShulkerBox shulkerBox;
        final Inventory inventory;

        public ShoulderBag(final Material material, final ShulkerBox shulkerBox) {
            this.material = material;
            this.shulkerBox = shulkerBox;
            final var title = shulkerBox.customName();
            this.inventory = title == null
                    ? Bukkit.createInventory(this, InventoryType.SHULKER_BOX)
                    : Bukkit.createInventory(this, InventoryType.SHULKER_BOX, title);
            inventory.setContents(shulkerBox.getInventory().getContents());
        }

        @Override
        public @NotNull Inventory getInventory() {
            return inventory;
        }

        public ItemStack getItemStack() {
            final var itemStack = new ItemStack(material);
            if (itemStack.getItemMeta() instanceof BlockStateMeta blockStateMeta) {
                shulkerBox.getInventory().setContents(inventory.getContents());
                blockStateMeta.setBlockState(shulkerBox);
                itemStack.setItemMeta(blockStateMeta);
            }
            return itemStack;
        }
    }
}
