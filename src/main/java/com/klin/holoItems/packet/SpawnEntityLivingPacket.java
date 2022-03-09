package com.klin.holoItems.packet;

import java.util.UUID;

import com.comphenix.protocol.PacketType;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public class SpawnEntityLivingPacket extends AbstractPacket {

    // https://nms.screamingsandals.org/1.18.1/net/minecraft/network/protocol/game/ClientboundAddMobPacket.html
    public SpawnEntityLivingPacket(int entityId, UUID uniqueId, EntityType entityType, Location location) {
        super(PacketType.Play.Server.SPAWN_ENTITY_LIVING);
        // https://wiki.vg/Entity_metadata#Mobs
        final var typeId = switch (entityType) {
            case ARMOR_STAND -> 1;
            case GUARDIAN -> 35;
            case SLIME -> 80;
            default -> throw new UnsupportedOperationException();
        };
        handle.getIntegers()
            .write(0, entityId)             // id
            .write(1, typeId)               // type
            .write(2, 0)                    // xd
            .write(3, 0)                    // yd
            .write(4, 0);                   // zd
        handle.getDoubles()
            .write(0, location.getX())      // x
            .write(1, location.getY())      // y
            .write(2, location.getZ());     // z
        handle.getUUIDs()
            .write(0, uniqueId);            // uuid
        handle.getBytes()
            .write(0, (byte)0)              // yRot
            .write(1, (byte)0)              // xRot
            .write(2, (byte)0);             // yHeadRot
    }
}
