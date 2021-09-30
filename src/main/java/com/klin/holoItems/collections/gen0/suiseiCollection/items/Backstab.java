package com.klin.holoItems.collections.gen0.suiseiCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.gen0.suiseiCollection.SuiseiCollection;
import com.klin.holoItems.interfaces.Interactable;
import com.klin.holoItems.utility.Utility;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.Set;

public class Backstab extends Item implements Interactable{
    public static final String name = "backstab";
    public static final Set<Enchantment> accepted = Set.of(
            Enchantment.FIRE_ASPECT,
            Enchantment.DAMAGE_ALL,
            Enchantment.DAMAGE_ARTHROPODS,
            Enchantment.DAMAGE_UNDEAD,
            Enchantment.DURABILITY,
            Enchantment.LOOT_BONUS_MOBS,
            Enchantment.KNOCKBACK,
            Enchantment.MENDING,
            Enchantment.SWEEPING_EDGE
    );

    private static final Material material = Material.STONE_SWORD;
    private static final int quantity = 1;
    private static final String lore =
            "Right click to sneak behind enemy";
    private static final int durability = 32;
    public static final boolean stackable = false;
    private static final boolean shiny = false;
    public static final int cost = 2100;

    public Backstab(){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost);
    }

    public void registerRecipes(){
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("a","b","b");
        recipe.setIngredient('a', Material.END_ROD);
        recipe.setIngredient('b', Material.GILDED_BLACKSTONE);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(PlayerInteractEvent event, Action action){
        if(!(action==Action.RIGHT_CLICK_AIR || action==Action.RIGHT_CLICK_BLOCK) ||
                event.useInteractedBlock()==Event.Result.ALLOW)
            return;
        Player player = event.getPlayer();
        World world = player.getWorld();
        Location location = player.getEyeLocation();
        Utility.addDurability(event.getItem(), -1, player);

        Vector vector = location.getDirection();
        RayTraceResult result = world.rayTraceEntities(location, vector, 20, 0.5, entity -> (entity!=player && entity instanceof LivingEntity && !(entity instanceof ArmorStand)));
        if(result==null)
            return;
        Entity entity = result.getHitEntity();
        if(entity==null)
            return;
        if(location.distance(entity.getLocation())<4){
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("Too close!"));
            return;
        }
        Location loc = entity.getLocation();
        Vector dir = loc.getDirection().setY(0).multiply(-2);
        loc.add(dir);
        Block block = loc.getBlock();
        if(block.isPassable() && block.getRelative(BlockFace.UP).isPassable())
            player.teleport(loc.setDirection(dir.setY(vector.getY())));
        else
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("Target: back against wall"));
    }
}
