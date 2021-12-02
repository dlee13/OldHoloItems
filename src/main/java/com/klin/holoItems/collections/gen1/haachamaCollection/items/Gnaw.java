package com.klin.holoItems.collections.gen1.haachamaCollection.items;

import com.klin.holoItems.Collections;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.misc.ingredientsCollection.items.CoalPetal;
import com.klin.holoItems.interfaces.Consumable;
import com.klin.holoItems.utility.Utility;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Gnaw extends Item implements Consumable {
    public static final String name = "gnaw";
    public static final Set<Enchantment> accepted = Stream.of(
            Enchantment.DURABILITY,
            Enchantment.FIRE_ASPECT,
            Enchantment.IMPALING,
            Enchantment.LURE,
            Enchantment.KNOCKBACK,
            Enchantment.MENDING,
            Enchantment.THORNS
    ).collect(Collectors.toCollection(HashSet::new));

    private static final Material material = Material.DRIED_KELP;
    private static final int quantity = 1;
    private static final String lore =
            "Sate your hunger";
    private static final int durability = 13;
    public static final boolean stackable = false;
    private static final boolean shiny = false;
    public static final int cost = 0;

    public Gnaw() {
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost);
    }

    public void registerRecipes() {
        ShapedRecipe recipe =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("a a", "bab", " b ");
        recipe.setIngredient('a', Material.WITHER_SKELETON_SKULL);
        recipe.setIngredient('b', new RecipeChoice.ExactChoice(Collections.items.get(CoalPetal.name).item));
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(PlayerItemConsumeEvent event, ItemStack item) {
        event.setCancelled(true);
        Player player = event.getPlayer();

        Location loc = player.getLocation().add(0, 1, 0);
        loc = loc.add(loc.getDirection());
        ItemMeta meta = item.getItemMeta();
        double range = 0.5*(meta.getEnchantLevel(Enchantment.IMPALING)+1);
        Collection<Entity> entities = loc.getWorld().getNearbyEntities(loc, range, 1, range, entity -> (entity!=player && entity instanceof LivingEntity));
        if(entities.isEmpty())
            return;

        int lure = meta.getEnchantLevel(Enchantment.LURE);
        int knockback = meta.getEnchantLevel(Enchantment.KNOCKBACK);
        int thorns = meta.getEnchantLevel(Enchantment.THORNS);
        for(Entity entity : entities){
            LivingEntity food = (LivingEntity) entity;
            int damage = 11+thorns*3;
            if(!Utility.damage(item, damage, player.getVelocity().getY()<0, player, food, true, false, true))
                return;
            if(thorns!=0) {
                player.damage(thorns);
                player.setNoDamageTicks(0);
            }
            if(lure!=0)
                entity.setVelocity(loc.subtract(entity.getLocation()).toVector().normalize().setY(0).multiply(lure/3));
            else if(knockback!=0)
                entity.setVelocity(food.getEyeLocation().subtract(loc).add(0, 1, 0).toVector().normalize().multiply(knockback));
        }
        int size = entities.size();
        PlayerInventory inv = player.getInventory();
        if(item.equals(inv.getItemInOffHand())){
            if(Math.random()<(1f/(meta.getEnchantLevel(Enchantment.DURABILITY)+1))) {
                List<String> lore = meta.getLore();
                int[] durability = Utility.getDurability(lore);
                durability[0] = durability[0] - size;
                if (durability[0] <= 0)
                    item.setAmount(0);
                String breaking = "§fDurability: " + durability[0] + "/" + durability[1];
                lore.set(lore.size() - 1, breaking);
                meta.setLore(lore);
                item.setItemMeta(meta);
                inv.setItemInOffHand(item);
                Utility.addDurability(inv.getItemInMainHand(), (double) size / 2, player);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(breaking));
            } else
                Utility.addDurability(inv.getItemInMainHand(), (double) size / 2, player);
        }
    }
}