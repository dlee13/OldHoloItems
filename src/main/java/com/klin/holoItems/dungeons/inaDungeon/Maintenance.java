package com.klin.holoItems.dungeons.inaDungeon;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.dungeons.inaDungeon.classes.Member;
import com.klin.holoItems.dungeons.inaDungeon.classes.Watson;
import com.klin.holoItems.utility.Task;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

import static org.bukkit.Bukkit.getServer;

public class Maintenance implements Listener {
    private final Material[][] seal;
    //maintain -41 -292 -27 -267
    private final int[] cage;
    private final Set<Block> decay;
    public final Set<Player> knockBack;
    public Map<Player, Member> classes;
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
        decay = new HashSet<>();
        knockBack = new HashSet<>();
        classes = new HashMap<>();
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
        decay = new HashSet<>();
        knockBack = new HashSet<>();
        classes = new HashMap<>();
        inputs = new HashMap<>();
        getServer().getPluginManager().registerEvents(this, HoloItems.getInstance());
    }

    @EventHandler
    public void cage(PlayerMoveEvent event){
        if(event.isCancelled())
            return;
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

        if(InaDungeon.cookie!=null){
            double[] buff = InaDungeon.cookie.buffs.get(player);
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
                    if (block.isPassable() || decay.remove(block)) {
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

    @EventHandler(priority = EventPriority.HIGHEST)
    public void track(BlockPlaceEvent event){
        if(!event.isCancelled())
            decay(event.getBlock(), 80);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void prevent(BlockBreakEvent event){
        if(!event.isCancelled()) {
            if(decay.remove(event.getBlock()))
                event.setDropItems(false);
            else
                event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void regen(EntityExplodeEvent event){
        if(event.isCancelled())
            return;
        event.setYield(0);
        Map<Integer, Map<Block, BlockData>> blast = new HashMap<>();
        Location center = event.getLocation();
        int max = -1;
        for(Block block : event.blockList()) {
            int radius = (int) center.distance(block.getLocation().add(0.5, 0.5, 0.5));
            blast.computeIfAbsent(radius, k -> new HashMap<>()).put(block, block.getBlockData());
            max = Math.max(max, radius);
        }
        int temp = max;
        new Task(HoloItems.getInstance(), 80, 1){
            int radius = temp;
            Map<Block, BlockData> regen;
            public void run(){
                regen = blast.get(radius);
                if(regen==null){
                    cancel();
                    return;
                }
                for(Block block : regen.keySet()){
                    BlockData data = regen.get(block);
                    if(!data.getMaterial().isAir())
                        block.setBlockData(data);
                }
                radius--;
            }
        };
    }

    private void decay(Block block, int duration){
        decay.add(block);
        new Task(HoloItems.getInstance(), 2, 1){
            int increment = 0;
            public void run(){
                if(increment>=duration || !decay.contains(block)) {
                    block.getWorld().spawnParticle(Particle.BLOCK_CRACK, block.getLocation().add(0.5,0.5,0.5), 40, 0, 0, 0, 4, block.getType().createBlockData());
                    block.setType(Material.AIR);
                    cancel();
                    return;
                }
                increment++;
            }
        };
    }

    @EventHandler
    public void input(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Member member = classes.get(player);
        if(member!=null)
            member.ability(inputs.get(player).getValue(), event);
    }

    @EventHandler
    public void input(PlayerTeleportEvent event){
        Player player = event.getPlayer();
        Member member = classes.get(player);
        if(member instanceof Watson) {
            Watson watson = (Watson) member;
            watson.to = event.getTo();
            watson.from = event.getFrom();
            int teleport = watson.teleport+=10;
            if(teleport==10){
                new Task(HoloItems.getInstance(), 1, 1){
                    public void run(){
                        if(watson.teleport<=0){
                            cancel();
                            return;
                        }
                        watson.teleport--;
                    }
                };
            }
        }
    }

    public void reset(){
        PlayerMoveEvent.getHandlerList().unregister(this);
        BlockPlaceEvent.getHandlerList().unregister(this);
        BlockBreakEvent.getHandlerList().unregister(this);
        PlayerInteractEvent.getHandlerList().unregister(this);
        for(Block block : decay)
            block.breakNaturally();
    }
}

//    List<BlockFace> input = inputs.computeIfAbsent(player, k -> new ArrayList<>());
//    BlockFace face = player.getFacing();
//    int size = input.size();
//        if(size>0) {
//                BlockFace blockFace = input.get(size - 1);
//                if (face != blockFace) {
//                for (int i = 1; i <= 3; i++) {
//                int index = size - i;
//                if (index < 0)
//        break;
//        if (blockFace == input.get(index))
//        input.remove(index);
//        }
//        input.add(blockFace);
//        }
//        }
//        if(size>=8)
//        input.remove(0);
//        input.add(face);

//    public boolean input(List<BlockFace> inputs){
//        int size = inputs.size();
//        if(size<4)
//            return false;
//        BlockFace prev;
//        BlockFace curr = inputs.get(size-4);
//        boolean leftFullCircle = true;
//        for(int i=size-3; i<=size-1; i++){
//            prev = curr;
//            curr = inputs.get(i);
//            if(Utility.left.get(prev)!=curr) {
//                leftFullCircle = false;
//                break;
//            }
//        }
//        if(leftFullCircle)
//            return true;
//        curr = inputs.get(size-4);
//        boolean rightFullCircle = true;
//        for(int i=size-3; i<=size-1; i++){
//            prev = curr;
//            curr = inputs.get(i);
//            if(Utility.opposites.get(Utility.left.get(prev))!=curr) {
//                rightFullCircle = false;
//                break;
//            }
//        }
//        return rightFullCircle;
//    }