package com.klin.holoItems.collections.misc.klinCollection.items;

import com.klin.holoItems.abstractClasses.Pack;
import com.klin.holoItems.collections.misc.klinCollection.KlinCollection;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.*;
import org.bukkit.block.data.type.Lantern;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Trowel extends Pack {
    public static final String name = "trowel";
    public static final Set<Enchantment> accepted = null;
    private static final Map<Integer, BlockFace> directions = new HashMap<Integer, BlockFace>(){{
        put(0, BlockFace.NORTH);
        put(1, BlockFace.EAST);
        put(2, BlockFace.SOUTH);
        put(3, BlockFace.WEST);
    }};
    private static final Map<BlockFace, Integer> faces = new HashMap<BlockFace, Integer>(){{
        put(BlockFace.NORTH, 0);
        put(BlockFace.WEST, 1);
        put(BlockFace.SOUTH, 2);
        put(BlockFace.EAST, 3);
    }};

    private static final Material material = Material.NETHERITE_SHOVEL;
    private static final String lore =
            "§6Ability" +"/n"+
                    "brrrrrrrrrr";
    private static final int durability = -1;
    private static final boolean shiny = false;
    private static final int size = 54;

    private static final String title = "Blueprint";
    private static final boolean display = false;

    public static final int cost = -1;
    public static final char key = '1';

    public Trowel(){
        super(name, accepted, material, lore, durability, shiny, size, title, display, cost,
                ""+KlinCollection.key+key, key);
    }

    public void registerRecipes(){}

    public int ability(Inventory inv, ItemStack item, Player player){
        String stored = "";
        ItemStack[] blocks = inv.getStorageContents();
        for(int i=53; i>=0; i--){
            ItemStack block = blocks[i];
            if(block==null || block.getType()==Material.AIR)
                stored += " ";
            else {
                Material type = block.getType();
                if(type==Material.WATER_BUCKET)
                    stored += " WATER";
                else
                    stored += " " + block.getType();
            }
        }
        String trim = stored;
        if(trim.trim().isEmpty())
            return -1;
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(Utility.pack, PersistentDataType.STRING, stored.substring(1));
        item.setItemMeta(meta);
        return -1;
    }

    protected void repack(ItemStack item, Inventory inv){
        String stored = item.getItemMeta().
                getPersistentDataContainer().get(Utility.pack, PersistentDataType.STRING);
        if (stored == null)
            return;

        String[] blocks = stored.split(" ");
        int i = 53;
        for(String block : blocks){
            if(!block.isEmpty()) {
                Material type = Material.getMaterial(block);
                if (type != null) {
                    if(type==Material.WATER)
                        inv.setItem(i, new ItemStack(Material.WATER_BUCKET));
                    else
                        inv.setItem(i, new ItemStack(type));
                }
            }
            i--;
        }
    }

    protected void effect(PlayerInteractEvent event){
        if(event.getAction()!= Action.RIGHT_CLICK_BLOCK)
            return;
        Player player = event.getPlayer();
        BlockFace direction = player.getFacing();
        boolean axis;
        switch(direction){
            case NORTH:
            case SOUTH:
                axis = true;
                break;
            case EAST:
            case WEST:
                axis = false;
                break;
            default:
                player.sendMessage("Face a cardinal direction: "+direction);
                return;
        }
        Location bottomCenter = event.getClickedBlock().getLocation();
        World world = bottomCenter.getWorld();

        ItemStack trowel = event.getItem();
        ItemMeta meta = trowel.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        String stored = container.get(Utility.pack, PersistentDataType.STRING);
        if(stored==null){
            stored = "";
            String stack = "";
            for(int j=0; j<6; j++){
                for(int i=-4; i<5; i++){
                    Block block = world.getBlockAt(bottomCenter.clone().add(axis ? i : 0, j, !axis ? i : 0));
                    Material type = block.getType();
                    BlockData data = block.getBlockData();
                    if(data instanceof Bisected)
                        stack += " "+((Bisected) data).getHalf();
                    if(data instanceof Openable)
                        stack += " "+(((Openable) data).isOpen() ? "1" : "0");
                    if(data instanceof Waterlogged)
                        stack += " "+(((Waterlogged) data).isWaterlogged() ? "1" : "0");
                    if (data instanceof Directional) {
                        stack += " "+((Directional) data).getFacing();
                        if(data instanceof Stairs)
                            stack += " "+((Stairs) data).getShape();
                    }
                    else if(data instanceof Slab)
                        stack += " "+((Slab) data).getType();
                    else if(data instanceof Lantern)
                        stack += " "+(((Lantern) data).isHanging() ? "1" : "0");
                    stored += type==Material.AIR ? " GLASS" : " "+type;
                }
            }
            container.set(Utility.pack, PersistentDataType.STRING, stored.substring(1));
            if(!stack.isEmpty())
                container.set(Utility.stack, PersistentDataType.STRING, stack.substring(1));
            container.set(Utility.cooldown, PersistentDataType.INTEGER, faces.get(direction));
            trowel.setItemMeta(meta);
            return;
        }

        String[] blocks = stored.split(" ");
        String stack = container.get(Utility.stack, PersistentDataType.STRING);
        String[] facing = null;
        if(stack!=null)
            facing = stack.split(" ");
        Integer off = container.get(Utility.cooldown, PersistentDataType.INTEGER);
        int offset = 0;
        if(off!=null)
            offset = off-faces.get(player.getFacing());
        int blocksIncrement = 0;
        int facingIncrement = 0;
        for(int j=0; j<6; j++){
            for(int i=-4; i<5; i++){
                if(!blocks[blocksIncrement].trim().isEmpty()) {
                    Material type = Material.getMaterial(blocks[blocksIncrement]);
                    if (type!=null) {
                        if(type==Material.GLASS)
                            type = Material.AIR;
                        Block block = world.getBlockAt(bottomCenter.clone().
                                add(axis ? i : 0, j, !axis ? i : 0));
                        block.setType(type);
                        if(facing!=null && facing.length>facingIncrement) {
                            BlockData data = block.getBlockData();
                            if(data instanceof Bisected){
                                Bisected bisected = (Bisected) data;
                                bisected.setHalf(Bisected.Half.valueOf(facing[facingIncrement]));
                                facingIncrement++;
                            }
                            if(data instanceof Openable){
                                Openable openable = (Openable) data;
                                openable.setOpen(facing[facingIncrement].equals("1"));
                                facingIncrement++;
                            }
                            if(data instanceof Waterlogged){
                                Waterlogged waterlogged = (Waterlogged) data;
                                waterlogged.setWaterlogged(facing[facingIncrement].equals("1"));
                                facingIncrement++;
                            }
                            if(data instanceof Directional) {
                                Directional directional = (Directional) data;
                                BlockFace face = BlockFace.valueOf(facing[facingIncrement]);
                                int rotate = faces.get(face)+offset;
                                if(rotate<0)
                                    rotate += 4;
                                else if(rotate>3)
                                    rotate -= 4;
                                directional.setFacing(directions.get(rotate));
                                facingIncrement++;
                                if(data instanceof Stairs){
                                    Stairs stairs = (Stairs) data;
                                    stairs.setShape(Stairs.Shape.valueOf(facing[facingIncrement]));
                                    facingIncrement++;
                                }
                                block.setBlockData(directional);
                            }
                            else if(data instanceof Slab){
                                Slab slab = (Slab) data;
                                slab.setType(Slab.Type.valueOf(facing[facingIncrement]));
                                block.setBlockData(slab);
                                facingIncrement++;
                            }
                            else if(data instanceof Lantern){
                                Lantern lantern = (Lantern) data;
                                lantern.setHanging(facing[facingIncrement].equals("1"));
                                block.setBlockData(lantern);
                                facingIncrement++;
                            }
                        }
                    }
                }
                blocksIncrement++;
            }
        }
    }
}
