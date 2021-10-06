package com.klin.holoItems.collections.holoMyth.watsonCollection.items;

import com.klin.holoItems.Collections;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.abstractClasses.Pack;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GroundPounder extends Pack {
    public static final String name = "groundPounder";
    private static final Set<Enchantment> accepted = Set.of(Enchantment.MENDING);
    private final Map<Player, Integer> held;

    private static final Material material = Material.STONE_PICKAXE;
    private static final String lore =
            "Crush your foes";
    private static final int durability = 131;
    private static final boolean shiny = false;
    public static final int cost = 0;

    private static  final int size = 18;
    public static final String title = "Rewinding. . .";
    public static final boolean display = false;

    public GroundPounder(){
        super(name, accepted, material, lore, durability, shiny, size, title, display, cost);
        held = new HashMap<>();
    }

    public void registerRecipes(){
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.DURABILITY, 1, false);
        List<String> lore = meta.getLore();
        lore.add(0, "");
        meta.setLore(lore);
        item.setItemMeta(meta);

        ShapedRecipe recipe =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("aaa"," b "," b ");
        recipe.setIngredient('a', Material.AMETHYST_CLUSTER);
        recipe.setIngredient('b', Material.POINTED_DRIPSTONE);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    protected void cause(PlayerInteractEvent event){
        if(held.remove(event.getPlayer())==null)
            super.cause(event);
    }

    protected void effect(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        LivingEntity entity = event.getPlayer();
        if(entity.isOnGround())
            return;
        Integer ticks = held.putIfAbsent(player, 0);
        if(ticks!=null){
            held.replace(player, ticks+1);
            return;
        }
        Location location = player.getLocation();
        new Task(HoloItems.getInstance(), 1, 1){
            int increment = 0;
            int offset = 0;
            public void run(){
                Integer ticks = held.get(player);
                boolean ground = entity.isOnGround();
                boolean rewind = ticks==null;
                if(ground || rewind || increment>120 || offset>ticks){
                    World world = player.getWorld();
                    Location loc = player.getLocation();
                    Block block = world.getBlockAt(loc).getRelative(BlockFace.DOWN);
                    if(!ground && !rewind && offset>ticks){
                        for(int i=0; i<=3; i++){
                            if(!block.isPassable()) {
                                player.setFallDistance(0);
                                ground = true;
                                break;
                            }
                            block = block.getRelative(BlockFace.DOWN);
                        }
                    }
                    double intensity = Math.log(location.distance(loc));
                    if(ground && intensity>1){
                        Vector velocity = new Vector(0, Math.min(intensity/2, 2), 0);
                        for(Entity entity : player.getNearbyEntities(4, 1, 4)){
                            if(entity.isOnGround() && entity instanceof LivingEntity && !(entity instanceof ArmorStand)) {
                                boolean squash = loc.distance(entity.getLocation())<=1;
                                if(Utility.damage(null, intensity*(squash?4:2), true, player, (LivingEntity) entity, true, false, false) && !squash)
                                    entity.setVelocity(velocity);
                            }
                        }
                        Block center = block;
                        BlockData[] data = new BlockData[]{
                                Bukkit.createBlockData(Material.SMALL_AMETHYST_BUD),
                                Bukkit.createBlockData(Material.MEDIUM_AMETHYST_BUD),
                                Bukkit.createBlockData(Material.LARGE_AMETHYST_BUD),
                                Bukkit.createBlockData(Material.AMETHYST_CLUSTER)};
                        new Task(HoloItems.getInstance(), 2, 2){
                            int increment = 0;
                            Set<Block> pound = Stream.of(center).collect(Collectors.toCollection(HashSet::new));
                            final Set<Block> pounded = Stream.of(center).collect(Collectors.toCollection(HashSet::new));
                            public void run(){
                                if(increment>4){
                                    cancel();
                                    return;
                                }
                                Set<BlockFace> faces = Stream.of(BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST).collect(Collectors.toCollection(HashSet::new));
                                if(increment>0){
                                    faces.add(BlockFace.NORTH_EAST);
                                    faces.add(BlockFace.SOUTH_EAST);
                                    faces.add(BlockFace.NORTH_WEST);
                                    faces.add(BlockFace.SOUTH_WEST);
                                }
                                Set<Block> temp = new HashSet<>();
                                BlockData air = Bukkit.createBlockData(Material.AIR);
                                for(Entity entity : world.getNearbyEntities(loc, 16, 16, 16, entity -> entity instanceof Player)) {
                                    Player player = (Player) entity;
                                    for (Block block : pound) {
                                        if(increment!=3)
                                            player.sendBlockChange(block.getRelative(BlockFace.UP).getLocation(), air);
                                        if (increment >= 3)
                                            continue;
                                        for (BlockFace face : faces) {
                                            Block relative = block.getRelative(face);
                                            if (pounded.contains(relative))
                                                continue;
                                            Block up = relative.getRelative(BlockFace.UP);
                                            if (!up.isEmpty())
                                                continue;
                                            player.sendBlockChange(up.getLocation(), data[increment]);
                                            pounded.add(relative);
                                            temp.add(relative);
                                        }
                                    }
                                }
                                if(increment!=3)
                                    pound = temp;
                                increment++;
                            }
                        };
                    }
                    held.remove(player);
                    if(rewind) {
                        if(player.getGameMode()==GameMode.CREATIVE || Utility.deplete(event.getItem(), player, size)!=-1) {
                            player.teleport(location);
                            player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 10, 1));
                        }
                    }
                    else
                        Utility.addDurability(event.getItem(), -1, player);
                    cancel();
                    return;
                }
                player.setVelocity(player.getVelocity().add(new Vector(0, -0.2, 0)));
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 2, 3));
                increment++;
                if(increment%5==0)
                    offset++;
            }
        };
    }

    public int ability(Inventory inv, ItemStack item, Player player) {
        int size = 0;
        World world = player.getWorld();
        for(ItemStack content : inv.getContents()) {
            if(content==null || content.getType()==Material.AIR)
                continue;
            if(content.getType()!=Material.SPLASH_POTION || !Hourglass.name.equals(content.getItemMeta().getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING)))
                world.dropItemNaturally(player.getLocation(), content);
            else
                size++;
        }
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(Utility.pack, PersistentDataType.INTEGER, size);
        item.setItemMeta(meta);
        player.sendMessage("Filled "+meta.getDisplayName()+"§f to: "+size);
        return size;
    }

    protected void repack(ItemStack item, Inventory inv) {
        Integer size = item.getItemMeta().getPersistentDataContainer().get(Utility.pack, PersistentDataType.INTEGER);
        if(size!=null){
            ItemStack hourglass = Collections.items.get(Hourglass.name).item;
            for(int i=0; i<size; i++)
                inv.addItem(hourglass);
        }
    }
}
