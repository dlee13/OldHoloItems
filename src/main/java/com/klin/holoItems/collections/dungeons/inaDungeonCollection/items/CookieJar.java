package com.klin.holoItems.collections.dungeons.inaDungeonCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.dungeons.inaDungeonCollection.InaDungeonCollection;
import com.klin.holoItems.dungeons.inaDungeon.Cookie;
import com.klin.holoItems.dungeons.inaDungeon.InaDungeon;
import com.klin.holoItems.interfaces.Placeable;
import com.klin.holoItems.interfaces.Punchable;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class CookieJar  extends Item implements Placeable, Punchable {
    public static final String name = "cookieJar";

    private static final Material material = Material.CHEST;
    private static final int quantity = 1;
    private static final String lore =
            "Open for a cookie buff";
    private static final int durability = 0;
    public static final boolean stackable = true;
    private static final boolean shiny = false;

    public static final int cost = -1;
    public static final char key = '8';

    public CookieJar(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost, ""+InaDungeonCollection.key+key, key);
    }

    public void registerRecipes(){}

    public void ability(BlockPlaceEvent event){
        event.setCancelled(false);
        Block block = event.getBlock();
        TileState state = (TileState) block.getState();
        state.getPersistentDataContainer().set(Utility.key, PersistentDataType.STRING, id);
        if(event.getPlayer().isSneaking())
            state.getPersistentDataContainer().set(Utility.pack, PersistentDataType.INTEGER, -1);
        state.update();
    }

    public void ability(PlayerInteractEvent event, Action action){
        Cookie cookie = (Cookie) InaDungeon.presets.get("cookie");
        if(cookie==null || action!=Action.RIGHT_CLICK_BLOCK)
            return;

        Block block = event.getClickedBlock();
        World world = block.getWorld();
        ArmorStand stand = world.spawn(block.getLocation().add(0.5, -0.6, 0.5), ArmorStand.class);
        stand.setInvisible(true);
        stand.setInvulnerable(true);
        stand.setGravity(false);
        stand.setBasePlate(false);
        stand.setCanPickupItems(false);
        stand.addEquipmentLock(EquipmentSlot.CHEST, ArmorStand.LockType.ADDING);
        stand.addEquipmentLock(EquipmentSlot.FEET, ArmorStand.LockType.ADDING);
        stand.addEquipmentLock(EquipmentSlot.LEGS, ArmorStand.LockType.ADDING);
        stand.addEquipmentLock(EquipmentSlot.HAND, ArmorStand.LockType.ADDING);
        stand.addEquipmentLock(EquipmentSlot.OFF_HAND, ArmorStand.LockType.ADDING);
        stand.getPersistentDataContainer().set(Utility.key, PersistentDataType.STRING, "hI");

        int roll;
        if(((TileState) block.getState()).getPersistentDataContainer().get(Utility.pack, PersistentDataType.INTEGER)==null) {
            roll = (int) (Math.random()*7);
            if(roll==6)
                roll = 7;
        }
        else roll = 6;
        stand.getEquipment().setHelmet(cookie.heads[roll==7?1:roll]);
        String desc;
        switch (roll){
            case 0:
                desc = "Takoyaki: Attack Boost!";
                break;
            case 1:
                desc = "Chocochip: Defense Boost!";
                break;
            case 2:
                desc = "Pancakes: Regenerating Shield!";
                break;
            case 3:
                desc = "Sushi: ";
                break;
            case 4:
                desc = "Burger: ";
                break;
            case 5:
                desc = "Bread: ";
                break;
            case 7:
                desc = "Raisin Cookie: Too bad!";
                break;
            case 6:
                desc = "KFP: Burn everything!";
                break;
            default:
                return;
        }
        block.setType(Material.AIR);
        int index = roll;
        new Task(HoloItems.getInstance(), 1, 1){
            int increment = 0;
            final Vector dir = stand.getLocation().getDirection();
            double path = 0.72;
            int spin = 10;
            public void run(){
                if(increment>=1200){
                    stand.remove();
                    cancel();
                    return;
                }
                for(Entity nearby : stand.getNearbyEntities(0.375, 1, 0.375)){
                    if(nearby instanceof Player){
                        Player player = (Player) nearby;
                        double[] buffs = cookie.buffs.get(player);
                        if(buffs!=null) {
                            if(index==7) player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 80, 2));
                            else buffs[index]++;
                            if(index==2)
                                player.setAbsorptionAmount(buffs[index]);
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(desc));
                        }
                        stand.remove();
                        cancel();
                        return;
                    }
                }
                Location loc = stand.getLocation().setDirection(dir.rotateAroundY(Math.PI/spin));
                if(path>=-0.56)
                    loc.add(0, path-=0.08, 0);
                else if(spin==10)
                    spin = 20;
                stand.teleport(loc);
                increment++;
            }
        };
    }
}
