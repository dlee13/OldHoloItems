package com.klin.holoItems.utility;

import com.klin.holoItems.Collections;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.abstractClasses.Enchant;
import com.klin.holoItems.interfaces.combinable.Spawnable;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.TileState;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
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
import org.bukkit.util.Vector;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.bukkit.Material.*;

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
    public static final NamespacedKey data =
            new NamespacedKey(HoloItems.getInstance(), "data");

    public static boolean test = false;

    public static final Set<Material> fences = Stream.of(
            Material.ACACIA_FENCE, Material.BIRCH_FENCE, Material.OAK_FENCE, Material.DARK_OAK_FENCE, Material.CRIMSON_FENCE, Material.JUNGLE_FENCE, Material.NETHER_BRICK_FENCE, Material.SPRUCE_FENCE, Material.WARPED_FENCE, Material.CHAIN, Material.IRON_BARS, Material.GLASS_PANE, Material.BLACK_STAINED_GLASS_PANE, Material.RED_STAINED_GLASS_PANE, Material.GREEN_STAINED_GLASS_PANE, Material.BLUE_STAINED_GLASS_PANE, Material.PURPLE_STAINED_GLASS_PANE, Material.CYAN_STAINED_GLASS_PANE, Material.LIGHT_GRAY_STAINED_GLASS_PANE, Material.GRAY_STAINED_GLASS_PANE, Material.PINK_STAINED_GLASS_PANE, Material.LIME_STAINED_GLASS_PANE, Material.YELLOW_STAINED_GLASS_PANE, Material.LIGHT_BLUE_STAINED_GLASS_PANE, Material.MAGENTA_STAINED_GLASS_PANE, Material.ORANGE_STAINED_GLASS_PANE, Material.WHITE_STAINED_GLASS_PANE, Material.BROWN_STAINED_GLASS_PANE, Material.COBBLESTONE_WALL, Material.MOSSY_COBBLESTONE_WALL,  Material.STONE_BRICK_WALL, Material.MOSSY_STONE_BRICK_WALL, Material.ANDESITE_WALL, Material.DIORITE_WALL, Material.GRANITE_WALL, Material.SANDSTONE_WALL, Material.RED_SANDSTONE_WALL, Material.BRICK_WALL, Material.PRISMARINE_WALL, Material.NETHER_BRICK_WALL, Material.RED_NETHER_BRICK_WALL, Material.END_STONE_BRICK_WALL, Material.BLACKSTONE_WALL, Material.POLISHED_BLACKSTONE_WALL, Material.POLISHED_BLACKSTONE_BRICK_WALL).
            collect(Collectors.toCollection(HashSet::new));

    public static final Map<BlockFace, BlockFace> opposites = Stream.of(new BlockFace[][] {
            { BlockFace.UP, BlockFace.DOWN },
            { BlockFace.DOWN, BlockFace.UP },
            { BlockFace.NORTH, BlockFace.SOUTH },
            { BlockFace.SOUTH, BlockFace.NORTH },
            { BlockFace.EAST, BlockFace.WEST },
            { BlockFace.WEST, BlockFace.EAST }
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

    public static final Map<Material, Material> ageable = new LinkedHashMap<>() {{
        put(BEETROOT_SEEDS, BEETROOTS);
        put(CARROT, CARROTS);
        put(POTATO, POTATOES);
        put(NETHER_WART, NETHER_WART);
        put(CHORUS_FLOWER, CHORUS_FLOWER);
        put(WHEAT_SEEDS, WHEAT);
    }};

    public static final Set<Enchantment> enchantedBoots = Stream.of(
            Enchantment.PROTECTION_EXPLOSIONS,
            Enchantment.DEPTH_STRIDER,
            Enchantment.PROTECTION_FALL,
            Enchantment.PROTECTION_FIRE,
            Enchantment.FROST_WALKER,
            Enchantment.MENDING,
            Enchantment.PROTECTION_PROJECTILE,
            Enchantment.PROTECTION_ENVIRONMENTAL,
            Enchantment.SOUL_SPEED,
            Enchantment.THORNS,
            Enchantment.DURABILITY
    ).collect(Collectors.toCollection(HashSet::new));

    public static final Map<BlockFace, Vector> cardinal = new HashMap<>() {{
        put(BlockFace.NORTH, new Vector(0, 0, -1));
        put(BlockFace.SOUTH, new Vector(0, 0, 1));
        put(BlockFace.EAST, new Vector(1, 0, 0));
        put(BlockFace.WEST, new Vector(-1, 0, 0));
    }};

    public static final Set<Material> boots = Stream.of(
            LEATHER_BOOTS,
            IRON_BOOTS,
            CHAINMAIL_BOOTS,
            GOLDEN_BOOTS,
            DIAMOND_BOOTS,
            NETHERITE_BOOTS
    ).collect(Collectors.toCollection(HashSet::new));

    public static final Set<Material> flowers = Stream.of(
            DANDELION,
            POPPY,
            BLUE_ORCHID,
            ALLIUM,
            AZURE_BLUET,
            RED_TULIP,
            ORANGE_TULIP,
            WHITE_TULIP,
            PINK_TULIP,
            OXEYE_DAISY,
            CORNFLOWER,
            LILY_OF_THE_VALLEY,
            WITHER_ROSE
//            SUNFLOWER,
//            LILAC,
//            ROSE_BUSH,
//            PEONY
    ).collect(Collectors.toCollection(HashSet::new));

    public static final Map<Material, Material> sowable = new LinkedHashMap<>() {{
        put(BEETROOTS, BEETROOT_SEEDS);
        put(CARROTS, CARROT);
        put(POTATOES, POTATO);
        put(NETHER_WART, NETHER_WART);
        put(WHEAT, WHEAT_SEEDS);
    }};

    public static final Map<String, Set<Material>> dirt = new HashMap<>() {{
        put("SAPLING", new HashSet<>() {{
            add(GRASS_BLOCK);
            add(DIRT);
            add(COARSE_DIRT);
            add(PODZOL);
            add(FARMLAND);
        }});
        put("FUNGUS", new HashSet<>() {{
            add(GRASS_BLOCK);
            add(DIRT);
            add(COARSE_DIRT);
            add(PODZOL);
            add(FARMLAND);
            add(CRIMSON_NYLIUM);
            add(WARPED_NYLIUM);
            add(MYCELIUM);
            add(SOUL_SOIL);
        }});
    }};

    private static final Set<EntityType> humanoids = Stream.of(
            EntityType.PLAYER,
            EntityType.ZOMBIE,
            EntityType.ZOMBIE_VILLAGER,
            EntityType.HUSK,
            EntityType.SKELETON,
            EntityType.STRAY,
            EntityType.PIGLIN_BRUTE,
            EntityType.PIGLIN,
            EntityType.ZOMBIFIED_PIGLIN,
            EntityType.DROWNED,
            EntityType.WITHER_SKELETON,
            EntityType.VILLAGER,
            EntityType.PILLAGER,
            EntityType.VINDICATOR,
            EntityType.EVOKER,
            EntityType.WITCH,
            EntityType.GIANT
    ).collect(Collectors.toCollection(HashSet::new));

    private static final Map<PotionType, Double> durations = new HashMap<>() {{
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

    public static final Set<Material> shovels = Stream.of(
            DIAMOND_SHOVEL,
            GOLDEN_SHOVEL,
            IRON_SHOVEL,
            STONE_SHOVEL,
            NETHERITE_SHOVEL,
            WOODEN_SHOVEL
    ).collect(Collectors.toCollection(HashSet::new));

    public static final Set<Material> axes = Stream.of(
            DIAMOND_AXE,
            GOLDEN_AXE,
            IRON_AXE,
            STONE_AXE,
            NETHERITE_AXE,
            WOODEN_AXE
    ).collect(Collectors.toCollection(HashSet::new));

    public static final Set<Material> hoes = Stream.of(
            DIAMOND_HOE,
            GOLDEN_HOE,
            IRON_HOE,
            STONE_HOE,
            NETHERITE_HOE,
            WOODEN_HOE
    ).collect(Collectors.toCollection(HashSet::new));

    public static final Set<Material> pickaxes = Stream.of(
            DIAMOND_PICKAXE,
            GOLDEN_PICKAXE,
            IRON_PICKAXE,
            STONE_PICKAXE,
            NETHERITE_PICKAXE,
            WOODEN_PICKAXE
    ).collect(Collectors.toCollection(HashSet::new));

    public static final Set<Material> buckets = Stream.of(
            BUCKET,
            WATER_BUCKET,
//            LAVA_BUCKET,
//            POWERED_SNOW_BUCKET
            BOWL
    ).collect(Collectors.toCollection(HashSet::new));

    public static final Set<Material> slabs = Stream.of(
            OAK_SLAB,
            SPRUCE_SLAB,
            BIRCH_SLAB,
            JUNGLE_SLAB,
            ACACIA_SLAB,
            DARK_OAK_SLAB,
            CRIMSON_SLAB,
            WARPED_SLAB,
            PETRIFIED_OAK_SLAB,
            STONE_SLAB,
            SMOOTH_STONE_SLAB,
            COBBLESTONE_SLAB,
            MOSSY_COBBLESTONE_SLAB,
            STONE_BRICK_SLAB,
            MOSSY_STONE_BRICK_SLAB,
            ANDESITE_SLAB,
            POLISHED_ANDESITE_SLAB,
            DIORITE_SLAB,
            POLISHED_DIORITE_SLAB,
            GRANITE_SLAB,
            POLISHED_GRANITE_SLAB,
            SANDSTONE_SLAB,
            CUT_SANDSTONE_SLAB,
            SMOOTH_SANDSTONE_SLAB,
            RED_SANDSTONE_SLAB,
            CUT_RED_SANDSTONE_SLAB,
            SMOOTH_RED_SANDSTONE_SLAB,
            BRICK_SLAB,
            PRISMARINE_SLAB,
            PRISMARINE_BRICK_SLAB,
            DARK_PRISMARINE_SLAB,
            NETHER_BRICK_SLAB,
            RED_NETHER_BRICK_SLAB,
            QUARTZ_SLAB,
            SMOOTH_QUARTZ_SLAB,
            PURPUR_SLAB,
            END_STONE_BRICK_SLAB,
            BLACKSTONE_SLAB,
            POLISHED_BLACKSTONE_SLAB,
            POLISHED_BLACKSTONE_BRICK_SLAB
    ).collect(Collectors.toCollection(HashSet::new));

    public static final Set<Material> logs = Stream.of(
            ACACIA_LOG,
            OAK_LOG,
            BIRCH_LOG,
            DARK_OAK_LOG,
            JUNGLE_LOG,
            SPRUCE_LOG,
            CRIMSON_STEM,
            WARPED_STEM,
            STRIPPED_ACACIA_LOG,
            STRIPPED_OAK_LOG,
            STRIPPED_BIRCH_LOG,
            STRIPPED_DARK_OAK_LOG,
            STRIPPED_JUNGLE_LOG,
            STRIPPED_SPRUCE_LOG,
            STRIPPED_CRIMSON_STEM,
            STRIPPED_WARPED_STEM,
            ACACIA_WOOD,
            OAK_WOOD,
            BIRCH_WOOD,
            DARK_OAK_WOOD,
            JUNGLE_WOOD,
            SPRUCE_WOOD,
            CRIMSON_HYPHAE,
            WARPED_HYPHAE,
            STRIPPED_ACACIA_WOOD,
            STRIPPED_OAK_WOOD,
            STRIPPED_BIRCH_WOOD,
            STRIPPED_DARK_OAK_WOOD,
            STRIPPED_JUNGLE_WOOD,
            STRIPPED_SPRUCE_WOOD,
            STRIPPED_CRIMSON_HYPHAE,
            STRIPPED_WARPED_HYPHAE
    ).collect(Collectors.toCollection(HashSet::new));
    
    public static final Set<Material> spawnEggs = Stream.of(
            BAT_SPAWN_EGG,
            BEE_SPAWN_EGG,
            BLAZE_SPAWN_EGG,
            CAT_SPAWN_EGG,
            CAVE_SPIDER_SPAWN_EGG,
            CHICKEN_SPAWN_EGG,
            COD_SPAWN_EGG,
            COW_SPAWN_EGG,
            CREEPER_SPAWN_EGG,
            DOLPHIN_SPAWN_EGG,
            DONKEY_SPAWN_EGG,
            DROWNED_SPAWN_EGG,
            ELDER_GUARDIAN_SPAWN_EGG,
            ENDERMAN_SPAWN_EGG,
            ENDERMITE_SPAWN_EGG,
            EVOKER_SPAWN_EGG,
            FOX_SPAWN_EGG,
            GHAST_SPAWN_EGG,
            GUARDIAN_SPAWN_EGG,
            HOGLIN_SPAWN_EGG,
            HORSE_SPAWN_EGG,
            HUSK_SPAWN_EGG,
            LLAMA_SPAWN_EGG,
            MAGMA_CUBE_SPAWN_EGG,
            MOOSHROOM_SPAWN_EGG,
            MULE_SPAWN_EGG,
            OCELOT_SPAWN_EGG,
            PANDA_SPAWN_EGG,
            PARROT_SPAWN_EGG,
            PHANTOM_SPAWN_EGG,
            PIG_SPAWN_EGG,
            PIGLIN_BRUTE_SPAWN_EGG,
            PIGLIN_SPAWN_EGG,
            PILLAGER_SPAWN_EGG,
            POLAR_BEAR_SPAWN_EGG,
            PUFFERFISH_SPAWN_EGG,
            RABBIT_SPAWN_EGG,
            RAVAGER_SPAWN_EGG,
            SALMON_SPAWN_EGG,
            SHEEP_SPAWN_EGG,
            SHULKER_SPAWN_EGG,
            SILVERFISH_SPAWN_EGG,
            SKELETON_HORSE_SPAWN_EGG,
            SKELETON_SPAWN_EGG,
            SLIME_SPAWN_EGG,
            SPIDER_SPAWN_EGG,
            SQUID_SPAWN_EGG,
            STRAY_SPAWN_EGG,
            STRIDER_SPAWN_EGG,
            TROPICAL_FISH_SPAWN_EGG,
            TURTLE_SPAWN_EGG,
            VEX_SPAWN_EGG,
            VILLAGER_SPAWN_EGG,
            VINDICATOR_SPAWN_EGG,
            WANDERING_TRADER_SPAWN_EGG,
            WITCH_SPAWN_EGG,
            WITHER_SKELETON_SPAWN_EGG,
            WOLF_SPAWN_EGG,
            ZOGLIN_SPAWN_EGG,
            ZOMBIE_HORSE_SPAWN_EGG,
            ZOMBIFIED_PIGLIN_SPAWN_EGG,
            ZOMBIE_SPAWN_EGG,
            ZOMBIE_VILLAGER_SPAWN_EGG
    ).collect(Collectors.toCollection(HashSet::new));

    public static <T> T findItem(ItemStack item, Class<T> cls){
        if(item==null || item.getType()==Material.AIR || item.getItemMeta()==null)
            return null;
        String id = item.getItemMeta().getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
        return findItem(id, cls);
    }

    public static <T> T findItem(ItemStack item, Class<T> cls, Player player){
        if(item==null || item.getType()==Material.AIR || item.getItemMeta()==null)
            return null;
        String id = item.getItemMeta().getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
        return findItem(id, cls, player);
    }

    public static <T> T findItem(Entity entity, Class<T> cls){
        String id = entity.getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
        return findItem(id, cls);
    }

    public static <T> T findItem(Entity entity, Class<T> cls, Player player){
        String id = entity.getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
        return findItem(id, cls, player);
    }

    public static <T> T findItem(Block block, Class<T> cls){
        BlockState state = block.getState();
        if(!(state instanceof TileState))
            return null;
        String id = ((TileState) state).getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
        return findItem(id, cls);
    }

    public static <T> T findItem(Block block, Class<T> cls, Player player){
        BlockState state = block.getState();
        if(!(state instanceof TileState))
            return null;
        String id = ((TileState) state).getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
        return findItem(id, cls, player);
    }

    public static <T> T findItem(String id, Class<T> cls){
        if(id==null || Collections.disabled.contains(id))
            return null;
        Item generic = Collections.findItem(id);
        if(!cls.isInstance(generic))
            return null;
        return cls.cast(generic);
    }

    public static <T> T findItem(String id, Class<T> cls, Player player){
        if(id==null)
            return null;
        if(Collections.disabled.contains(id)){
            if(player!=null)
                player.sendMessage("§cThis item has been disabled");
            return null;
        }
        Item generic = Collections.findItem(id);
        if(!cls.isInstance(generic))
            return null;
        return cls.cast(generic);
    }

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
        meta.setCustomModelData(id.charAt(0)*10 + Character.getNumericValue(id.charAt(1)));
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, id);
        item.setItemMeta(meta);

        return item;
    }

    public static boolean damage(ItemStack item, double damage, boolean crit,
                              LivingEntity attacker, LivingEntity target,
                              boolean strength, boolean projectile, boolean bypass){
        EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(attacker, target, EntityDamageEvent.DamageCause.ENTITY_ATTACK, 0.1);
        Bukkit.getServer().getPluginManager().callEvent(event);
        if(event.isCancelled())
            return false;

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
        if(bypass && target instanceof Player && ((Player) target).isBlocking())
            target.damage(damage);
        //not bypass && attack blocked
        else if (target.getHealth() >= initial)
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
        int multishot = 1;
        if (item != null) {
            damage *= (1 + (.25 * item.getEnchantmentLevel(Enchantment.ARROW_DAMAGE)));
            punch = item.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK) > 0;
            flame = item.getEnchantmentLevel(Enchantment.ARROW_FIRE) > 0;
            multishot = item.getEnchantmentLevel(Enchantment.MULTISHOT) > 0 ? 3 : 1;
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
                    double duration = durations.get(data.getType())/8*60*20*multishot;
                    target.addPotionEffect(new PotionEffect(type,
                            data.isExtended() ? (int) (duration * 2.666) :
                                    data.isUpgraded() ? (int) (duration / 2) : (int) duration,
                            data.isUpgraded() ? 1 : 0));
                }
            }
        }
        if (punch)
            target.setVelocity(abstractArrow.getVelocity().setY(0.1).multiply(
                    item.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK)).multiply(multishot));
        if (flame)
            target.setFireTicks(100*multishot);
        if (damage >= 0) {
                target.damage((abstractArrow.isCritical() ?
                                damage + Math.random() * (damage / 2 + 1) : damage)*multishot,
                        (Player) abstractArrow.getShooter());
        } else {
            target.damage(2*multishot, (Player) abstractArrow.getShooter());
            if (target.isValid())
                target.setHealth(Math.min(target.getMaxHealth(), (target.getHealth() + Math.abs(damage) + 2)*multishot));
        }
        target.setArrowsInBody(target.getArrowsInBody()+multishot);
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
            }.runTaskLater(HoloItems.getInstance(), (long) spectralArrow.getGlowingTicks()*multishot);
        }
    }

    public static int addDurability(ItemStack item, double addend, LivingEntity player){
        if(player instanceof Player && ((Player) player).getGameMode()==GameMode.CREATIVE && addend<0)
            return 0;
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

        if(player instanceof Player)
            ((Player) player).spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(breaking));
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
                ItemMeta meta = item.getItemMeta();
                if(meta==null)
                    return;
                meta.getPersistentDataContainer().remove(cooldown);
                item.setItemMeta(meta);
            }
        }.runTaskLater(HoloItems.getInstance(), ticks);
    }

    public static boolean onCooldown(ItemStack item){
        return item.getItemMeta().getPersistentDataContainer().get(cooldown, PersistentDataType.INTEGER) != null;
    }

//    public static boolean cooldown(ItemStack item, double seconds){
//        ItemMeta meta = item.getItemMeta();
//        if(meta==null)
//            return false;
//        Long cooldown = meta.getPersistentDataContainer().get(Utility.cooldown, PersistentDataType.LONG);
//        Long nanoTime = System.nanoTime();
//        if(cooldown==null || nanoTime < cooldown || cooldown + seconds*1000000 < nanoTime){
//            meta.getPersistentDataContainer().set(Utility.cooldown, PersistentDataType.LONG, nanoTime);
//            item.setItemMeta(meta);
//            new BukkitRunnable(){
//                public void run(){
//                    ItemMeta meta = item.getItemMeta();
//                    if(meta==null)
//                        return;
//                    meta.getPersistentDataContainer().remove(Utility.cooldown);
//                    item.setItemMeta(meta);
//                }
//            }.runTaskLater(HoloItems.getInstance(), (int)(seconds*20));
//            return true;
//        }
//        else return false;
//    }

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

    public static boolean isInBoat(Player player){
        return player.isInsideVehicle() &&
                player.getVehicle().getType().equals(EntityType.BOAT);
    }

    public static int checkPotionEffect(LivingEntity entity, PotionEffectType type){
        if(entity.hasPotionEffect(type))
            return entity.getPotionEffect(type).getAmplifier()+1;
        return 0;
    }

    public static LivingEntity spawn(Location loc, World world, String base, String modifiers){
        try {
            EntityType type = EntityType.valueOf(base);
            LivingEntity entity = (LivingEntity) world.spawnEntity(loc, type);
            if(modifiers!=null) {
                entity.getPersistentDataContainer().set(Utility.pack, PersistentDataType.STRING, modifiers);
                for (String modifier : modifiers.split("-")) {
                    com.klin.holoItems.Item generic = Collections.findItem(modifier.substring(0, 2));
                    if (generic instanceof Spawnable)
                        ((Spawnable) generic).ability(entity, modifier.length()>2?modifier.substring(3):null);
                }
            }
            return entity;
        } catch(IllegalArgumentException e){
            return null;
        }
    }
}
