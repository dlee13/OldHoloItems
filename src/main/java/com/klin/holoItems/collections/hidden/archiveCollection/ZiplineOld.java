package com.klin.holoItems.collections.hidden.archiveCollection;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.interfaces.Interactable;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ShapedRecipe;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ZiplineOld extends Item implements Interactable {
    public static final String name = "zipline";
    public static final Set<Enchantment> accepted = null;
    Map<BlockFace, BlockFace> opposites = Stream.of(new BlockFace[][] {
            { BlockFace.UP, BlockFace.DOWN },
            { BlockFace.DOWN, BlockFace.UP },
            { BlockFace.NORTH, BlockFace.SOUTH },
            { BlockFace.SOUTH, BlockFace.NORTH },
            { BlockFace.EAST, BlockFace.WEST },
            { BlockFace.WEST, BlockFace.EAST },
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));
    Set<BlockFace> faces = Stream.of(
            BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST).
            collect(Collectors.toCollection(HashSet::new));
    Set<BlockFace> vertical = Stream.of(BlockFace.UP, BlockFace.DOWN).
            collect(Collectors.toCollection(HashSet::new));
    Set<Material> fences = Stream.of(
            Material.ACACIA_FENCE, Material.BIRCH_FENCE, Material.OAK_FENCE, Material.DARK_OAK_FENCE, Material.CRIMSON_FENCE, Material.JUNGLE_FENCE, Material.NETHER_BRICK_FENCE, Material.SPRUCE_FENCE, Material.WARPED_FENCE, Material.CHAIN, Material.IRON_BARS).
            collect(Collectors.toCollection(HashSet::new));

    private static final Material material = Material.STRING;
    private static final int quantity = 1;
    private static final String lore =
            "Ride fences, iron bars, and chains";
    private static final int durability = 2;
    private static final boolean stackable = false;
    private static final boolean shiny = true;
    public static final int cost = 0;

    public ZiplineOld(){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost);
    }

    public void registerRecipes(){
        ShapedRecipe recipe =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("***","*&*","***");
        recipe.setIngredient('*', Material.IRON_NUGGET);
        recipe.setIngredient('&', Material.LEAD);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(PlayerInteractEvent event, Action action){
        if(action!=Action.RIGHT_CLICK_BLOCK)
            return;
        Block clicked = event.getClickedBlock();
        Material fence = clicked.getType();
        if(!fences.contains(fence))
            return;
        Player player = event.getPlayer();
        if(!((LivingEntity) player).isOnGround())
            return;

        Utility.addDurability(event.getItem(), -1, player);
        BlockFace forward = player.getFacing();
        Set<BlockFace> faces = new HashSet<>(this.faces);
        faces.remove(opposites.get(forward));
        faces.remove(forward);

        new Task(HoloItems.getInstance(), 0, 1){
            int increment = 0;
            Block block = clicked;
            BlockFace alongVertical = null;
            BlockFace alongHorizontal = null;

            public void run(){
                increment++;
                if(increment>128){
                    cancel();
                    return;
                }

                Location loc = player.getLocation();
                Location dest = block.getLocation().add(0.5, -2, 0.5);
                dest.setYaw(loc.getYaw());
                dest.setPitch(loc.getPitch());
                player.teleport(dest);
                block = block.getRelative(forward);
                if(block.getType()==fence)
                    return;

                if(alongHorizontal==null || alongVertical==null){
                    for(BlockFace face : faces){
                        Block relative = block.getRelative(face);
                        if(relative.getType()==fence) {
                            block = relative;
                            if(vertical.contains(face))
                                alongVertical = face;
                            alongHorizontal = face;
                            faces.remove(opposites.get(face));
                            return;
                        }
                        if(!vertical.contains(face)){
                            for(BlockFace corner : vertical) {
                                Block cornered = relative.getRelative(corner);
                                if(cornered.getType()==fence) {
                                    block = relative;
                                    alongVertical = corner;
                                    faces.remove(opposites.get(face));
                                    if(alongHorizontal==null){
                                        alongHorizontal = face;
                                        faces.remove(opposites.get(face));
                                    }
                                    return;
                                }
                            }
                        }
                    }
                }
                else{
                    Block relative = block.getRelative(alongVertical);
                    if(block.getRelative(alongVertical).getType()==fence) {
                        block = relative;
                        return;
                    }
                    relative = block.getRelative(alongHorizontal);
                    if(relative.getType()==fence) {
                        block = relative;
                        return;
                    }
                    relative = relative.getRelative(alongVertical);
                    if(relative.getType()==fence) {
                        block = relative;
                        return;
                    }
                }

                cancel();
            }
        };
    }
}
