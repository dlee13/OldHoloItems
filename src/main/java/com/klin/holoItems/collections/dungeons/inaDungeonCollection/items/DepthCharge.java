package com.klin.holoItems.collections.dungeons.inaDungeonCollection.items;

import com.klin.holoItems.Collections;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.dungeons.inaDungeonCollection.InaDungeonCollection;
import com.klin.holoItems.dungeons.inaDungeon.InaDungeon;
import com.klin.holoItems.dungeons.inaDungeon.Payload;
import com.klin.holoItems.interfaces.Placeable;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;

public class DepthCharge extends Item implements Placeable {
    public static final String name = "depthCharge";

    private static final Material material = Material.TNT;
    private static final int quantity = 1;
    private static final String lore =
            "Locked and loaded";
    private static final int durability = 0;
    public static final boolean stackable = true;
    private static final boolean shiny = true;
    public static final int cost = 0;

    public DepthCharge(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost);
    }

    public void registerRecipes() {
        new BukkitRunnable(){
            public void run(){
                ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
                recipe.shape("aba","bab", "aba");
                recipe.setIngredient('a', new RecipeChoice.ExactChoice(Collections.items.get(BlackPowder.name).item));
                recipe.setIngredient('b', new RecipeChoice.ExactChoice(Collections.items.get(CoarseSand.name).item));
                recipe.setGroup(name);
                Bukkit.getServer().addRecipe(recipe);
            }
        }.runTask(HoloItems.getInstance());
    }

    public void ability(BlockPlaceEvent event) {
        if(InaDungeon.presets==null)
            return;
        Payload payload = (Payload) InaDungeon.presets.get("payload");
        if(payload==null)
            return;
        Block against = event.getBlockAgainst();
        Block placed = event.getBlockPlaced();
        if(payload.payload.containsKey(against.getLocation()) && against.getRelative(BlockFace.UP).equals(placed) && Set.of(Material.BARREL, Material.CHEST, Material.DARK_OAK_SLAB, Material.SPRUCE_FENCE, Material.SPRUCE_SLAB, Material.SPRUCE_TRAPDOOR, Material.TNT).contains(against.getType())) {
            BlockData data = against.getBlockData();
            if(data instanceof TrapDoor) {
                TrapDoor trapDoor = (TrapDoor) data;
                if(trapDoor.isOpen()){
                    if(trapDoor.getFacing()==BlockFace.NORTH)
                        placed = placed.getRelative(BlockFace.DOWN);
                    else
                        return;
                }
                else
                    placed = placed.getRelative(BlockFace.DOWN);
            }
            if(event.getPlayer().getGameMode()!= GameMode.CREATIVE) {
                ItemStack item = event.getItemInHand();
                item.setAmount(item.getAmount() - 1);
            }
            Block block = placed;
            new BukkitRunnable(){
                public void run(){
                    payload.payload.putIfAbsent(block.getLocation(), block.getBlockData());
                    block.setType(Material.TNT);
                }
            }.runTask(HoloItems.getInstance());
        }
    }
}