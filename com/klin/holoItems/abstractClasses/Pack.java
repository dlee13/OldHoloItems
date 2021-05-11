package com.klin.holoItems.abstractClasses;

import com.klin.holoItems.Item;
import com.klin.holoItems.interfaces.Interactable;
import com.klin.holoItems.interfaces.Sealable;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public abstract class Pack extends Item implements Interactable, Sealable {
    private static final int quantity = 1;
    public static final boolean stackable = false;

    private final int size;
    public final String title;
    public final boolean display;

    public Pack(String name, Set<Enchantment> accepted, Material material, String lore,
                int durability, boolean shiny, int size, String title, boolean display,
                int cost, String id, char key){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost, id, key);
        this.size = size;
        this.title = title;
        this.display = display;
    }

    public void ability(PlayerInteractEvent event, Action action){
        if(action==Action.LEFT_CLICK_AIR || action==Action.LEFT_CLICK_BLOCK) {
            event.setCancelled(true);
            Inventory inv = Bukkit.createInventory(null, size, title);
            repack(event.getItem(), inv);
            event.getPlayer().openInventory(inv);
        }
        else
            effect(event);
    }

    protected abstract void effect(PlayerInteractEvent event);

    protected abstract void repack(ItemStack item, Inventory inv);
}
