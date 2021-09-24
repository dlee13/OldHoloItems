package com.klin.holoItems.abstractClasses;

import com.klin.holoItems.interfaces.Clickable;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Set;

public abstract class SlidingPack extends Pack implements Clickable {
    private static final int size = 9;
    public static final String title = "Selecting. . .";
    public static final boolean display = true;

    public final ItemStack content;

    public SlidingPack(String name, Set<Enchantment> accepted, Material material, String lore,
                       int durability, boolean shiny, int cost, ItemStack content){
        super(name, accepted, material, lore, durability, shiny, size, title, display, cost);
        this.content = content;
    }

    public int ability(Inventory inv, ItemStack item, Player player){
        return -1;
    }

    protected void repack(ItemStack item, Inventory inv) {
        ItemStack selection = content.clone();
        ItemMeta meta = selection.getItemMeta();
        meta.getPersistentDataContainer().set(Utility.key, PersistentDataType.STRING, name);
        selection.setItemMeta(meta);

        for(int i=0; i<9; i++) {
            selection.setAmount(i+1);
            inv.setItem(i, selection);
        }
    }

    public void ability(InventoryClickEvent event, boolean current){
        if(!current || event.getSlot()>=event.getView().getTopInventory().getSize())
            return;
        ItemStack selected = event.getCurrentItem();
        ItemMeta selectedMeta = selected.getItemMeta();
        selectedMeta.addEnchant(Enchantment.LUCK, 1, false);
        selectedMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        selected.setItemMeta(selectedMeta);

        Player player = (Player) event.getWhoClicked();
        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();
        if(meta==null)
            return;
        meta.getPersistentDataContainer().set(Utility.pack, PersistentDataType.INTEGER, selected.getAmount());
        item.setItemMeta(meta);
    }
}