package com.klin.holoItems.collections.gen4.kanataCollection.items;

import com.klin.holoItems.abstractClasses.SlidingPack;
import com.klin.holoItems.interfaces.Launchable;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.collections.gen4.kanataCollection.KanataCollection;
import com.klin.holoItems.utility.Utility;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import java.util.Set;

public class Shuribow extends SlidingPack implements Launchable {
    public static final String name = "shuribow";
    public static final Set<Enchantment> accepted = null;

    private static final Material material = Material.CROSSBOW;
    private static final String lore =
            "§6Ability" +"/n"+
                "Multi-shot equal to quantity of" +"/n"+
                "prismarine shards consumed";
    private static final int durability = 162;
    private static final boolean shiny = false;

    private static final ItemStack content = new ItemStack(Material.PRISMARINE_SHARD);

    public static final int cost = -1;
    public static final char key = '0';

    public Shuribow(){
        super(name, accepted, material, lore, durability, shiny, cost, content,
                ""+KanataCollection.key+key, key);
    }

    public void registerRecipes(){
        ShapedRecipe bowRight = new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"Right"), item);
        bowRight.shape("%& ","% *","%& ");
        bowRight.setIngredient('*', Material.CONDUIT);
        bowRight.setIngredient('&', Material.SPONGE);
        bowRight.setIngredient('%', Material.CHAIN);
        bowRight.setGroup(name);
        Bukkit.getServer().addRecipe(bowRight);

        ShapedRecipe bowLeft = new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"Left"), item);
        bowLeft.shape(" &%","* %"," &%");
        bowLeft.setIngredient('*', Material.CONDUIT);
        bowLeft.setIngredient('&', Material.SPONGE);
        bowLeft.setIngredient('%', Material.CHAIN);
        bowLeft.setGroup(name);
        Bukkit.getServer().addRecipe(bowLeft);
    }

    public void ability(ProjectileLaunchEvent event, ItemStack item){
        Integer spread = item.getItemMeta().getPersistentDataContainer().
                get(Utility.pack, PersistentDataType.INTEGER);
        if(spread==null || spread==1)
            return;
        spread--;
        //consume prismarine shards
        Projectile proj = event.getEntity();
        LivingEntity shooter = (LivingEntity) proj.getShooter();
        if(shooter instanceof Player) {
            Player player = (Player) shooter;
            if (player.getGameMode() != GameMode.CREATIVE) {
                Inventory inv = player.getInventory();
                if (inv.containsAtLeast(content, spread)) {
                    ItemStack toRemove = content.clone();
                    toRemove.setAmount(spread);
                    inv.removeItem(toRemove);
                } else
                    return;
            }
        }

        Location loc = shooter.getEyeLocation();
        World world = loc.getWorld();

        Vector dir = proj.getVelocity().clone();
        double up = dir.getY();
        dir.setY(0);
        double magnitude = dir.length();
        double east = dir.getX();
        double south = dir.getZ();
        //angle with respect to player as origin, south as positive x axis
        if(south==0)
            south += 0.0001;
        double baseAngle = Math.atan(east/south);
        if(south < 0)
            baseAngle -= Math.PI;
        double diff = Math.PI/3/(2*spread);

        int[] plusMinus = new int[]{-1, 1};
//        Set<Projectile> shots = new HashSet<>();
        for(int i=1; i<spread+1; i++){
            for(int j : plusMinus) {
                double angle = baseAngle + i*j*diff;
                east = Math.sin(angle);
                south = Math.cos(angle);
                Vector aim = new Vector(east, 0, south).normalize().multiply(magnitude);
                aim.setY(up);
                //Crossbows can only shoot arrows and fireworks
                Arrow copy = (Arrow) world.spawnEntity(loc, EntityType.ARROW);
                copy.setVelocity(aim);
                //Utility.copyArrow(AbstractArrow arrow, AbstractArrow copy);
//                shots.add(copy);
            }
        }

//        RayTraceResult result = world.rayTrace(loc, loc.getDirection(), 100,
//                FluidCollisionMode.NEVER, true, 0.5,
//                entity -> (entity != player && entity instanceof LivingEntity));
//        if(result==null)
//            return;
//        Location target;
//        Entity entity = result.getHitEntity();
//        if (entity != null)
//            target = result.getHitEntity().getLocation();
//        else if (result.getHitBlock() != null)
//            target = result.getHitBlock().getLocation();
//        else return;
//        double dist = loc.distance(target);
//
//        new Task(HoloItems.getInstance(), 1, 1){
//            int increment = 0;
//            public void run(){
//                if(!proj.isValid() || proj.getVelocity().length()==0 || increment>=1200){
//                    cancel();
//                    return;
//                }
//
//                if(proj.getLocation().distance(target)*2<dist){
//                    for(Projectile shot : shots)
//                        shot.setVelocity(target.subtract(shot.getLocation()).toVector());
//                }
//                increment++;
//            }
//        };
    }

    protected void effect(PlayerInteractEvent event){}
}
