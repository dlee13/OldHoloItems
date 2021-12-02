package com.klin.holoItems.collections.en1.guraCollection.items;

import com.klin.holoItems.Collections;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.abstractClasses.BatteryPack;
import com.klin.holoItems.collections.misc.ingredientsCollection.items.EmeraldLeaf;
import com.klin.holoItems.interfaces.Extractable;
import com.klin.holoItems.interfaces.Holdable;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class Bloop extends BatteryPack implements Extractable, Holdable {
    public static final String name = "quartzBlossom";
    private static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYzNTk2MDgwMTEzNiwKICAicHJvZmlsZUlkIiA6ICJkMWY2OTc0YzE2ZmI0ZjdhYjI1NjU4NzExNjM3M2U2NSIsCiAgInByb2ZpbGVOYW1lIiA6ICJGaW9saWVzdGEiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWFhNWU5ODY0ZTNjYjNkZmEwZTVhYjM5ZDZjYzM3ZTllMjc2NTJkZWQ1ZmM5MDc4MzAyYjcxNmZjMjFhZmUzNyIKICAgIH0KICB9Cn0=";

    private static final Material material = Material.LILY_OF_THE_VALLEY;
    private static final String lore =
            "When held in the offhand after\n"+
            "fully charging with nether bricks,\n"+
            "nether quartz ore will drop blocks";
    private static final int durability = 0;
    private static final boolean shiny = false;
    public static final int cost = 0;

    public static final Material content = Material.NETHER_BRICKS;
    public static final double perFuel = 1;
    public static final int cap = 576;

    public Bloop(){
        super(name, material, lore, durability, shiny, cost, content, perFuel, cap);
    }

    public void registerRecipes(){
        ShapedRecipe recipe =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("a","b","c");
        recipe.setIngredient('a', Material.QUARTZ_BLOCK);
        recipe.setIngredient('b', Material.END_ROD);
        recipe.setIngredient('c', new RecipeChoice.ExactChoice(Collections.items.get(EmeraldLeaf.name).item));
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    //exits bucket when swimming, interact event only fires when bloop is out of the bucket (item==bucket)
    //tailing builds counter, deal bonus dm, right click to tail automatically. missing attack creates a roar instead
    public void effect(PlayerInteractEvent event){
        ItemStack item = event.getItem();
        if(item.getType()==Material.OXEYE_DAISY)
            return;
        ItemMeta meta = item.getItemMeta();
        Integer currCharge = meta.getPersistentDataContainer().get(Utility.pack, PersistentDataType.INTEGER);
        if(currCharge==null || currCharge!=576)
            return;

        meta.getPersistentDataContainer().remove(Utility.pack);
        item.setItemMeta(meta);
        item.setType(Material.OXEYE_DAISY);
    }

    public void ability(BlockBreakEvent event){
        Player player = event.getPlayer();
        if(player.getInventory().getItemInOffHand().getType()!=Material.OXEYE_DAISY)
            return;
        Block block = event.getBlock();
        if(block.getType()!=Material.NETHER_QUARTZ_ORE || !event.isDropItems())
            return;

        event.setCancelled(true);
        block.setType(Material.AIR);
        ItemStack pickaxe = player.getInventory().getItemInMainHand();
        int drops = pickaxe.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS)+1;
        player.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.QUARTZ_BLOCK, 1+(int)(Math.random()*drops)));
    }
}
