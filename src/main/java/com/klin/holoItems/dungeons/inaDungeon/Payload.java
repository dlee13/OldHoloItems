package com.klin.holoItems.dungeons.inaDungeon;

import com.klin.holoItems.Collections;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.collections.dungeons.inaDungeonCollection.items.AshWood;
import com.klin.holoItems.collections.dungeons.inaDungeonCollection.items.DepthCharge;
import com.klin.holoItems.collections.dungeons.inaDungeonCollection.items.Seat;
import com.klin.holoItems.dungeons.Resetable;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Payload implements Resetable {
    //payload world -77 74 -204 -155
    public Map<AbstractMap.SimpleEntry<Integer, Integer>, List<FallingBlock>> payload;
    private Set<FallingBlock> plates;
    private Set<FallingBlock> doors;
    private int taskId;

    public Payload(World world, int x, int y, int z1, int z2) {
        Location loc = new Location(world, x, y, z1);
        File file = new File(HoloItems.getInstance().getDataFolder(), "payload.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String wall;
            int increment = 0;
            payload = new HashMap<>();
            plates = new HashSet<>();
            doors = new HashSet<>();
            while ((wall = reader.readLine()) != null) {
                String[] fence = wall.split(", ");
                for (int i = 0; i < fence.length; i++) {
                    String[] tile = fence[i].split(" ");
                    for (int j = 0; j < tile.length; j++) {
                        BlockData data = Bukkit.createBlockData(tile[j]);
                        Material type = data.getMaterial();
                        if(type.isAir())
                            continue;
                        Location location = loc.clone().add(increment+0.5, i, j+0.5);
                        FallingBlock block = world.spawnFallingBlock(location, data);
                        block.setGravity(false);
                        block.getPersistentDataContainer().set(Utility.key, PersistentDataType.STRING, Seat.id);
                        AbstractMap.SimpleEntry<Integer, Integer> key = new AbstractMap.SimpleEntry<>(location.getBlockX(), location.getBlockZ());
                        List<FallingBlock> falling = payload.computeIfAbsent(key, k -> new ArrayList<>());
                        falling.add(block);
                        if(type==Material.SPRUCE_PRESSURE_PLATE || type==Material.DARK_OAK_PRESSURE_PLATE)
                            plates.add(block);
                        else if(type==Material.SPRUCE_TRAPDOOR && tile[j].contains("open=false"))
                            doors.add(block);
                    }
                }
                increment++;
            }
        } catch (IOException e) { return; }

        double i = ((double) (z2-z1))/360;
        if(i<=0)
            return;
        int pause = (int) (0.1/i)+1;
        taskId = new Task(HoloItems.getInstance(), 10, 10) {
            int increment = 0;
            Location location = loc;
            final int interval = (int) (i/pause*20);
            public void run() {
                if(increment>=36){
                    cancel();
                    return;
                }
                if(increment%2==0) {
                    Set<AbstractMap.SimpleEntry<Integer, Integer>> remove = new HashSet<>();
                    Map<AbstractMap.SimpleEntry<Integer, Integer>, List<FallingBlock>> add = new HashMap<>();
                    boolean update = true;
                    for (AbstractMap.SimpleEntry<Integer, Integer> key : payload.keySet()) {
                        List<FallingBlock> falling = payload.get(key);
                        FallingBlock bottom = falling.get(0);
                        Location loc = bottom.getLocation().add(0, 0, interval);
                        Block bloc = loc.getBlock();
                        double diff = loc.getY() - loc.getBlockY();
                        if (Math.abs(diff - 1) > 0.01 && Utility.slabs.contains(bloc.getType()))
                            loc.add(0, 0.5, 0);
                        else if (!bloc.isPassable())
                            loc.add(0, diff > 0.01 ? 0.5 : 1, 0);
                        loc.add(0, -1, 0);
                        for (FallingBlock block : falling)
                            block.teleport(loc.add(0, 1, 0));
                        if (update) {
                            location = bottom.getLocation();
                            update = false;
                        }
                        remove.add(key);
                        add.put(new AbstractMap.SimpleEntry<>(loc.getBlockX(), loc.getBlockZ()), falling);
                    }
                    for (AbstractMap.SimpleEntry<Integer, Integer> key : remove)
                        payload.remove(key);
                    payload.putAll(add);
                }
                else {
                    int wood = 0;
                    int tnt = 0;
                    for (Entity nearby : world.getNearbyEntities(location, 5, 1, 5)) {
                        if (!(nearby instanceof Item))
                            continue;
                        ItemStack item = ((Item) nearby).getItemStack();
                        if (item.getType() == Material.AIR)
                            return;
                        ItemMeta meta = item.getItemMeta();
                        if (meta == null)
                            return;
                        String id = meta.getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
                        if (id == null)
                            return;
                        if (id.equals(AshWood.id)) {
                            wood += item.getAmount();
                            nearby.remove();
                        } else if (id.equals(DepthCharge.id)) {
                            tnt += item.getAmount();
                            nearby.remove();
                        }
                    }
                    for (int i = 0; i < wood / 5; i++)
                        seat();
                    for (int i = 0; i < tnt; i++)
                        arm();
                    if (wood % 5 > 0) {
                        ItemStack drop = Collections.findItem(AshWood.id).item.clone();
                        drop.setAmount(wood % 5);
                        world.dropItemNaturally(loc, drop);
                    }
                }
                increment++;
            }
        }.getTaskId();
    }

    public void seat(){
        Optional<FallingBlock> plate = Utility.getRandom(plates);
        if(plate.isEmpty())
            return;
        Location loc = plate.get().getLocation();
        List<FallingBlock> falling = payload.get(new AbstractMap.SimpleEntry<>(loc.getBlockX(), loc.getBlockZ()));
        if(falling==null)
            return;
        FallingBlock top = falling.get(falling.size()-1);
        doors.remove(top);

        Material material = top.getBlockData().getMaterial();
        Material type;
        if(material==Material.SPRUCE_PRESSURE_PLATE)
            type = Material.SPRUCE_TRAPDOOR;
        else if(material==Material.DARK_OAK_PRESSURE_PLATE)
            type = Material.DARK_OAK_TRAPDOOR;
        else
            return;
        Location location = top.getLocation();
        top.remove();
        FallingBlock block = location.getWorld().spawnFallingBlock(location, Bukkit.createBlockData(type));
        block.setGravity(false);
        block.getPersistentDataContainer().set(Utility.key, PersistentDataType.STRING, Seat.id);
        falling.add(block);
        doors.add(block);
    }

    public void arm(){
        Optional<FallingBlock> door = Utility.getRandom(doors);
        if(door.isEmpty())
            return;
        Location loc = door.get().getLocation();
        List<FallingBlock> falling = payload.get(new AbstractMap.SimpleEntry<>(loc.getBlockX(), loc.getBlockZ()));
        if(falling==null)
            return;
        FallingBlock top = falling.get(falling.size()-1);

        Material type = top.getBlockData().getMaterial();
        if(type==Material.SPRUCE_TRAPDOOR || type==Material.DARK_OAK_TRAPDOOR){
            Location location = top.getLocation();
            top.remove();
            FallingBlock block = location.getWorld().spawnFallingBlock(location, Bukkit.createBlockData(Material.TNT));
            block.setGravity(false);
            block.getPersistentDataContainer().set(Utility.key, PersistentDataType.STRING, Seat.id);
            falling.add(block);
        }
        else if(type==Material.TNT){
            Location location = top.getLocation();
            FallingBlock block = location.getWorld().spawnFallingBlock(location.add(0, 1, 0), Bukkit.createBlockData(Material.TNT));
            block.setGravity(false);
            block.getPersistentDataContainer().set(Utility.key, PersistentDataType.STRING, Seat.id);
            falling.add(block);
        }
    }

    public void reset(){
        for(List<FallingBlock> falling : payload.values()) {
            for(FallingBlock block : falling)
                block.remove();
        }
        Bukkit.getScheduler().cancelTask(taskId);
    }
}
