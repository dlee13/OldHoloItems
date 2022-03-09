package com.klin.holoItems.packet;

import java.util.Optional;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedDataWatcher.Registry;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

public class EntityMetadataPacket extends AbstractPacket {

    public static class Metadata {

        private final WrappedDataWatcher watcher;

        // https://wiki.vg/Entity_metadata#Entity_Metadata_Format
        public Metadata() {
            this.watcher = new WrappedDataWatcher();
        }

        public WrappedDataWatcher getWatcher() {
            return watcher;
        }

        private void setObject(int index, Object value, Class<?> clazz) {
            watcher.setObject(index, Registry.get(clazz), value, true);
        }

        public void setByte(int index, byte value) {
            setObject(index, value, Byte.class);
        }

        public void setBoolean(int index, boolean value) {
            setObject(index, value, Boolean.class);
        }

        public void setVarInt(int index, int value) {
            setObject(index, value, Integer.class);
        }

        public void setCustomName(Component name) {
            final var jsonComponent = GsonComponentSerializer.gson().serialize(name);
            final var chatComponent = WrappedChatComponent.fromJson(jsonComponent);
            final var optionalChatComponent = Optional.of(chatComponent.getHandle());
            watcher.setObject(2, Registry.getChatComponentSerializer(true), optionalChatComponent, true);
        }

        public void setCustomNameVisible() {
            setBoolean(3, true);
        }
    }

    // https://nms.screamingsandals.org/1.18.1/net/minecraft/network/protocol/game/ClientboundSetEntityDataPacket.html
    public EntityMetadataPacket(int entityId, Metadata metadata) {
        super(PacketType.Play.Server.ENTITY_METADATA);
        handle.getIntegers()
            .write(0, entityId);                                    // id
        handle.getWatchableCollectionModifier()
            .write(0, metadata.getWatcher().getWatchableObjects()); // packedItems
    }
}
