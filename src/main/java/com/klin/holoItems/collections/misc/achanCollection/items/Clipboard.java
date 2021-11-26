package com.klin.holoItems.collections.misc.achanCollection.items;

import com.klin.holoItems.Events;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.interfaces.Interactable;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class Clipboard extends Item implements Interactable {
    public static final String name = "clipboard";

    private static final Material material = Material.POLISHED_BLACKSTONE_PRESSURE_PLATE;
    private static final int quantity = 1;
    private static final String lore =
            "Manage item durability";
    private static final int durability = 0;
    private static final boolean stackable = false;
    private static final boolean shiny = true;
    public static final int cost = 0;

    public Clipboard(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost);
    }

    public void registerRecipes(){
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("aaa","bbb");
        recipe.setIngredient('a', Material.PAPER);
        recipe.setIngredient('b', Material.POLISHED_BLACKSTONE);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(PlayerInteractEvent event, Action action){
        if(action!=Action.RIGHT_CLICK_AIR && action!=Action.RIGHT_CLICK_BLOCK)
            return;
        event.setCancelled(true);
        Player player = event.getPlayer();
        if(Events.managers.remove(player)){
            Scoreboard scoreboard = player.getScoreboard();
            Objective obj = scoreboard.getObjective("durability");
            if(obj!=null)
                obj.unregister();
        } else {
            Events.managers.add(player);
            Events.manageDurability(player);
        }
    }
}