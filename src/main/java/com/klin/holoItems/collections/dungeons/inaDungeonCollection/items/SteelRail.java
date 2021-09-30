package com.klin.holoItems.collections.dungeons.inaDungeonCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.dungeons.inaDungeonCollection.InaDungeonCollection;
import com.klin.holoItems.dungeons.inaDungeon.InaDungeon;
import com.klin.holoItems.dungeons.inaDungeon.Payload;
import com.klin.holoItems.interfaces.Interactable;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class SteelRail extends Item implements Interactable {
    public static final String name = "steelRail";

    private static final Material material = Material.RAIL;
    private static final int quantity = 1;
    private static final String lore =
            "Paves the way for the payload";
    private static final int durability = 0;
    public static final boolean stackable = true;
    private static final boolean shiny = false;
    public static final int cost = -1;

    public SteelRail(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost);
    }

    public void registerRecipes(){}

    public void ability(PlayerInteractEvent event, Action action) {
        if(action!=Action.RIGHT_CLICK_BLOCK || InaDungeon.presets==null)
            return;
        Payload payload = (Payload) InaDungeon.presets.get("payload");
        if(payload==null || payload.spikes.isEmpty())
            return;
        Block block = event.getClickedBlock();
        Block spike = payload.spikes.get(0);
        if(block.equals(spike)) {
            payload.spikes.remove(0);
            if(event.getPlayer().getGameMode()!= GameMode.CREATIVE) {
                ItemStack item = event.getItem();
                item.setAmount(item.getAmount() - 1);
            }
            new BukkitRunnable(){
                public void run(){
                    if(payload.spikes.isEmpty())
                        payload.fire(block.getLocation());
                    else
                        payload.push(block.getZ());
                }
            }.runTask(HoloItems.getInstance());
        }
    }
}
