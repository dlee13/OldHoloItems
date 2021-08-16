package com.klin.holoItems.collections.misc.franCollection.items;

import com.klin.holoItems.abstractClasses.Pack;
import com.klin.holoItems.collections.misc.franCollection.FranCollection;
import com.klin.holoItems.interfaces.Dispensable;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Dispenser;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Set;

public class TotemPole extends Pack implements Dispensable {
    public static final String name = "totemPole";
    public static final Set<Enchantment> accepted = null;

    private static final Material material = Material.ARMOR_STAND;
    private static final String lore =
            "Stack eggs";
    private static final int durability = 0;
    private static final boolean shiny = true;

    private static final int size = 9;
    public static final String title = "Eggs";
    public static final boolean display = false;

    public static final int cost = -1;
    public static final char key = '0';

    public TotemPole(){
        super(name, accepted, material, lore, durability, shiny, size, title, display, cost,
                ""+FranCollection.key+key, key);
    }

    public void registerRecipes(){}

    public int ability(Inventory inv, ItemStack item, Player player){
        String mobs = "";
        
        int size = 0;
        for(ItemStack content : inv.getContents()) {
            if(content==null || content.getType()==Material.AIR)
                continue;
            String type = content.getType().toString();
            if(!type.endsWith("_SPAWN_EGG"))
                continue;
            mobs += " "+type.substring(0, type.length()-10);
            ItemMeta meta = content.getItemMeta();
            if(meta!=null && SteinsEgg.id.equals(meta.getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING)))
                mobs += ":" + meta.getPersistentDataContainer().get(Utility.pack, PersistentDataType.STRING);
            size++;
        }
        if(mobs.isEmpty())
            return -1;

        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(Utility.pack, PersistentDataType.STRING, mobs.substring(1));
        item.setItemMeta(meta);
        player.sendMessage("Filled "+meta.getDisplayName()+"§f to: "+size);
        return size;
    }

    protected void repack(ItemStack item, Inventory inv){
        String stored = item.getItemMeta().
                getPersistentDataContainer().get(Utility.pack, PersistentDataType.STRING);
        if (stored == null)
            return;

        String[] mobs = stored.split(" ");
        int i = 0;
        for(String mob : mobs){
            if(mob.isEmpty())
                return;
            Material egg = Material.getMaterial(mob+"_SPAWN_EGG");
            //ignores steins eggs when repacking
            if(egg==null)
                continue;
            inv.setItem(i, new ItemStack(egg));
            i++;
        }
    }

    public void effect(PlayerInteractEvent event){
        if(event.useItemInHand()==Event.Result.DENY)
            return;
        Block block = event.getClickedBlock();
        if(block==null)
            return;
        event.setCancelled(true);
        ItemStack item = event.getItem();
        if(item==null || Utility.onCooldown(item))
            return;
        Utility.cooldown(event.getItem(), 20);
        effect(item, block);
    }

    private void effect(ItemStack item, Block block){
        ItemMeta meta = item.getItemMeta();
        String stored = meta.getPersistentDataContainer().get(Utility.pack, PersistentDataType.STRING);
        if (stored == null)
            return;

        try {
            String[] mobs = stored.split(" ");
            Entity base = null;
            Location loc = block.getRelative(BlockFace.UP).getLocation().add(0.5, 0, 0.5);
            World world = loc.getWorld();
            for(String mob : mobs) {
                String modifier = null;
                if(mob.contains(":")){
                    int index = mob.indexOf(":");
                    modifier = mob.substring(index+1);
                    mob = mob.substring(0, index);
                }
                Entity current = Utility.spawn(loc, world, mob, modifier);
                if(base!=null)
                    base.addPassenger(current);
                base = current;
            }
        } catch(IllegalArgumentException e){
            meta.getPersistentDataContainer().remove(Utility.pack);
            item.setItemMeta(meta);
        }
    }

    public void ability(BlockDispenseEvent event) {
        event.setCancelled(true);
        Block block = event.getBlock();
        effect(event.getItem(), block.getRelative(((Dispenser) block.getBlockData()).getFacing()));
    }
}
