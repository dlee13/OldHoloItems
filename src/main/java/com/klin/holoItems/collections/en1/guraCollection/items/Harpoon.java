package com.klin.holoItems.collections.en1.guraCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.interfaces.Fishable;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.*;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class Harpoon extends Item implements Fishable {
    public static final String name = "harpoon";

    private static final Material material = Material.ARROW;
    private static final int quantity = 1;
    private static final String lore =
            "Only the freshest catches";
    private static final int durability = 0;
    public static final boolean stackable = false;
    private static final boolean shiny = true;
    public static final int cost = -1;

    public Harpoon(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost);
    }

    public void registerRecipes(){
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("a","b","c");
        recipe.setIngredient('a', Material.END_ROD);
        recipe.setIngredient('b', Material.PUFFERFISH_BUCKET);
        recipe.setIngredient('c', Material.POINTED_DRIPSTONE);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(PlayerFishEvent event, ItemStack item) {
        if(event.getState()!=PlayerFishEvent.State.CAUGHT_FISH)
            return;
        Entity entity = event.getCaught();
        if(!(entity instanceof org.bukkit.entity.Item))
            return;
        Class<? extends Fish> clas;
        switch(((org.bukkit.entity.Item) entity).getItemStack().getType()){
            case COD:
                clas = Cod.class;
                break;
            case PUFFERFISH:
                clas = PufferFish.class;
                break;
            case SALMON:
                clas = Salmon.class;
                break;
            case TROPICAL_FISH:
                clas = TropicalFish.class;
                break;
            default:
                return;
        }
        event.setCancelled(true);
        FishHook hook = event.getHook();
        Fish fish = hook.getWorld().spawn(hook.getLocation(), clas);
        hook.setHookedEntity(fish);
        hook.pullHookedEntity();
        hook.remove();
    }
}
