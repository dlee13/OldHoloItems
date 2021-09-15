package com.klin.holoItems.dungeons.inaDungeon;

import com.klin.holoItems.dungeons.Resetable;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Stray;
import org.bukkit.entity.WitherSkeleton;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Deadbeats implements Resetable {
    private final Map<Deadbeat, Location> deadbeats;

    public Deadbeats(World world, int x, int y, int z, int amount){
        deadbeats = new HashMap<>();
        Location location = new Location(world, x%2==1?x+1:x, y, z%2==1?z+1:z);
        Set<Class<? extends Mob>> undead = Set.of(Skeleton.class, Stray.class, WitherSkeleton.class);
        for(int i=0; i<amount; i++)
            deadbeats.put(new Deadbeat(world, location, Utility.getRandom(undead).get(), deadbeats), null);
    }

    @Override
    public void reset() {
        for(Deadbeat deadbeat : deadbeats.keySet())
            deadbeat.deadbeat.remove();
    }
}
