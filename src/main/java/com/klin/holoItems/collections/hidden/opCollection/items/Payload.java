package com.klin.holoItems.collections.hidden.opCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.abstractClasses.SlidingPack;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.entity.minecart.ExplosiveMinecart;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Payload extends SlidingPack {
    public static final String name = "payload";
    public static final Set<Enchantment> accepted = null;

    private static final Material material = Material.MINECART;
    private static final String lore =
            "Set a time and destination,\n"+
            "line it up and watch it go";
    private static final int durability = 0;
    public static final boolean stackable = false;
    private static final boolean shiny = true;

    private static final ItemStack content = new ItemStack(Material.TNT);

    public static final int cost = -1;
    public static final char key = '3';

    public Payload(){
        super(name, accepted, material, lore, durability, shiny, cost, content);
    }

    public void registerRecipes(){}

    protected void effect(PlayerInteractEvent event){
        if(event.getAction()!=Action.RIGHT_CLICK_BLOCK)
            return;
        if(Utility.onCooldown(event.getItem()))
            return;
        Utility.cooldown(event.getItem(), 20);

        ItemStack item = event.getItem();
        Block block = event.getClickedBlock();
        ItemMeta meta = item.getItemMeta();
        Player player = event.getPlayer();

        int[] coords = meta.getPersistentDataContainer().get(Utility.data, PersistentDataType.INTEGER_ARRAY);
        if(event.getBlockFace()!=BlockFace.UP){
            if(coords==null) {
                coords = new int[]{block.getX(), block.getY(), block.getZ()};
                player.sendMessage("Corner 1: §7"+coords[0]+" "+coords[1]+" "+coords[2]);
            }
            else{
                coords = new int[]{block.getX(), block.getY(), block.getZ(), coords[0], coords[1], coords[2]};
                player.sendMessage("Corner 1: §7"+coords[0]+" "+coords[1]+" "+coords[2] + " §fCorner 2: §7"+coords[3]+" "+coords[4]+" "+coords[5]);
            }
            meta.getPersistentDataContainer().set(Utility.data, PersistentDataType.INTEGER_ARRAY, coords);
            item.setItemMeta(meta);
            return;
        }

        if(coords==null || coords.length<6) {
            player.sendMessage("Corners unset");
            return;
        }
        Integer lifespan = meta.getPersistentDataContainer().get(Utility.pack, PersistentDataType.INTEGER);
        if(lifespan==null) {
            player.sendMessage("Set lifespan, in minutes, of the payload");
            return;
        }
        if(Math.abs((coords[0]-coords[3])*(coords[1]-coords[4])*(coords[2]-coords[5]))>3276){
            player.sendMessage("Detonation area exceeds maximum of 3276 blocks");
            return;
        }
        int[] start = new int[]{block.getX(), block.getY()+1, block.getZ()};
        if((coords[0]-start[0])*(coords[3]-start[0])<0 && (coords[1]-start[1])*(coords[4]-start[1])<0 && (coords[2]-start[2])*(coords[5]-start[2])<0){
            player.sendMessage("Payload cannot start inside detonation area");
            return;
        }

        BlockFace direction = player.getFacing();
        int x = 0;
        int z = 0;
        int[] finish;
        switch(direction){
            case NORTH:
                z = -1;
            case SOUTH:
                if(z==0)
                    z = 1;
                if(Math.abs(coords[2]-(start[2]+z))>Math.abs((coords[2]-start[2])) || (coords[0]-start[0])*(coords[3]-start[0])>0){
                    player.sendMessage("Payload isn't aligned to detonation area");
                    return;
                }
                finish = new int[]{start[0], (coords[1]+coords[4])/2, (Math.abs(coords[2]-start[2])>Math.abs(coords[5]-start[2])?coords[5]:coords[2])-2*z};
                break;
            case EAST:
                x = 1;
            case WEST:
                if(x==0)
                    x = -1;
                if(Math.abs(coords[0]-(start[0]+x))>Math.abs((coords[0]-start[0])) || (coords[2]-start[2])*(coords[5]-start[2])>0){
                    player.sendMessage("Payload isn't aligned to detonation area");
                    return;
                }
                finish = new int[]{(Math.abs(coords[0]-start[0])>Math.abs(coords[3]-start[0])?coords[3]:coords[0])-2*x, (coords[1]+coords[4])/2, start[2]};
                break;
            default:
                player.sendMessage("Face a cardinal direction: "+direction);
                return;
        }

        Queue<Location> path = new LinkedList<>();
        Block current = block.getRelative(BlockFace.UP);
        boolean found = false;
        for(int i=0; i<327; i++){
            if(current.isEmpty()) {
                Block down = current.getRelative(BlockFace.DOWN);
                if(down.isEmpty() && !(current.getX()==finish[0] && current.getZ()==finish[2])){
                    player.sendMessage("The path shouldn't delevate at any point");
                    return;
                }
                path.add(current.getLocation().add(0.5, Utility.slabs.contains(down.getType())?-0.5:0, 0.5));
            }
            else {
                double y = Utility.slabs.contains(current.getType())?-0.5:0;
                current = current.getRelative(BlockFace.UP);
                if (current.isEmpty())
                    path.add(current.getLocation().add(0.5, y, 0.5));
                else{
                    player.sendMessage("The path shouldn't elevate more than at most a block at any point");
                    return;
                }
            }
            if(current.getX()==finish[0] && current.getZ()==finish[2]) {
                start[1] = current.getY();
                found = true;
                break;
            }
            current = current.getRelative(direction);
        }
        if(!found){
            player.sendMessage("No valid path found");
            return;
        }

        World world = block.getWorld();
        //map path, including where to throw tnt at end
        int above = Integer.signum(finish[1]-start[1]);
        for(int i=0; i<=Math.abs(finish[1]-start[1]); i+=1){
            for(int j=0; j<=1; j++){
                if(!world.getBlockAt(finish[0]+x*j, start[1]+i*above, finish[2]+z*j).isEmpty()){
                    player.sendMessage("Firing area isn't clear");
                    return;
                }
            }
        }

        ExplosiveMinecart payload = world.spawn(block.getLocation().add(0.5, 1, 0.5), ExplosiveMinecart.class);
        payload.setInvulnerable(true);
        Block explode = world.getBlockAt(finish[0]+2*x, finish[1], finish[2]+2*z);
        int[] copy = coords.clone();
        new Task(HoloItems.getInstance(), 1, 1){
            Location current;
            Location next = path.poll();
            double xInterval;
            double zInterval;
            int increment = 10;
            int ticks = lifespan*1200;

            public void run(){
                if(!payload.isValid() || path.isEmpty() && increment==10) {
                    payload.remove();
                    cancel();

                    Minecart minecart = world.spawn(next, Minecart.class);
                    TNTPrimed tnt = world.spawn(next, TNTPrimed.class);
                    tnt.setFuseTicks(64);
                    Vector aim;
                    if(next.getY()>finish[1])
                        aim = new Vector(xInterval, 0.25, zInterval);
                    else
                        aim = new Vector(xInterval/2, ((float)(finish[1]-start[1]))/2.5, zInterval/2);
                    tnt.setVelocity(aim);
                    new Task(HoloItems.getInstance(), 1, 1){
                        int increment = 0;

                        public void run(){
                            Location loc = tnt.getLocation();
                            if(tnt.getVelocity().getY()<0 && Math.abs(loc.getY()-finish[1])<3 || increment>=60){
                                world.spawnParticle(Particle.EXPLOSION_HUGE, loc, 1);
                                world.playSound(loc, Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 0.4f, 1f);
                                minecart.remove();
                                tnt.remove();
                                cancel();

                                Queue<Block> toExplode = new LinkedList<>();
                                Set<Block> checked = new HashSet<>();
                                toExplode.add(explode);
                                new Task(HoloItems.getInstance(), 2, 2){
                                    public void run(){
                                        if(toExplode.isEmpty()){
                                            cancel();
                                            return;
                                        }

                                        for(int i=0; i<2; i++) {
                                            Block center = toExplode.poll();
                                            if(center==null)
                                                break;
                                            checked.add(center);

                                            center.setType(Material.AIR);
                                            world.spawnParticle(Particle.EXPLOSION_LARGE, center.getLocation().add(0.5, 0, 0.5), 1);
                                            world.playSound(loc, Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 0.1f, 1f);
                                            for (Block block : new Block[]{
                                                    center.getRelative(BlockFace.UP),
                                                    center.getRelative(BlockFace.NORTH),
                                                    center.getRelative(BlockFace.SOUTH),
                                                    center.getRelative(BlockFace.EAST),
                                                    center.getRelative(BlockFace.WEST),
                                                    center.getRelative(BlockFace.DOWN)}) {
                                                if (!block.isEmpty() && (copy[0]-block.getX())*(copy[3]-block.getX())<=0 && (copy[1]-block.getY())*(copy[4]-block.getY())<=0 && (copy[2]-block.getZ())*(copy[5]-block.getZ())<=0 && !checked.contains(block) && !toExplode.contains(block))
                                                    toExplode.add(block);
                                            }
                                        }
                                    }
                                };
                                return;
                            }
                            minecart.teleport(next);
                            increment++;
                        }
                    };
                    return;
                }

                if(ticks<=0){
                    Location loc = payload.getLocation();
                    world.spawnParticle(Particle.EXPLOSION_HUGE, loc, 1);
                    world.playSound(loc, Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1f, 1f);
                    payload.remove();

                    for(Entity entity : payload.getNearbyEntities(10, 10, 10)){
                        if(entity instanceof LivingEntity) {
                            ((LivingEntity) entity).damage(100);
                            world.spawnParticle(Particle.EXPLOSION_LARGE, entity.getLocation(), 1);
                        }
                    }
                    cancel();
                    return;
                }

                if(!payload.getNearbyEntities(2, 2, 2).isEmpty()) {
                    if(increment==10) {
                        current = next;
                        next = path.poll();
                        xInterval = (next.getX()-current.getX())/10;
                        zInterval = (next.getZ()-current.getZ())/10;
                        payload.teleport(current);
                        increment = 1;
                    }
                    else {
                        payload.teleport(current.clone().add(xInterval*increment, 0, zInterval*increment));
                        increment++;
                    }
                }
                ticks--;
            }
        };
    }
}
