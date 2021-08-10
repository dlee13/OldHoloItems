package com.klin.holoItems.abstractClasses;

import com.klin.holoItems.utility.Utility;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Set;

public abstract class BatteryPack extends Pack {
    private static  final int size = 9;
    public static final String title = "Charging. . .";
    public static final boolean display = false;

    public final Material content;
    public final double perCharge;
    public final int cap;

    public BatteryPack(String name, Set<Enchantment> accepted, Material material, String lore, int durability, boolean shiny, int cost,
                       Material content, double perCharge, int cap, String id, char key){
        super(name, accepted, material, lore, durability, shiny, size, title, display, cost, id, key);
        this.content = content;
        this.perCharge = perCharge;
        this.cap = cap;
    }

    public BatteryPack(String name, Material material, String lore, int durability, boolean shiny, int cost,
                       Material content, double perCharge, int cap, String id, char key){
        super(name, material, lore, durability, shiny, size, title, display, cost, id, key);
        this.content = content;
        this.perCharge = perCharge;
        this.cap = cap;
    }

    public int ability(Inventory inv, ItemStack item, Player player){
        int count = 0;
        Location loc = player.getLocation();
        World world = loc.getWorld();
        Material content = this.content;
        if(content==null)
            content = item.getType();
        for(ItemStack fuel : inv.getContents()) {
            if(fuel==null || fuel.getType()==Material.AIR)
                continue;
            if(fuel.getType()!=content) {
                world.dropItemNaturally(loc, fuel);
                continue;
            }
            count += fuel.getAmount();
        }
        count *= perCharge;
        int excess = count-cap;
        excess = (int) (excess/perCharge);
        if(excess>0) {
            int stackSize = content.getMaxStackSize();
            if(stackSize==64)
                world.dropItemNaturally(loc, new ItemStack(content, excess));
            else{
                while(excess>0){
                    world.dropItemNaturally(loc, new ItemStack(content, Math.min(stackSize, excess)));
                    excess -= stackSize;
                }
            }
        }

        int by = Math.min(cap, count);
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(Utility.pack, PersistentDataType.INTEGER, by);
        item.setItemMeta(meta);
        player.sendMessage("Charged "+meta.getDisplayName()+"§f by: "+by);
        return by;
    }

    protected void repack(ItemStack item, Inventory inv) {
        Integer amount = item.getItemMeta().
                getPersistentDataContainer().get(Utility.pack, PersistentDataType.INTEGER);
        if (amount != null) {
            Material content = this.content;
            if (content == null)
                content = item.getType();
            int stackSize = content.getMaxStackSize();
            amount = (int) (amount / perCharge);
            if(stackSize==64)
                inv.addItem(new ItemStack(content, amount));
            else{
                while(amount>0){
                    inv.addItem(new ItemStack(content, Math.min(stackSize, amount)));
                    amount -= stackSize;
                }
            }
        }
    }
}
