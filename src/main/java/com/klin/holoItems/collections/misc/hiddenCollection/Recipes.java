package com.klin.holoItems.collections.misc.hiddenCollection;

import com.klin.holoItems.HoloItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Recipes {
    private static final String[] colors = new String[]{
            "WHITE_", "ORANGE_", "MAGENTA_", "LIGHT_BLUE_", "YELLOW_", "LIME_",
            "PINK_", "GRAY_", "LIGHT_GRAY_", "CYAN_", "PURPLE_", "BLUE_", "BROWN_",
            "GREEN_", "RED_", "BLACK_"};

    public static void registerRecipes(){
        Server server = Bukkit.getServer();
        
        ItemStack whiteWool = new ItemStack(Material.WHITE_WOOL);
        ItemStack whiteCarpet = new ItemStack(Material.WHITE_CARPET);
        ItemStack clearGlass = new ItemStack(Material.GLASS);
        ItemStack clearGlassPane = new ItemStack(Material.GLASS_PANE);
        ItemStack unfiredTerracotta = new ItemStack(Material.TERRACOTTA);
        ItemStack whiteBed = new ItemStack(Material.WHITE_BED);

        for(String color : colors) {
            String key = color.charAt(0)+ color.substring(1, color.length()-1).toLowerCase();

            ShapelessRecipe wool = new ShapelessRecipe(
                    new NamespacedKey(HoloItems.getInstance(), "bleach"+key+"Wool"), whiteWool);
            wool.addIngredient(Material.getMaterial(color+"WOOL"));
            wool.setGroup("wool");
            server.addRecipe(wool);

            ShapelessRecipe carpet = new ShapelessRecipe(
                    new NamespacedKey(HoloItems.getInstance(), "bleach"+key+"Carpet"), whiteCarpet);
            carpet.addIngredient(Material.getMaterial(color+"CARPET"));
            carpet.setGroup("carpet");
            server.addRecipe(carpet);

            ShapelessRecipe glass = new ShapelessRecipe(
                    new NamespacedKey(HoloItems.getInstance(), "bleach"+key+"Glass"), clearGlass);
            glass.addIngredient(Material.getMaterial(color+"STAINED_GLASS"));
            glass.setGroup("glass");
            server.addRecipe(glass);

            ShapelessRecipe pane = new ShapelessRecipe(
                    new NamespacedKey(HoloItems.getInstance(), "bleach"+key+"Pane"), clearGlassPane);
            pane.addIngredient(Material.getMaterial(color+"STAINED_GLASS_PANE"));
            pane.setGroup("pane");
            server.addRecipe(pane);

            ShapelessRecipe terracotta = new ShapelessRecipe(
                    new NamespacedKey(HoloItems.getInstance(), "bleach"+key+"Terracotta"), unfiredTerracotta);
            terracotta.addIngredient(Material.getMaterial(color+"TERRACOTTA"));
            terracotta.setGroup("terracotta");
            server.addRecipe(terracotta);

            ShapelessRecipe bed = new ShapelessRecipe(
                    new NamespacedKey(HoloItems.getInstance(), "bleach"+key+"Bed"), whiteBed);
            bed.addIngredient(Material.getMaterial(color+"BED"));
            bed.setGroup("carpet");
            server.addRecipe(bed);
        }

        ItemStack dispenser = new ItemStack(Material.DISPENSER);
        ShapedRecipe bowDropper = new ShapedRecipe(
                new NamespacedKey(HoloItems.getInstance(), "bowDropper"), dispenser);
        bowDropper.shape(" *%", "*#%", " *%");
        bowDropper.setIngredient('*', Material.STICK);
        bowDropper.setIngredient('#', Material.DROPPER);
        bowDropper.setIngredient('%', Material.STRING);
        bowDropper.setGroup("dispenser");
        server.addRecipe(bowDropper);

        ShapedRecipe dropperBow = new ShapedRecipe(
                new NamespacedKey(HoloItems.getInstance(), "dropperBow"), dispenser);
        dropperBow.shape("%* ", "%#*", "%* ");
        dropperBow.setIngredient('*', Material.STICK);
        dropperBow.setIngredient('#', Material.DROPPER);
        dropperBow.setIngredient('%', Material.STRING);
        dropperBow.setGroup("dispenser");
        server.addRecipe(dropperBow);

        ShapelessRecipe shapelessDispenser = new ShapelessRecipe(
                new NamespacedKey(HoloItems.getInstance(), "shapelessDispenser"), dispenser);
        shapelessDispenser.addIngredient(Material.DROPPER);
        shapelessDispenser.addIngredient(Material.BOW);
        shapelessDispenser.setGroup("dispenser");
        server.addRecipe(shapelessDispenser);
        
        ShapelessRecipe boneToBlock = new ShapelessRecipe(
                new NamespacedKey(HoloItems.getInstance(), "boneToBlock"), new ItemStack(Material.BONE_BLOCK, 3));
        boneToBlock.addIngredient(9, Material.BONE);
        boneToBlock.setGroup("boneBlock");
        server.addRecipe(boneToBlock);
    }
}
