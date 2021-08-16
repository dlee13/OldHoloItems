package com.klin.holoItems.collections.gen3.rushiaCollection.items;

import com.klin.holoItems.abstractClasses.Pack;
import com.klin.holoItems.interfaces.Defensible;
import com.klin.holoItems.interfaces.Perishable;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.collections.gen3.rushiaCollection.RushiaCollection;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.*;
import org.bukkit.block.Banner;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.RayTraceResult;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NecromancersTome extends Pack implements Perishable, Defensible {
    public static final String name = "necromancersTome";
    public static final Set<Enchantment> accepted = new HashSet<Enchantment>(){{
        add(Enchantment.MENDING);
    }};

    private static final Material material = Material.SHIELD;
    private static final String lore =
            "Hold the shield to bring out\n"+
            "defeated foes to defend yourself";
    private static final int durability = 12;
    private static final boolean shiny = true;

    private static final int size = 9;
    public static final String title = "Souls captured";
    public static final boolean display = true;

    public static final int cost = -1;
    public static final char key = '0';

    private static Map<Player, LivingEntity> raised = new HashMap<>();

    public NecromancersTome(){
        super(name, accepted, material, lore, durability, shiny, size, title, display, cost,
                ""+RushiaCollection.key+key, key);

        Pattern[] patterns = new Pattern[]{
                new Pattern(DyeColor.BLACK, PatternType.BORDER),
                new Pattern(DyeColor.BLACK, PatternType.TRIANGLE_BOTTOM),
                new Pattern(DyeColor.BLACK, PatternType.SKULL),
                new Pattern(DyeColor.BLACK, PatternType.TRIANGLE_TOP),
                new Pattern(DyeColor.BLACK, PatternType.STRIPE_CENTER),
                new Pattern(DyeColor.BLACK, PatternType.GRADIENT)};
        BlockStateMeta blockStateMeta = (BlockStateMeta) item.getItemMeta();
        Banner banner = (Banner) blockStateMeta.getBlockState();
        banner.setBaseColor(DyeColor.CYAN);
        for(Pattern pattern : patterns)
            banner.addPattern(pattern);
        banner.update();
        blockStateMeta.setBlockState(banner);

        blockStateMeta.setDisplayName("§6Necromancer's Tome");
        item.setItemMeta(blockStateMeta);
    }

    public void registerRecipes(){
        ShapedRecipe recipe =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("*&*","***"," * ");
        recipe.setIngredient('*', Material.WARPED_PLANKS);
        recipe.setIngredient('%', Material.NETHERITE_INGOT);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public int ability(Inventory inv, ItemStack item, Player player){
        return -1;
    }

    protected void repack(ItemStack item, Inventory inv){
        String stored = item.getItemMeta().
                getPersistentDataContainer().get(Utility.pack, PersistentDataType.STRING);
        if (stored == null)
            return;

        String[] souls = stored.split(" ");
        int i = 0;
        for(String soul : souls){
            if(soul.isEmpty())
                continue;
            Material egg = Material.getMaterial(soul+"_SPAWN_EGG");
            if(egg==null)
                return;
            inv.setItem(i, new ItemStack(egg));
            i++;
        }
    }

    public void effect(PlayerInteractEvent event){
        if(event.useItemInHand()==Event.Result.DENY)
            return;
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if(item==null)
            return;
        ItemMeta meta = item.getItemMeta();
        if(Utility.onCooldown(item)){
            String name = meta.getDisplayName();
            if(name.contains("§"))
                name = name.substring(0, name.indexOf("§"))+name.substring(name.indexOf("§")+2);
            player.sendMessage("§7"+name+" is on cooldown");
            event.setUseItemInHand(Event.Result.DENY);
            return;
        }
        String stored = meta.getPersistentDataContainer().get(Utility.pack, PersistentDataType.STRING);
        if (stored == null) {
            player.sendMessage("§7No summons available");
            event.setUseItemInHand(Event.Result.DENY);
            return;
        }

        Location loc = player.getLocation();
        EntityType type;
        try {
            if(stored.contains(" ")) {
                type = EntityType.valueOf(stored.substring(0, stored.indexOf(" ")));
                stored = stored.substring(stored.indexOf(" ")+1);
            }
            else {
                type = EntityType.valueOf(stored);
                stored = "";
            }
        } catch(IllegalArgumentException e){
            meta.getPersistentDataContainer().remove(Utility.pack);
            item.setItemMeta(meta);
            return;
        }
        LivingEntity summon =
                (LivingEntity) loc.getWorld().spawnEntity(loc.add(loc.getDirection().multiply(1.5)), type);
        summon.getPersistentDataContainer().set(Utility.key, PersistentDataType.STRING, id);
        summon.addPotionEffect(new PotionEffect(
                PotionEffectType.GLOWING, 99999, 0, false, false));
        summon.addPotionEffect(new PotionEffect(
                PotionEffectType.INVISIBILITY, 99999, 0, false, false));
        summon.setGravity(false);
        if(summon instanceof Ageable)
            ((Ageable) summon).setAdult();
        raised.put(player, summon);

        final double interval = summon.getHealth()*0.1;
        String summons = stored;
        new Task(HoloItems.getInstance(), 1, 1){
            double i = -100;

            @Override
            public void run(){
                if(!summon.isValid() || !player.isValid() || !player.isHandRaised()){
                    if(summon.isValid())
                        summon.damage(summon.getHealth()*2);
                    meta.getPersistentDataContainer().set(Utility.pack, PersistentDataType.STRING, summons);
                    item.setItemMeta(meta);
                    Utility.addDurability(item, -1, player);
                    effect(summon.getKiller(), player, item);
                    raised.remove(player);
                    cancel();
                    return;
                }

                Location target = player.getLocation();
                if(i%10==0){
                    RayTraceResult result = player.getWorld().rayTrace(summon.getLocation(), target.getDirection(), 16,
                            FluidCollisionMode.NEVER, true, 0.5,
                            entity -> (entity instanceof LivingEntity && !(entity instanceof ArmorStand) &&
                                    !raised.containsValue(entity)));
                    if(result!=null && result.getHitEntity()!=null)
                        ((Mob) summon).setTarget((LivingEntity) result.getHitEntity());
                }

                target.add(target.getDirection().multiply(1.5));
                summon.setVelocity(target.subtract(summon.getLocation()).toVector());

                if(i>0 && i%20==0)
                    summon.damage(Math.min(interval, summon.getHealth() * 1 / (i/20==10 ? 1 : 10 - i / 20)));
                i += 1;
            }
        };
    }

    private void effect(Player killer, Player defender, ItemStack shield){
        if(defender==null || shield==null)
            return;
        if(killer!=null){
            if (!killer.equals(defender)) {
                if(killer.getInventory().getItemInMainHand().getType().toString().contains("AXE")) {
                    Utility.cooldown(shield, 160);
                }
                else
                    Utility.cooldown(shield, 80);
            }
        }
        else
            Utility.cooldown(shield, 40);
    }

    public void ability(EntityDamageByEntityEvent event){
        Player player = (Player) event.getEntity();
        //loop occurs if damage is credited to event.getDamager()
        player.damage(event.getDamage());

        if(raised.containsKey(player))
            raised.get(player).damage(raised.get(player).getHealth()*2);
    }

    public void ability(EntityTargetLivingEntityEvent event){
        if(raised.containsKey((event.getTarget())))
            event.setTarget(raised.get(event.getTarget()));
    }
}
