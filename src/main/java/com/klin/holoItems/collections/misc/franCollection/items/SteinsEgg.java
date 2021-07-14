package com.klin.holoItems.collections.misc.franCollection.items;

import com.klin.holoItems.Collections;
import com.klin.holoItems.Item;
import com.klin.holoItems.abstractClasses.Pack;
import com.klin.holoItems.collections.misc.franCollection.FranCollection;
import com.klin.holoItems.interfaces.Combinable;
import com.klin.holoItems.interfaces.Dispensable;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Dispenser;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Set;

public class SteinsEgg extends Pack implements Dispensable {
    public static final String name = "steinsEgg";
    public static final Set<Enchantment> accepted = null;

    private static final Material material = Material.EGG;
    private static final String lore =
            "§6Ability" +"/n"+
            "egg";
    private static final int durability = 0;
    private static final boolean shiny = true;

    private static final int size = 9;
    public static final String title = "egg";
    public static final boolean display = false;

    public static final int cost = -1;
    public static final char key = '1';
    public static final String id = ""+FranCollection.key+key;

    public SteinsEgg(){
        super(name, accepted, material, lore, durability, shiny, size, title, display, cost, id, key);
    }

    public void registerRecipes(){}

    public int ability(Inventory inv, ItemStack item, Player player){
        ItemMeta itemMeta = item.getItemMeta();
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        String modifiers = container.get(Utility.pack, PersistentDataType.STRING);
        boolean blank = modifiers==null;
        if(blank)
            modifiers = "";
        Material base = null;
        for(ItemStack content : inv.getContents()) {
            if(content==null)
                continue;
            Material type = content.getType();
            if(type==Material.AIR)
                continue;
            if(Utility.spawnEggs.contains(type))
                base = type;
            ItemMeta meta = content.getItemMeta();
            if(meta==null)
                continue;
            String id = meta.getPersistentDataContainer().get(Utility.key, PersistentDataType.STRING);
            if(id==null)
                continue;
            Item generic = Collections.findItem(id);
            if(generic instanceof Combinable) {
                modifiers += "-" + id + ((Combinable) generic).processInfo(content);
            }
        }
        if(base!=null){
            item.setType(base);
            String entityType = base.toString();
            player.sendMessage("Base set to: "+entityType.substring(0, entityType.indexOf("_SPAWN_EGG")));
        }
        if(modifiers.isEmpty())
            return -1;

        if(blank)
            modifiers = modifiers.substring(1);
        container.set(Utility.pack, PersistentDataType.STRING, modifiers);
        item.setItemMeta(itemMeta);
        player.sendMessage("Modifiers added: "+modifiers.replace("-", " "));
        return -1;
    }

    protected void repack(ItemStack item, Inventory inv){}

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
        Player player = event.getPlayer();
        effect(item, block, player);
    }

    private void effect(ItemStack item, Block block, Player player){
        String base = item.getType().toString();
        int index = base.indexOf("_SPAWN_EGG");
        if (index==-1) {
            if(player!=null)
                player.sendMessage("No base has been set");
            return;
        }
        String modifiers = item.getItemMeta().getPersistentDataContainer().get(Utility.pack, PersistentDataType.STRING);
        if(modifiers == null && player!=null)
            player.sendMessage("No modifiers have been added");
        else {
            Location loc = block.getRelative(BlockFace.UP).getLocation().add(0.5, 0, 0.5);
            World world = loc.getWorld();
            Utility.spawn(loc, world, base.substring(0, index), modifiers);
        }
    }

    public void ability(BlockDispenseEvent event) {
        event.setCancelled(true);
        Block block = event.getBlock();
        effect(event.getItem(), block.getRelative(((Dispenser) block.getBlockData()).getFacing()), null);
    }
}
