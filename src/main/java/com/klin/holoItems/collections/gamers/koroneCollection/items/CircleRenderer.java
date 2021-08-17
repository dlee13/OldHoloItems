package com.klin.holoItems.collections.gamers.koroneCollection.items;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.map.*;

public class CircleRenderer extends MapRenderer {
    public CircleRenderer(boolean contextual){
        super(contextual);
    }

    MapCursor center = null;
    public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
        Location loc = player.getLocation();
        int x = loc.getBlockX();
        int z = loc.getBlockZ();
        mapView.setCenterX(x);
        mapView.setCenterZ(z);
        if(center==null) {
            center = mapCanvas.getCursors().addCursor(0, 0, (byte) 8);
            center.setType(MapCursor.Type.SMALL_WHITE_CIRCLE);
        }
        else{
            center.setX((byte) 0);
            center.setY((byte) 0);
        }
    }
}
