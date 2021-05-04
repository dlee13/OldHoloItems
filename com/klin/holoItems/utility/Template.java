//package com.klin.holoItems.utility;
//
//import com.klin.holoItems.HoloItems;
//import com.klin.holoItems.Item;
//import org.bukkit.*;
//import org.bukkit.enchantments.Enchantment;
//import org.bukkit.event.player.PlayerInteractEvent;
//import org.bukkit.inventory.ShapedRecipe;
//
//import java.util.HashSet;
//import java.util.Set;
//
//public class Template extends Item implements {
//    public static final String name = ;
//    public static final Set<Enchantment> accepted = new HashSet<Enchantment>(){{
//
//    }};
//
//    private static final Material material = Material.;
//    private static final int quantity = ;
//    private static final String lore =
//            "§6Ability" +"/n"+
//            ;
//    private static final int durability = ;
//    private static final boolean shiny = ;
//
//    public static final int cost = ;
//    private static final String type = +":";
//    public static final char key = ;
//
//    public Template(){
//        super(name, accepted, material, quantity, lore, durability, shiny, cost,
//                type+Collection.key+key, key);
//    }
//
//    public void registerRecipes(){
//        ShapedRecipe recipe0 =
//                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
//        recipe.shape("** ","*% "," % ");
//        recipe.setIngredient('*', Material.);
//        recipe.setIngredient('%', Material.);
//        recipe.setGroup(name);
//        Bukkit.getServer().addRecipe(recipe);
//    }
//
//    public void ability(PlayerInteractEvent event, String action) {
//    }
//}
