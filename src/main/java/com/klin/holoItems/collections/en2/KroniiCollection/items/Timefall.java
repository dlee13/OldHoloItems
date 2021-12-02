package com.klin.holoItems.collections.en2.KroniiCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.interfaces.Interactable;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

public class Timefall extends Item implements Interactable {
    public static final String name = "timefall";
    private final Map<Material, Material> age;

    private static final Material material = Material.POWDER_SNOW_BUCKET;
    private static final int quantity = 1;
    private static final String lore =
            "Age all unwaxed copper blocks\n" +
            "within a 5 block radius";
    private static final int durability = 0;
    public static final boolean stackable = true;
    private static final boolean shiny = false;
    public static final int cost = 0;

    public Timefall(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost);
        age = new HashMap<>();
        age.put(Material.COPPER_BLOCK, Material.EXPOSED_COPPER);
        age.put(Material.CUT_COPPER, Material.EXPOSED_CUT_COPPER);
        age.put(Material.CUT_COPPER_SLAB, Material.EXPOSED_CUT_COPPER_SLAB);
        age.put(Material.CUT_COPPER_STAIRS, Material.EXPOSED_CUT_COPPER_STAIRS);
        age.put(Material.EXPOSED_COPPER, Material.WEATHERED_COPPER);
        age.put(Material.EXPOSED_CUT_COPPER, Material.WEATHERED_CUT_COPPER);
        age.put(Material.EXPOSED_CUT_COPPER_SLAB, Material.WEATHERED_CUT_COPPER_SLAB);
        age.put(Material.EXPOSED_CUT_COPPER_STAIRS, Material.WEATHERED_CUT_COPPER_STAIRS);
        age.put(Material.WEATHERED_COPPER, Material.OXIDIZED_COPPER);
        age.put(Material.WEATHERED_CUT_COPPER, Material.OXIDIZED_CUT_COPPER);
        age.put(Material.WEATHERED_CUT_COPPER_SLAB, Material.OXIDIZED_CUT_COPPER_SLAB);
        age.put(Material.WEATHERED_CUT_COPPER_STAIRS, Material.OXIDIZED_CUT_COPPER_STAIRS);
    }

    public void registerRecipes(){
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6Timefall§meronii");
        item.setItemMeta(meta);

        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("aba","bcb","aba");
        recipe.setIngredient('a', Material.SNOW_BLOCK);
        recipe.setIngredient('b', Material.BONE_BLOCK);
        recipe.setIngredient('c', Material.BUCKET);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(PlayerInteractEvent event, Action action){
        if(action!=Action.RIGHT_CLICK_AIR && action!=Action.RIGHT_CLICK_BLOCK)
            return;
        ItemStack item = event.getItem();
        item.setType(Material.BUCKET);
        item.setItemMeta(new ItemStack(Material.BUCKET).getItemMeta());
        Player player = event.getPlayer();
        player.setFreezeTicks(60);
        Location center = player.getLocation();
        player.getWorld().spawnParticle(Particle.WHITE_ASH, center, 200, 5, 5, 5);
        for (int i=-5; i<=5; i++) {
            for (int j=-5; j<=5; j++) {
                for(int k=-5; k<=5; k++) {
                    Block block = center.clone().add(i, j, k).getBlock();
                    Material type = age.get(block.getType());
                    if(type!=null)
                        block.setType(type);
                }
            }
        }
    }
}

