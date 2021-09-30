package com.klin.holoItems.collections.misc.franCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.misc.franCollection.FranCollection;
import com.klin.holoItems.interfaces.customMobs.Combinable;
import com.klin.holoItems.interfaces.customMobs.Spawnable;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.material.Colorable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class DyeConcentrate extends Item implements Combinable, Spawnable{
    public static final String name = "dyeConcentrate";
    public static final Set<Enchantment> accepted = null;

    private static final Material material = Material.MAGMA_CREAM;
    private static final int quantity = 1;
    private static final String lore =
            "Burst into a sea of colors";
    private static final int durability = 0;
    public static final boolean stackable = true;
    private static final boolean shiny = false;
    public static final int cost = -1;

    public DyeConcentrate(){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost);
    }

    public void registerRecipes() {}

    public String processInfo(ItemStack item) {
        return ":"+item.getItemMeta().getDisplayName();
    }

    public void ability(LivingEntity entity, String info) {
        DyeColor dye;
        if(info!=null) {
            try {
                dye = DyeColor.valueOf(info);
            }catch(IllegalArgumentException e){
                dye = Utility.getRandom(Utility.colors.keySet()).get();
            }
        }
        else {
            dye = Utility.getRandom(Utility.colors.keySet()).get();
        }
        if(entity instanceof Colorable)
            ((Colorable) entity).setColor(dye);
        entity.setAI(false);
        Map<Block, BlockData> blast = Utility.explode(entity.getLocation(), 4, null);
        World world = entity.getWorld();
        Color ink = dye.getColor();
        for(Entity nearby : entity.getNearbyEntities(4, 4, 4)){
            if(nearby instanceof LivingEntity){
                LivingEntity livingEntity = (LivingEntity) nearby;
                livingEntity.setNoDamageTicks(0);
                livingEntity.damage(8);
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 2));
                equip(world, livingEntity, ink);
            }
        }
        Set<Material> colors = Utility.colors.get(dye);
        for (Block block : blast.keySet()) {
            Optional<Material> type = Utility.getRandom(colors);
            type.ifPresent(block::setType);
        }
        DyeColor color = dye;
        new Task(HoloItems.getInstance(), 1, 1){
            int increment = 0;
            double health = entity.getHealth();
            public void run(){
                if(increment>=40 || !entity.isValid()){
                    if(entity.isValid()){
                        Map<Block, BlockData> post = Utility.explode(entity.getLocation(), 8, null);
                        for(Entity nearby : entity.getNearbyEntities(8, 8, 8)){
                            if(nearby instanceof LivingEntity){
                                LivingEntity livingEntity = (LivingEntity) nearby;
                                livingEntity.setNoDamageTicks(0);
                                livingEntity.damage(16);
                                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 80, 4));
                                equip(world, livingEntity, ink);
                            }
                        }
                        Set<Material> colors = Utility.colors.get(color);
                        for (Block block : post.keySet()) {
                            Optional<Material> type = Utility.getRandom(colors);
                            type.ifPresent(block::setType);
                        }
                        entity.remove();
                        new Task(HoloItems.getInstance(), 1, 1){
                            public void run(){
                                for(int i=0; i<8; i++) {
                                    Optional<Block> optional = Utility.getRandom(post.keySet());
                                    if (optional.isEmpty()) {
                                        cancel();
                                        return;
                                    }
                                    Block block = optional.get();
                                    BlockData data = post.remove(block);
                                    if (blast.containsKey(block))
                                        data = blast.get(block);
                                    block.setBlockData(data);
                                }
                            }
                        };
                    }
                    else{
                        new Task(HoloItems.getInstance(), 1, 1){
                            public void run(){
                                for(int i=0; i<8; i++) {
                                    Optional<Block> optional = Utility.getRandom(blast.keySet());
                                    if (optional.isEmpty()) {
                                        cancel();
                                        return;
                                    }
                                    Block block = optional.get();
                                    block.setBlockData(blast.remove(block));
                                }
                            }
                        };
                    }
                    cancel();
                    return;
                }
                double hp = entity.getHealth();
                double difference = health-hp;
                if(difference>0){
                    if(difference>=7)
                        increment -= 20;
                    else
                        increment += 20;
                    health = hp;
                }
                increment++;
            }
        };
    }

    private static void equip(World world, LivingEntity livingEntity, Color color){
        EntityEquipment equipment = livingEntity.getEquipment();
        if(equipment==null)
            return;
        Location loc = livingEntity.getLocation();
        ItemStack armor = equipment.getHelmet();
        if(armor!=null && armor.getType()!=Material.AIR) {
            if(livingEntity instanceof Player){
                Inventory inv = ((Player) livingEntity).getInventory();
                for(ItemStack item : inv.addItem(equipment.getHelmet()).values())
                    world.dropItemNaturally(loc, item);
                inv.remove(Material.LEATHER_HELMET);
            }
            else
                world.dropItemNaturally(loc, equipment.getHelmet());
            ItemStack item = new ItemStack(Material.LEATHER_HELMET);
            LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
            meta.setColor(color);
            item.setItemMeta(meta);
            equipment.setHelmet(item);
        }
        armor = equipment.getChestplate();
        if(armor!=null && armor.getType()!=Material.AIR) {
            if(livingEntity instanceof Player){
                Inventory inv = ((Player) livingEntity).getInventory();
                for(ItemStack item : inv.addItem(equipment.getChestplate()).values())
                    world.dropItemNaturally(loc, item);
                inv.remove(Material.LEATHER_CHESTPLATE);
            }
            else
                world.dropItemNaturally(loc, equipment.getChestplate());
            ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE);
            LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
            meta.setColor(color);
            item.setItemMeta(meta);
            equipment.setChestplate(item);
        }
        armor = equipment.getLeggings();
        if(armor!=null && armor.getType()!=Material.AIR) {
            if(livingEntity instanceof Player){
                Inventory inv = ((Player) livingEntity).getInventory();
                for(ItemStack item : inv.addItem(equipment.getLeggings()).values())
                    world.dropItemNaturally(loc, item);
                inv.remove(Material.LEATHER_LEGGINGS);
            }
            else
                world.dropItemNaturally(loc, equipment.getLeggings());
            ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS);
            LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
            meta.setColor(color);
            item.setItemMeta(meta);
            equipment.setLeggings(item);
        }
        armor = equipment.getBoots();
        if(armor!=null && armor.getType()!=Material.AIR) {
            if(livingEntity instanceof Player){
                Inventory inv = ((Player) livingEntity).getInventory();
                for(ItemStack item : inv.addItem(equipment.getBoots()).values())
                    world.dropItemNaturally(loc, item);
                inv.remove(Material.LEATHER_BOOTS);
            }
            else
                world.dropItemNaturally(loc, equipment.getBoots());
            ItemStack item = new ItemStack(Material.LEATHER_BOOTS);
            LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
            meta.setColor(color);
            item.setItemMeta(meta);
            equipment.setBoots(item);
        }
    }
}
