package com.klin.holoItems.dungeons.inaDungeon;

import com.klin.holoItems.dungeons.Resetable;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Stray;
import org.bukkit.entity.WitherSkeleton;

import java.util.HashSet;
import java.util.Set;

public class Deadbeats implements Resetable {
    private final Set<Deadbeat> deadbeats;

    public Deadbeats(World world, Location loc, int amount){
        deadbeats = new HashSet<>();
        Set<Class<? extends Mob>> undead = Set.of(Skeleton.class, Stray.class, WitherSkeleton.class);
        for(int i=0; i<amount; i++)
            deadbeats.add(new Deadbeat(world, loc, Utility.getRandom(undead).get()));
    }

    @Override
    public void reset() {
        for(Deadbeat deadbeat : deadbeats)
            deadbeat.deadbeat.remove();
    }
}
