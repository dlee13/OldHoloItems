package com.klin.holoItems.collections.gen3.pekoraCollection.items;

import com.klin.holoItems.Events;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.gen3.pekoraCollection.PekoraCollection;
import com.klin.holoItems.interfaces.Activatable;
import com.klin.holoItems.interfaces.Flauntable;
import com.klin.holoItems.interfaces.Writable;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

public class PekoNote extends Item implements Activatable, Flauntable, Writable {
    public static final String name = "pekoNote";
    private static Set<Player> players = new HashSet<>();

    private static final Material material = Material.WRITABLE_BOOK;
    private static final int quantity = 1;
    private static final String lore =
            "Peko another player";
    private static final int durability = 0;
    public static final boolean stackable = false;
    private static final boolean shiny = true;

    public static final int cost = 0;
    public static final char key = '4';
    public static final String id = ""+PekoraCollection.key+key;

    public PekoNote(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost, id, key);
    }

    public void registerRecipes(){
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6PekoNote");
        item.setItemMeta(meta);

        ShapelessRecipe recipe =
                new ShapelessRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.addIngredient(4, Material.RABBIT_HIDE);
        recipe.addIngredient(3, Material.PAPER);
        recipe.addIngredient(Material.INK_SAC);
        recipe.addIngredient(Material.FEATHER);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        while(message.contains("okay")){
            int index = message.indexOf("okay");
            message = message.substring(0, index)+"ogey"+message.substring(index+4);
        }
        if(!message.endsWith("rrat"))
            message += " peko";
        event.setMessage(message);
    }

    public Set<Player> survey() {
        return players;
    }

    public void ability(PlayerEditBookEvent event, BookMeta meta){
        if(meta.getPageCount()<1)
            return;
        String name = meta.getPage(1);
        if(name.contains("\n"))
            name = name.substring(0, name.indexOf("\n"));
        if(name.isEmpty())
            return;
        Player player = Bukkit.getPlayer(name);
        if(player==null) {
            event.getPlayer().sendMessage("Unknown player");
            if(event.isSigning())
                event.setSigning(false);
        }
        else{
            Player writer = event.getPlayer();
            if(event.isSigning()) {
                World world = player.getWorld();
                world.playSound(player.getLocation(), Sound.AMBIENT_CAVE, 4, 1);
                world.playSound(writer.getLocation(), Sound.AMBIENT_CAVE, 4, 1);
                PekoNote instance = this;
                if(players.isEmpty())
                    Events.activatables.add(instance);
                if(players.add(player)) {
                    new BukkitRunnable() {
                        public void run() {
                            players.remove(player);
                            if (players.isEmpty())
                                Events.activatables.remove(instance);
                    //https://youtu.be/gvRPXNAKUp8
                        }
                    }.runTaskLater(HoloItems.getInstance(), 3580);
                }
                else{
                    event.setSigning(false);
                    writer.sendMessage(name + "'s already peko'd");
                }
            }
            else
                writer.sendMessage(name + " found");
        }
    }
}
