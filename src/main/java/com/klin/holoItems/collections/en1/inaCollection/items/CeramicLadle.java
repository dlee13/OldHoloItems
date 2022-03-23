package com.klin.holoItems.collections.en1.inaCollection.items;

import com.destroystokyo.paper.MaterialSetTag;
import com.destroystokyo.paper.MaterialTags;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.abstractClasses.Wiring;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.*;

public class CeramicLadle extends Wiring {
    public static final String name = "ceramicLadle";

    private static final NamespacedKey KEY = new NamespacedKey(HoloItems.getInstance(), name);
    private static final MaterialSetTag COMPATIBLE_MATERIALS = new MaterialSetTag(KEY, CeramicLadle::isCompatibleMaterial);

    private static final Material material = Material.STONE_SHOVEL;
    private static final String lore =
            "Dispensers wired with this item will\n" +
            "plant saplings, flowers, and mushrooms\n" +
            "Break the dispenser to retrieve";
    private static final boolean shiny = true;
    public static final int cost = 16720;

    public CeramicLadle(){
        super(name, material, lore, shiny, cost);
    }

    public void registerRecipes(){
        BlastingRecipe blastingRecipe = new BlastingRecipe(KEY,
                item, new RecipeChoice.ExactChoice(
                Utility.process("earthenSpoon", Material.WOODEN_SHOVEL, 1,
                        "Dispensers wired with this item will\n" +
                             "plant crops from its inventory\n" +
                             "Break the dispenser to retrieve", 0, shiny)
        ), 1, 900);
        Bukkit.getServer().addRecipe(blastingRecipe);
    }

    // in the rewrite, BlockPreDispenseEvent should be used instead
    public void ability(BlockDispenseEvent event) {
        final var itemStack = event.getItem();
        final var material = itemStack.getType();

        if (!COMPATIBLE_MATERIALS.isTagged(material)) {
            return;
        }

        event.setCancelled(true);

        final var block = event.getBlock();
        final var face = ((Directional) block.getBlockData()).getFacing();
        final var placedBlock = block.getRelative(face, face.equals(BlockFace.UP) ? 2 : 1);

        if (!placedBlock.isEmpty() || !placedBlock.canPlace(material.createBlockData())) {
            return;
        }

        placedBlock.setType(material);

        final var inventory = ((InventoryHolder) block.getState()).getInventory();
        Bukkit.getScheduler().runTask(HoloItems.getInstance(), () -> inventory.removeItem(itemStack));
    }

    private static boolean isCompatibleMaterial(Material material) {
        return Tag.FLOWERS.isTagged(material) || Tag.SAPLINGS.isTagged(material)
            || MaterialTags.MUSHROOMS.isTagged(material) || material.toString().endsWith("FUNGUS");
    }
}
