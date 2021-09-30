package com.klin.holoItems.collections.en.guraCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.dungeons.inaDungeon.InaDungeon;
import com.klin.holoItems.dungeons.inaDungeon.Maintenance;
import com.klin.holoItems.interfaces.Interactable;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TideRider extends Item implements Interactable {
    public static final String name = "tideRider";
    private static final Set<Enchantment> accepted = Set.of(Enchantment.DURABILITY, Enchantment.MENDING);

    private static final Material material = Material.TRIDENT;
    private static final int quantity = 1;
    private static final String lore =
            "Surf the waves";
    private static final int durability = 250;
    private static final boolean stackable = false;
    private static final boolean shiny = false;
    public static final int cost = 321;

    public TideRider(){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost);
    }

    public void registerRecipes(){
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.LOYALTY, 3, false);
        List<String> lore = meta.getLore();
        lore.add(0, "");
        meta.setLore(lore);
        item.setItemMeta(meta);

        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("aba"," c "," a ");
        recipe.setIngredient('a', Material.WAXED_OXIDIZED_CUT_COPPER);
        recipe.setIngredient('b', Material.TRIDENT);
        recipe.setIngredient('c', Material.ENCHANTED_GOLDEN_APPLE);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(PlayerInteractEvent event, Action action){
        if(action!=Action.RIGHT_CLICK_BLOCK && action!=Action.RIGHT_CLICK_AIR)
            return;
        Player player = event.getPlayer();
        Map<Block, BlockData> data = new HashMap<>();
        Location loc = player.getLocation();
        World world = player.getWorld();
        Block block = world.getBlockAt(loc);
        data.put(block, block.getBlockData());
        BlockData water = Bukkit.createBlockData(Material.WATER);
        player.sendBlockChange(loc, water);

        ItemStack item = event.getItem();
        ItemMeta meta = item.getItemMeta();
        new Task(HoloItems.getInstance(), 0, 1){
            int increment = 0;
            Block current = block;
            Block previous;
            public void run(){
                Maintenance maintenance = InaDungeon.presets==null?null:(Maintenance) InaDungeon.presets.get("maintenance");
                if(increment>=1200 || !player.isValid() || !player.isHandRaised() ||
                        maintenance!=null && maintenance.knockBack.contains(player)){
                    player.sendBlockChange(current.getLocation(), data.remove(current));
                    meta.removeEnchant(Enchantment.RIPTIDE);
                    meta.addEnchant(Enchantment.LOYALTY, 3, false);
                    item.setItemMeta(meta);
                    Utility.addDurability(item, -1, player);
                    cancel();
                    return;
                }
                else if(increment==0){
                    meta.removeEnchant(Enchantment.LOYALTY);
                    meta.addEnchant(Enchantment.RIPTIDE, 3, false);
                    item.setItemMeta(meta);
                }
                increment++;
                previous = current;
                Location loc = player.getLocation();
                current = world.getBlockAt(loc);
                //test
                Vector dir = loc.getDirection().setY(0.0001).normalize();
                Block rise = world.getBlockAt(loc.clone().add(dir));
                Block fall = world.getBlockAt(loc.add(0, -1, 0));
                double multiplier = 1;
                if(player.hasPotionEffect(PotionEffectType.DOLPHINS_GRACE))
                    multiplier = 1.33;
                boolean slow = fall.getType()==Material.SOUL_SAND;
                if(slow)
                    multiplier -= 0.33;
                ItemStack boots = player.getInventory().getBoots();
                if(boots!=null && boots.getType()!=Material.AIR) {
                    multiplier += ((double) boots.getEnchantmentLevel(Enchantment.DEPTH_STRIDER))/9
                            +((slow || fall.getType()==Material.WATER
                            && fall.getRelative(BlockFace.DOWN).getType()==Material.SOUL_SAND)
                            && boots.containsEnchantment(Enchantment.SOUL_SPEED)?
                            ((double) boots.getEnchantmentLevel(Enchantment.SOUL_SPEED))*0.11+0.33:0);
                }
                player.setVelocity(player.getVelocity().add(dir).normalize().multiply(multiplier)
                        .setY(rise.isPassable()&&!rise.isLiquid()?(fall.isPassable()&&!fall.isLiquid()?-0.5:0):0.5));
                if(previous.equals(current))
                    return;
                player.sendBlockChange(previous.getLocation(), data.remove(previous));
                data.put(current, current.getBlockData());
                player.sendBlockChange(current.getLocation(), water);
                player.setFallDistance(0);
            }
        };
    }
}