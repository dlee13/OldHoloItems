package com.klin.holoItems.dungeons.inaDungeon;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.dungeons.Resetable;
import com.klin.holoItems.dungeons.inaDungeon.members.Member;
import com.klin.holoItems.dungeons.inaDungeon.members.Watson;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.block.data.Openable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

import static org.bukkit.Bukkit.getServer;

public class Maintenance implements Listener, Resetable {
    private final Material[][] seal;
    //maintain -41 -292 -27 -267
    private final int[] cage;
    private final Map<Block, Integer> decay;
    public final Set<Player> knockBack;
    public Map<Player, Member> members;
    public final Map<Player, AbstractMap.SimpleEntry<Vector, Double>> inputs;

    public Maintenance(int x1, int z1, int x2, int z2){
        seal = new Material[][]{
                {Material.PURPLE_STAINED_GLASS, Material.PURPLE_STAINED_GLASS, Material.WHITE_STAINED_GLASS, Material.PURPLE_STAINED_GLASS, Material.PURPLE_STAINED_GLASS},
                {Material.PURPLE_STAINED_GLASS, Material.PURPLE_STAINED_GLASS, Material.WHITE_STAINED_GLASS, Material.PURPLE_STAINED_GLASS, Material.PURPLE_STAINED_GLASS},
                {Material.WHITE_STAINED_GLASS, Material.WHITE_STAINED_GLASS, Material.PURPLE_STAINED_GLASS, Material.WHITE_STAINED_GLASS, Material.WHITE_STAINED_GLASS},
                {Material.PURPLE_STAINED_GLASS, Material.PURPLE_STAINED_GLASS, Material.WHITE_STAINED_GLASS, Material.PURPLE_STAINED_GLASS, Material.PURPLE_STAINED_GLASS},
                {Material.PURPLE_STAINED_GLASS, Material.PURPLE_STAINED_GLASS, Material.WHITE_STAINED_GLASS, Material.PURPLE_STAINED_GLASS, Material.PURPLE_STAINED_GLASS}
        };
        cage = new int[]{x1, z1, x2, z2};
        decay = new HashMap<>();
        knockBack = new HashSet<>();
        members = new HashMap<>();
        inputs = new HashMap<>();
        getServer().getPluginManager().registerEvents(this, HoloItems.getInstance());
    }

    public Maintenance(){
        seal = new Material[][]{
                {Material.PURPLE_STAINED_GLASS, Material.PURPLE_STAINED_GLASS, Material.WHITE_STAINED_GLASS, Material.PURPLE_STAINED_GLASS, Material.PURPLE_STAINED_GLASS},
                {Material.PURPLE_STAINED_GLASS, Material.PURPLE_STAINED_GLASS, Material.WHITE_STAINED_GLASS, Material.PURPLE_STAINED_GLASS, Material.PURPLE_STAINED_GLASS},
                {Material.WHITE_STAINED_GLASS, Material.WHITE_STAINED_GLASS, Material.PURPLE_STAINED_GLASS, Material.WHITE_STAINED_GLASS, Material.WHITE_STAINED_GLASS},
                {Material.PURPLE_STAINED_GLASS, Material.PURPLE_STAINED_GLASS, Material.WHITE_STAINED_GLASS, Material.PURPLE_STAINED_GLASS, Material.PURPLE_STAINED_GLASS},
                {Material.PURPLE_STAINED_GLASS, Material.PURPLE_STAINED_GLASS, Material.WHITE_STAINED_GLASS, Material.PURPLE_STAINED_GLASS, Material.PURPLE_STAINED_GLASS}
        };
        cage = null;
        decay = new HashMap<>();
        knockBack = new HashSet<>();
        members = new HashMap<>();
        inputs = new HashMap<>();
        getServer().getPluginManager().registerEvents(this, HoloItems.getInstance());
        BlockPlaceEvent.getHandlerList().unregister(this);
        BlockBreakEvent.getHandlerList().unregister(this);
        EntityExplodeEvent.getHandlerList().unregister(this);
        PlayerDropItemEvent.getHandlerList().unregister(this);
    }

    @EventHandler(ignoreCancelled = true)
    public void cage(PlayerMoveEvent event){
        Player player = event.getPlayer();
        Location location = player.getLocation();

        AbstractMap.SimpleEntry<Vector, Double> input = inputs.get(player);
        Vector dir = location.getDirection().setY(0).normalize();
        if(input==null)
            inputs.put(player, new AbstractMap.SimpleEntry<>(dir, 0.0));
        else {
            double angle = input.getKey().crossProduct(dir).getY();
            double total = input.getValue();
            double sign = Math.signum(total);
            double require;
            if(total<0.2)
                require = 0;
            else if(total<Math.PI)
                require = 0.2;
            else
                require = 0.1;
            if((angle-sign*require)*sign<0)
                angle = 0;
            else
                angle += total;
            inputs.replace(player, new AbstractMap.SimpleEntry<>(dir, angle));
        }

        Cookie cookie = (Cookie) InaDungeon.presets.get("cookie");
        if(cookie!=null){
            double[] buff = cookie.buffs.get(player);
            if(buff!=null && buff[6]>0) {
                Block block = location.clone().add(location.getDirection().setY(0).normalize().multiply(-1.5)).getBlock();
                if(block.isEmpty()) {
                    block.setType(Material.FIRE);
                    new BukkitRunnable(){
                        public void run(){
                            if(block.getType()==Material.FIRE)
                                block.setType(Material.AIR);
                        }
                    }.runTaskLater(HoloItems.getInstance(), 80);
                }
            }
        }

        if(cage==null || knockBack.contains(player))
            return;
        int x = location.getBlockX();
        int z = location.getBlockZ();
        Vector velocity = player.getVelocity();
        double difference = 2 + Math.pow(velocity.getX(), 2);
        boolean shatter;
        boolean axis = true;
        if(x-difference<=cage[0]) {
            shatter = x<cage[0];
            x = cage[0];
            z -= 2;
            velocity.setX(1);
            velocity.setZ(0);
        }
        else if(x+difference>=cage[2]) {
            shatter = x>cage[2];
            x = cage[2];
            z -= 2;
            velocity.setX(-1);
            velocity.setZ(0);
        }
        else {
            axis = false;
            difference = 2 + Math.pow(player.getVelocity().getZ(), 2);
            if(z-difference<=cage[1]) {
                shatter = z<cage[1];
                z = cage[1];
                x -= 2;
                velocity.setZ(1);
                velocity.setX(0);
            }
            else if (z+difference>=cage[3]) {
                shatter = z>cage[3];
                z = cage[3];
                x -= 2;
                velocity.setZ(-1);
                velocity.setX(0);
            }
            else return;
        }
        knockBack.add(player);
        if(shatter)
            velocity.multiply(2);
        else{
            World world = player.getWorld();
            int y = location.getBlockY();
            Location loc = new Location(world, x, y - 1, z);
            for (int i = 0; i < seal.length; i++) {
                if (y + i > 256)
                    break;
                for (int j = 0; j < seal[i].length; j++) {
                    Block block = world.getBlockAt(loc.clone().add(!axis ? j : 0, i, axis ? j : 0));
                    if (block.isPassable() || decay.remove(block)!=null) {
                        block.setType(seal[i][j]);
                        decay(block, 4 + 2 * (Math.abs(2 - i) + Math.abs(2 - j)));
                    }
                }
            }
        }
        player.setGliding(false);
        velocity.setY(0.5);
        player.setVelocity(velocity);
        new BukkitRunnable(){
            public void run(){
                knockBack.remove(player);
            }
        }.runTaskLater(HoloItems.getInstance(), 8);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void track(BlockPlaceEvent event){
        Block block = event.getBlockPlaced();
        decay(block, block.getType()==Material.WATER?10:80);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void prevent(BlockBreakEvent event){
        Integer taskId = decay.remove(event.getBlock());
        if(taskId!=null) {
            event.setDropItems(false);
            Bukkit.getScheduler().cancelTask(taskId);
        }
        else
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void regen(EntityExplodeEvent event){
        Set<Block> remove = new HashSet<>();
        List<Block> blocks = event.blockList();
        for(Block block : blocks) {
            if(!decay.containsKey(block))
                remove.add(block);
        }
        blocks.removeAll(remove);
        event.setYield(0);
    }

    private void decay(Block block, int duration){
        decay.put(block,
        new BukkitRunnable(){
            public void run(){
                block.setType(Material.AIR);
                block.getWorld().spawnParticle(Particle.BLOCK_CRACK, block.getLocation().add(0.5,0.5,0.5), 40, 0, 0, 0, 4, block.getType().createBlockData());
                decay.remove(block);
            }
        }.runTaskLater(HoloItems.getInstance(), duration).getTaskId());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void input(PlayerInteractEvent event){
        Block block = event.getClickedBlock();
        if(block!=null && (!decay.containsKey(block) && (block.getBlockData() instanceof Openable || block.getState() instanceof Container)))
            event.setCancelled(true);
        Player player = event.getPlayer();
        Member member = members.get(player);
        if(member!=null)
            member.ability(inputs.get(player).getValue(), event);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void input(EntityDamageByEntityEvent event){
        Entity damager = event.getDamager();
        Entity entity = event.getEntity();
        if(damager instanceof Player) {
            Player player = (Player) damager;
            Member member = members.get(player);
            if(member!=null)
                member.attack(event, entity);
        }
        if(entity instanceof Player) {
            Player player = (Player) entity;
            Member member = members.get(player);
            if(member!=null)
                member.defend(event, player);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void input(PlayerTeleportEvent event){
        Player player = event.getPlayer();
        Member member = members.get(player);
        if(member instanceof Watson) {
            Watson watson = (Watson) member;
            watson.to = event.getTo();
            watson.from = event.getFrom();
            if(watson.taskId==-1)
                watson.cooldown = true;
            else
                Bukkit.getScheduler().cancelTask(watson.taskId);
            watson.taskId = new BukkitRunnable(){
                public void run(){
                    watson.cooldown = false;
                    watson.taskId = -1;
                }
            }.runTaskLater(HoloItems.getInstance(), 20).getTaskId();
        }
    }

    @EventHandler
    public void burst(PlayerToggleSneakEvent event){
        if(event.isSneaking()){
            Member member = members.get(event.getPlayer());
            if (member!=null)
                member.burst(event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void pickUp(PlayerDropItemEvent event){
        Item item = event.getItemDrop();
        if (item.getPickupDelay()>10)
            item.setPickupDelay(10);
    }

    public void reset(){
        PlayerMoveEvent.getHandlerList().unregister(this);
        BlockPlaceEvent.getHandlerList().unregister(this);
        BlockBreakEvent.getHandlerList().unregister(this);
        EntityExplodeEvent.getHandlerList().unregister(this);
        PlayerInteractEvent.getHandlerList().unregister(this);
        EntityDamageByEntityEvent.getHandlerList().unregister(this);
        PlayerToggleSneakEvent.getHandlerList().unregister(this);
        PlayerDropItemEvent.getHandlerList().unregister(this);
    }
}