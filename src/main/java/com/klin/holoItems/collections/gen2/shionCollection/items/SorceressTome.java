package com.klin.holoItems.collections.gen2.shionCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.abstractClasses.Spell;
import com.klin.holoItems.interfaces.Holdable;
import com.klin.holoItems.interfaces.Interactable;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class SorceressTome extends Item implements Interactable, Holdable{
    public static final String name = "sorceressTome";

    private static final Material material = Material.WRITTEN_BOOK;
    private static final int quantity = 1;
    private static final String lore =
            "Mix and match powerful spells";
    private static final int durability = 0;
    private static final boolean stackable = true;
    private static final boolean shiny = true;
    public static final int cost = 0;

    public SorceressTome(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost);
    }

    public void registerRecipes(){
        BookMeta meta = (BookMeta) item.getItemMeta();
        meta.setTitle("§6Sorceress Tome");
        meta.setAuthor("klin");
        meta.addPage("");
        item.setItemMeta(meta);

        ShapelessRecipe recipe = new ShapelessRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.addIngredient(Material.GOLDEN_HORSE_ARMOR);
        recipe.addIngredient(2, Material.LEATHER);
        recipe.addIngredient(3, Material.GLOBE_BANNER_PATTERN);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(PlayerInteractEvent event, Action action){
        if(event.getHand()==EquipmentSlot.OFF_HAND) {
            event.setCancelled(true);
            return;
        } if(action==Action.RIGHT_CLICK_AIR || action==Action.RIGHT_CLICK_BLOCK)
            return;
        ItemStack item = event.getItem();
        ItemMeta meta = item.getItemMeta();
        String stored = meta.getPersistentDataContainer().get(Utility.pack, PersistentDataType.STRING);
        if(stored==null || stored.isEmpty())
            return;

        String[] spells = stored.split(" ");
        Spell spell = Utility.findItem(spells[0], Spell.class);
        if(spell!=null) {
            if(spells.length<=1)
                meta.getPersistentDataContainer().remove(Utility.pack);
            else {
                stored = "";
                for (int i = 1; i < spells.length; i++)
                    stored += " " + spells[i];
                meta.getPersistentDataContainer().set(Utility.pack, PersistentDataType.STRING, stored.substring(1));
            }
            item.setItemMeta(meta);
            Player player = event.getPlayer();
            player.getWorld().dropItemNaturally(player.getEyeLocation(), spell.item);
            player.sendMessage("Ripped out : §6"+Utility.formatName(spells[0]));
        }
    }

    public void ability(PlayerInteractEvent event, ItemStack item){
        ItemMeta meta = item.getItemMeta();
        String stored = meta.getPersistentDataContainer().get(Utility.pack, PersistentDataType.STRING);
        if(stored==null || stored.isEmpty())
            return;

        String[] spells = stored.split(" ");
        Spell spell = Utility.findItem(spells[0], Spell.class);
        if(spell!=null && spell.ability(event) && spells.length>1){
            stored = "";
            for (int i = 1; i < spells.length; i++)
                stored += " " + spells[i];
            stored += spells[0];
            meta.getPersistentDataContainer().set(Utility.pack, PersistentDataType.STRING, stored.substring(1));
            item.setItemMeta(meta);
            event.getPlayer().sendMessage("Next spell: §6"+Utility.formatName(spells[1]));
        }
    }
}
