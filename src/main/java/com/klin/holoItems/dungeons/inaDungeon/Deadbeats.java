package com.klin.holoItems.dungeons.inaDungeon;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.dungeons.Resetable;
import com.klin.holoItems.utility.Task;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Deadbeats implements Resetable {
    private final Set<Mob> deadbeats;
    private int beat;
    private boolean even;
    private final Map<Block, BlockData> reset;

    public Deadbeats(World world, Location loc, EntityType type, int amount){
        deadbeats = new HashSet<>();
        beat = 0;
        even = false;
        reset = new HashMap<>();
        boolean evenX = loc.getBlockX()%2==0;
        boolean evenZ = loc.getBlockZ()%2==0;
        for(int i=0; i<amount; i++) {
            Entity entity = world.spawnEntity(loc, type);
            if (i == 0 && !(entity instanceof Mob)) {
                entity.remove();
                System.out.println("Invalid entity type");
                return;
            }
            Mob mob = (Mob) entity;
            deadbeats.add(mob);
            mob.setAware(false);
            mob.setGravity(false);
            mob.getEquipment().setHelmet(new ItemStack(Math.random()<0.5?Material.BARRIER:Math.random()<0.1?Material.JACK_O_LANTERN:Material.CARVED_PUMPKIN));
            new Task(HoloItems.getInstance(), 1, 1) {
                int increment = 0;
                Location location = loc;
                boolean x = false;
                boolean z = false;
                boolean negative = false;
                public void run() {
                    if (!mob.isValid() || mob.isDead()) {
                        deadbeats.remove(mob);
                        cancel();
                        return;
                    }
                    mob.teleport(location.clone().add(x ? 0.2 * increment * (negative ? -1 : 1) : 0, ((x || z) ? 0.2 : 0.1) * (5 - Math.abs(increment % 10 - 5)), z ? 0.2 * increment * (negative ? -1 : 1) : 0));
                    increment++;
                    if (increment > 10) {
                        increment = 0;
                        location = mob.getLocation();
                        Location floor = location.clone().add(0, -1, 0);
                        Material type = (location.getBlockX()%2==0==evenX && location.getBlockZ()%2==0==evenZ)==even?Material.WHITE_CONCRETE:Material.BLACK_CONCRETE;
                        beat();
                        for(int i=-1; i<1; i++){
                            for(int j=-1; j<1; j++) {
                                Block block = floor.clone().add(i, 0, j).getBlock();
                                reset.putIfAbsent(block, block.getBlockData());
                                block.setType(type);
                            }
                        }
                        x = Math.random() < 0.5;
                        z = Math.random() < 0.5;
                        negative = Math.random() < 0.5;
                    }
                }
            };
        }
    }

    private void beat(){
        beat++;
        if(beat>=deadbeats.size()) {
            beat = 0;
            even = !even;
        }
    }

    @Override
    public void reset() {
        for(Block block : reset.keySet())
            block.setBlockData(reset.get(block));
        for(Mob mob : deadbeats)
            mob.remove();
    }
}
