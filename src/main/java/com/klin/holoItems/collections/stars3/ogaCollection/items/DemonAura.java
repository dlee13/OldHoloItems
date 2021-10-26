package com.klin.holoItems.collections.stars3.ogaCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.abstractClasses.Enchant;
import com.klin.holoItems.collections.gen1.melCollection.items.ReadingGlasses;
import com.klin.holoItems.collections.gen4.cocoCollection.items.DragonHorns;
import com.klin.holoItems.collections.gen5.botanCollection.items.Backdash;
import com.klin.holoItems.interfaces.Chargeable;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DemonAura extends Enchant implements Chargeable {
    public static final String name = "demonAura";
    public static final Set<String> acceptedIds = Stream.of(ReadingGlasses.name, DragonHorns.name, Backdash.name).collect(Collectors.toCollection(HashSet::new));
    public static final Set<Material> acceptedTypes = Utility.armor;
    public static final int expCost = 20;

    private static final Material material = Material.COAL;
    private static final String lore =
            "Absorb experience as health";
    private static final int durability = 0;
    private static final boolean shiny = true;
    public static final boolean stackable = false;
    public static final int cost = 0;

    public DemonAura(){
        super(name, material, lore, durability, shiny, cost, acceptedIds, acceptedTypes, expCost);
    }

    public void registerRecipes(){
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("aaa","aba", "aaa");
        recipe.setIngredient('a', Material.CANDLE);
        recipe.setIngredient('b', Material.IRON_HORSE_ARMOR);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(PlayerExpChangeEvent event) {
        Player player = event.getPlayer();
        double health = player.getHealth();
        if(health>=20)
            return;
        int amount = event.getAmount();
        int multiplier = 8;
        PlayerInventory inv = player.getInventory();
        for(ItemStack item : new ItemStack[]{inv.getHelmet(), inv.getChestplate(), inv.getLeggings(), inv.getBoots()}) {
            if(item==null)
                continue;
            ItemMeta meta = item.getItemMeta();
            if(meta==null)
                continue;
            String enchants = meta.getPersistentDataContainer().get(Utility.enchant, PersistentDataType.STRING);
            if(enchants!=null && enchants.contains(name))
                multiplier--;
        }
        if((health += (double) amount/multiplier) > 20){
            amount = (int) health - 20;
            health = 20;
        } else
            amount = 0;
        event.setAmount(amount);
        player.setHealth(health);
    }
}
