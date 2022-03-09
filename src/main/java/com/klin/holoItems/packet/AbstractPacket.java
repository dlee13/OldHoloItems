package com.klin.holoItems.packet;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;

public abstract class AbstractPacket {

    protected final PacketContainer handle;

    protected AbstractPacket(PacketType packetType) {
        this.handle = new PacketContainer(packetType);
    }

    public PacketContainer getHandle() {
        return handle;
    }

    public void sendPacket(Player player) {
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, getHandle());
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void broadcastNearbyPacket(Location origin, int maxObserverDistance) {
        ProtocolLibrary.getProtocolManager().broadcastServerPacket(getHandle(), origin, maxObserverDistance);
    }

    public void broadcastServerPacket() {
        ProtocolLibrary.getProtocolManager().broadcastServerPacket(getHandle());
    }
}
