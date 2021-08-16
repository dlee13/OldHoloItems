package com.klin.holoItems.collections.gen5.botanCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.gen5.botanCollection.BotanCollection;
import com.klin.holoItems.interfaces.Interactable;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.List;

public class ScopedRifle extends Item implements Interactable {
    public static final String name = "scopedRifle";

    private static final Material material = Material.SPYGLASS;
    private static final int quantity = 1;
    private static final String lore =
            "Long ranged damage";
    private static final int durability = 9;
    private static final boolean stackable = false;
    private static final boolean shiny = false;

    public static final int cost = 0;
    public static final char key = '3';
    public static final String id = ""+BotanCollection.key+key;

    public ScopedRifle(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost, id, key);
    }

    public void registerRecipes(){
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("aaa","aba","aaa");
        recipe.setIngredient('a', Material.ARROW);
        recipe.setIngredient('b', Material.SPYGLASS);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);

        ItemStack outOfAmmo = item.clone();
        ItemMeta meta = outOfAmmo.getItemMeta();
        List<String> lore = meta.getLore();
        lore.remove(lore.size()-1);
        lore.add("§7Out of ammo");
        meta.setLore(lore);
        outOfAmmo.setItemMeta(meta);

        ShapelessRecipe revert = new ShapelessRecipe(new NamespacedKey(HoloItems.getInstance(), name+"OutOfAmmo"), new ItemStack(Material.SPYGLASS));
        revert.addIngredient(new RecipeChoice.ExactChoice(outOfAmmo));
        revert.setGroup(name);
        Bukkit.getServer().addRecipe(revert);
    }

    public void ability(PlayerInteractEvent event, Action action){
        if(action!=Action.RIGHT_CLICK_BLOCK && action!=Action.RIGHT_CLICK_AIR)
            return;
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        int[] durability = Utility.getDurability(lore);
        if(durability==null || durability[0]==1){
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("Out of ammo"));
            if(durability==null)
                return;
            lore.remove(lore.size()-1);
            lore.add("§7Out of ammo");
            meta.setLore(lore);
            item.setItemMeta(meta);
            return;
        }
        new Task(HoloItems.getInstance(), 1, 1){
            Vector dir = player.getEyeLocation().getDirection();
            int steadiness = 0;
            int increment = 0;
            public void run(){
                if(increment>= 1200 || !player.isValid() || !player.isHandRaised()){
                    cancel();
                    if(!player.isValid())
                        return;
                    RayTraceResult result = player.getWorld().rayTrace(player.getEyeLocation(), dir, 150, FluidCollisionMode.NEVER, true, 0.1,
                            entity -> (entity != player && entity instanceof LivingEntity && !(entity instanceof ArmorStand)));
                    if (result != null) {
                        LivingEntity entity = (LivingEntity) result.getHitEntity();
                        if(entity!=null) {
                            if(Utility.damage(item, result.getHitPosition().distance(entity.getEyeLocation().toVector())<0.6?10:5 + Math.pow((double) steadiness / 10, 2) * 3 / 5, true, player, entity, false, true, true)) {
                                entity.setArrowsInBody(entity.getArrowsInBody() + 1);
                                if (player.getInventory().getItemInOffHand().equals(item))
                                    Utility.addDurability(item, -1, player);
                            }
                        }
                        else
                            Utility.addDurability(item, -1, player);
                    }
                    else
                        Utility.addDurability(item, -1, player);
                    return;
                }
                Vector aim = player.getEyeLocation().getDirection();
                if(dir.clone().subtract(aim).length()<0.01)
                    steadiness = Math.min(50, steadiness+1);
                else
                    steadiness = 0;
                dir = aim;
                increment++;
            }
        };
    }
}