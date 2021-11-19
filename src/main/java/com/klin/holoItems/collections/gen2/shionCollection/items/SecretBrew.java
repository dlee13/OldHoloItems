package com.klin.holoItems.collections.gen2.shionCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.gen2.shionCollection.ShionCollection;
import com.klin.holoItems.interfaces.Brewable;
import com.klin.holoItems.interfaces.Mixable;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public class SecretBrew extends Item implements Brewable, Mixable {
    public static final String name = "secretBrew";

    private static final Material material = Material.NETHER_WART;
    private static final int quantity = 4;
    private static final String lore =
            "Hide potion effects";
    private static final int durability = 0;
    public static final boolean stackable = true;
    private static final boolean shiny = true;
    public static final int cost = 0;

    public SecretBrew(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost);
    }

    public void registerRecipes() {
        ShapelessRecipe recipe = new ShapelessRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.addIngredient(Material.DEAD_BUSH);
        recipe.addIngredient(Material.FERN);
        recipe.addIngredient(Material.GRASS);
        recipe.addIngredient(Material.GLOW_LICHEN);
        recipe.addIngredient(Material.LILY_PAD);
        recipe.addIngredient(Material.SEAGRASS);
        recipe.addIngredient(Material.VINE);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(BrewEvent event, ItemStack item, BrewerInventory inv) {
        new BukkitRunnable() {
            public void run(){
                for(int i=0; i<3; i++) {
                    ItemStack item = inv.getItem(i);
                    if(item==null)
                        continue;
                    ItemMeta itemMeta = item.getItemMeta();
                    if(!(itemMeta instanceof PotionMeta))
                        continue;
                    PotionMeta meta = (PotionMeta) itemMeta;
                    meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
                    meta.setColor(Utility.getRandom(Utility.colors.keySet()).get().getColor());
                    meta.setDisplayName("§f"+Utility.processType(item.getType().toString()));
                    meta.getPersistentDataContainer().set(Utility.key, PersistentDataType.STRING, name);
                    item.setItemMeta(meta);
                }
            }
        }.runTask(HoloItems.getInstance());
    }

    public void ability(BrewEvent event, ItemStack item, ItemStack ingredient, BrewerInventory inv, int slot) {
        Color color = ((PotionMeta) item.getItemMeta()).getColor();
        new BukkitRunnable() {
            public void run(){
                ItemStack item = inv.getItem(slot);
                PotionMeta meta = (PotionMeta) item.getItemMeta();
                meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
                meta.setColor(color);
                meta.setDisplayName("§f"+Utility.processType(item.getType().toString()));
                meta.getPersistentDataContainer().set(Utility.key, PersistentDataType.STRING, name);
                item.setItemMeta(meta);
            }
        }.runTask(HoloItems.getInstance());
    }
}
