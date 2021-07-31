package com.klin.holoItems.collections.gen4.towaCollection.items;

import com.klin.holoItems.Events;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.abstractClasses.Crate;
import com.klin.holoItems.collections.gen4.towaCollection.TowaCollection;
import com.klin.holoItems.interfaces.Activatable;
import com.klin.holoItems.interfaces.Placeable;
import com.klin.holoItems.interfaces.Punchable;
import com.klin.holoItems.utility.Utility;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.block.data.Lightable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

public class HolyFire extends Crate implements Activatable, Punchable, Placeable {
    public static final String name = "holyFire";
    public static final HashSet<Enchantment> accepted = null;
//    private static final Set<CreatureSpawnEvent.SpawnReason> reasons = Stream.of(
//            CreatureSpawnEvent.SpawnReason.DEFAULT,
//            CreatureSpawnEvent.SpawnReason.NATURAL,
//            CreatureSpawnEvent.SpawnReason.NETHER_PORTAL,
//            CreatureSpawnEvent.SpawnReason.PATROL,
//            CreatureSpawnEvent.SpawnReason.REINFORCEMENTS,
//            CreatureSpawnEvent.SpawnReason.SPAWNER
//    ).collect(Collectors.toCollection(HashSet::new));
    private static Set<Location> locations = new HashSet<>();

    private static final Material material = Material.SOUL_CAMPFIRE;
    private static final int quantity = 1;
    private static final String lore =
            "§6Ability" +"/n"+
                "Prevents natural mob spawning" +"/n"+
                "within 100 blocks when lit";
    private static final int durability = 0;
    public static final boolean stackable = false;
    private static final boolean shiny = true;

    public static final int cost = 0;
    public static final char key = '0';
    public static final String id = ""+TowaCollection.key+key;

    public HolyFire(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost, id, key);
    }

    public void registerRecipes(){
        ShapedRecipe recipe =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape(" a ","aba","ccc");
        recipe.setIngredient('a', Material.END_ROD);
        recipe.setIngredient('b', Material.DRAGON_EGG);
        //1.17: replace with block of raw gold
        recipe.setIngredient('c', Material.GOLD_BLOCK);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(CreatureSpawnEvent event) {
        Location loc = event.getEntity().getLocation();
        World world = loc.getWorld();
        for(Location location : locations){
            if(world.equals(location.getWorld()) && loc.distance(location)<=100/* && reasons.contains(event.getSpawnReason())*/){
                event.setCancelled(true);
                return;
            }
        }
    }

    public Set<Location> survey() {
        return locations;
    }

    public void ability(PlayerInteractEvent event, Action action){
        if(action!= Action.RIGHT_CLICK_BLOCK)
            return;
        event.setCancelled(true);
        Block block = event.getClickedBlock();
        Lightable lightable = (Lightable) block.getBlockData();
        if(lightable.isLit())
            return;
        lightable.setLit(true);
        block.setBlockData(lightable);

        if(locations.isEmpty())
            Events.activatables.add(this);
        Location loc = block.getLocation();
        locations.add(loc);
        new BukkitRunnable(){
            public void run(){
                if(remove(loc))
                    unlit(loc.getWorld().getBlockAt(loc));
            }
        }.runTaskLater(HoloItems.getInstance(), 24000);
    }

    public void ability(BlockPlaceEvent event){
        event.setCancelled(false);
        Block block = event.getBlockPlaced();
        TileState state = (TileState) block.getState();
        state.getPersistentDataContainer().set(Utility.key, PersistentDataType.STRING, id);
        state.update();

        unlit(block);
    }

    public void ability(BlockBreakEvent event) {
        event.setDropItems(false);
        super.ability(event);
        remove(event.getBlock().getLocation());
    }

    private boolean remove(Location loc){
        boolean remove = locations.remove(loc);
        if(locations.isEmpty())
            Events.activatables.remove(this);
        return remove;
    }

    private void unlit(Block block){
        Lightable lightable = (Lightable) block.getBlockData();
        lightable.setLit(false);
        block.setBlockData(lightable);
    }
}
