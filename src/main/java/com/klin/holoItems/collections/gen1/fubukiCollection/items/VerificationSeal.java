package com.klin.holoItems.collections.gen1.fubukiCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.interfaces.Responsible;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class VerificationSeal extends Item implements Responsible {
    public static final String name = "verificationSeal";

    private static final Material material = Material.LIGHT_BLUE_DYE;
    private static final int quantity = 1;
    private static final String lore =
            "Authenticate mapart";
    private static final int durability = 0;
    public static final boolean stackable = false;
    private static final boolean shiny = true;
    public static final int cost = 0;

    public VerificationSeal(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost);
    }

    public void registerRecipes(){
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("aaa","aba","aaa");
        recipe.setIngredient('a', Material.DIAMOND);
        recipe.setIngredient('b', Material.WHITE_CANDLE);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public boolean ability(PlayerInteractEntityEvent event, ItemStack item){
        Entity entity = event.getRightClicked();
        if(!(entity instanceof ItemFrame))
            return false;
        ItemFrame frame = (ItemFrame) entity;
        ItemStack map = frame.getItem();
        if(map.getType()!=Material.FILLED_MAP)
            return false;
        event.setCancelled(true);
        ItemMeta meta = map.getItemMeta();
        meta.getPersistentDataContainer().set(Utility.key, PersistentDataType.STRING, "hI");
        map.setItemMeta(meta);
        frame.setItem(map);
        item.setAmount(0);
        return false;
    }
}
