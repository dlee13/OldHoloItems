package com.klin.holoItems.collections.gen5.lamyCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.gen5.lamyCollection.LamyCollection;
import com.klin.holoItems.interfaces.Brewable;
import com.klin.holoItems.interfaces.Consumable;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;

public class Starch extends Item implements Brewable, Consumable {
    public static final String name = "starch";

    private static final Material material = Material.SUGAR;
    private static final int quantity = 1;
    private static final String lore =
            "Brew into sake";
    private static final int durability = 0;
    public static final boolean stackable = true;
    private static final boolean shiny = false;

    public static final int cost = 0;
    public static final char key = '0';
    public static final String id = ""+LamyCollection.key+key;

    public Starch(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost, id, key);
    }

    public void registerRecipes() {
        ShapelessRecipe recipe =
                new ShapelessRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.addIngredient(Material.AZURE_BLUET);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(BrewEvent event, ItemStack item, BrewerInventory inv) {
        new BukkitRunnable() {
            @Override
            public void run(){
                for(int i=0; i<3; i++) {
                    ItemStack item = inv.getItem(i);
                    if(item==null)
                        continue;
                    ItemMeta itemMeta = item.getItemMeta();
                    if(!(itemMeta instanceof PotionMeta))
                        continue;
                    PotionMeta meta = (PotionMeta) item.getItemMeta();
                    PotionType type = meta.getBasePotionData().getType();
                    int multiplier = 1;
                    if(item.getType()==Material.LINGERING_POTION)
                        multiplier = 4;
                    if(type==PotionType.MUNDANE)
                        meta.addCustomEffect(new PotionEffect(PotionEffectType.CONFUSION, 400/multiplier, 1), true);
                    else if(type==PotionType.SPEED)
                        meta.addCustomEffect(new PotionEffect(PotionEffectType.BLINDNESS, 800/multiplier, 1), true);
                    else
                        continue;
                    meta.setBasePotionData(new PotionData(PotionType.UNCRAFTABLE));
                    meta.setDisplayName("§6Sake");
                    meta.setColor(Color.SILVER);
                    item.setItemMeta(meta);
                }
            }
        }.runTask(HoloItems.getInstance());
    }

    public void ability(PlayerItemConsumeEvent event, ItemStack item) {}
}
