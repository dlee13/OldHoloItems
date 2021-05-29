package com.klin.holoItems.utility;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.abstractClasses.Enchant;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class Utility {
    public static final NamespacedKey key =
            new NamespacedKey(HoloItems.getInstance(), "holoItems");
    public static final NamespacedKey pack =
            new NamespacedKey(HoloItems.getInstance(), "pack");
    public static final NamespacedKey stack =
            new NamespacedKey(HoloItems.getInstance(), "stack");
    public static final NamespacedKey cooldown =
            new NamespacedKey(HoloItems.getInstance(), "cooldown");
    public static final NamespacedKey enchant =
            new NamespacedKey(HoloItems.getInstance(), "enchant");

    public static final Map<Material, Material> ageable = new LinkedHashMap<Material, Material>(){{
        put(Material.BEETROOT_SEEDS, Material.BEETROOTS);
        put(Material.CARROT, Material.CARROTS);
        put(Material.POTATO, Material.POTATOES);
        put(Material.NETHER_WART, Material.NETHER_WART);
        put(Material.WHEAT_SEEDS, Material.WHEAT);
    }};

    public static final Map<Material, Material> sowable = new LinkedHashMap<Material, Material>(){{
        put(Material.BEETROOTS, Material.BEETROOT_SEEDS);
        put(Material.CARROTS, Material.CARROT);
        put(Material.POTATOES, Material.POTATO);
        put(Material.NETHER_WART, Material.NETHER_WART);
        put(Material.WHEAT, Material.WHEAT_SEEDS);
    }};

    public static final Map<String, Set<Material>> dirt = new HashMap<String, Set<Material>>(){{
        put("SAPLING", new HashSet<Material>(){{
            add(Material.GRASS_BLOCK);
            add(Material.DIRT);
            add(Material.COARSE_DIRT);
            add(Material.PODZOL);
            add(Material.FARMLAND);
        }});
        put("FUNGUS", new HashSet<Material>(){{
            add(Material.GRASS_BLOCK);
            add(Material.DIRT);
            add(Material.COARSE_DIRT);
            add(Material.PODZOL);
            add(Material.FARMLAND);
            add(Material.CRIMSON_NYLIUM);
            add(Material.WARPED_NYLIUM);
            add(Material.MYCELIUM);
            add(Material.SOUL_SOIL);
        }});
    }};

    private static final Set<EntityType> humanoids = new HashSet<EntityType>(){{
        add(EntityType.PLAYER);
        add(EntityType.ZOMBIE);
        add(EntityType.ZOMBIE_VILLAGER);
        add(EntityType.HUSK);
        add(EntityType.SKELETON);
        add(EntityType.STRAY);
        add(EntityType.PIGLIN_BRUTE);
        add(EntityType.PIGLIN);
        add(EntityType.ZOMBIFIED_PIGLIN);
        add(EntityType.DROWNED);
        add(EntityType.WITHER_SKELETON);
        add(EntityType.VILLAGER);
        add(EntityType.PILLAGER);
        add(EntityType.VINDICATOR);
        add(EntityType.EVOKER);
        add(EntityType.WITCH);
        add(EntityType.GIANT);
    }};

    private static final Map<PotionType, Double> durations = new HashMap<PotionType, Double>(){{
        put(PotionType.FIRE_RESISTANCE, 3.0);
        put(PotionType.INVISIBILITY, 3.0);
        put(PotionType.JUMP, 3.0);
        put(PotionType.LUCK, 5.0);
        put(PotionType.NIGHT_VISION, 3.0);
        put(PotionType.POISON, 0.75);
        put(PotionType.REGEN, 0.75);
        put(PotionType.SLOW_FALLING, 1.5);
        put(PotionType.SLOWNESS, 1.5);
        put(PotionType.SPEED, 3.0);
        put(PotionType.STRENGTH, 3.0);
        put(PotionType.TURTLE_MASTER, 0.333);
        put(PotionType.WATER_BREATHING, 3.0);
        put(PotionType.WEAKNESS, 1.5);
    }};

    public static final Set<Material> shovels = new HashSet<Material>(){{
        add(Material.DIAMOND_SHOVEL);
        add(Material.GOLDEN_SHOVEL);
        add(Material.IRON_SHOVEL);
        add(Material.STONE_SHOVEL);
        add(Material.NETHERITE_SHOVEL);
        add(Material.WOODEN_SHOVEL);
    }};

    public static final Set<Material> axes = new HashSet<Material>(){{
        add(Material.DIAMOND_AXE);
        add(Material.GOLDEN_AXE);
        add(Material.IRON_AXE);
        add(Material.STONE_AXE);
        add(Material.NETHERITE_AXE);
        add(Material.WOODEN_AXE);
    }};

    public static final Set<Material> hoes = new HashSet<Material>(){{
        add(Material.DIAMOND_HOE);
        add(Material.GOLDEN_HOE);
        add(Material.IRON_HOE);
        add(Material.STONE_HOE);
        add(Material.NETHERITE_HOE);
        add(Material.WOODEN_HOE);
    }};

    public static final Set<Material> logs = new HashSet<Material>(){{
        add(Material.ACACIA_LOG);
        add(Material.OAK_LOG);
        add(Material.BIRCH_LOG);
        add(Material.DARK_OAK_LOG);
        add(Material.JUNGLE_LOG);
        add(Material.SPRUCE_LOG);
        add(Material.CRIMSON_STEM);
        add(Material.WARPED_STEM);
        add(Material.STRIPPED_ACACIA_LOG);
        add(Material.STRIPPED_OAK_LOG);
        add(Material.STRIPPED_BIRCH_LOG);
        add(Material.STRIPPED_DARK_OAK_LOG);
        add(Material.STRIPPED_JUNGLE_LOG);
        add(Material.STRIPPED_SPRUCE_LOG);
        add(Material.STRIPPED_CRIMSON_STEM);
        add(Material.STRIPPED_WARPED_STEM);
        add(Material.ACACIA_WOOD);
        add(Material.OAK_WOOD);
        add(Material.BIRCH_WOOD);
        add(Material.DARK_OAK_WOOD);
        add(Material.JUNGLE_WOOD);
        add(Material.SPRUCE_WOOD);
        add(Material.CRIMSON_HYPHAE);
        add(Material.WARPED_HYPHAE);
        add(Material.STRIPPED_ACACIA_WOOD);
        add(Material.STRIPPED_OAK_WOOD);
        add(Material.STRIPPED_BIRCH_WOOD);
        add(Material.STRIPPED_DARK_OAK_WOOD);
        add(Material.STRIPPED_JUNGLE_WOOD);
        add(Material.STRIPPED_SPRUCE_WOOD);
        add(Material.STRIPPED_CRIMSON_HYPHAE);
        add(Material.STRIPPED_WARPED_HYPHAE);
    }};

    public static ItemStack addEnchant(ItemStack itemStack, Enchant enchant){
        ItemMeta meta = itemStack.getItemMeta();
        meta.getPersistentDataContainer().set(Utility.enchant, PersistentDataType.STRING, enchant.id);
        List<String> lore = meta.getLore();
        if(lore==null)
            lore = new ArrayList<>();
        lore.add(0, "§7"+formatName(enchant.name));
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static ItemStack process(String name, Material material, int quantity,
                 String lore, int durability, boolean shiny, String id){
        ItemStack item = new ItemStack(material, quantity);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6"+formatName(name));
        List<String> list = processStr(lore);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        if(meta instanceof Damageable) {
            meta.setUnbreakable(true);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        }
        //Durability: -1 Unbreakable, 0 Hide, 1 Display
        if(durability==-1) {
            list.add("");
            list.add("§fUnbreakable");
        }
        else if(durability>0) {
            list.add("");
            list.add("§fDurability: " + durability + "/" + durability);
        }
        if(shiny){
            meta.addEnchant(Enchantment.LUCK, 1, false);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        else if(meta.hasEnchants())
            list.add(0, "");
        meta.setLore(list);
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, id);
        item.setItemMeta(meta);

        return item;
    }

    public static boolean damage(ItemStack item, double damage, boolean crit,
                              LivingEntity attacker, LivingEntity target,
                              boolean strength, boolean projectile){
        if(strength) {
            int multiplier = checkPotionEffect(attacker, PotionEffectType.INCREASE_DAMAGE);
            damage = damage+3*multiplier*
                    (checkPotionEffect(attacker, PotionEffectType.INCREASE_DAMAGE)-
                    checkPotionEffect(attacker, PotionEffectType.WEAKNESS));
        }
        if(crit)
            damage *= 1.5;
        if(target.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE))
            damage *= Math.pow(0.8, target.getPotionEffect(PotionEffectType.DAMAGE_RESISTANCE).getAmplifier());
        if(item!=null)
            damage += 0.5+0.5*item.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
        double thornsChance = 0;
        if(target.getEquipment()!=null && humanoids.contains(target.getType())) {
            if (item!=null && target.getCategory().equals(EntityCategory.UNDEAD))
                damage += 2.5*item.getEnchantmentLevel(Enchantment.DAMAGE_UNDEAD);
            ItemStack[] armor = target.getEquipment().getArmorContents();
            double projProtection = 0;
            for (ItemStack piece : armor) {
                if(piece==null || !piece.hasItemMeta())
                    continue;
                thornsChance += 0.15*piece.getEnchantmentLevel(Enchantment.THORNS);
                projProtection += 0.08*piece.getEnchantmentLevel(Enchantment.PROTECTION_PROJECTILE);
            }
            if(projectile)
                damage *= 1-Math.min(0.8, projProtection);
        }
        else if (item!=null && target.getCategory().equals(EntityCategory.ARTHROPOD))
            damage += 2.5*item.getEnchantmentLevel(Enchantment.DAMAGE_ARTHROPODS);

        int fire = item!=null ? item.getEnchantmentLevel(Enchantment.FIRE_ASPECT) : 0;
        if(fire>0) {
            target.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1, 1));
            target.setFireTicks(1);
        }

        double initial = target.getHealth();
        target.damage(damage, attacker);
        if(target.getHealth()>=initial)
            return false;

        double returnDamage = Math.random()*3+1;
        if (Math.random()<thornsChance)
            attacker.damage(returnDamage+Math.max(0,thornsChance-1.5), target);
        if(fire>0)
            target.setFireTicks(80*fire);
        return true;
    }

    public static void arrowDamage(ItemStack item, AbstractArrow abstractArrow, LivingEntity target){
        double damage = Math.min(6, ((int) (abstractArrow.getVelocity().length()*2))+1);
        boolean punch = false;
        boolean flame = false;
        if (item != null) {
            damage *= (1 + (.25 * item.getEnchantmentLevel(Enchantment.ARROW_DAMAGE)));
            punch = item.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK) > 0;
            flame = item.getEnchantmentLevel(Enchantment.ARROW_FIRE) > 0;
        }

        if(abstractArrow instanceof Arrow) {
            Arrow arrow = (Arrow) abstractArrow;

            PotionData data = arrow.getBasePotionData();
            PotionEffectType type = data.getType().getEffectType();
            if (type != null) {
                if (type.isInstant()) {
                    if (target.getCategory().equals(EntityCategory.UNDEAD) &&
                            data.getType().equals(PotionType.INSTANT_DAMAGE) ||
                            data.getType().equals(PotionType.INSTANT_HEAL)) {
                        if (damage < 12)
                            damage -= data.isUpgraded() ? 6 : 4;
                    } else
                        damage = 12;
                } else {
                    double duration = durations.get(data.getType()) / 8 * 60 * 20;
                    target.addPotionEffect(new PotionEffect(type,
                            data.isExtended() ? (int) (duration * 2.666) :
                                    data.isUpgraded() ? (int) (duration / 2) : (int) duration,
                            data.isUpgraded() ? 1 : 0));
                }
            }
        }

        if (punch)
            target.setVelocity(abstractArrow.getVelocity().setY(0.1).multiply(
                    item.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK)));
        if (flame)
            target.setFireTicks(100);
        int arrowsInBody = 1;
        if(item.getEnchantmentLevel(Enchantment.MULTISHOT)==1) {
            damage *= 3;
            arrowsInBody = 3;
        }
        if (damage >= 0) {
            target.damage(abstractArrow.isCritical() ?
                    damage + Math.random() * (damage / 2 + 1) : damage,
                    (Player) abstractArrow.getShooter());
        } else {
            target.damage(2, (Player) abstractArrow.getShooter());
            if (target.isValid())
                target.setHealth(Math.min(20, target.getHealth() + Math.abs(damage) + 2));
        }
        target.setArrowsInBody(target.getArrowsInBody()+arrowsInBody);
        if(!target.isValid())
            return;

        if(abstractArrow instanceof SpectralArrow) {
            target.setGlowing(true);
            SpectralArrow spectralArrow = (SpectralArrow) abstractArrow;
            new BukkitRunnable() {
                @Override
                public void run(){
                    target.setGlowing(false);
                }
            }.runTaskLater(HoloItems.getInstance(), spectralArrow.getGlowingTicks());
        }
    }

    public static int addDurability(ItemStack item, double addend, Player player){
        if(addend==0 || item==null)
            return 0;
        ItemMeta meta = item.getItemMeta();
        if(meta==null)
            return 0;
        List<String> lore = meta.getLore();
        int[] durability = getDurability(lore);
        if(durability[0]==-1 || addend>0 && durability[0]==durability[1])
            return (int) addend;
        int unbreaking = meta.getEnchantLevel(Enchantment.DURABILITY);
        int total = durability[0]+(int) (addend*2);
        if(addend<0 && Math.random()<(1f/(unbreaking+1))) {
            durability[0] = durability[0]+(int) addend;
            if(durability[0]<=0){
                item.setAmount(0);
                return -1;
            }
        }
        else if(addend>0) {
            if(total>durability[1]) {
                durability[0] = durability[1];
                total = (total - durability[1])/2;
            }
            else {
                durability[0] = total;
                total = 0;
            }
        }
        else
            return (int) addend;
        String breaking = "§fDurability: "+durability[0]+"/"+durability[1];
        lore.set(lore.size()-1, breaking);
        meta.setLore(lore);
        item.setItemMeta(meta);

        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(breaking));
        return total;
    }

    public static int[] getDurability(List<String> lore){
        int[] durability = new int[]{-1, -1};

        String status = lore.get(lore.size()-1);
        if(!status.contains("/"))
            return durability;
        durability[0] = Integer.parseInt(status.substring(14, status.indexOf("/")));
        durability[1] = Integer.parseInt(status.substring(status.indexOf("/")+1));
        return durability;
    }

    public static void cooldown(ItemStack item, int ticks){
        ItemMeta meta = item.getItemMeta();
        if(meta==null)
            return;
        meta.getPersistentDataContainer().set(cooldown, PersistentDataType.INTEGER, 1);
        item.setItemMeta(meta);
        new BukkitRunnable(){
            public void run(){
                meta.getPersistentDataContainer().remove(cooldown);
                item.setItemMeta(meta);
            }
        }.runTaskLater(HoloItems.getInstance(), ticks);
    }

    public static boolean onCooldown(ItemStack item){
        return item.getItemMeta().getPersistentDataContainer().get(cooldown, PersistentDataType.INTEGER) != null;
    }

    public static List<String> processStr(String str){
        List<String> list = new ArrayList<>();
        list.add("");
        String[] lines = str.split("/n");
        for(String line : lines)
            list.add("§7"+line);
        return list;
    }

    public static int add(Map<String, Integer> collection){
        if(collection==null)
            return 0;
        int total = 0;
        for(int quantity : collection.values())
            total += quantity;
        return total;
    }

    public static void reOpen(InventoryView view, Inventory inv, Player player){
        new BukkitRunnable() {
            @Override
            public void run() {
                view.close();
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.openInventory(inv);
                    }
                }.runTask(HoloItems.getInstance());
            }
        }.runTask(HoloItems.getInstance());
    }

    public static String formatName(String str){
        str = str.substring(0, 1).toUpperCase()+str.substring(1);
        for(int i=1; i<str.length()-1; i++){
            if(Character.isUpperCase(str.charAt(i))) {
                str = str.substring(0, i) + " " + str.substring(i);
                i++;
            }
        }
        return str;
    }

    public static int deplete(ItemStack item){
        ItemMeta meta = item.getItemMeta();
        Integer charge = meta.getPersistentDataContainer().
                get(Utility.pack, PersistentDataType.INTEGER);
        if(charge==null || charge==0)
            return -1;
        else {
            charge -= 1;
            meta.getPersistentDataContainer().
                    set(Utility.pack, PersistentDataType.INTEGER, charge);
            item.setItemMeta(meta);
            return charge;
        }
    }

    public static int inspect(ItemStack item){
        ItemMeta meta = item.getItemMeta();
        Integer charge = meta.getPersistentDataContainer().
                get(Utility.pack, PersistentDataType.INTEGER);
        if(charge==null || charge==0)
            return -1;
        else {
            charge -= 1;
            return charge;
        }
    }

    public static void imbueProjectile(Projectile proj, String id){
        proj.getPersistentDataContainer().set(Utility.key, PersistentDataType.STRING, id);
    }

    public static boolean isInBoat(Player player){
        return player.isInsideVehicle() &&
                player.getVehicle().getType().equals(EntityType.BOAT);
    }

    public static int checkPotionEffect(LivingEntity entity, PotionEffectType type){
        if(entity.hasPotionEffect(type))
            return entity.getPotionEffect(type).getAmplifier()+1;
        return 0;
    }
}
