package com.klin.holoItems.collections.gen5.lamyCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.gen5.lamyCollection.LamyCollection;
import com.klin.holoItems.interfaces.Brewable;
import com.klin.holoItems.interfaces.Consumable;
import com.klin.holoItems.utility.Utility;

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
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;

public class Starch extends Item implements Brewable, Consumable {
    public static final String name = "starch";

    private static final Material material = Material.SUGAR;
    private static final int quantity = 2;
    private static final String lore =
            "Brew into sake";
    private static final int durability = 0;
    public static final boolean stackable = true;
    private static final boolean shiny = false;
    public static final int cost = 0;

    public Starch(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost);
    }

    public void registerRecipes() {
        ShapelessRecipe recipe =
                new ShapelessRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.addIngredient(2, Material.AZURE_BLUET);
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
                    PotionType type = meta.getBasePotionType();
                    int multiplier = 1;
                    if(item.getType()==Material.LINGERING_POTION)
                        multiplier = 4;
                    if(type==PotionType.MUNDANE)
                        meta.addCustomEffect(new PotionEffect(PotionEffectType.NAUSEA, 400/multiplier, 1), true);
                    else if(type==PotionType.SWIFTNESS)
                        meta.addCustomEffect(new PotionEffect(PotionEffectType.BLINDNESS, 800/multiplier, 1), true);
                    else
                        continue;
                    meta.setBasePotionType(PotionType.MUNDANE);
                    meta.setDisplayName("§6Sake");
                    meta.setColor(Color.SILVER);
                    Sake.setSakeId(meta);
                    item.setItemMeta(meta);
                }
            }
        }.runTask(HoloItems.getInstance());
    }

    public void ability(PlayerItemConsumeEvent event, ItemStack item) {}

    public class Sake {
        //TODO: move potion transformation above to here

        private static final String id = "sake_potion";

        public static void setSakeId(ItemMeta itemMeta) {
            itemMeta.getPersistentDataContainer().set(Utility.id, PersistentDataType.STRING, id);
        }

        public static boolean isSakePotion(ItemStack item) {
            if (item == null || item.getType() != Material.POTION) {
                return false;
            }
            ItemMeta meta = item.getItemMeta();
            if (meta == null || !(meta instanceof PotionMeta)) {
                return false;
            }
            String id = meta.getPersistentDataContainer().get(Utility.id, PersistentDataType.STRING);
            return Sake.id.equals(id);
        }
    }
}
