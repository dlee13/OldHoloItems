package com.klin.holoItems.dungeons.inaDungeon.members;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.dungeons.inaDungeon.InaDungeon;
import com.klin.holoItems.dungeons.inaDungeon.Maintenance;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Kiara extends Member {
    public int taskId;
    private boolean dunk;

    public Kiara(Player player){
        super(player, BarColor.GREEN);
        taskId = -1;
        dunk = false;
    }

    public void ability(double angle, PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if(item==null || item.getType()!=Material.SHIELD){
            powerDunk(angle, event);
            return;
        }
        cooldown = false;
        if(taskId!=-1){
            Bukkit.getScheduler().cancelTask(taskId);
            taskId = -1;
        }
        new Task(HoloItems.getInstance(), 1, 1){
            int increment = 0;
            public void run(){
                if(increment>=1200 || !player.isValid() || player.isDead()){
                    cancel();
                    return;
                }
                if(!player.isHandRaised()){
                    double abs = Math.abs(((Maintenance) InaDungeon.presets.get("maintenance")).inputs.get(player).getValue());
                    if(abs<Math.PI*0.4)
                        return;
                    cooldown = true;
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("Command: Reversal"));
                    taskId = new BukkitRunnable(){
                        public void run(){
                            cooldown = false;
                            taskId = -1;
                        }
                    }.runTaskLater(HoloItems.getInstance(), 6).getTaskId();
                    cancel();
                    return;
                }
                increment++;
            }
        };
    }

    private void powerDunk(double angle, PlayerInteractEvent event){
        if(cooldown)
            return;
        double abs = Math.abs(angle);
        Vector velocity = player.getLocation().getDirection().setY(0).normalize();
        if(dunk){
            //test
            System.out.println("dunk");
            //
            dunk = false;
            player.setVelocity(player.getVelocity().add(velocity.setY(-2)));
        } else if(abs>Math.PI*0.5 && abs<Math.PI*1.2){
            //test
            System.out.println("power");
            //
            dunk = true;
            player.setVelocity(player.getVelocity().add(velocity.multiply(2).setY(1)));
//            player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20, 1));
        } else return;
        World world = player.getWorld();
        new Task(HoloItems.getInstance(), 1, 1){
            int increment = 0;
            public void run(){
                if(increment>=20 || ((Entity) player).isOnGround()){
                    dunk = false;
                    cancel();
                    return;
                }
                world.spawnParticle(Particle.FLAME, player.getLocation().add(0, 2, 0), 1);
                for(Entity nearby : player.getNearbyEntities(1, 1, 1)){
                    if(nearby instanceof LivingEntity){
                        LivingEntity living = (LivingEntity) nearby;
                        living.setNoDamageTicks(0);
                        living.damage(1);
                        living.setFireTicks(160);
                        if(dunk)
                            living.setVelocity(player.getVelocity());
                    }
                }
                increment++;
            }
        };
    }

    public void attack(EntityDamageByEntityEvent event, Entity entity) {

    }

    public void defend(EntityDamageByEntityEvent event, Entity damager){
        if(!cooldown || !(damager instanceof LivingEntity)) //reversal
            return;
        event.setCancelled(true);
        LivingEntity living = (LivingEntity) damager;
        living.setFireTicks(160);
        Vector velocity = living.getLocation().subtract(player.getLocation()).toVector().setY(0).normalize().setY(1);
        Vector twirl = new Vector(-velocity.getZ(), velocity.getY(), -velocity.getX());
        Vector helix = twirl.clone();
        velocity.multiply(0.5);
        Vector axis = velocity.multiply(0.5);
        World world = player.getWorld();
        Location loc = living.getLocation();
        new Task(HoloItems.getInstance(), 0, 1){
            int increment = 0;
            public void run(){
                if(increment>=160){
                    cancel();
                    return;
                }
                living.setNoDamageTicks(0);
                living.damage(0.1);
                living.setVelocity(velocity);
                for(int i=0; i<2; i++) {
                    Location clone = loc.add(axis).clone().add(twirl);
                    world.spawnParticle(clone.getBlock().getLightLevel()<=7?Particle.SOUL_FIRE_FLAME:Particle.FLAME, clone, 1, 0, 0, 0, 0);
                    clone = loc.clone().add(helix);
                    world.spawnParticle(clone.getBlock().getLightLevel()<=7?Particle.SOUL_FIRE_FLAME:Particle.FLAME, clone, 1, 0, 0, 0, 0);
                }
                twirl.rotateAroundAxis(axis, Math.PI/6);
                helix.rotateAroundAxis(axis, -Math.PI/6);
                increment++;
            }
        };
    }

    @Override
    public void burst(PlayerToggleSneakEvent event){
        BossBar bossbar = Bukkit.createBossBar(com.klin.holoItems.utility.Utility.key, "charging. . .", barColor, BarStyle.SEGMENTED_20);
        bossbar.addPlayer(player);
        new Task(HoloItems.getInstance(), 0, 1) {
            int increment = 0;
            boolean cap = false;
            @Override
            public void run() {
                if (!player.isSneaking() || !player.isSneaking() && increment>=16) {
                    bossbar.removePlayer(player);
                    Bukkit.removeBossBar(Utility.key);
                    burst(increment);
                    cancel();
                    return;
                }
                bossbar.setProgress(increment*0.05);
                if(cap)
                    increment = Math.max(16, increment-1);
                else if(increment>=20)
                    cap = true;
                else
                    increment++;
            }
        };
    }

    public void burst(int charge) {

    }
}
