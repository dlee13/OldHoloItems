package com.klin.holoItems.collections.gen5.botanCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.gen5.botanCollection.BotanCollection;
import com.klin.holoItems.interfaces.Interactable;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

public class ScopedRifle extends Item implements Interactable {
    public static final String name = "scopedRifle";

    private static final Material material = Material.SPYGLASS;
    private static final int quantity = 1;
    private static final String lore =
            "§6Ability" +"/n"+
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
        ShapedRecipe recipe =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("aaa","aba","aaa");
        recipe.setIngredient('a', Material.ARROW);
        recipe.setIngredient('b', Material.SPYGLASS);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(PlayerInteractEvent event, Action action){
        if(action!=Action.RIGHT_CLICK_BLOCK && action!=Action.RIGHT_CLICK_AIR || Utility.getDurability(event.getItem().getItemMeta().getLore())[0]==1)
            return;
        Player player = event.getPlayer();
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
                        if(entity!=null)
                            entity.damage(5+Math.pow((double) steadiness/10, 2)*3/5, player);
                    }
                    Utility.addDurability(event.getItem(), -1, player);
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