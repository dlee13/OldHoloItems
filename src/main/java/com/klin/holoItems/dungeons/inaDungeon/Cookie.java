package com.klin.holoItems.dungeons.inaDungeon;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.dungeons.Resetable;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

import java.util.HashMap;
import java.util.Map;

import static org.bukkit.Bukkit.getOnlinePlayers;
import static org.bukkit.Bukkit.getServer;

public class Cookie implements Listener, Resetable {
    public ItemStack[] heads;
    public Map<Player, double[]> buffs;
    private final Map<Player, Integer> shield;

    public Cookie(){
        String[] base64 = new String[]{
        /*takoyaki*/ "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWVmOTNjYWM5YTkxNTM1OWQ0MzgyNmMxZjljZDdiMzUzMWFjNTg2MWQ0MjlhMjZlZmRhOGQzMmMyOTY0MTUifX19",
        /*cookie*/ "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzllNWQ2YmZjNmRjOWFiMjQyMTE4Nzk5ZmFiODk5YTc4NWQxZWE2MjZhODQyMzJmOGE2NDZmZTlhNDA3ZTZkIn19fQ==",
        /*pancakes*/ "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTU4YzA5ZGEzMmZkM2Y0MTg3ZGY3MzU3ZGFkODdkNjkzMjdlNzIzZGU0ODcxNmM4MDRjYTVkNzM3MTY4YmI2YSJ9fX0=",
        /*sushi*/ "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTBlYzU4NWZhMzA0NGNjYmM5NDQ0MWM4YTc5YjRiOGMyM2VhYTZkZGI0ZDUxOTk0OTgyNmM1MWVmMzM1MDU5In19fQ==",
        /*burger*/ "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWZiNDhlMmI5NjljNGMxYjg2YzVmODJhMmUyMzc5OWY0YTZmMzFjZTAwOWE1ZjkyYjM5ZjViMjUwNTdiMmRkMCJ9fX0=",
        /*bread*/ "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTRjYzg3YWZkMWMyZGEyNTNjM2RlMGJmZmEyNjNkNGRlNmYyZDI2NjI1NTQ1ZDI1ZjQ2Y2UyYzY0ZDRiNSJ9fX0=",
        /*kfp*/ "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjhmYjA1MDJhM2FhNWY4YmQzMmE1ZWE1ZTUxOWMzZGQzNTMyMzQxNzBkZmVmOTU5ZWU4YWRiOTQ4N2ZlYSJ9fX0="
        };
        heads = new ItemStack[7];
        for(int i=0; i<base64.length; i++)
            heads[i] = Utility.playerHeadFromBase64(base64[i]);

        buffs = new HashMap<>();
        for(Player player : getOnlinePlayers())
            buffs.put(player, new double[7]);
        shield = new HashMap<>();
        getServer().getPluginManager().registerEvents(this, HoloItems.getInstance());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void buff(EntityDamageByEntityEvent event){
        Entity damager = event.getDamager();
        if(damager instanceof Player) {
            Player player = (Player) damager;
            double[] buff = buffs.get(player);
            if (buff!=null){
                if(buff[0]>0)
                    event.setDamage(event.getDamage()+buff[0]);
                if(buff[6]>0)
                    event.getEntity().setFireTicks((int) (buff[6]*160));
            }
            return;
        }
        Entity entity = event.getEntity();
        if(!(entity instanceof Player))
            return;
        Player player = (Player) entity;
        double[] buff = buffs.get(player);
        if(buff!=null) {
            if(buff[1]>0)
                event.setDamage(event.getDamage()-buff[1]/2);
            if(buff[6]>0) {
                if(damager instanceof Projectile) {
                    ProjectileSource shooter = ((Projectile) damager).getShooter();
                    if(shooter instanceof LivingEntity)
                        ((LivingEntity) shooter).setFireTicks((int) (buff[6] * 160));
                } else damager.setFireTicks((int) (buff[6] * 160));
            }
            if(buff[2]>0){
                Integer countdown = shield.get(player);
                shield.put(player, 8);
                if(countdown!=null)
                    return;
                new Task(HoloItems.getInstance(), 10, 10){
                    public void run(){
                        Integer countdown = shield.get(player);
                        if(countdown==null){
                            cancel();
                            return;
                        }
                        countdown--;
                        if(countdown<=0){
                            double shield = Math.min(buff[2], player.getAbsorptionAmount()+1);
                            if(shield==buff[2])
                                countdown = null;
                            player.setAbsorptionAmount(shield);
                        }
                        shield.replace(player, countdown);
                    }
                };
            }
        }
    }

    @EventHandler
    public void fry(PlayerDeathEvent event){
        double[] buff = buffs.get(event.getEntity());
        if(buff!=null && buff[6]>0){
            String message = event.getDeathMessage();
            int index = message.indexOf("went up in flames");
            if(index>-1){
                event.setDeathMessage(message.substring(0, index) + "was sent to the usual room");
                return;
            }
            index = message.indexOf("was burnt to a crisp");
            if(index>-1) {
                event.setDeathMessage(message.substring(0, index) + "turned out crispy" + message.substring(index+20));
                return;
            }
            index = message.indexOf("burned to death");
            if(index>-1) {
                event.setDeathMessage(message.substring(0, index) + "spent too long in the oven");
                return;
            }
            index = message.indexOf("walked into fire");
            if(index>-1)
                event.setDeathMessage(message.substring(0, index) + "fell into the deep fryer" + message.substring(index+16));
        }
    }

    public void reset(){
        EntityDamageByEntityEvent.getHandlerList().unregister(this);
        PlayerDeathEvent.getHandlerList().unregister(this);
    }
}
