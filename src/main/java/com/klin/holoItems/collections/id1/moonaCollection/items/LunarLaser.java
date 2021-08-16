package com.klin.holoItems.collections.id1.moonaCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.abstractClasses.Enchant;
import com.klin.holoItems.collections.id1.moonaCollection.MoonaCollection;
import com.klin.holoItems.interfaces.Interactable;
import com.klin.holoItems.utility.Utility;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LunarLaser extends Enchant implements Interactable {
    public static final String name = "lunarLaser";
    private static final Set<Material> glass = Stream.of(
            Material.GLASS, Material.GLASS_PANE,
            Material.BLACK_STAINED_GLASS, Material.BLACK_STAINED_GLASS_PANE,
            Material.RED_STAINED_GLASS, Material.RED_STAINED_GLASS_PANE,
            Material.GREEN_STAINED_GLASS, Material.GREEN_STAINED_GLASS_PANE,
            Material.BLUE_STAINED_GLASS, Material.BLUE_STAINED_GLASS_PANE,
            Material.PURPLE_STAINED_GLASS, Material.PURPLE_STAINED_GLASS_PANE,
            Material.CYAN_STAINED_GLASS, Material.CYAN_STAINED_GLASS_PANE,
            Material.LIGHT_GRAY_STAINED_GLASS, Material.LIGHT_GRAY_STAINED_GLASS_PANE,
            Material.GRAY_STAINED_GLASS, Material.GRAY_STAINED_GLASS_PANE,
            Material.PINK_STAINED_GLASS, Material.PINK_STAINED_GLASS_PANE,
            Material.LIME_STAINED_GLASS, Material.LIME_STAINED_GLASS_PANE,
            Material.YELLOW_STAINED_GLASS, Material.YELLOW_STAINED_GLASS_PANE,
            Material.LIGHT_BLUE_STAINED_GLASS, Material.LIGHT_BLUE_STAINED_GLASS_PANE,
            Material.MAGENTA_STAINED_GLASS, Material.MAGENTA_STAINED_GLASS_PANE,
            Material.ORANGE_STAINED_GLASS, Material.ORANGE_STAINED_GLASS_PANE,
            Material.WHITE_STAINED_GLASS, Material.WHITE_STAINED_GLASS_PANE,
            Material.BROWN_STAINED_GLASS, Material.BROWN_STAINED_GLASS_PANE
            ).collect(Collectors.toCollection(HashSet::new));
    public static final Set<Enchantment> accepted = Stream.of(Enchantment.SILK_TOUCH, Enchantment.MENDING).collect(Collectors.toCollection(HashSet::new));
    public static final Set<String> acceptedIds = Stream.of("a2", "u1").collect(Collectors.toCollection(HashSet::new));
    public static final Set<Material> acceptedTypes = new HashSet<>(){{
        addAll(Utility.axes);
        addAll(Utility.shovels);
        addAll(Utility.hoes);
        addAll(Utility.pickaxes);
    }};
    public static final int expCost = 32;

    private static final Material material = Material.IRON_SWORD;
    private static final String lore =
            "Cut through glass";
    private static final int durability = 250;
    private static final boolean shiny = true;

    public static final int cost = 0;
    public static final char key = '1';

    public LunarLaser(){
        super(name, accepted, material, lore, durability, shiny, cost,
                ""+MoonaCollection.key+key, key, acceptedIds, acceptedTypes, expCost);
    }

    public void registerRecipes(){
        ShapedRecipe recipe =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("&&&","&*&","###");
        recipe.setIngredient('&', Material.TINTED_GLASS);
        recipe.setIngredient('*', Material.NETHER_STAR);
        recipe.setIngredient('#', Material.OBSIDIAN);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(PlayerInteractEvent event, Action action){
        if(action==Action.LEFT_CLICK_AIR)
            return;
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if(action==Action.LEFT_CLICK_BLOCK){
            Block block = event.getClickedBlock();
            if(glass.contains(block.getType())) {
                BlockBreakEvent breakEvent = new BlockBreakEvent(block, player);
                Bukkit.getServer().getPluginManager().callEvent(breakEvent);
                if(!breakEvent.isCancelled())
                    event.getClickedBlock().breakNaturally(item);
            }
            return;
        }
        Location loc = player.getEyeLocation();
        World world = loc.getWorld();
        if(world.getTime() < 13000 || world.getHighestBlockAt(loc, HeightMap.OCEAN_FLOOR).getY()-1 > loc.getY())
            return;
        RayTraceResult result = world.rayTraceEntities(loc, loc.getDirection(), 50, 0.5,
                entity -> (entity != player && entity instanceof LivingEntity && !(entity instanceof ArmorStand)));
        if (result==null || result.getHitEntity()==null)
            return;

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        String id = container.get(Utility.key, PersistentDataType.STRING);
        if(id!=null && id.equals(this.id) &&
                container.get(Utility.cooldown, PersistentDataType.STRING)==null) {
            List<String> lore = meta.getLore();
            String durability = lore.remove(lore.size()-1);
            lore.remove(lore.size()-1);
            lore.add("§7and highlights foes under the");
            lore.add("§7moonlight");
            lore.add("");
            lore.add(durability);
            meta.setLore(lore);
            container.set(Utility.cooldown, PersistentDataType.STRING, "");
            item.setItemMeta(meta);
        }
        LivingEntity entity = (LivingEntity) result.getHitEntity();
        if(entity.isGlowing())
            return;

        entity.setGlowing(true);
        Utility.addDurability(item, -1, player);
        new BukkitRunnable(){
            public void run(){
                entity.setGlowing(false);
            }
        }.runTaskLater(HoloItems.getInstance(), 100);
    }
}