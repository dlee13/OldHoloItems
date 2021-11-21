package com.klin.holoItems.collections.gen2.shionCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.abstractClasses.Spell;
import com.klin.holoItems.utility.Task;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LargeFireball;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Fireball extends Spell {
    public static final String name = "fireball";

    private static final String lore =
            "Light a fire and watch it float";
    public static final int cost = -1;

    private static final Class<? extends Event> condition = PlayerInteractEvent.class;

    public Fireball(){
        super(name, lore, cost, condition);
    }

    public void registerRecipes() { }

    protected boolean effect(Event even) {
        PlayerInteractEvent event = (PlayerInteractEvent) even;
        Action action = event.getAction();
        if(action!=Action.RIGHT_CLICK_BLOCK)
            return false;
        ItemStack item = event.getItem();
        if(item==null || item.getType()!= Material.FLINT_AND_STEEL)
            return false;

        Block block = event.getClickedBlock().getRelative(BlockFace.UP);
        Player player = event.getPlayer();
        new BukkitRunnable(){
            public void run(){
                Material type = block.getType();
                Location loc = block.getLocation().add(0.5, 1.4, 0.5).setDirection(new Vector(0, -0.1, 0));
                org.bukkit.entity.Fireball fireball;
                if(type==Material.FIRE)
                    fireball = loc.getWorld().spawn(loc, LargeFireball.class);
                else if(type==Material.SOUL_FIRE)
                    fireball = loc.getWorld().spawn(loc, org.bukkit.entity.Fireball.class);
                else return;
                fireball.setShooter(player);
                block.setType(Material.AIR);
                new Task(HoloItems.getInstance(), 0, 1){
                    int increment = 0;
                    public void  run(){
                        if(increment>=20 || fireball.getDirection().getY()!=-0.1){
                            cancel();
                            return;
                        }
                        fireball.teleport(loc);
                        increment++;
                    }
                };
            }
        }.runTask(HoloItems.getInstance());
        return true;
    }
}
