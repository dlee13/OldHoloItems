package com.klin.holoItems.collections.en2.KroniiCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.interfaces.Interactable;
import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class TimeLapse extends Item implements Interactable {
    public static final String name = "timeLapse";

    private static final Material material = Material.CLOCK;
    private static final int quantity = 1;
    private static final String lore =
            "Reverts all metal in selected\n" +
            "container to their raw forms";
    private static final int durability = 0;
    public static final boolean stackable = false;
    private static final boolean shiny = true;
    public static final int cost = 0;

    public TimeLapse(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost);
    }

    public void registerRecipes(){
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6Time Lapse§mronii");
        item.setItemMeta(meta);

        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("aba","bcb","aba");
        recipe.setIngredient('a', Material.CLOCK);
        recipe.setIngredient('b', Material.COPPER_BLOCK);
        recipe.setIngredient('c', Material.BLAST_FURNACE);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(PlayerInteractEvent event, Action action){
        if(action!=Action.RIGHT_CLICK_BLOCK)
            return;
        BlockState state = event.getClickedBlock().getState();
        if(!(state instanceof Container))
            return;
        event.setCancelled(true);
        Container container = (Container) state;
        Map<ItemStack, Material> metals = Map.of(
                new ItemStack(Material.IRON_INGOT), Material.RAW_IRON,
                new ItemStack(Material.GOLD_INGOT), Material.RAW_GOLD,
                new ItemStack(Material.COPPER_INGOT), Material.RAW_COPPER,
                new ItemStack(Material.NETHERITE_SCRAP), Material.ANCIENT_DEBRIS
        );
        boolean consume = false;
        for(ItemStack item : container.getInventory()){
            if(item==null)
                continue;
            for(ItemStack metal : metals.keySet()) {
                if (item.isSimilar(metal)) {
                    item.setType(metals.get(metal));
                    consume = true;
                }
            }
        }
        if(consume && event.getPlayer().getGameMode()!=GameMode.CREATIVE)
            event.getItem().setAmount(0);
    }
}

