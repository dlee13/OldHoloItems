package com.klin.holoItems.collections.gen4.watameCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.abstractClasses.Crate;
import com.klin.holoItems.interfaces.Punchable;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LaunchPad extends Crate implements Punchable {
    public static final String name = "launchPad";
    private final Map<Chunk, Integer> tickets;

    private static final Material material = Material.SMOKER;
    private static final int quantity = 1;
    private static final String lore =
            "Light to launch a package";
    private static final int durability = 0;
    public static final boolean stackable = true;
    private static final boolean shiny = false;
    public static final int cost = 0;

    public LaunchPad(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost);
        tickets = new HashMap<>();
    }

    public void registerRecipes(){
        ShapedRecipe recipe =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("a a","bcb","ded");
        recipe.setIngredient('a', Material.SMOOTH_STONE_SLAB);
        recipe.setIngredient('b', Material.GLASS);
        recipe.setIngredient('c', Material.LODESTONE);
        recipe.setIngredient('d', Material.STONE);
        recipe.setIngredient('e', Material.CAMPFIRE);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(PlayerInteractEvent event, Action action){
        if(action!=Action.RIGHT_CLICK_BLOCK)
            return;
        event.setCancelled(true);
        ItemStack fns = event.getItem();
        if(fns==null || fns.getType()!=Material.FLINT_AND_STEEL)
            return;
        Block clicked = event.getClickedBlock();
        World world = clicked.getWorld();
        Block block = world.getHighestBlockAt(clicked.getLocation());
        BlockState state = block.getState();
        Player player = event.getPlayer();
        if(!(state instanceof Barrel)) {
            player.sendMessage("No packages to deliver");
            return;
        }
        Barrel barrel = (Barrel) state;
        if(!UberSheepPackage.name.equals(barrel.getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING))) {
            player.sendMessage("Unsupported container");
            return;
        }

        String name = barrel.getCustomName();
        if(name==null) {
            player.sendMessage("Destination set incorrectly");
            return;
        }
        Location dest = block.getLocation();
        int i=0;
        for(String coord : name.split(" ")){
            try {
                if(i==0)
                    dest.setX(Integer.parseInt(coord)+0.5);
                else if(i==1)
                    dest.setZ(Integer.parseInt(coord)+0.5);
                else if(i>=2) {
                    World dimension = Bukkit.getWorld(coord);
                    if(dimension!=null)
                        dest.setWorld(dimension);
                    else{
                        player.sendMessage("Invalid world name");
                        return;
                    }
                }
                i++;
            } catch(NumberFormatException e){
                player.sendMessage("Destination set incorrectly");
                return;
            }
        }
        Chunk chunk = dest.getChunk();
        if(chunk.getPluginChunkTickets().contains(HoloItems.getInstance())) {
            Integer ticket = tickets.get(chunk);
            tickets.put(chunk, ticket==null?2:(ticket+1));
        } else
            chunk.addPluginChunkTicket(HoloItems.getInstance());
        Block launchPad = dest.getWorld().getHighestBlockAt(dest);
        while(launchPad.getType()==Material.BARREL && UberSheepPackage.name.equals(((TileState) launchPad.getState()).getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING)))
            launchPad = launchPad.getRelative(BlockFace.DOWN);
        state = launchPad.getState();
        if(!(state instanceof Smoker) || !LaunchPad.name.equals(((TileState) state).getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING))) {
            player.sendMessage("No receiving launch pad identified");
            return;
        }
        Inventory inv = barrel.getInventory();
        ItemStack[] contents = inv.getContents().clone();
        //important: setting a container to air drops its contents
        inv.setContents(new ItemStack[27]);
        BlockData data = block.getBlockData();
        Location spawn = block.getLocation().add(0.5, 0.5, 0.5);
        FallingBlock drone = world.spawnFallingBlock(spawn, data);
        block.setType(Material.AIR);

        Smoker smoker = (Smoker) clicked.getState();
        smoker.setBurnTime((short) 20);
        smoker.update();
        world.spawnParticle(Particle.END_ROD, spawn, 4, 0, 0, 0, 0.05);

        new Task(HoloItems.getInstance(), 0, 1){
            int increment = 0;
            public void run() {
                if (increment >= 20 || !drone.isValid() || drone.getLocation().getY() > 256) {
                    drone.remove();

                    String name = world.getName();
                    World world = dest.getWorld();
                    boolean interdimension = !name.equals(world.getName());
                    new Task(HoloItems.getInstance(), 1, 1) {
                        int increment = 0;
                        final FallingBlock drone = world.spawnFallingBlock(dest.clone().add(0, 256-dest.getY(), 0), data);
                        Location loc;
                        public void run() {
                            loc = drone.getLocation();
                            if (increment >= 240 || !drone.isValid()) {
                                if (drone.isValid())
                                    drone.remove();
                                Block drop = world.getHighestBlockAt(dest);
                                if (drop.getType() == Material.BARREL) {
                                    Barrel barrel = (Barrel) drop.getState();
                                    barrel.getPersistentDataContainer().set(Utility.key, PersistentDataType.STRING, UberSheepPackage.name);
                                    barrel.setCustomName(block.getX() + " " + block.getZ() + (interdimension?" "+name:""));
                                    barrel.update();

                                    for(ItemStack excess : barrel.getInventory().addItem(Arrays.stream(contents)
                                            .filter(item -> (item != null && item.getType()!=Material.AIR))
                                            .toArray(ItemStack[]::new)).values())
                                        world.dropItemNaturally(loc, excess);
                                } else {
                                    for (ItemStack content : contents) {
                                        if (content != null && content.getType() != Material.AIR)
                                            world.dropItemNaturally(loc, content);
                                    }
                                }
                                Integer ticket = tickets.remove(chunk);
                                if(ticket!=null) {
                                    ticket--;
                                    if(ticket>1)
                                        tickets.put(chunk, ticket);
                                    else
                                        chunk.removePluginChunkTicket(HoloItems.getInstance());
                                } else
                                    chunk.removePluginChunkTicket(HoloItems.getInstance());
                                cancel();
                                return;
                            }
                            if(increment==0)
                                drone.getPersistentDataContainer().set(Utility.key, PersistentDataType.STRING, UberSheepPackage.name);
//                                container.set(Utility.pack, PersistentDataType.STRING, block.getX() + " " + block.getZ() + (interdimension?" "+name:""));
                            increment++;
                        }
                    };
                    cancel();
                    return;
                }
                drone.setVelocity(drone.getVelocity().add(new Vector(0, 1, 0)));
                increment++;
            }
        };
    }
}
