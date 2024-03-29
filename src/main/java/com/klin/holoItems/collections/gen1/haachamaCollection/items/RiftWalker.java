package com.klin.holoItems.collections.gen1.haachamaCollection.items;

import com.klin.holoItems.Collections;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.abstractClasses.BatteryPack;
import com.klin.holoItems.collections.misc.ingredientsCollection.items.EmeraldLeaf;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

public class RiftWalker extends BatteryPack {
    public static final String name = "riftWalker";

    private static final Material material = Material.POPPY;
    private static final String lore =
            "Right click to consume a charge and\n"+
            "teleport yourself 8 blocks in the\n"+
            "direction you're facing up to 8 times\n"+
            "in a row. 2 epearls per charge";
    private static final int durability = 0;
    private static final boolean shiny = true;
    public static final int cost = 0;

    public static final Material content = Material.ENDER_PEARL;
    public static final double perFuel = 0.5;
    public static final int cap = 72;

    public RiftWalker(){
        super(name, material, lore, durability, shiny, cost, content, perFuel, cap);
    }

    public void registerRecipes(){
        ShapedRecipe recipe0 =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"0"), item);
        recipe0.shape(" * ","/%/");
        recipe0.setIngredient('*', new RecipeChoice.ExactChoice(new ItemStack(Material.COOKED_PORKCHOP, 64)));
        recipe0.setIngredient('%', Material.ELYTRA);
        recipe0.setIngredient('/', new RecipeChoice.ExactChoice(Collections.items.get(EmeraldLeaf.name).item));
        recipe0.setGroup(name);
        Bukkit.getServer().addRecipe(recipe0);
    }

    public void effect(PlayerInteractEvent event){
        event.setCancelled(true);
        Block clicked = event.getClickedBlock();
        Player player = event.getPlayer();
        Location loc = player.getLocation();
        if(clicked!=null && clicked.getLocation().distance(loc)<2)
            return;

        if(player.getGameMode()!= GameMode.CREATIVE) {
            ItemStack item = event.getItem();
            int charge = Utility.inspect(item);
            if (charge == -1)
                return;
            ItemMeta meta = item.getItemMeta();
            PersistentDataContainer container = meta.getPersistentDataContainer();
            int jumps = 1;
            Integer cooldown = container.get(Utility.cooldown, PersistentDataType.INTEGER);
            if (cooldown == null) {
                container.set(Utility.cooldown, PersistentDataType.INTEGER, 1);
                item.setItemMeta(meta);

                new Task(HoloItems.getInstance(), 5, 1) {
                    int increment = 0;

                    public void run() {
                        if (increment >= 1200 || player.isValid() && ((LivingEntity) player).isOnGround()) {
                            ItemMeta meta = item.getItemMeta();
                            meta.getPersistentDataContainer().remove(Utility.cooldown);
                            item.setItemMeta(meta);
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("Refreshed"));
                            cancel();
                        }
                        increment++;
                    }
                };
            } else {
                if (cooldown>=8) {
                    if(((LivingEntity) player).isOnGround()){
                        container.remove(Utility.cooldown);
                        item.setItemMeta(meta);
                    }
                    return;
                }
                else {
                    jumps += cooldown;
                    container.set(Utility.cooldown, PersistentDataType.INTEGER, jumps);
                    item.setItemMeta(meta);
                }
            }
            Utility.deplete(item, null, cap);
            if(charge%8==0)
                player.sendMessage("§7"+charge+" remaining");
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("Jumps: "+jumps+"/8"));
        }

        Block block = player.getTargetBlockExact(8);
        double dist;
        if(block==null)
            dist = 8;
        else
            dist = block.getLocation().add(0.5, 0.5, 0.5).distance(player.getLocation())-1.5;
        if(dist<=1)
            return;
        Vector dir = loc.getDirection();
        loc.add(dir.clone().multiply(dist));
        if(dir.getY()<0)
            block = loc.add(0, 1, 0).getBlock();
        dir.multiply(-1);
        int limit = 0;
        while (block!=null && (!block.isPassable() || !block.getRelative(BlockFace.UP).isPassable())) {
            block = loc.add(dir).getBlock();
            if(limit++>dist)
                return;
        }
        player.teleport(loc);
    }
}
