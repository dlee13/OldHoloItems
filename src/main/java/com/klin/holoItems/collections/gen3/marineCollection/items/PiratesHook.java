package com.klin.holoItems.collections.gen3.marineCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.gen3.marineCollection.MarineCollection;
import com.klin.holoItems.interfaces.Fishable;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PiratesHook extends Item implements Fishable {
    public static final String name = "piratesHook";
    public static final Set<Enchantment> accepted = Stream.of(Enchantment.DURABILITY, Enchantment.MENDING).collect(Collectors.toCollection(HashSet::new));

    private static final Material material = Material.FISHING_ROD;
    private static final int quantity = 1;
    private static final String lore =
            "Strip a random item off a mob";
    private static final int durability = 6;
    public static final boolean stackable = false;
    private static final boolean shiny = false;

    public static final int cost = 0;
    public static final char key = '1';

    public PiratesHook(){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost,
                ""+MarineCollection.key+key, key);
    }

    public void registerRecipes(){
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6Pirate's Hook");
        item.setItemMeta(meta);

        ShapedRecipe recipe0 =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"0"), item);
        recipe0.shape("  a"," ab","a c");
        recipe0.setIngredient('a', Material.STICK);
        recipe0.setIngredient('b', Material.CHAIN);
        recipe0.setIngredient('c', Material.TRIPWIRE_HOOK);
        recipe0.setGroup(name);
        Bukkit.getServer().addRecipe(recipe0);

        ShapedRecipe recipe1 =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"1"), item);
        recipe1.shape("a  ","ba ","c a");
        recipe1.setIngredient('a', Material.STICK);
        recipe1.setIngredient('b', Material.CHAIN);
        recipe1.setIngredient('c', Material.TRIPWIRE_HOOK);
        recipe1.setGroup(name);
        Bukkit.getServer().addRecipe(recipe1);
    }

    public void ability(PlayerFishEvent event, ItemStack item){
        PlayerFishEvent.State state = event.getState();
        if(state==PlayerFishEvent.State.CAUGHT_FISH)
            event.setCancelled(true);
        if(state!=PlayerFishEvent.State.CAUGHT_ENTITY)
            return;
        event.setCancelled(true);

        event.getHook().remove();
        Entity entity = event.getCaught();
        if(!Utility.humanoids.contains(entity.getType()))
            return;
        EntityEquipment equipment = ((LivingEntity) entity).getEquipment();
        if(equipment==null)
            return;
        Map<ItemStack, EquipmentSlot> items = new HashMap<>();
        items.put(equipment.getItemInMainHand(), EquipmentSlot.HAND);
//        items.put(equipment.getItemInOffHand(), EquipmentSlot.OFF_HAND);
        items.put(equipment.getHelmet(), EquipmentSlot.HEAD);
        items.put(equipment.getChestplate(), EquipmentSlot.CHEST);
        items.put(equipment.getLeggings(), EquipmentSlot.LEGS);
        items.put(equipment.getBoots(), EquipmentSlot.FEET);
        boolean hook = true;
        while(hook && !items.isEmpty()){
            Optional<ItemStack> optional = Utility.getRandom(items.keySet());
            if(optional.isEmpty())
                return;
            ItemStack strip = optional.get();
            if(strip.getType()!=Material.AIR){
                boolean remove = true;
                Map<Enchantment, Integer> enchantments = strip.getEnchantments();
                for(Enchantment enchantment : enchantments.keySet()){
                    if(enchantment.getMaxLevel()<enchantments.get(enchantment)){
                        remove = false;
                        break;
                    }
                }
                if(remove) {
                    entity.getWorld().dropItemNaturally(entity.getLocation(), strip);
                    equipment.setItem(items.get(strip), null);
                    hook = false;
                    Utility.addDurability(item, -1, event.getPlayer());
                }
            }
            items.remove(strip);
        }
    }
}
