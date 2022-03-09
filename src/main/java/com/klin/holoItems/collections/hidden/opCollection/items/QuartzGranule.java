package com.klin.holoItems.collections.hidden.opCollection.items;

import com.klin.holoItems.Collections;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.misc.ingredientsCollection.items.QuartzFragment;
import com.klin.holoItems.interfaces.Breakable;
import com.klin.holoItems.interfaces.Harmable;
import com.klin.holoItems.interfaces.Placeable;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Skull;
import org.bukkit.entity.*;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class QuartzGranule extends Item implements Placeable, Breakable, Harmable {
    public static final String name = "quartzGranule";
    private static final String endstoneBase64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2NkOWMzNzg0N2IxNDRiYzI2ZTM2ZmJhNWIzYzdjOGFjYzI1NTNhMDRkODQ0YjI1ZjhmMzNmNDlmMjEwMzFiZSJ9fX0=";
    private final Map<Material, Material> keys;
    private final Map<Material, Set<Material>> values;
    int increment;
    private Block center;
    private final Set<String> names;
    private int uses;
    private int hits;
    private int misses;

    private static final Material material = Material.SKELETON_SKULL;
    private static final int quantity = 1;
    private static final String lore =
            "Mine saint quartz fragments";
    private static final int durability = 0;
    public static final boolean stackable = true;
    private static final boolean shiny = false;
    public static final int cost = -1;

    public QuartzGranule(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost);
        keys = new HashMap<>();
        for(Material type : Utility.axes)
            keys.put(type, Material.IRON_AXE);
        for(Material type : Utility.pickaxes)
            keys.put(type, Material.IRON_PICKAXE);
        keys.replace(Material.DIAMOND_PICKAXE, Material.DIAMOND_PICKAXE);
        keys.replace(Material.NETHERITE_PICKAXE, Material.DIAMOND_PICKAXE);
        for(Material type : Utility.shovels)
            keys.put(type, Material.IRON_SHOVEL);
        values = new HashMap<>();
        values.put(Material.IRON_AXE, Set.of(Material.OAK_WOOD, Material.SPRUCE_WOOD, Material.BIRCH_WOOD, Material.ACACIA_WOOD));
        values.put(Material.IRON_PICKAXE, Set.of(Material.STONE, Material.END_STONE, Material.BLACKSTONE, Material.DRIPSTONE_BLOCK));
        values.put(Material.DIAMOND_PICKAXE, Set.of(Material.OBSIDIAN, Material.CRYING_OBSIDIAN, Material.ANCIENT_DEBRIS, Material.NETHERITE_BLOCK));
        values.put(Material.IRON_SHOVEL, Set.of(Material.DIRT, Material.COARSE_DIRT, Material.ROOTED_DIRT, Material.CLAY));
        increment = -1;
        center = null;
        names = new HashSet<>();
        uses = 0;
        hits = 0;
        misses = 0;
    }

    public void registerRecipes(){}

    public void ability(BlockPlaceEvent event){
        event.setCancelled(false);
        setUpSkull(event.getBlockPlaced());
    }

    public void ability(BlockBreakEvent event) {
        event.setDropItems(false);
        if(increment>0)
            return;
        increment = 10;

        Block block = event.getBlock();
        World world = block.getWorld();
        ArmorStand stand = world.spawn(block.getLocation().add(0.5, -1.4, 0.5), ArmorStand.class);
        stand.setInvisible(true);
        stand.setInvulnerable(true);
        stand.setGravity(false);
        stand.setBasePlate(false);
        stand.setCanPickupItems(false);
        stand.addEquipmentLock(EquipmentSlot.CHEST, ArmorStand.LockType.ADDING);
        stand.addEquipmentLock(EquipmentSlot.FEET, ArmorStand.LockType.ADDING);
        stand.addEquipmentLock(EquipmentSlot.LEGS, ArmorStand.LockType.ADDING);
        stand.addEquipmentLock(EquipmentSlot.HAND, ArmorStand.LockType.ADDING);
        stand.addEquipmentLock(EquipmentSlot.OFF_HAND, ArmorStand.LockType.ADDING);
        stand.getPersistentDataContainer().set(Utility.key, PersistentDataType.STRING, "hI");
        stand.getEquipment().setHelmet(Utility.playerHeadFromBase64(endstoneBase64));

        new Task(HoloItems.getInstance(), 1, 1){
            final Location loc = stand.getLocation();
            final Vector dir = loc.getDirection();
            EnderCrystal crystal;
            PolarBear bear;
            public void run(){
                if(increment>650){
                    crystal.remove();
                    bear.remove();
                    center.setType(Material.AIR);
                    loc.add(0, 1, 0);
                    loc.getBlock().setType(Material.AIR);
                    int amount = 0;
                    float total = hits + misses;
                    if(0.9<Math.random()+(float) Math.min(hits, 90)/100)
                        amount++;
                    if(0.9<Math.random()+(float) Math.min(hits/3, 30)/100)
                        amount++;
                    if(0.9<Math.random()+(float) Math.min(hits/9, 10)/100)
                        amount++;
                    if(amount>0) {
                        ItemStack drop = Collections.items.get(QuartzFragment.name).item.clone();
                        drop.setAmount(amount);
                        world.dropItemNaturally(loc, drop);
                    }
                    ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
                    BookMeta meta = (BookMeta) book.getItemMeta();
                    meta.setTitle("§6Challenge Results");
                    meta.setAuthor("klin");
                    String players = "Players\n-------------------\n";
                    for(String name : names)
                        players += name + "\n";
                    meta.addPage(players + "-------------------\n" +
                            "Crystal Uses: " + uses + "\n" +
                            "Hits: " + hits + "\n" +
                            "Misses: " + misses + "\n" +
                            "Accuracy: " + (total==0?"0.00":String.format("%.2f", (hits/total*100))) + "%\n" +
                            "Gacha: " + amount + " fragment" + (amount==1?"":"s") + "\n-------------------");
                    book.setItemMeta(meta);
                    world.dropItemNaturally(loc, book);
                    increment = -1;
                    center = null;
                    names.clear();
                    uses = 0;
                    hits = 0;
                    misses = 0;
                    world.playSound(loc, Sound.ENTITY_PLAYER_LEVELUP, 4, 1);
                    cancel();
                    return;
                } if(increment<=40) {
                    if(increment==40) {
                        world.spawnFallingBlock(loc.add(0, 1.4, 0), Material.END_STONE.createBlockData());
                        stand.remove();
                    } else
                        stand.teleport(loc.add(0, 0.1 - 0.0025 * increment, 0).setDirection(dir.rotateAroundY(Math.PI / (0.5 * increment))));
                } else if(increment>=50) {
                    if(increment==50) {
                        crystal = world.spawn(loc, EnderCrystal.class);
                        crystal.getPersistentDataContainer().set(Utility.key, PersistentDataType.STRING, QuartzGranule.name);
                        crystal.setShowingBottom(false);
                        bear = world.spawn(loc.add(0, -1.4, 0), PolarBear.class);
                        bear.getPersistentDataContainer().set(Utility.key, PersistentDataType.STRING, QuartzGranule.name);
                        bear.setAI(false);
                        bear.setInvisible(true);
                        bear.setSilent(true);
                        bear.setNoDamageTicks(650);
                        center = loc.add(0, 0.4, 0).getBlock();
                    }
                    for(BlockFace face : Utility.cardinal.keySet()){
                        Block relative = center.getRelative(face);
                        if(!relative.isEmpty())
                            center.getRelative(face).breakNaturally();
                    }
                }
                increment++;
            }
        };
    }

    public static void setUpSkull(Block block){
        block.setType(Material.PLAYER_HEAD);
        final var skull = (Skull) block.getState();
        skull.getPersistentDataContainer().set(Utility.key, PersistentDataType.STRING, QuartzGranule.name);
        skull.setPlayerProfile(Utility.profileWithPrefilledBase64Texture(endstoneBase64));
        skull.update();
    }

    public void ability(EntityDamageByEntityEvent event) {
        event.setCancelled(true);
        Entity damager = event.getDamager();
        if(!(damager instanceof Player))
            return;
        Player player = (Player) damager;
        names.add(player.getName());
        Entity entity = event.getEntity();
        if(entity instanceof EnderCrystal){
            uses++;
            if(values.get(Material.DIAMOND_PICKAXE).contains(center.getType())) {
                misses++;
                increment = 651;
                return;
            }
            hit();
            World world = center.getWorld();
            world.spawnParticle(Particle.EXPLOSION_LARGE, center.getLocation().add(0.5, 0.5, 0.5), 1);
            world.playSound(center.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 0.1f, 1f);
            return;
        } else if(!(entity instanceof PolarBear))
            return;
        ItemStack item = player.getInventory().getItemInMainHand();
        if(item.getType()==Material.AIR)
            return;
        Material type = keys.get(item.getType());
        if(type==null)
            return;
        Material material = center.getType();
        if(values.get(type).contains(material)) {
            hit();
            return;
        } if(type==Material.DIAMOND_PICKAXE && values.get(Material.IRON_PICKAXE).contains(material))
            hit();
        else
            misses++;
    }

    private void hit(){
        hits++;
        center.setType(Utility.getRandom(values.get(Utility.getRandom(values.keySet()).get())).get());
        center.getWorld().playSound(center.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 4f, (float) Math.min(2, Math.max(hits, 50)/100));
    }
}