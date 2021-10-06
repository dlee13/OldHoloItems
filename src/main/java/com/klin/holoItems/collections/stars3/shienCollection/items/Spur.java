package com.klin.holoItems.collections.stars3.shienCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.abstractClasses.Enchant;
import com.klin.holoItems.collections.gen0.suiseiCollection.items.Hoshiyumi;
import com.klin.holoItems.interfaces.Interactable;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Spur extends Enchant implements Interactable {
    public static final String name = "spur";
    public static final Set<Enchantment> accepted = null;

    private static final Material material = Material.TRIPWIRE_HOOK;
    private static final String lore =
            "Combine with a bow to swiftly\n" +
            "maneuver while reloading";
    private static final int durability = 0;
    private static final boolean shiny = true;

    public static final Set<String> acceptedIds = Stream.of(Hoshiyumi.name).collect(Collectors.toCollection(HashSet::new));
    public static final Set<Material> acceptedTypes = Stream.of(Material.BOW, Material.CROSSBOW).collect(Collectors.toCollection(HashSet::new));
    public static final int expCost = 22;
    public static final int cost = 0;

    public Spur(){
        super(name, accepted, material, lore, durability, shiny, cost, acceptedIds, acceptedTypes, expCost);
    }

    public void registerRecipes(){
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("a","b","c");
        recipe.setIngredient('a', Material.NETHERITE_INGOT);
        recipe.setIngredient('b', Material.LEVER);
        recipe.setIngredient('c', Material.SKELETON_SKULL);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(PlayerInteractEvent event, Action action) {
        ItemStack item = event.getItem();
        Material type = item.getType();
        int charge;
        int amplifier = 10;
        if(type==Material.BOW)
            charge = 22;
        else if(type==Material.CROSSBOW) {
            if(((CrossbowMeta) item.getItemMeta()).hasChargedProjectiles())
                return;
            amplifier = item.getEnchantmentLevel(Enchantment.QUICK_CHARGE);
            charge = 25 - 5 * amplifier;
            amplifier = Math.min(amplifier, 1) * 10;
        } else return;
        Player player = event.getPlayer();
        amplifier += Utility.checkPotionEffect(player, PotionEffectType.SPEED) * 5;
        PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, 1, amplifier);
        new Task(HoloItems.getInstance(), 1, 1){
            int increment = 0;
            public void run(){
                if(increment>=charge || !player.isHandRaised()){
                    cancel();
                    return;
                }
                player.addPotionEffect(speed);
                increment++;
            }
        };
    }
}
