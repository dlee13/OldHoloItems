package com.klin.holoItems.collections.gen3.pekoraCollection.items;

import com.klin.holoItems.Events;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.interfaces.Activatable;
import com.klin.holoItems.interfaces.Craftable;
import com.klin.holoItems.interfaces.Flauntable;
import com.klin.holoItems.interfaces.Writable;
import com.klin.holoItems.utility.Utility;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class PekoNote extends Item implements Activatable, Flauntable, Writable, Craftable {
    public static final String name = "pekoNote";
    private static final Set<Player> players = new HashSet<>();

    private static final Material material = Material.WRITABLE_BOOK;
    private static final int quantity = 1;
    private static final String lore =
            "Peko another player";
    private static final int durability = 0;
    public static final boolean stackable = false;
    private static final boolean shiny = true;
    public static final int cost = 0;

    public PekoNote(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost);
    }

    public void registerRecipes(){
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6PekoNote");
        item.setItemMeta(meta);

        ShapelessRecipe recipe = new ShapelessRecipe(new NamespacedKey(HoloItems.getInstance(), name+"0"), item);
        recipe.addIngredient(4, Material.RABBIT_HIDE);
        recipe.addIngredient(3, Material.PAPER);
        recipe.addIngredient(Material.INK_SAC);
        recipe.addIngredient(Material.FEATHER);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);

        ItemStack written = item.clone();
        written.setType(Material.WRITTEN_BOOK);
        for(int i=2; i<=9; i++){
            ShapelessRecipe combine = new ShapelessRecipe(new NamespacedKey(HoloItems.getInstance(), name+i), written);
            combine.addIngredient(i, Material.WRITTEN_BOOK);
            combine.setGroup(name);
            Bukkit.getServer().addRecipe(combine);
        }
    }

    public void ability(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        int index;
        while((index = message.indexOf("okay"))>=0)
            message = message.substring(0, index)+"ogey"+message.substring(index+4);
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
                    meta.setPage(1, name);
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

    public void ability(CraftItemEvent event) {
        ItemStack result = event.getInventory().getResult();
        if(item.equals(result))
            return;
        Inventory inv = event.getInventory();
        List<String> names = new ArrayList<>();
        BookMeta meta = null;
        for(int i=1; i<=9; i++){
            ItemStack book = inv.getItem(i);
            if(book==null || book.getType()==Material.AIR)
                continue;
            ItemMeta itemMeta = book.getItemMeta();
            if(itemMeta==null || !name.equals(itemMeta.getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING))){
                event.setCancelled(true);
                return;
            }
            BookMeta bookMeta = (BookMeta) itemMeta;
            if(meta==null)
                meta = bookMeta;
            for(String page : bookMeta.getPages())
                names.addAll(Arrays.asList(page.split("\n")));
        }
//        Collections.sort(names);
        int increment = 0;
        int page = 1;
        String list = "";
        for(String name : names){
            if(increment>=14) {
                if(page==1)
                    meta.setPage(page, list);
                else
                    meta.addPage(list);
                list = "";
                increment = 0;
                page++;
            }
            else
                list += name + "\n";
            increment++;
        } if(!list.isEmpty()) {
            if(page==1)
                meta.setPage(page, list);
            else
                meta.addPage(list);
        }
        result.setItemMeta(meta);
    }
}
