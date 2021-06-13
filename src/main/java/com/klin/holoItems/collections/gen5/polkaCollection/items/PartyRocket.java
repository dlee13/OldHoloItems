package com.klin.holoItems.collections.gen5.polkaCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.gen5.polkaCollection.PolkaCollection;
import com.klin.holoItems.interfaces.Craftable;
import com.klin.holoItems.interfaces.Interactable;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.Set;

public class PartyRocket extends Item implements Interactable, Craftable {
    public static final String name = "partyRocket";
    public static final Set<Enchantment> accepted = null;

    private static final Material material = Material.FIREWORK_ROCKET;
    private static final int quantity = 1;
    private static final String lore =
            "§6Ability" +"/n"+
                "Send all active players a"+"/n"+
                "colorful surprise";
    private static final int durability = 0;
    private static final boolean stackable = true;
    private static final boolean shiny = true;

    public static final int cost = 0;
    public static final char key = '0';

    public PartyRocket(){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost,
                ""+PolkaCollection.key+key, key);
    }

    public void registerRecipes(){
        ShapedRecipe recipe =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("&%&","%*%","&%&");
        recipe.setIngredient('*', Material.FIREWORK_ROCKET);
        recipe.setIngredient('%', Material.TNT);
        recipe.setIngredient('&', Material.TARGET);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(PlayerInteractEvent event, Action action){
        Player player = event.getPlayer();
        World world = player.getWorld();
        FireworkMeta meta = (FireworkMeta) event.getItem().getItemMeta();
        for(Player target : Bukkit.getOnlinePlayers()){
            Firework firework = world.spawn(target.getLocation(), Firework.class);
            firework.setShooter(player);
            firework.setFireworkMeta(meta);
        }
    }

    public void ability(CraftItemEvent event){
        ItemStack rocket = event.getInventory().getResult();
        FireworkMeta rocketMeta = (FireworkMeta) rocket.getItemMeta();
        FireworkMeta meta = (FireworkMeta) event.getInventory().getItem(5).getItemMeta();
        rocketMeta.addEffects(meta.getEffects());
        rocketMeta.setPower(meta.getPower());
        rocket.setItemMeta(rocketMeta);
    }
}