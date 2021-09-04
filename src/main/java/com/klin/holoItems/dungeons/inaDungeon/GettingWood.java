package com.klin.holoItems.dungeons.inaDungeon;

import com.klin.holoItems.Collections;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.collections.dungeons.inaDungeonCollection.items.AshWood;
import com.klin.holoItems.collections.dungeons.inaDungeonCollection.items.BoneCrystal;
import com.klin.holoItems.dungeons.Resetable;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

import static org.bukkit.Bukkit.getServer;

public class GettingWood implements Listener, Resetable {
    //plant world 45 64 -284
    private final Location[] locations;
    private final List<Integer> stages;
    private int stage;
    private Map<Location, BlockData> tree;
    private final Set<Material> spruce;
    private Map<Integer, Set<Block>> leaves;
    private Set<Block> stem;
    private final Set<Location> cut;
    private final Map<Material, Material> ignite;
    private boolean bloom;
    private int y;

    GettingWood(Location loc){
        locations = new Location[]{loc.clone().add(7, 0, 0), loc.add(-9, 0, -10)};
        stages = new ArrayList<>();
        stage = 0;
        tree = new HashMap<>();
        spruce = Set.of(Material.SPRUCE_FENCE, Material.SPRUCE_PRESSURE_PLATE, Material.SPRUCE_SLAB, Material.STRIPPED_DARK_OAK_WOOD);
        leaves = new HashMap<>();
        stem = new HashSet<>();
        cut = new HashSet<>();
        ignite = Map.of(
                Material.WHITE_WOOL, Material.RED_WOOL,
                Material.WHITE_GLAZED_TERRACOTTA, Material.RED_GLAZED_TERRACOTTA,
                Material.SMOOTH_QUARTZ, Material.NETHER_WART_BLOCK,
                Material.WHITE_STAINED_GLASS, Material.RED_STAINED_GLASS,
                Material.WHITE_STAINED_GLASS_PANE, Material.RED_STAINED_GLASS_PANE,
                Material.DIORITE, Material.RED_NETHER_BRICKS,
                Material.DIORITE_WALL, Material.RED_NETHER_BRICK_WALL,
                Material.END_ROD, Material.LIGHTNING_ROD,
                Material.SNOW_BLOCK, Material.RED_TERRACOTTA
        );
        bloom = true;
        y = 256;
        getServer().getPluginManager().registerEvents(this, HoloItems.getInstance());
    }

    @EventHandler
    public void grow(BlockPlaceEvent event){
        //5 bone crystals (80 fragments) to grow fully
        if(stage+stages.size()>=5)
            return;
        Location place = event.getBlock().getLocation();
        Location loc = locations[0];
        if(Math.abs(place.getBlockX()-loc.getBlockX())>1 || Math.abs(place.getBlockZ()-loc.getBlockZ())>1)
            return;
        ItemStack item = event.getItemInHand();
        ItemMeta meta = item.getItemMeta();
        if(meta==null || !BoneCrystal.id.equals(meta.getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING)))
            return;
        event.setCancelled(false);
        new Task(HoloItems.getInstance(), 10, 10){
            Block block = event.getBlock();
            Material type = Material.AIR;
            boolean grow = false;
            public void run(){
                block.setType(type);
                block = block.getRelative(BlockFace.DOWN);
                if(!block.isEmpty() && block.getType()!=Material.BONE_BLOCK){
                    if(grow){
                        cancel();
                        return;
                    }
                    type = block.getType();
                    grow = true;
                }
                block.setType(Material.BONE_BLOCK);
            }
        };
        if(!stages.isEmpty()) {
            stages.add(stages.get(stages.size()-1)+1);
            return;
        }
        stages.add(stage+1);
        Set<Location> keySet = tree.keySet();
        Set<Material> white = ignite.keySet();
        Set<Material> overwrite = new HashSet<>();
        overwrite.addAll(white);
        overwrite.addAll(spruce);
        World world = locations[0].getWorld();
        leaves = new HashMap<>();
        stem = new HashSet<>();
        y = 256;
        new Task(HoloItems.getInstance(), 20, 10){
            char subStage = '`';
            public void run(){
                if(stages.isEmpty()){
                    for(Location loc : keySet){
                        Block block = world.getBlockAt(loc);
                        Material type = block.getType();
                        if(white.contains(type)) {
                            int blockY = block.getY();
                            y = Math.min(blockY, y);
                            Set<Block> layer = leaves.computeIfAbsent(blockY, s -> new HashSet<>());
                            layer.add(block);
                        }
                        else if(spruce.contains(type)) {
                            if(cut.contains(loc))
                                block.setType(Material.AIR);
                            else
                                stem.add(block);
                        }
                    }
                    if(bloom && stage>=5){
                        Map<Integer, Set<Block>> layers = new HashMap<>();
                        for(Integer key : leaves.keySet())
                            layers.put(key, new HashSet<>(leaves.get(key)));
                        int blockY = y;
                        while(!leaves.containsKey(blockY)){
                            if(blockY>256){
                                cancel();
                                return;
                            }
                            blockY++;
                        }
                        int tempY = blockY;
                        new Task(HoloItems.getInstance(), 10, 2){
                            int y = tempY;
                            Set<Block> layer = layers.get(y);
                            public void run(){
                                for(int i=0; i<10; i++){
                                    Optional<Block> leave = Utility.getRandom(layer);
                                    if(leave.isEmpty()) {
                                        y++;
                                        layer = layers.get(y);
                                        if(layer==null){
                                            locations[0].getWorld().dropItemNaturally(locations[0], Collections.findItem(AshWood.id).item);
                                            cancel();
                                            return;
                                        }
                                        i--;
                                        continue;
                                    }
                                    Block leaf = leave.get();
                                    layer.remove(leaf);
                                    Material type = ignite.get(leaf.getType());
                                    if(type!=null)
                                        leaf.setType(type);
                                }
                            }
                        };
                    }
                    cancel();
                    return;
                }
                stage = stages.get(0);
                Map<Location, BlockData> temp = InaDungeon.build("tree"+(stage-1)+(subStage=='`'?"":subStage+1), locations[1], tree, overwrite);
                if(temp==null){
                    subStage++;
                    temp = InaDungeon.build("tree"+(stage-1)+subStage, locations[1], tree, overwrite);
                    if(temp==null) {
                        stages.remove(0);
                        subStage = '`';
                        return;
                    }
                }
                else {
                    stages.remove(0);
                    subStage = '`';
                }
                tree = temp;
                for(Location loc : keySet){
                    Block block = world.getBlockAt(loc);
                    Material type = block.getType();
                    if(spruce.contains(type) && cut.contains(loc))
                        block.setType(Material.AIR);
                }
            }
        };
    }

    @EventHandler
    public boolean gettingWood(BlockBreakEvent event){
        Block block = event.getBlock();
        if(!stem.contains(block))
            return true;
        cut.add(block.getLocation());
        event.setCancelled(false);
        event.setDropItems(false);
        World world = block.getWorld();
        if(spruce.contains(block.getType()))
            world.dropItemNaturally(block.getLocation().add(0.5, 0.5, 0.5), Collections.findItem(AshWood.id).item);
        int blockY = y;
        while(!leaves.containsKey(blockY)){
            if(blockY>256)
                return true;
            blockY++;
        }
        Set<Block> layer = leaves.get(blockY);
        BlockData fire = Bukkit.createBlockData(Material.FIRE);
        if(bloom && stage>=5){
            new Task(HoloItems.getInstance(), 10, 2){
                public void run(){
                    if(gettingWood(event))
                        cancel();
                }
            };
        }
        bloom = false;
        for(int i=0; i<10; i++){
            Optional<Block> leave = Utility.getRandom(layer);
            if(leave.isEmpty()) {
                blockY++;
                layer = leaves.get(blockY);
                if(layer==null)
                    return true;
                i--;
                continue;
            }
            Block leaf = leave.get();
            layer.remove(leaf);
            Material type = leaf.getType();
            if(!ignite.containsValue(type))
                type = ignite.get(type);
            if(type==null)
                continue;
            BlockData data = Bukkit.createBlockData(type);
            leaf.setType(Material.AIR);
            Location loc = leaf.getLocation().add(0.5, 0, 0.5);
            world.spawnFallingBlock(loc, fire);
            if(leaf.getRelative(BlockFace.DOWN).isEmpty()) {
                FallingBlock ash = world.spawnFallingBlock(loc.add(0, -1, 0), data);
                ash.setDropItem(false);
                ash.setHurtEntities(true);
                ash.getPersistentDataContainer().set(Utility.key, PersistentDataType.STRING, AshWood.id);
                new Task(HoloItems.getInstance(), 1, 1){
                    int increment = 0;
                    public void run(){
                        if(increment>=40 || !ash.isValid()){
                            cancel();
                            return;
                        }
                        List<Entity> nearby = ash.getNearbyEntities(0.5, 0.5, 0.5);
                        if(!nearby.isEmpty()){
                            boolean remove = false;
                            for(Entity entity : nearby){
                                if(entity instanceof LivingEntity){
                                    LivingEntity living = (LivingEntity) entity;
                                    living.setFireTicks(80);
                                    if(living.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) {
                                        living.setNoDamageTicks(0);
                                        living.damage(8);
                                    }
                                    else{
                                        new Task(HoloItems.getInstance(), 0, 4) {
                                            int increment = 0;
                                            public void run() {
                                                if (increment>=20 || !living.isValid() || living.isDead()) {
                                                    cancel();
                                                    return;
                                                }
                                                living.setNoDamageTicks(0);
                                                living.damage(1);
                                                increment++;
                                            }
                                        };
                                    }
                                    remove = true;
                                }
                            }
                            if(remove)
                                ash.remove();
                        }
                        increment++;
                    }
                };
            }
        }
        return false;
    }

    @EventHandler
    public void burn(EntityChangeBlockEvent event){
        Entity entity = event.getEntity();
        if(!(entity instanceof FallingBlock) || !AshWood.id.equals(entity.getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING)))
            return;
        Block block = event.getBlock();
        new BukkitRunnable(){
            public void run(){
                block.setType(Material.AIR);
            }
        }.runTaskLater(HoloItems.getInstance(), 10);
    }

    public void reset(){
        BlockPlaceEvent.getHandlerList().unregister(this);
        BlockBreakEvent.getHandlerList().unregister(this);
        EntityChangeBlockEvent.getHandlerList().unregister(this);
        World world = locations[0].getWorld();
        for(Location loc : tree.keySet()) {
            Block block = world.getBlockAt(loc);
            block.setBlockData(tree.get(loc));
        }
    }
}
