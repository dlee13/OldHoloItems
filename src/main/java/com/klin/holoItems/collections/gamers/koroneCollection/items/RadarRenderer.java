package com.klin.holoItems.collections.gamers.koroneCollection.items;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.map.*;

import java.util.HashSet;
import java.util.Set;

public class RadarRenderer extends MapRenderer {
    Set<MapCursor> cursors = new HashSet<>();
    int increment = 0;

    public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
        increment++;
        MapCursorCollection collection = mapCanvas.getCursors();
        if(increment==2){
            for(MapCursor cursor : cursors)
                collection.removeCursor(cursor);
        }
        if(increment<10 || !Radar.trackers.contains(player))
            return;
        else
            increment = 0;
        int x = mapView.getCenterX();
        int z = mapView.getCenterZ();
        for (Entity entity : player.getWorld().getNearbyEntities(player.getLocation(), 128, 1, 128, entity -> entity instanceof Player && !player.equals(entity) && Radar.trackers.contains(entity))) {
            Location location = entity.getLocation();
            MapCursor cursor = collection.addCursor(location.getBlockX()-x, location.getBlockZ()-z, (byte) 8);
            cursor.setType(MapCursor.Type.RED_MARKER);
            cursors.add(cursor);
        }
    }
}

