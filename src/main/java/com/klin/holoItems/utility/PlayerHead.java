package com.klin.holoItems.utility;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Based on SkullCreator implementation by Dean B.
 */
public class PlayerHead {

    private PlayerHead() {}

    /**
     * Creates a player head item with the skin based on a player's UUID.
     *
     * @param id The Player's UUID.
     * @return The head of the Player.
     */
    public static ItemStack itemFromUuid(@NotNull UUID id) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta)item.getItemMeta();
        meta.setOwningPlayer(Bukkit.getOfflinePlayer(id));
        item.setItemMeta(meta);

        return item;
    }

    /**
     * Sets the block to a skull with the given UUID.
     *
     * @param block The block to set.
     * @param id    The player to set it to.
     */
    public static void blockWithUuid(@NotNull Block block, @NotNull UUID id) {
        block.setType(Material.PLAYER_HEAD, false);
        Skull state = (Skull)block.getState();
        state.setOwningPlayer(Bukkit.getOfflinePlayer(id));
        state.update(false, false);
    }
}