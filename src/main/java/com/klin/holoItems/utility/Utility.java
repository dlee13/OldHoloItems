package com.klin.holoItems.utility;

import com.klin.holoItems.Collections;
import com.klin.holoItems.Events;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.abstractClasses.Enchant;
import com.klin.holoItems.interfaces.customMobs.Spawnable;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.TileState;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Levelled;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.*;
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
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.bukkit.Material.*;

public class Utility {
    public static final NamespacedKey key = new NamespacedKey(HoloItems.getInstance(), "holoItems");
    public static final NamespacedKey stack = new NamespacedKey(HoloItems.getInstance(), "stack");
    public static final NamespacedKey cooldown = new NamespacedKey(HoloItems.getInstance(), "cooldown");
    public static final NamespacedKey enchant = new NamespacedKey(HoloItems.getInstance(), "enchant");
    public static final NamespacedKey pack = new NamespacedKey(HoloItems.getInstance(), "pack");
    public static final NamespacedKey data = new NamespacedKey(HoloItems.getInstance(), "data");
    public static final Map<BlockFace, Vector> cardinal = Map.of(
            BlockFace.NORTH, new Vector(0, 0, -1),
            BlockFace.SOUTH, new Vector(0, 0, 1),
            BlockFace.EAST, new Vector(1, 0, 0),
            BlockFace.WEST, new Vector(-1, 0, 0));
    public static final Map<BlockFace, BlockFace> left = Map.of(
            BlockFace.NORTH, BlockFace.WEST,
            BlockFace.SOUTH, BlockFace.EAST,
            BlockFace.EAST, BlockFace.NORTH,
            BlockFace.WEST, BlockFace.SOUTH);
    public static final Map<BlockFace, BlockFace> opposites = Map.of(
            BlockFace.UP, BlockFace.DOWN,
            BlockFace.DOWN, BlockFace.UP,
            BlockFace.NORTH, BlockFace.SOUTH,
            BlockFace.SOUTH, BlockFace.NORTH,
            BlockFace.EAST, BlockFace.WEST,
            BlockFace.WEST, BlockFace.EAST);
    public static final Set<Material> helmets = Set.of(LEATHER_HELMET, IRON_HELMET, CHAINMAIL_HELMET, GOLDEN_HELMET, DIAMOND_HELMET, NETHERITE_HELMET);
    public static final Set<Material> chestplates = Set.of(LEATHER_CHESTPLATE, IRON_CHESTPLATE, CHAINMAIL_CHESTPLATE, GOLDEN_CHESTPLATE, DIAMOND_CHESTPLATE, NETHERITE_CHESTPLATE);
    public static final Set<Material> leggings = Set.of(LEATHER_LEGGINGS, IRON_LEGGINGS, CHAINMAIL_LEGGINGS, GOLDEN_LEGGINGS, DIAMOND_LEGGINGS, NETHERITE_LEGGINGS);
    public static final Set<Material> boots = Set.of(LEATHER_BOOTS, IRON_BOOTS, CHAINMAIL_BOOTS, GOLDEN_BOOTS, DIAMOND_BOOTS, NETHERITE_BOOTS);
    public static final Set<Material> armor = new HashSet<>(){{
        addAll(Utility.helmets);
        addAll(Utility.chestplates);
        addAll(Utility.leggings);
        addAll(Utility.boots);
    }};
    public static final Set<Enchantment> enchantedBoots = Set.of(Enchantment.PROTECTION_EXPLOSIONS, Enchantment.DEPTH_STRIDER, Enchantment.PROTECTION_FALL, Enchantment.PROTECTION_FIRE, Enchantment.FROST_WALKER, Enchantment.MENDING, Enchantment.PROTECTION_PROJECTILE, Enchantment.PROTECTION_ENVIRONMENTAL, Enchantment.SOUL_SPEED, Enchantment.THORNS, Enchantment.DURABILITY);
    public static final Set<Material> fences = Set.of(Material.ACACIA_FENCE, Material.BIRCH_FENCE, Material.OAK_FENCE, Material.DARK_OAK_FENCE, Material.CRIMSON_FENCE, Material.JUNGLE_FENCE, Material.NETHER_BRICK_FENCE, Material.SPRUCE_FENCE, Material.WARPED_FENCE, Material.CHAIN, Material.IRON_BARS, Material.GLASS_PANE, Material.BLACK_STAINED_GLASS_PANE, Material.RED_STAINED_GLASS_PANE, Material.GREEN_STAINED_GLASS_PANE, Material.BLUE_STAINED_GLASS_PANE, Material.PURPLE_STAINED_GLASS_PANE, Material.CYAN_STAINED_GLASS_PANE, Material.LIGHT_GRAY_STAINED_GLASS_PANE, Material.GRAY_STAINED_GLASS_PANE, Material.PINK_STAINED_GLASS_PANE, Material.LIME_STAINED_GLASS_PANE, Material.YELLOW_STAINED_GLASS_PANE, Material.LIGHT_BLUE_STAINED_GLASS_PANE, Material.MAGENTA_STAINED_GLASS_PANE, Material.ORANGE_STAINED_GLASS_PANE, Material.WHITE_STAINED_GLASS_PANE, Material.BROWN_STAINED_GLASS_PANE, Material.COBBLESTONE_WALL, Material.MOSSY_COBBLESTONE_WALL,  Material.STONE_BRICK_WALL, Material.MOSSY_STONE_BRICK_WALL, Material.ANDESITE_WALL, Material.DIORITE_WALL, Material.GRANITE_WALL, Material.SANDSTONE_WALL, Material.RED_SANDSTONE_WALL, Material.BRICK_WALL, Material.PRISMARINE_WALL, Material.NETHER_BRICK_WALL, Material.RED_NETHER_BRICK_WALL, Material.END_STONE_BRICK_WALL, Material.BLACKSTONE_WALL, Material.POLISHED_BLACKSTONE_WALL, Material.POLISHED_BLACKSTONE_BRICK_WALL);
    public static final Set<Material> flowers = Set.of(RED_MUSHROOM, BROWN_MUSHROOM, DANDELION, POPPY, BLUE_ORCHID, ALLIUM, AZURE_BLUET, RED_TULIP, ORANGE_TULIP, WHITE_TULIP, PINK_TULIP, OXEYE_DAISY, CORNFLOWER, LILY_OF_THE_VALLEY, WITHER_ROSE);
    public static final Set<EntityType> humanoids = Set.of(EntityType.PLAYER, EntityType.ZOMBIE, EntityType.ZOMBIE_VILLAGER, EntityType.HUSK, EntityType.SKELETON, EntityType.STRAY, EntityType.PIGLIN_BRUTE, EntityType.PIGLIN, EntityType.ZOMBIFIED_PIGLIN, EntityType.DROWNED, EntityType.WITHER_SKELETON, EntityType.VILLAGER, EntityType.PILLAGER, EntityType.VINDICATOR, EntityType.EVOKER, EntityType.WITCH, EntityType.GIANT);
    public static final Set<Material> logs = Set.of(ACACIA_LOG, OAK_LOG, BIRCH_LOG, DARK_OAK_LOG, JUNGLE_LOG, SPRUCE_LOG, CRIMSON_STEM, WARPED_STEM, STRIPPED_ACACIA_LOG, STRIPPED_OAK_LOG, STRIPPED_BIRCH_LOG, STRIPPED_DARK_OAK_LOG, STRIPPED_JUNGLE_LOG, STRIPPED_SPRUCE_LOG, STRIPPED_CRIMSON_STEM, STRIPPED_WARPED_STEM, ACACIA_WOOD, OAK_WOOD, BIRCH_WOOD, DARK_OAK_WOOD, JUNGLE_WOOD, SPRUCE_WOOD, CRIMSON_HYPHAE, WARPED_HYPHAE, STRIPPED_ACACIA_WOOD, STRIPPED_OAK_WOOD, STRIPPED_BIRCH_WOOD, STRIPPED_DARK_OAK_WOOD, STRIPPED_JUNGLE_WOOD, STRIPPED_SPRUCE_WOOD, STRIPPED_CRIMSON_HYPHAE, STRIPPED_WARPED_HYPHAE, OAK_LEAVES, SPRUCE_LEAVES, BIRCH_LEAVES, JUNGLE_LEAVES, ACACIA_LEAVES, DARK_OAK_LEAVES, AZALEA_LEAVES, FLOWERING_AZALEA_LEAVES, NETHER_WART_BLOCK, WARPED_WART_BLOCK, MUSHROOM_STEM, BROWN_MUSHROOM_BLOCK, RED_MUSHROOM_BLOCK);
    public static final Set<Material> slabs = Set.of(OAK_SLAB, SPRUCE_SLAB, BIRCH_SLAB, JUNGLE_SLAB, ACACIA_SLAB, DARK_OAK_SLAB, CRIMSON_SLAB, WARPED_SLAB, PETRIFIED_OAK_SLAB, STONE_SLAB, SMOOTH_STONE_SLAB, COBBLESTONE_SLAB, MOSSY_COBBLESTONE_SLAB, STONE_BRICK_SLAB, MOSSY_STONE_BRICK_SLAB, ANDESITE_SLAB, POLISHED_ANDESITE_SLAB, DIORITE_SLAB, POLISHED_DIORITE_SLAB, GRANITE_SLAB, POLISHED_GRANITE_SLAB, SANDSTONE_SLAB, CUT_SANDSTONE_SLAB, SMOOTH_SANDSTONE_SLAB, RED_SANDSTONE_SLAB, CUT_RED_SANDSTONE_SLAB, SMOOTH_RED_SANDSTONE_SLAB, BRICK_SLAB, PRISMARINE_SLAB, PRISMARINE_BRICK_SLAB, DARK_PRISMARINE_SLAB, NETHER_BRICK_SLAB, RED_NETHER_BRICK_SLAB, QUARTZ_SLAB, SMOOTH_QUARTZ_SLAB, PURPUR_SLAB, END_STONE_BRICK_SLAB, BLACKSTONE_SLAB, POLISHED_BLACKSTONE_SLAB, POLISHED_BLACKSTONE_BRICK_SLAB);
    public static final Set<Material> spawnEggs = Set.of(BAT_SPAWN_EGG, BEE_SPAWN_EGG, BLAZE_SPAWN_EGG, CAT_SPAWN_EGG, CAVE_SPIDER_SPAWN_EGG, CHICKEN_SPAWN_EGG, COD_SPAWN_EGG, COW_SPAWN_EGG, CREEPER_SPAWN_EGG, DOLPHIN_SPAWN_EGG, DONKEY_SPAWN_EGG, DROWNED_SPAWN_EGG, ELDER_GUARDIAN_SPAWN_EGG, ENDERMAN_SPAWN_EGG, ENDERMITE_SPAWN_EGG, EVOKER_SPAWN_EGG, FOX_SPAWN_EGG, GHAST_SPAWN_EGG, GUARDIAN_SPAWN_EGG, HOGLIN_SPAWN_EGG, HORSE_SPAWN_EGG, HUSK_SPAWN_EGG, LLAMA_SPAWN_EGG, MAGMA_CUBE_SPAWN_EGG, MOOSHROOM_SPAWN_EGG, MULE_SPAWN_EGG, OCELOT_SPAWN_EGG, PANDA_SPAWN_EGG, PARROT_SPAWN_EGG, PHANTOM_SPAWN_EGG, PIG_SPAWN_EGG, PIGLIN_BRUTE_SPAWN_EGG, PIGLIN_SPAWN_EGG, PILLAGER_SPAWN_EGG, POLAR_BEAR_SPAWN_EGG, PUFFERFISH_SPAWN_EGG, RABBIT_SPAWN_EGG, RAVAGER_SPAWN_EGG, SALMON_SPAWN_EGG, SHEEP_SPAWN_EGG, SHULKER_SPAWN_EGG, SILVERFISH_SPAWN_EGG, SKELETON_HORSE_SPAWN_EGG, SKELETON_SPAWN_EGG, SLIME_SPAWN_EGG, SPIDER_SPAWN_EGG, SQUID_SPAWN_EGG, STRAY_SPAWN_EGG, STRIDER_SPAWN_EGG, TROPICAL_FISH_SPAWN_EGG, TURTLE_SPAWN_EGG, VEX_SPAWN_EGG, VILLAGER_SPAWN_EGG, VINDICATOR_SPAWN_EGG, WANDERING_TRADER_SPAWN_EGG, WITCH_SPAWN_EGG, WITHER_SKELETON_SPAWN_EGG, WOLF_SPAWN_EGG, ZOGLIN_SPAWN_EGG, ZOMBIE_HORSE_SPAWN_EGG, ZOMBIFIED_PIGLIN_SPAWN_EGG, ZOMBIE_SPAWN_EGG, ZOMBIE_VILLAGER_SPAWN_EGG);
    public static final Map<Material, Material> ageable = new LinkedHashMap<>() {{
        put(BEETROOT_SEEDS, BEETROOTS);
        put(CARROT, CARROTS);
        put(POTATO, POTATOES);
        put(NETHER_WART, NETHER_WART);
        put(CHORUS_FLOWER, CHORUS_FLOWER);
        put(WHEAT_SEEDS, WHEAT); }};
    public static final Map<Material, Material> sowable = new LinkedHashMap<>() {{
        put(BEETROOTS, BEETROOT_SEEDS);
        put(CARROTS, CARROT);
        put(POTATOES, POTATO);
        put(NETHER_WART, NETHER_WART);
        put(WHEAT, WHEAT_SEEDS); }};
    public static final Set<Material> axes = Set.of(DIAMOND_AXE, GOLDEN_AXE, IRON_AXE, STONE_AXE, NETHERITE_AXE, WOODEN_AXE);
    public static final Set<Material> hoes = Set.of(DIAMOND_HOE, GOLDEN_HOE, IRON_HOE, STONE_HOE, NETHERITE_HOE, WOODEN_HOE);
    public static final Set<Material> pickaxes = Set.of(DIAMOND_PICKAXE, GOLDEN_PICKAXE, IRON_PICKAXE, STONE_PICKAXE, NETHERITE_PICKAXE, WOODEN_PICKAXE);
    public static final Set<Material> shovels = Set.of(DIAMOND_SHOVEL, GOLDEN_SHOVEL, IRON_SHOVEL, STONE_SHOVEL, NETHERITE_SHOVEL, WOODEN_SHOVEL);
//    public static final Set<Material> swords = Set.of(DIAMOND_SWORD, GOLDEN_SWORD, IRON_SWORD, STONE_SWORD, NETHERITE_SWORD, WOODEN_SWORD);
    public static final Set<Material> fertile = Set.of(GRASS_BLOCK, DIRT, COARSE_DIRT, PODZOL, FARMLAND, MYCELIUM, ROOTED_DIRT);
    public static final Map<String, Set<Material>> dirt = new HashMap<>() {{
        put("SAPLING", fertile);
        put("FUNGUS", new HashSet<>() {{
            addAll(fertile);
            add(CRIMSON_NYLIUM);
            add(WARPED_NYLIUM);
            add(SOUL_SOIL);
        }}); }};
    public static final Map<PotionType, Integer> durations = new HashMap<>() {{
        put(PotionType.FIRE_RESISTANCE, 3600);
        put(PotionType.INSTANT_DAMAGE, 0);
        put(PotionType.INSTANT_HEAL, 0);
        put(PotionType.INVISIBILITY, 3600);
        put(PotionType.JUMP, 3600);
        put(PotionType.LUCK, 6000);
        put(PotionType.NIGHT_VISION, 3600);
        put(PotionType.POISON, 900);
        put(PotionType.REGEN, 900);
        put(PotionType.SLOW_FALLING, 1800);
        put(PotionType.SLOWNESS, 1800);
        put(PotionType.SPEED, 3600);
        put(PotionType.STRENGTH, 3600);
        put(PotionType.TURTLE_MASTER, 400);
        put(PotionType.WATER_BREATHING, 3600);
        put(PotionType.WEAKNESS, 1800); }};
    public static final HashMap<DyeColor, Set<Material>> colors = new HashMap<>(){{
        put(DyeColor.BLACK, Stream.of(/*BLACK_BED, BLACK_CARPET,*/ BLACK_CONCRETE/*, BLACK_CONCRETE_POWDER*/, BLACK_GLAZED_TERRACOTTA/*, BLACK_SHULKER_BOX*/, BLACK_STAINED_GLASS, BLACK_STAINED_GLASS_PANE, BLACK_TERRACOTTA, BLACK_WOOL, BLACKSTONE, BLACKSTONE_SLAB, BLACKSTONE_STAIRS, BLACKSTONE_WALL, COAL_BLOCK, CHISELED_POLISHED_BLACKSTONE, CRACKED_POLISHED_BLACKSTONE_BRICKS, GILDED_BLACKSTONE, OBSIDIAN, POLISHED_BLACKSTONE, POLISHED_BLACKSTONE_BRICK_SLAB, POLISHED_BLACKSTONE_BRICK_STAIRS, POLISHED_BLACKSTONE_BRICK_WALL, POLISHED_BLACKSTONE_BRICKS/*, POLISHED_BLACKSTONE_PRESSURE_PLATE*/, POLISHED_BLACKSTONE_SLAB, POLISHED_BLACKSTONE_STAIRS, POLISHED_BLACKSTONE_WALL).collect(Collectors.toSet()));
        put(DyeColor.GRAY, Stream.of(ANDESITE, BASALT, NETHERITE_BLOCK, COBBLESTONE, COBBLESTONE_SLAB, COBBLESTONE_STAIRS/*, GRAY_BED, GRAY_CARPET*/, GRAY_CONCRETE/*, GRAY_CONCRETE_POWDER*/, GRAY_GLAZED_TERRACOTTA/*, GRAY_SHULKER_BOX*/, GRAY_STAINED_GLASS, GRAY_STAINED_GLASS_PANE, GRAY_TERRACOTTA, GRAY_WOOL, POLISHED_ANDESITE, POLISHED_BASALT, TUFF).collect(Collectors.toSet()));
        put(DyeColor.LIGHT_GRAY, Stream.of(IRON_BLOCK, CLAY/*, HEAVY_WEIGHTED_PRESSURE_PLATE, LIGHT_GRAY_BED, LIGHT_GRAY_CARPET*/, LIGHT_GRAY_CONCRETE/*, LIGHT_GRAY_CONCRETE_POWDER*/, LIGHT_GRAY_GLAZED_TERRACOTTA/*, LIGHT_GRAY_SHULKER_BOX*/, LIGHT_GRAY_STAINED_GLASS, LIGHT_GRAY_STAINED_GLASS_PANE, LIGHT_GRAY_TERRACOTTA, LIGHT_GRAY_WOOL, LODESTONE).collect(Collectors.toSet()));
        put(DyeColor.WHITE, Stream.of(QUARTZ_BLOCK, BONE_BLOCK, CALCITE, CHISELED_QUARTZ_BLOCK, DIORITE, DIORITE_SLAB, DIORITE_STAIRS, DIORITE_WALL, END_STONE_BRICK_SLAB, END_STONE_BRICK_STAIRS, END_STONE_BRICK_WALL, END_STONE_BRICKS, POLISHED_DIORITE, POLISHED_DIORITE_SLAB, POLISHED_DIORITE_STAIRS, QUARTZ_BRICKS, QUARTZ_PILLAR, SMOOTH_QUARTZ, SMOOTH_QUARTZ_SLAB, SMOOTH_QUARTZ_STAIRS, SNOW_BLOCK/*, WHITE_BED, WHITE_CARPET*/, WHITE_CONCRETE/*, WHITE_CONCRETE_POWDER*/, WHITE_GLAZED_TERRACOTTA/*, WHITE_SHULKER_BOX*/, WHITE_STAINED_GLASS, WHITE_STAINED_GLASS_PANE, WHITE_TERRACOTTA, WHITE_WOOL).collect(Collectors.toSet()));
        put(DyeColor.YELLOW, Stream.of(GOLD_BLOCK, HAY_BLOCK/*, LIGHT_WEIGHTED_PRESSURE_PLATE*/, SPONGE, WET_SPONGE/*, YELLOW_BED, YELLOW_CARPET*/, YELLOW_CONCRETE/*, YELLOW_CONCRETE_POWDER*/, YELLOW_GLAZED_TERRACOTTA/*, YELLOW_SHULKER_BOX*/, YELLOW_STAINED_GLASS, YELLOW_STAINED_GLASS_PANE, YELLOW_TERRACOTTA, YELLOW_WOOL).collect(Collectors.toSet()));
        put(DyeColor.ORANGE, Stream.of(CUT_RED_SANDSTONE_SLAB/*, ORANGE_BED, ORANGE_CARPET*/, ORANGE_CONCRETE/*, ORANGE_CONCRETE_POWDER*/, ORANGE_GLAZED_TERRACOTTA/*, ORANGE_SHULKER_BOX*/, ORANGE_STAINED_GLASS, ORANGE_STAINED_GLASS_PANE, ORANGE_TERRACOTTA, ORANGE_WOOL, RED_SANDSTONE_WALL, SMOOTH_RED_SANDSTONE_SLAB, SMOOTH_RED_SANDSTONE_STAIRS).collect(Collectors.toSet()));
        put(DyeColor.RED, Stream.of(REDSTONE_BLOCK, CHISELED_NETHER_BRICKS, CRACKED_NETHER_BRICKS, CRIMSON_HYPHAE, CRIMSON_NYLIUM, CRIMSON_PLANKS/*, CRIMSON_PRESSURE_PLATE*/, CRIMSON_SLAB, CRIMSON_STAIRS, CRIMSON_STEM, NETHER_BRICK_WALL, NETHER_BRICKS, NETHER_WART_BLOCK/*, RED_BED, RED_CARPET, RED_CONCRETE, RED_CONCRETE_POWDER*/, RED_GLAZED_TERRACOTTA, RED_MUSHROOM_BLOCK, RED_NETHER_BRICK_SLAB, RED_NETHER_BRICK_STAIRS, RED_NETHER_BRICK_WALL, RED_NETHER_BRICKS/*, RED_SHULKER_BOX*/, RED_STAINED_GLASS, RED_STAINED_GLASS_PANE, RED_TERRACOTTA, RED_WOOL, STRIPPED_CRIMSON_HYPHAE, STRIPPED_CRIMSON_STEM).collect(Collectors.toSet()));
        put(DyeColor.PINK, Stream.of(/*PINK_BED, PINK_CARPET,*/ PINK_CONCRETE/*, PINK_CONCRETE_POWDER*/, PINK_GLAZED_TERRACOTTA/*, PINK_SHULKER_BOX*/, PINK_STAINED_GLASS, PINK_STAINED_GLASS_PANE, PINK_TERRACOTTA, PINK_WOOL).collect(Collectors.toSet()));
        put(DyeColor.MAGENTA, Stream.of(/*MAGENTA_BED, MAGENTA_CARPET,*/ MAGENTA_CONCRETE/*, MAGENTA_CONCRETE_POWDER*/, MAGENTA_GLAZED_TERRACOTTA/*, MAGENTA_SHULKER_BOX*/, MAGENTA_STAINED_GLASS, MAGENTA_STAINED_GLASS_PANE, MAGENTA_TERRACOTTA, MAGENTA_WOOL).collect(Collectors.toSet()));
        put(DyeColor.PURPLE, Stream.of(AMETHYST_BLOCK, BUDDING_AMETHYST, CRYING_OBSIDIAN/*, PURPLE_BED, PURPLE_CARPET*/, PURPLE_CONCRETE/*, PURPLE_CONCRETE_POWDER*/, PURPLE_GLAZED_TERRACOTTA/*, PURPLE_SHULKER_BOX*/, PURPLE_STAINED_GLASS, PURPLE_STAINED_GLASS_PANE, PURPLE_TERRACOTTA, PURPLE_WOOL, PURPUR_BLOCK, PURPUR_PILLAR, PURPUR_SLAB, PURPUR_STAIRS).collect(Collectors.toSet()));
        put(DyeColor.BLUE, Stream.of(LAPIS_BLOCK/*, BLUE_BED, BLUE_CARPET*/, BLUE_CONCRETE/*, BLUE_CONCRETE_POWDER*/, BLUE_GLAZED_TERRACOTTA, BLUE_ICE/*, BLUE_SHULKER_BOX*/, BLUE_STAINED_GLASS, BLUE_STAINED_GLASS_PANE, BLUE_TERRACOTTA, BLUE_WOOL).collect(Collectors.toSet()));
        put(DyeColor.LIGHT_BLUE, Stream.of(DIAMOND_BLOCK/*, LIGHT_BLUE_BED, LIGHT_BLUE_CARPET*/, LIGHT_BLUE_CONCRETE/*, LIGHT_BLUE_CONCRETE_POWDER*/, LIGHT_BLUE_GLAZED_TERRACOTTA/*, LIGHT_BLUE_SHULKER_BOX*/, LIGHT_BLUE_STAINED_GLASS, LIGHT_BLUE_STAINED_GLASS_PANE, LIGHT_BLUE_TERRACOTTA, LIGHT_BLUE_WOOL).collect(Collectors.toSet()));
        put(DyeColor.CYAN, Stream.of(/*CYAN_BED, CYAN_CARPET,*/ CYAN_CONCRETE/*, CYAN_CONCRETE_POWDER*/, CYAN_GLAZED_TERRACOTTA/*, CYAN_SHULKER_BOX*/, CYAN_STAINED_GLASS, CYAN_STAINED_GLASS_PANE, CYAN_TERRACOTTA, CYAN_WOOL, DARK_PRISMARINE, DARK_PRISMARINE_SLAB, DARK_PRISMARINE_STAIRS, PRISMARINE, PRISMARINE_BRICK_SLAB, PRISMARINE_BRICK_STAIRS, PRISMARINE_BRICKS, PRISMARINE_STAIRS, PRISMARINE_WALL, STRIPPED_WARPED_HYPHAE, STRIPPED_WARPED_STEM, WARPED_PLANKS/*, WARPED_PRESSURE_PLATE*/, WARPED_SLAB, WARPED_STAIRS, WARPED_STEM, WARPED_WART_BLOCK).collect(Collectors.toSet()));
        put(DyeColor.GREEN, Stream.of(EMERALD_BLOCK/*, GREEN_BED, GREEN_CARPET*/, GREEN_CONCRETE/*, GREEN_CONCRETE_POWDER*/, GREEN_GLAZED_TERRACOTTA/*, GREEN_SHULKER_BOX*/, GREEN_STAINED_GLASS, GREEN_STAINED_GLASS_PANE, GREEN_TERRACOTTA, GREEN_WOOL, MOSS_BLOCK/*, MOSS_CARPET*/).collect(Collectors.toSet()));
        put(DyeColor.LIME, Stream.of(/*LIME_BED, LIME_CARPET,*/ LIME_CONCRETE/*, LIME_CONCRETE_POWDER*/, LIME_GLAZED_TERRACOTTA/*, LIME_SHULKER_BOX*/, LIME_STAINED_GLASS, LIME_STAINED_GLASS_PANE, LIME_TERRACOTTA, LIME_WOOL, SLIME_BLOCK).collect(Collectors.toSet()));
        put(DyeColor.BROWN, Stream.of(ANCIENT_DEBRIS/*, BROWN_BED, BROWN_CARPET*/, BROWN_CONCRETE/*, BROWN_CONCRETE_POWDER*/, BROWN_GLAZED_TERRACOTTA, BROWN_MUSHROOM_BLOCK/*, BROWN_SHULKER_BOX*/, BROWN_STAINED_GLASS, BROWN_STAINED_GLASS_PANE, BROWN_TERRACOTTA, BROWN_WOOL, COARSE_DIRT, DIRT, PODZOL, ROOTED_DIRT).collect(Collectors.toSet())); }};
    public static boolean test = false;

    public static <T> T findItem(ItemStack item, Class<T> cls){
        if(item==null || item.getType()==Material.AIR || item.getItemMeta()==null)
            return null;
        return findItem(item.getItemMeta().getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING), cls);
    }

    public static <T> T findItem(ItemStack item, Class<T> cls, Player player){
        if(item==null || item.getType()==Material.AIR || item.getItemMeta()==null)
            return null;
        return findItem(item.getItemMeta().getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING), cls, player);
    }

    public static <T> T findItem(Entity entity, Class<T> cls){
        return findItem(entity.getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING), cls);

    }

    public static <T> T findItem(Entity entity, Class<T> cls, Player player){
        return findItem(entity.getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING), cls, player);
    }

    public static <T> T findItem(Block block, Class<T> cls){
        BlockState state = block.getState();
        if(!(state instanceof TileState))
            return null;
        return findItem(((TileState) state).getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING), cls);
    }

    public static <T> T findItem(Block block, Class<T> cls, Player player){
        BlockState state = block.getState();
        if(!(state instanceof TileState))
            return null;
        return findItem(((TileState) state).getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING), cls, player);
    }

    public static <T> T findItem(String id, Class<T> cls){
        if(id==null || Collections.disabled.contains(id))
            return null;
        Item generic = Collections.items.get(id);
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
        Item generic = Collections.items.get(id);
        if(!cls.isInstance(generic))
            return null;
        return cls.cast(generic);
    }

    public static ItemStack process(String name, Material material, int quantity, String lore, int durability, boolean shiny){
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
        //boolean enchant = meta.getPersistentDataContainer().get(Utility.enchant, PersistentDataType.STRING)!=null;
        else if(meta.hasEnchants())
            list.add(0, "");
        meta.setLore(list);
        int hash = 5;
        for (int i=0; i<name.length(); i++)
            hash = hash*3 + name.charAt(i);
        meta.setCustomModelData(hash);
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, name);
        item.setItemMeta(meta);

        return item;
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

    public static String formatType(Material type){
        String str = "";
        for(String s : type.toString().split("_")){
            if(s.length()<1)
                return "";
            str += " " + s.charAt(0) + s.substring(1).toLowerCase();
        }
        return str.substring(1);
    }

    public static List<String> processStr(String str){
        if(str==null)
            return new ArrayList<>(List.of("§7Crafting ingredient"));
        List<String> list = new ArrayList<>(List.of("§bAbility"));
        String[] lines = str.split("\n");
        for(String line : lines)
            list.add("§7"+line);
        return list;
    }

    public static String processType(String type){
        String string = "";
        for(String word : type.split("_"))
            string += word.charAt(0)+word.substring(1).toLowerCase()+" ";
        return string.substring(0, string.length()-1);
    }

    public static ItemStack addEnchant(ItemStack itemStack, Enchant enchant){
        Set<Enchantment> exclusive = enchant.exclusive;
        if(exclusive!=null) {
            for (Enchantment enchantment : itemStack.getEnchantments().keySet()) {
                if (exclusive.contains(enchantment))
                    itemStack.removeEnchantment(enchantment);
            }
        }
        ItemMeta meta = itemStack.getItemMeta();
        String enchantments = meta.getPersistentDataContainer().get(Utility.enchant, PersistentDataType.STRING);
        if(enchantments==null)
            enchantments = "";
        else
            enchantments += " ";
        meta.getPersistentDataContainer().set(Utility.enchant, PersistentDataType.STRING, enchantments+enchant.name);
        List<String> lore = meta.getLore();
        if(lore==null) {
            if(meta.isUnbreakable())
                lore = new ArrayList<>(List.of(""));
            else
                lore = new ArrayList<>();
        } else if(lore.get(0).startsWith("§b"))
            lore.add(0, "");
        lore.add(0, "§7"+formatName(enchant.name));
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static boolean damage(ItemStack item, double damage, boolean crit,
                              LivingEntity attacker, LivingEntity target,
                              boolean strength, boolean projectile, boolean bypass){
        if(!fireBlank(attacker, target))
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
                    double duration = durations.get(data.getType())/8*multishot;
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

    public static boolean fireBlank(Entity damager, Entity damagee){
        EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(damager, damagee, EntityDamageEvent.DamageCause.CUSTOM, 0);
        Bukkit.getServer().getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    public static int add(Map<String, Integer> collection){
        if(collection==null)
            return 0;
        int total = 0;
        for(int quantity : collection.values())
            total += quantity;
        return total;
    }

    public static int addDurability(ItemStack item, double addend, LivingEntity player){
        if(player instanceof Player && ((Player) player).getGameMode()==GameMode.CREATIVE && addend<0)
            return 0;
        if(addend==0 || item==null)
            return 0;
        ItemMeta meta = item.getItemMeta();
        if(meta==null)
            return 0;
        if(!meta.isUnbreakable() && meta instanceof Damageable){
            Damageable damageable = (Damageable) meta;
            int total = damageable.getDamage();
            int excess = 0;
            if(addend<0) {
                //adding (-= negative) damage
                total -= addend;
                if(total>=item.getType().getMaxDurability()){
                    item.setAmount(0);
                    if(player instanceof Player)
                        Events.manageDurability((Player) player);
                    return -1;
                }
            } else {
                total -= addend * 2;
                if (total < 0) {
                    excess = -total;
                    total = 0;
                }
            }
            damageable.setDamage(total);
            item.setItemMeta(meta);
            return excess;
        }
        List<String> lore = meta.getLore();
        int[] durability = getDurability(lore);
        if(durability==null)
            return 0;
        if(durability[0]==-1 || addend>0 && durability[0]==durability[1])
            return (int) addend;
        int unbreaking = meta.getEnchantLevel(Enchantment.DURABILITY);
        int total = durability[0]+(int) (addend*2);
        if(addend<0 && Math.random()<(1f/(unbreaking+1))) {
            durability[0] = durability[0]+(int) addend;
            if(durability[0]<=0){
                item.setAmount(0);
                if(player instanceof Player)
                    Events.manageDurability((Player) player);
                return -1;
            }
        } else if(addend>0) {
            if(total>durability[1]) {
                durability[0] = durability[1];
                total = (total - durability[1])/2;
            }
            else {
                durability[0] = total;
                total = 0;
            }
        } else
            return (int) addend;
        String breaking = "§fDurability: "+durability[0]+"/"+durability[1];
        lore.set(lore.size()-1, breaking);
        meta.setLore(lore);
        item.setItemMeta(meta);

        if(player instanceof Player) {
            Player p = (Player) player;
            if(Events.manageDurability(p))
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(breaking));
        }
        return total;
    }

    public static int[] getDurability(ItemStack item){
        if(item==null)
            return null;
        ItemMeta meta = item.getItemMeta();
        if(meta==null)
            return null;
        return getDurability(meta.getLore());
    }

    public static int[] getDurability(List<String> lore){
        if(lore==null || lore.isEmpty())
            return null;
        int[] durability = new int[2];
        String status = lore.get(lore.size()-1);
        if(!status.contains("/") || status.length()<15)
            return null;
        int index = status.indexOf("/");
        if(index<15)
            return null;
        durability[0] = Integer.parseInt(status.substring(14, index));
        durability[1] = Integer.parseInt(status.substring(index+1));
        return durability;
    }

    public static void cooldown(ItemStack item, int ticks){
        cooldown(item);
        new BukkitRunnable(){
            public void run(){
                removeCooldown(item);
            }
        }.runTaskLater(HoloItems.getInstance(), ticks);
    }

    public static void cooldown(ItemStack item){
        ItemMeta meta = item.getItemMeta();
        if(meta==null)
            return;
        meta.getPersistentDataContainer().set(cooldown, PersistentDataType.INTEGER, 1);
        item.setItemMeta(meta);
    }

    public static void removeCooldown(ItemStack item){
        ItemMeta meta = item.getItemMeta();
        if(meta==null)
            return;
        meta.getPersistentDataContainer().remove(cooldown);
        item.setItemMeta(meta);
    }

    public static boolean onCooldown(ItemStack item){
        return item.getItemMeta().getPersistentDataContainer().get(cooldown, PersistentDataType.INTEGER) != null;
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

    public static int deplete(ItemStack item, Player player, int total){
        ItemMeta meta = item.getItemMeta();
        Integer charge = meta.getPersistentDataContainer().get(Utility.pack, PersistentDataType.INTEGER);
        if(charge==null || charge==0)
            return -1;
        else {
            charge -= 1;
            meta.getPersistentDataContainer().
                    set(Utility.pack, PersistentDataType.INTEGER, charge);
            item.setItemMeta(meta);
            if(player!=null)
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("Remaining: "+charge+"/"+total));
            return charge;
        }
    }

    public static int inspect(ItemStack item){
        ItemMeta meta = item.getItemMeta();
        Integer charge = meta.getPersistentDataContainer().get(Utility.pack, PersistentDataType.INTEGER);
        if(charge==null || charge==0)
            return -1;
        else {
            charge -= 1;
            return charge;
        }
    }

    public static int checkPotionEffect(LivingEntity entity, PotionEffectType type){
        if(entity.hasPotionEffect(type))
            return entity.getPotionEffect(type).getAmplifier()+1;
        return 0;
    }

    public static LivingEntity spawn(Location loc, World world, String base, String modifiers){
        EntityType type;
        try {
            type = EntityType.valueOf(base);
        } catch(IllegalArgumentException e){return null;}
        return spawn(loc, world, type, modifiers);
    }

    public static LivingEntity spawn(Location loc, World world, EntityType type, String modifiers){
        LivingEntity entity = (LivingEntity) world.spawnEntity(loc, type);
        if(modifiers!=null) {
            String active = "";
            for (String modifier : modifiers.split("~")) {
                int index = modifier.indexOf(":");
                com.klin.holoItems.Item generic = Collections.items.get(index<0 ? modifier : modifier.substring(0, index));
                if (generic instanceof Spawnable) {
                    ((Spawnable) generic).ability(entity, index<0 ? null : modifier.substring(index+1));
                    if(generic.getClass().getInterfaces().length>1 || generic.getClass().getSuperclass()!=Item.class)
                        active += "~"+modifier;
                }
                else
                    active += "~"+modifier;
            }
            if(!active.isEmpty())
                entity.getPersistentDataContainer().set(Utility.pack, PersistentDataType.STRING, active.substring(1));
        }
        return entity;
    }

    public static <E> Optional<E> getRandom (Collection<E> e) {
        if(e==null)
            return Optional.empty();
        return e.stream()
                .skip((int) (e.size() * Math.random()))
                .findFirst();
    }

    public static Map<Block, BlockData> explode(Location loc, int radius, Map<Material, Material> remove){
        World world = loc.getWorld();
        Map<Block, BlockData> blast = new HashMap<>();
        for(int i=-1*radius; i<=radius; i++){
            for(int j=-1*radius; j<=radius; j++) {
                for (int k=-1*radius; k<=radius; k++) {
                    Block block = world.getBlockAt(loc.clone().add(i, j, k));
                    if(block.isPassable())
                        continue;
                    double distance = Math.sqrt(Math.pow(i, 2)+Math.pow(j, 2)+Math.pow(k, 2));
                    if(distance>radius)
                        continue;
                    else{
                        double offset = ((double) radius)/2-1;
                        double difference = distance-(radius-offset);
                        if((offset-difference)/offset<Math.random())
                            continue;
                    }
                    blast.put(block, block.getBlockData());
                    if(remove!=null) {
                        Material type = remove.get(block.getType());
                        if(type!=null)
                            block.setType(type);
                    }
                }
            }
        }
        if(radius<3)
            world.spawnParticle(Particle.EXPLOSION_LARGE, loc, 1);
        else
            world.spawnParticle(Particle.EXPLOSION_HUGE, loc, 1);
        world.playSound(loc, Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, ((float) radius)/10, 1f);
        return blast;
    }

    public static void vacuum(Block center, Material type, int frequency, int i){
        Queue<Block> vacuum = new LinkedList<>();
        Set<Block> checked = new HashSet<>();
        vacuum.add(center);
        checked.add(center);
        new Task(HoloItems.getInstance(), 0, frequency){
            int increment = i;
            public void run(){
                if(increment<0 || vacuum.isEmpty()){
                    cancel();
                    return;
                }
                for(int i=0; i<4; i++) {
                    Block center = vacuum.poll();
                    if(center==null)
                        break;
                    center.setType(Material.AIR);
                    for (BlockFace face : opposites.keySet()) {
                        Block block = center.getRelative(face);
                        if (block.getType()==type && !checked.contains(block)) {
                            vacuum.add(block);
                            checked.add(block);
                        }
                    }
                    increment--;
                    if(increment<0)
                        break;
                }
            }
        };
    }

    public static List<Block> vacuum(Block block, Material type, int limit, boolean remove){
        Queue<Block> vacuum = new LinkedList<>();
        Set<Block> checked = new HashSet<>();
        vacuum.add(block);
        checked.add(block);
        boolean levelled = type==WATER;
        List<Block> source = new ArrayList<>();
        while(!vacuum.isEmpty() && checked.size()<limit) {
            Block center = vacuum.poll();
            if (center==null)
                break;
            if(remove)
                center.setType(Material.AIR);
            for (BlockFace face : opposites.keySet()) {
                Block relative = center.getRelative(face);
                if(relative.getType()==type && !checked.contains(relative)) {
                    vacuum.add(relative);
                    checked.add(relative);
                    if (levelled && ((Levelled) relative.getBlockData()).getLevel()==0)
                        source.add(relative);
                }
            }
        }
        return source;
    }

    public static void mirror(ShapedRecipe recipe, String name, ItemStack item){
        String[] shape = recipe.getShape();
        Map<Character, ItemStack> ingredientMap = recipe.getIngredientMap();
        recipe = new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"_"), item);
        for(int i=0; i<shape.length; i++)
            shape[i] = new StringBuilder(shape[i]).reverse().toString();
        switch(shape.length){
            case 1:
                recipe.shape(shape[0]);
                break;
            case 2:
                recipe.shape(shape[0], shape[1]);
                break;
            case 3:
                recipe.shape(shape[0], shape[1], shape[2]);
                break;
            default: return;
        }
        for(Character character : ingredientMap.keySet()) {
            ItemStack ingredient = ingredientMap.get(character);
            if(ingredient!=null)
                recipe.setIngredient(character, ingredient.getType());
        }
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    private static Map<Player, String> messageBuffer = new HashMap<>();

    public static void actionMessage(Player player, String str){
        String message = messageBuffer.get(player);
        if(message!=null){
            messageBuffer.replace(player, message + str);
            return;
        } else messageBuffer.put(player, str);
        new BukkitRunnable(){
            public void run(){
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(messageBuffer.get(player)));
            }
        }.runTask(HoloItems.getInstance());
    }

    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    @SuppressWarnings("deprecation")
    public static int nextEntityId() {
        return Bukkit.getUnsafe().nextEntityId();
    }

    public static UUID randomUUID() {
        return new UUID(RANDOM.nextLong(), RANDOM.nextLong());
    }
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