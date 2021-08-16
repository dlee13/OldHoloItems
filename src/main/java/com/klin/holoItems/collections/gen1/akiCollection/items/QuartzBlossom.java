package com.klin.holoItems.collections.gen1.akiCollection.items;

import com.klin.holoItems.Collections;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.abstractClasses.BatteryPack;
import com.klin.holoItems.collections.gen1.akiCollection.AkiCollection;
import com.klin.holoItems.interfaces.*;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public class QuartzBlossom extends BatteryPack implements Extractable, Dropable, Holdable, Retainable, Clickable {
    public static final String name = "quartzBlossom";

    private static final Material material = Material.LILY_OF_THE_VALLEY;
    private static final String lore =
            "When used after fully charging\n"+
            "nether quartz ore will drop blocks\n"+
            "instead for 30 seconds";
    private static final int durability = 0;
    private static final boolean shiny = false;

    public static final Material content = Material.NETHER_BRICK;
    public static final double perFuel = 1;
    public static final int cap = 576;

    public static final int cost = 0;
    public static final char key = '0';

    public QuartzBlossom(){
        super(name, material, lore, durability, shiny, cost, content, perFuel, cap,
                ""+AkiCollection.key+key, key);
    }

    public void registerRecipes(){
        ShapedRecipe recipe =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("*","#","&");
        recipe.setIngredient('*', Material.QUARTZ_BLOCK);
        recipe.setIngredient('#', Material.END_ROD);
        recipe.setIngredient('&', new RecipeChoice.ExactChoice(Collections.findItem("00").item));
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void effect(PlayerInteractEvent event){
//        Block clicked = event.getClickedBlock();
//        if(clicked==null)
//            return;
        ItemStack item = event.getItem();
        if(/*clicked.getType()!=Material.NETHER_QUARTZ_ORE ||*/ item.getType()==Material.OXEYE_DAISY)
            return;
        Player player = event.getPlayer();
        ItemMeta meta = item.getItemMeta();
        Integer currCharge = meta.getPersistentDataContainer().
                get(com.klin.holoItems.utility.Utility.pack, PersistentDataType.INTEGER);
        if(currCharge==null || currCharge!=576)
            return;
//        currCharge = (int)(currCharge/19.2);
//        if(active.containsKey(player)){
//            player.sendMessage("You already have §6Quartz Blossom§f active");
//            return;
//        }
        if(event.getPlayer().getGameMode()!=GameMode.CREATIVE) {
            meta.getPersistentDataContainer().set(Utility.pack, PersistentDataType.INTEGER, 0);
            item.setItemMeta(meta);
        }

        item.setType(Material.OXEYE_DAISY);
//        Set<Block> quartz = new HashSet<>();
//        Queue<Block> toCheck = new LinkedList<>();
//        toCheck.add(clicked);
//        World world = player.getWorld();
//        while(!toCheck.isEmpty() && quartz.size()<60){
//            Block center = toCheck.poll();
//            world.spawnParticle(Particle.END_ROD, center.getLocation().add(0.5, 0.5, 0.5), 4, 0, 0, 0, 0.05);
//            quartz.add(center);
//            for (Block block : new Block[]{
//                    center.getRelative(BlockFace.UP),
//                    center.getRelative(BlockFace.NORTH),
//                    center.getRelative(BlockFace.SOUTH),
//                    center.getRelative(BlockFace.EAST),
//                    center.getRelative(BlockFace.WEST),
//                    center.getRelative(BlockFace.DOWN)}) {
//                if (block.getType() == Material.NETHER_QUARTZ_ORE &&
//                        !quartz.contains(block) && !toCheck.contains(block))
//                    toCheck.add(block);
//            }
//        }
//        active.put(player, quartz);
//        int duration = currCharge*(quartz.size()/60);

        player.sendMessage("Blossoming for 30 seconds");
//        new Task(HoloItems.getInstance(), 0, 10){
//            int increment = 0;
//
//            public void run(){
//                if(increment>=duration*2 || quartz.isEmpty() || player.isDead()){
//                    active.remove(player);
//                    PlayerInventory inv = player.getInventory();
//                    if(inv.getItemInOffHand().equals(item)) {
//                        item.setType(Material.LILY_OF_THE_VALLEY);
//                        inv.setItemInOffHand(item);
//                    }
//                    else {
//                        inv.removeItem(item);
//                        item.setType(Material.LILY_OF_THE_VALLEY);
//                        inv.addItem(item);
//                    }
//                    cancel();
//                    return;
//                }
//                Set<Block> toRemove = new HashSet<>();
//                for(Block center : quartz) {
//                    if(center.getType()!=Material.NETHER_QUARTZ_ORE)
//                        toRemove.add(center);
//                    else
//                        world.spawnParticle(Particle.END_ROD, center.getLocation().add(0.5, 0.5, 0.5), 4, 0, 0, 0, 0.05);
//                }
//                for(Block center : toRemove)
//                    quartz.remove(center);
//                increment++;
//                if(increment==duration*2-20)
//                    player.sendMessage("§710 seconds remaining");
//                if(increment>=duration*2-10 && increment%2==0)
//                    player.sendMessage("§7"+((duration*2-increment)/2));
//            }
//        };
        new BukkitRunnable(){
            public void run(){
                PlayerInventory inv = player.getInventory();
                if(inv.getItemInOffHand().equals(item)) {
                    item.setType(Material.LILY_OF_THE_VALLEY);
                    inv.setItemInOffHand(item);
                }
                else {
                    inv.removeItem(item);
                    item.setType(Material.LILY_OF_THE_VALLEY);
                    inv.addItem(item);
                }
            }
        }.runTaskLater(HoloItems.getInstance(), 600);
    }

    public void ability(PlayerDropItemEvent event){
        if(event.getItemDrop().getItemStack().getType()==Material.OXEYE_DAISY)
            event.setCancelled(true);
    }

    public void ability(BlockBreakEvent event){
        Player player = event.getPlayer();
//        Set<Block> blocks = active.get(player);
//        if(blocks==null)
//            return;
        if(player.getInventory().getItemInOffHand().getType()!=Material.OXEYE_DAISY)
            return;

        Block block = event.getBlock();
//        if(blocks.contains(block)){
//            blocks.remove(block);
            if(block.getType()!=Material.NETHER_QUARTZ_ORE || !event.isDropItems())
                return;

            event.setCancelled(true);
            block.setType(Material.AIR);
            ItemStack pickaxe = player.getInventory().getItemInMainHand();
            int drops = pickaxe.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS)+1;
            player.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.QUARTZ_BLOCK, 1+(int)(Math.random()*drops)));
//        }
    }

    public boolean ability(PlayerDeathEvent event, ItemStack item){
        item.setType(Material.LILY_OF_THE_VALLEY);
        return false;
    }

    public void ability(InventoryClickEvent event, boolean current){
        if(event.getClickedInventory() instanceof CraftingInventory || !current)
            return;

        ItemStack item = event.getCurrentItem();
        if(item.getType()==Material.OXEYE_DAISY)
            event.setCancelled(true);
//        if(!active.containsKey(event.getWhoClicked()))
//            item.setAmount(0);
    }
}
