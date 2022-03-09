package com.klin.holoItems.packet;

import com.comphenix.protocol.PacketType;

import it.unimi.dsi.fastutil.ints.IntList;

public class EntityDestroyPacket extends AbstractPacket {

    // https://nms.screamingsandals.org/1.18.1/net/minecraft/network/protocol/game/ClientboundRemoveEntitiesPacket.html
    public EntityDestroyPacket(int... entityIds) {
        super(PacketType.Play.Server.ENTITY_DESTROY);
        handle.getIntLists()
            .write(0, IntList.of(entityIds));   // entityIds
    }
}
