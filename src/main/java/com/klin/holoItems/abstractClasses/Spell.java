package com.klin.holoItems.abstractClasses;

import com.klin.holoItems.Item;
import com.klin.holoItems.collections.gen2.shionCollection.items.SorceressTome;
import com.klin.holoItems.interfaces.Interactable;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public abstract class Spell extends Item implements Interactable {
    private static final Material material = Material.PAPER;
    private static final int quantity = 1;
    private static final int durability = 0;
    public static final boolean stackable = true;
    private static final boolean shiny = true;

    private final Class<? extends Event> condition;

    public Spell(String name, String lore, int cost, Class<? extends Event> condition){
        super(name, material, quantity, lore, durability, stackable, shiny, cost);
        this.condition = condition;
    }

    public void ability(PlayerInteractEvent event, Action action){
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInOffHand();
        SorceressTome sorceressTome = Utility.findItem(item, SorceressTome.class, player);
        if(sorceressTome==null)
            return;
        ItemStack spell = event.getItem();
        if(Utility.onCooldown(spell))
            return;
        Utility.cooldown(spell, 2);
        spell.setAmount(spell.getAmount()-1);

        BookMeta meta = (BookMeta) item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        String spells = container.get(Utility.pack, PersistentDataType.STRING);
        boolean nul = spells==null;
//        if(!nul && spells.split(" ").length>=14){
//            player.sendMessage("Out of memory");
//            return;
//        }
        meta.getPersistentDataContainer().set(Utility.pack, PersistentDataType.STRING, nul?name:spells+" "+name);
        item.setItemMeta(meta);
        //flip to created page
//        meta.setPage(1, meta.getPage(1)+(nul?"":"\n")+name);
//        player.openBook(item);
    }

    public boolean ability(Event event){
        if(condition.isInstance(event))
            return effect(event);
        return false;
    }

    protected abstract boolean effect(Event even);
}