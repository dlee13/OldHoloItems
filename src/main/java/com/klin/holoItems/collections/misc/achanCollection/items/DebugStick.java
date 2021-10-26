package com.klin.holoItems.collections.misc.achanCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.abstractClasses.Enchant;
import com.klin.holoItems.collections.gen0.suiseiCollection.items.Comet;
import com.klin.holoItems.collections.gen3.flareCollection.items.Splinter;
import com.klin.holoItems.collections.misc.utilityCollection.items.Modified;
import com.klin.holoItems.interfaces.Interactable;
import com.klin.holoItems.utility.Utility;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.RayTraceResult;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DebugStick extends Enchant implements Interactable {
    public static final String name = "debugStick";
    public static final Map<Integer, BlockFace> connections = Map.of(
            0, BlockFace.NORTH,
            1, BlockFace.SOUTH,
            2, BlockFace.EAST,
            3, BlockFace.WEST);
    public static final Set<String> acceptedIds = Stream.of(Comet.name, Splinter.name).collect(Collectors.toCollection(HashSet::new));
    public static final Set<Material> acceptedTypes = Utility.axes;
    public static final int expCost = 39;

    private static final Material material = Material.STICK;
    private static final String lore =
            "Modify item drops";
    private static final int durability = 0;
    private static final boolean shiny = true;
    public static final int cost = 0;

    public DebugStick(){
        super(name, material, lore, durability, shiny, cost, acceptedIds, acceptedTypes, expCost);
    }

    public void registerRecipes(){
        ShapedRecipe recipe =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("a","b","c");
        recipe.setIngredient('a', Material.LEVER);
        recipe.setIngredient('b', Material.TURTLE_HELMET);
        recipe.setIngredient('c', Material.ARMOR_STAND);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(PlayerInteractEvent event, Action action){
        ItemStack itemStack = event.getItem();
        if(Utility.onCooldown(itemStack))
            return;
        Utility.cooldown(itemStack, 2);
        Player player = event.getPlayer();
        if(action==Action.LEFT_CLICK_AIR || action==Action.LEFT_CLICK_BLOCK){
            ItemStack item = event.getItem();
            ItemMeta meta = item.getItemMeta();
            PersistentDataContainer container = meta.getPersistentDataContainer();
            Integer pack = container.get(Utility.pack, PersistentDataType.INTEGER);
            container.set(Utility.pack, PersistentDataType.INTEGER, pack = ((pack==null)?0:pack +1)%10);
            item.setItemMeta(meta);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(pack+1+""));
            return;
        }
        Location loc = player.getEyeLocation();
        RayTraceResult result = loc.getWorld().rayTraceEntities(loc, loc.getDirection(), 4, 0.2, entity -> entity instanceof Item);
        if (result==null)
            return;
        Item entity = (Item) result.getHitEntity();
        if(entity==null)
            return;
        ItemStack item = entity.getItemStack();
        Material type = item.getType();
        if(Utility.findItem(item, Modified.class)==null && !item.isSimilar(new ItemStack(type)))
            return;
        ItemMeta meta = item.getItemMeta();
        if(meta==null)
            return;

        if(type==Material.IRON_TRAPDOOR){
            PersistentDataContainer container = meta.getPersistentDataContainer();
            int[] modifier = container.get(Utility.pack, PersistentDataType.INTEGER_ARRAY);
            if(modifier==null){
                container.set(Utility.pack, PersistentDataType.INTEGER_ARRAY, new int[1]);
                if(container.get(Utility.key, PersistentDataType.STRING)==null)
                    container.set(Utility.key, PersistentDataType.STRING, Modified.name);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("\"open\" to true"));
            } else {
                container.remove(Utility.pack);
                if(Modified.name.equals(container.get(Utility.key, PersistentDataType.STRING)))
                    container.remove(Utility.key);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("\"open\" to false"));
            }
            item.setItemMeta(meta);
        } else if(Utility.fences.contains(type)) {
            PersistentDataContainer container = meta.getPersistentDataContainer();
            Integer pack = itemStack.getItemMeta().getPersistentDataContainer().get(Utility.pack, PersistentDataType.INTEGER);
            pack = pack==null?0:pack%5;
            int[] modifier = container.get(Utility.pack, PersistentDataType.INTEGER_ARRAY);
            if(pack>=4) {
                container.remove(Utility.pack);
                if(Modified.name.equals(container.get(Utility.key, PersistentDataType.STRING)))
                    container.remove(Utility.key);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("reset"));
            } else if(modifier==null){
                modifier = new int[4];
                modifier[pack] = 1;
                container.set(Utility.pack, PersistentDataType.INTEGER_ARRAY, modifier);
                if(container.get(Utility.key, PersistentDataType.STRING)==null)
                    container.set(Utility.key, PersistentDataType.STRING, Modified.name);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("\"" + connections.get(pack).toString().toLowerCase() + "\" to true"));
            } else {
                if(modifier[pack]==1){
                    modifier[pack] = 0;
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("\"" + connections.get(pack).toString().toLowerCase() + "\" to false"));
                } else{
                    modifier[pack] = 1;
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("\"" + connections.get(pack).toString().toLowerCase() + "\" to true"));
                }
                container.set(Utility.pack, PersistentDataType.INTEGER_ARRAY, modifier);
            }
            item.setItemMeta(meta);
        } else if(Utility.axes.contains(itemStack.getType()) && Utility.logs.contains(type)){
            String material = type.toString();
            if(!material.endsWith("_LOG") && !material.endsWith("_STEM"))
                return;
            if(material.startsWith("STRIPPED_"))
                material = material.substring(9);
            else
                material = "STRIPPED_" + material;
            item.setType(Material.valueOf(material));
            Utility.addDurability(itemStack, -item.getAmount(), player);
        } else return;
        entity.setItemStack(item);
    }
}