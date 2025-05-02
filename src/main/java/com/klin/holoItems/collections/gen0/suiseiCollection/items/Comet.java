package com.klin.holoItems.collections.gen0.suiseiCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.en2.SanaCollection.items.SpaceBreadSplash;
import com.klin.holoItems.interfaces.Interactable;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.joml.Quaternionf;

import java.util.HashSet;
import java.util.Set;

public class Comet extends Item implements Interactable {
    public static final String name = "comet";
    public static final Set<Enchantment> accepted = Set.of(
        Enchantment.FIRE_ASPECT,
        Enchantment.SHARPNESS,
        Enchantment.BANE_OF_ARTHROPODS,
        Enchantment.SMITE,
        Enchantment.EFFICIENCY,
        Enchantment.UNBREAKING,
        Enchantment.FORTUNE,
        Enchantment.LOOTING,
        Enchantment.MENDING,
        Enchantment.PIERCING,
        Enchantment.SILK_TOUCH
    );

    private static final Material material = Material.GOLDEN_AXE;
    private static final int quantity = 1;
    private static final String lore =
            "Right click to throw an axe";
    private static final int durability = 32;
    public static final boolean stackable = false;
    private static final boolean shiny = false;
    public static final int cost = 2100;

    public Comet(){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost);
    }

    public void registerRecipes(){
        ShapedRecipe recipe0 =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"0"), item);
        recipe0.shape("**","*%"," %");
        recipe0.setIngredient('*', Material.NETHER_STAR);
        recipe0.setIngredient('%', Material.CHAIN);
        recipe0.setGroup(name);
        Bukkit.getServer().addRecipe(recipe0);

        ShapedRecipe recipe1 =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"1"), item);
        recipe1.shape("**","%*","% ");
        recipe1.setIngredient('*', Material.NETHER_STAR);
        recipe1.setIngredient('%', Material.CHAIN);
        recipe1.setGroup(name);
        Bukkit.getServer().addRecipe(recipe1);
    }

    public void ability(PlayerInteractEvent event, Action action){
        // Cancel if not right click
        if(!(action==Action.RIGHT_CLICK_AIR || action==Action.RIGHT_CLICK_BLOCK) ||
                event.useInteractedBlock()==Event.Result.ALLOW)
            return;

        // Cancel if owner is affected by Weakness
        Player player = event.getPlayer();
        if(player.hasPotionEffect(PotionEffectType.WEAKNESS)){
            player.sendMessage("§7The axe weighs heavily on your arms");
            return;
        }

        // Cancel if on cooldown
        ItemStack item = event.getItem();
        if(Utility.onCooldown(item))
            return;
        Utility.cooldown(item, 20);

        double damage = 4 + 3 * (Utility.checkPotionEffect(player, PotionEffectType.STRENGTH));

        Location location = player.getEyeLocation();
        World world = player.getWorld();

        final double maxDistance = 50;
        double distance = maxDistance;

        // Normalized vector
        Vector direction = location.getDirection();

        Set<LivingEntity> targets = new HashSet<>();

        // Raytrace to find entities in the way. If piercing is applied, do it multiple times
        for(int i=0; i<1+item.getEnchantmentLevel(Enchantment.PIERCING); i++) {
            RayTraceResult result = world.rayTrace(location, direction, maxDistance,
                    FluidCollisionMode.NEVER, true, 0.5,
                    entity -> (entity != player &&
                            entity instanceof LivingEntity && !(entity instanceof ArmorStand) &&
                            !targets.contains(entity))); // Skip previously raytraced entities
            if (result != null) {
                LivingEntity entity = (LivingEntity) result.getHitEntity();
                if (entity != null) {
                    distance = location.distance(result.getHitEntity().getLocation());
                    targets.add(entity);
                }
                else if (result.getHitBlock() != null) {
                    distance = location.distance(result.getHitBlock().getLocation());
                    break;
                }
            }
        }

        // Offset axe according to player's hand
        int rotation;
        if (event.getHand() == EquipmentSlot.HAND) {
            rotation = -1;
        } else {
            rotation = 1;
        }

        ItemDisplay axeDisplay = world.spawn(location, ItemDisplay.class, entity -> {
            // This block runs before entity is ticked, meaning it won't show a mark in minimap right as the axe is spawned
            entity.setInvisible(true); // Remove mark in minimaps
            entity.setPersistent(false); // Remove if chunk unloads
            entity.setItemStack(item);
            entity.setViewRange((float)maxDistance);

            Transformation currentTransformation = entity.getTransformation();
            currentTransformation.getLeftRotation()
                .rotateLocalY((float) Math.toRadians(-90)) // Rotate vertically (to face forward pointing frontwards)
                .rotateLocalZ((float) Math.toRadians(15 * rotation)); // Slant inwards
            currentTransformation.getTranslation().add(0.4f * rotation, -0.25f, 0.5f); // Move closer to hand
            entity.setTransformation(currentTransformation);
        });

        // Check if SpaceBreadSplash is applied
        String enchant = item.getItemMeta().getPersistentDataContainer().get(Utility.enchant, PersistentDataType.STRING);
        boolean bread = enchant!=null && enchant.contains(SpaceBreadSplash.name);
        double height = player.getLocation().getY();

        // Consume durability
        if (player.getGameMode()!=GameMode.CREATIVE)
            Utility.addDurability(item, -1, player);

        // Set vector speed as 3 blocks/tick
        final double speed = 3;
        final double maxIteration = distance / (double) speed;

        new Task(HoloItems.getInstance(), 1, 1){
            double increment = 0;
            boolean crit = player.getLocation().getY()<height;
            Quaternionf rotationPerTick = new Quaternionf().rotateZ((float) Math.toRadians(-60));

            public void run(){
                try {
                    if(increment >= maxIteration) {
                        if(!targets.isEmpty()) {
                            // Restore half durability
                            if (player.getGameMode()!=GameMode.CREATIVE)
                                Utility.addDurability(item, 0.5, player);

                            // Prepare for Utility.damage()
                            ItemStack itemForDamage = item;
                            if(bread) {
                                itemForDamage = item.clone();
                                itemForDamage.addUnsafeEnchantment(Enchantment.SMITE, 5);
                                itemForDamage.addUnsafeEnchantment(Enchantment.BANE_OF_ARTHROPODS, 5);
                                itemForDamage.addUnsafeEnchantment(Enchantment.SHARPNESS, 5);
                            }
                            for (LivingEntity target : targets) {
                                if (target.isValid() && (!(target instanceof Player) || !((Player) target).isBlocking()))
                                    Utility.damage(itemForDamage, damage, crit, player, target, false, true, false);
                            }
                        }
                        axeDisplay.remove();
                        cancel();
                        return;
                    }


                    if (increment != 0) {
                        Transformation currentTransformation = axeDisplay.getTransformation();
                        currentTransformation.getLeftRotation().mul(rotationPerTick); // Spin
                        currentTransformation.getTranslation().add(0, 0, (float)speed); // Move forward
                        axeDisplay.setTransformation(currentTransformation);
                        axeDisplay.setInterpolationDelay(0);
                        axeDisplay.setInterpolationDuration(1);
                    }
        
                    ++increment;
                } catch (Exception e) {
                    // Avoid being in loop logging errors in case of exception
                    HoloItems.getInstance().getLogger().warning("Error in Comet ability: " + e.getMessage());
                    axeDisplay.remove();
                    cancel();
                }
            }
        };
    }
}
