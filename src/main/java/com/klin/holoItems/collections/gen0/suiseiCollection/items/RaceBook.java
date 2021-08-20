package com.klin.holoItems.collections.gen0.suiseiCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.gen0.suiseiCollection.SuiseiCollection;
import com.klin.holoItems.interfaces.Interactable;
import com.klin.holoItems.interfaces.Writable;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;

public class RaceBook extends Item implements Interactable, Writable {
    public static final String name = "raceBook";
    private static final Set<Player> drivers = new HashSet<>();

    private static final Material material = Material.MUSIC_DISC_STAL;
    private static final int quantity = 1;
    private static final String lore =
            "Hold to accelerate and turn";
    private static final int durability = 0;
    public static final boolean stackable = false;
    private static final boolean shiny = false;

    public static final int cost = -1;
    public static final char key = '5';
    public static final String id = ""+SuiseiCollection.key+key;

    public RaceBook(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost, id, key);
    }

    public void registerRecipes() {

    }

    public void ability(PlayerEditBookEvent event, BookMeta meta) {

    }

    public void ability(PlayerInteractEvent event, Action action){
        if(action!=Action.RIGHT_CLICK_AIR && action!=Action.RIGHT_CLICK_BLOCK)
            return;
        ItemStack item = event.getItem();
        if(Utility.onCooldown(item))
            return;
        Utility.cooldown(item, 20);
        Player player = event.getPlayer();
        if(drivers.remove(player))
            return;
        Entity cart = player.getVehicle();
        if(!(cart instanceof Minecart))
            return;
        drivers.add(player);
        cart.eject();
        player.setGravity(false);
        new Task(HoloItems.getInstance(), 0, 1){
            int increment = 0;
            double limit = 3;
            public void run(){
                if(increment>=1200 || !drivers.contains(player)){
                    cart.remove();
                    player.setGravity(true);
                    drivers.remove(player);
                    cancel();
                    return;
                }
                cart.teleport(player.getLocation());
                increment++;
                if(player.isSneaking())
                    limit = Math.max(0, limit-0.2);
                else
                    limit = 3;
                Location loc = player.getLocation();
                Vector dir = loc.getDirection().setY(0).normalize();
                World world = player.getWorld();
                Block rise = world.getBlockAt(loc.clone().add(dir));
                Block fall = world.getBlockAt(loc.add(0, -1, 0));
                Vector velocity = accelerate(player.getVelocity().add(dir.multiply(0.2).setY(rise.isPassable()?(fall.isPassable()?-0.2:0):0.2)), limit);
                player.setVelocity(velocity);
                cart.setVelocity(velocity);
            }
        };
    }

    private static Vector accelerate(Vector velocity, double limit){
        if(limit==1)
            return velocity;
        double length = velocity.length();
        return velocity.normalize().multiply(Math.min(limit, length));
    }
}
