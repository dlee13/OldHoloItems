package com.klin.holoItems.collections.holoCouncil.SanaCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.Collections;
import com.klin.holoItems.collections.gen0.suiseiCollection.items.Comet;
import com.klin.holoItems.collections.holoCouncil.SanaCollection.items.SpaceBreadSplash;
import com.klin.holoItems.utility.Utility;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class SanaCollection extends Collection {
    public static final String name = "Sana";
    public static final String desc = "";
    public static final String theme = "";
//    public static final String ign = "bigsana";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYzMDEzNDE2MzYxMSwKICAicHJvZmlsZUlkIiA6ICJhNzdkNmQ2YmFjOWE0NzY3YTFhNzU1NjYxOTllYmY5MiIsCiAgInByb2ZpbGVOYW1lIiA6ICIwOEJFRDUiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2QxYWY4NTMxMzk4OTI2NGQ5NTFiZWJmMjM3YWQzNjcyM2U5MjYwZjE1MmQwMjVhOGFiNWE3NjQ4MDc4MDZjOCIKICAgIH0KICB9Cn0=";

    public SanaCollection(){
        super(name, desc, theme, base64);
        collection.add(new SpaceBreadSplash());
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }

    public void inquire(Player player, ItemStack itemStack, PlayerInteractEntityEvent event) {
        Comet comet = Utility.findItem(itemStack, Comet.class);
        if(comet==null)
            return;
        String enchant = itemStack.getItemMeta().getPersistentDataContainer().get(Utility.enchant, PersistentDataType.STRING);
        if(enchant!=null && enchant.contains(SpaceBreadSplash.name))
            return;
        Inventory inv = player.getInventory();
        AtomicBoolean sharp = new AtomicBoolean(itemStack.getEnchantmentLevel(Enchantment.DAMAGE_ALL)>=5);
        AtomicBoolean smite = new AtomicBoolean(itemStack.getEnchantmentLevel(Enchantment.DAMAGE_UNDEAD)>=5);
        AtomicBoolean bane = new AtomicBoolean(itemStack.getEnchantmentLevel(Enchantment.DAMAGE_ARTHROPODS)>=5);
        Set<ItemStack> books = new HashSet<>();
        for(ItemStack item : inv.getStorageContents()){
            if(item==null)
                continue;
            if(item.getType().equals(Material.ENCHANTED_BOOK)){
                EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
                Map<Enchantment, Integer> storedEnchantments = meta.getStoredEnchants();
                boolean cont = false;
                Set<AtomicBoolean> found = new HashSet<>();
                for(Enchantment enchantment : storedEnchantments.keySet()) {
                    if(storedEnchantments.get(enchantment)<5) {
                        cont = true;
                        break;
                    } if(enchantment.equals(Enchantment.DAMAGE_ALL)) {
                        if(sharp.get()) {
                            cont = true;
                            break;
                        }
                        found.add(sharp);
                    } else if(enchantment.equals(Enchantment.DAMAGE_UNDEAD)) {
                        if(smite.get()){
                            cont = true;
                            break;
                        }
                        found.add(smite);
                    } else if(enchantment.equals(Enchantment.DAMAGE_ARTHROPODS)) {
                        if(bane.get()){
                            cont = true;
                            break;
                        }
                        found.add(bane);
                    } else {
                        cont = true;
                        break;
                    }
                }
                if(cont)
                    continue;
                for(AtomicBoolean find : found)
                    find.set(true);
                books.add(item);
            } if(sharp.get() && smite.get() && bane.get()){
                event.setCancelled(true);
                for(ItemStack book : books)
                    inv.removeItem(book);
                Utility.addEnchant(itemStack, (SpaceBreadSplash) (Collections.items.get(SpaceBreadSplash.name)));
                Location loc = event.getRightClicked().getLocation().add(0, 1.4, 0);
                World world = player.getWorld();
                world.spawnParticle(Particle.REDSTONE, loc, 4, 1, 1, 1, new Particle.DustOptions(Color.fromBGR(129, 1, 237), 2));
                world.spawnParticle(Particle.REDSTONE, loc, 4, 1, 1, 1, new Particle.DustOptions(Color.WHITE, 2));
                world.spawnParticle(Particle.REDSTONE, loc, 4, 1, 1, 1, new Particle.DustOptions(Color.BLUE, 2));
                player.sendMessage("§a[§dSana§a]§f: §dLimiter REMOVED!");
                return;
            }
        }
    }
}
