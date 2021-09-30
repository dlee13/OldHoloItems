package com.klin.holoItems.collections.gen2.aquaCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.gen2.aquaCollection.AquaCollection;
import com.klin.holoItems.interfaces.Holdable;
import com.klin.holoItems.interfaces.Retainable;
import com.klin.holoItems.interfaces.Launchable;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Trident;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Set;

public class OnionRing extends Item implements Launchable, Holdable, Retainable {
    public static final String name = "onionRing";
    public static final Set<Enchantment> accepted = null;

    private static final Material material = Material.MUSIC_DISC_PIGSTEP;
    private static final int quantity = 1;
    private static final String lore =
            "Guarantees your arrows landing while\n"+
            "this is held in your offhand";
    private static final int durability = 0;
    private static final boolean stackable = false;
    private static final boolean shiny = true;
    public static final int cost = 1440;

    public OnionRing(){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost);
    }

    public void registerRecipes(){
        ShapedRecipe recipe =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape(" * ","*%*"," * ");
        recipe.setIngredient('*', Material.BEETROOT);
        recipe.setIngredient('%', Material.SPECTRAL_ARROW);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(ProjectileLaunchEvent event, ItemStack item) {
        Projectile proj = event.getEntity();
        if(!(proj instanceof AbstractArrow) || proj instanceof Trident)
            return;

        AbstractArrow arrow = (AbstractArrow) proj;
        Player player = (Player) arrow.getShooter();
        Vector velocity = arrow.getVelocity().multiply(-1);
        arrow.setVelocity(velocity);
        Utility.arrowDamage(player.getInventory().getItemInMainHand(), arrow, player);

        Bukkit.getServer().getPluginManager().callEvent(new ProjectileHitEvent(arrow, player));
        Location loc = player.getLocation().add(0, 1, 0);
        arrow.teleport(arrow.getLocation().add(loc.getDirection()));
        arrow.setVelocity(velocity);
        new BukkitRunnable() {
            public void run() {
                if(arrow.isValid())
                    arrow.remove();
            }
        }.runTaskLater(HoloItems.getInstance(), 1);
    }

    public boolean ability(PlayerDeathEvent event, ItemStack item){
        PlayerInventory inv = event.getEntity().getInventory();
        ItemStack ring = inv.getItemInOffHand();
        if(!event.getKeepInventory() && item.equals(ring) /*ring.getItemMeta()!=null &&
                id.equals(ring.getItemMeta().getPersistentDataContainer().
                get(Utility.key, PersistentDataType.STRING))*/) {
            ItemStack crossbow = inv.getItemInMainHand();
            if (crossbow.getType() == Material.CROSSBOW) {
                new BukkitRunnable() {
                    public void run() {
                        crossbow.setAmount(0);
                    }
                }.runTask(HoloItems.getInstance());
            }
        }
        return false;
    }
}
