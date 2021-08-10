package com.klin.holoItems.collections.gen3.pekoraCollection.items;

import com.klin.holoItems.abstractClasses.BatteryPack;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.collections.gen3.pekoraCollection.PekoraCollection;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CarrotCannon extends BatteryPack {
    public static final String name = "carrotCannon";
    public static final Set<Enchantment> accepted = new HashSet<>(){{
        add(Enchantment.DURABILITY);
        add(Enchantment.MENDING);
    }};
    private static final ItemStack carrot = new ItemStack(Material.CARROT);

    private static final Material material = Material.BLAZE_ROD;
    private static final String lore =
            "§6Ability" +"/n"+
                "Right click to consume a charge and" +"/n"+
                "launch an explosive carrot";
    private static final int durability = 100;
    private static final boolean shiny = false;

    public static final Material content = Material.CARROT;
    public static final double perFuel = 0.5;
    public static final int cap = 288;

    public static final int cost = 1000;
    public static final char key = '1';

    public CarrotCannon(){
        super(name, accepted, material, lore, durability, shiny, cost, content, perFuel, cap,
                ""+PekoraCollection.key+key, key);
    }

    public void registerRecipes(){
        ShapedRecipe recipe0 =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"0"), item);
        recipe0.shape("%/*","/& ","*#/");
        recipe0.setIngredient('%', Material.NETHERITE_INGOT);
        recipe0.setIngredient('/', Material.BAMBOO);
        recipe0.setIngredient('&', Material.TRIPWIRE_HOOK);
        recipe0.setIngredient('*', Material.TNT_MINECART);
        recipe0.setIngredient('#', Material.RABBIT_FOOT);
        recipe0.setGroup(name);
        Bukkit.getServer().addRecipe(recipe0);

        ShapedRecipe recipe1 =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"1"), item);
        recipe1.shape("%/*","/&#","* /");
        recipe1.setIngredient('%', Material.NETHERITE_INGOT);
        recipe1.setIngredient('/', Material.BAMBOO);
        recipe1.setIngredient('&', Material.TRIPWIRE_HOOK);
        recipe1.setIngredient('*', Material.TNT_MINECART);
        recipe1.setIngredient('#', Material.RABBIT_FOOT);
        recipe1.setGroup(name);
        Bukkit.getServer().addRecipe(recipe1);

        ShapedRecipe recipe2 =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"2"), item);
        recipe2.shape("*/%"," &/","/#*");
        recipe2.setIngredient('%', Material.NETHERITE_INGOT);
        recipe2.setIngredient('/', Material.BAMBOO);
        recipe2.setIngredient('&', Material.TRIPWIRE_HOOK);
        recipe2.setIngredient('*', Material.TNT_MINECART);
        recipe2.setIngredient('#', Material.RABBIT_FOOT);
        recipe2.setGroup(name);
        Bukkit.getServer().addRecipe(recipe2);

        ShapedRecipe recipe3 =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"3"), item);
        recipe3.shape("*/%","#&/","/ *");
        recipe3.setIngredient('%', Material.NETHERITE_INGOT);
        recipe3.setIngredient('/', Material.BAMBOO);
        recipe3.setIngredient('&', Material.TRIPWIRE_HOOK);
        recipe3.setIngredient('*', Material.TNT_MINECART);
        recipe3.setIngredient('#', Material.RABBIT_FOOT);
        recipe3.setGroup(name);
        Bukkit.getServer().addRecipe(recipe3);
    }

    public void effect(PlayerInteractEvent event){
        event.setCancelled(true);
        ItemStack item = event.getItem();
        int charge = Utility.deplete(item);
        if(charge==-1)
            return;

        Player player = event.getPlayer();
        Location loc = player.getEyeLocation();
        World world = loc.getWorld();
        ThrowableProjectile proj = (ThrowableProjectile) world.
                spawnEntity(loc, EntityType.SNOWBALL);
        proj.setItem(carrot);
        Vector dir = loc.getDirection().multiply(4);
        proj.setVelocity(dir);

        new Task(HoloItems.getInstance(), 0, 1){
            int increment = 0;
            Location explosion = proj.getLocation().add(proj.getVelocity().multiply(0.2));

            public void run(){
                if(!proj.isValid() || increment>=600){
                    world.spawnParticle(Particle.EXPLOSION_LARGE, explosion, 1);
                    Collection<Entity> entities = world.getNearbyEntities(explosion, 1.5, 1.5, 1.5,
                            entity -> entity instanceof LivingEntity);
                    for(Entity entity : entities) {
                        if(Utility.damage(item, 1, false, player, (LivingEntity) entity, false, false, false)) {
                            ((LivingEntity) entity).setNoDamageTicks(0);
                            entity.setVelocity(entity.getLocation().subtract(explosion).toVector().normalize());
                        }
                    }
                    cancel();
                    return;
                }

                explosion = proj.getLocation().add(proj.getVelocity().multiply(0.2));
                increment++;
            }
        };

        if(charge==96 || charge==32 || charge==0)
            player.sendMessage("§7" + charge + " remaining");
    }
}