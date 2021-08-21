package com.klin.holoItems.collections.dungeons.inaDungeon.items;

import com.klin.holoItems.Item;
import com.klin.holoItems.collections.dungeons.inaDungeon.InaDungeon;
import com.klin.holoItems.interfaces.Reactable;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class Payload extends Item implements Reactable {
    public static final String name = "noDrop";

    private static final Material material = Material.TNT;
    private static final int quantity = 1;
    private static final String lore =
            "Add tnt";
    private static final int durability = 0;
    public static final boolean stackable = true;
    private static final boolean shiny = false;

    public static final int cost = -1;
    public static final char key = '0';
    public static final String id = ""+InaDungeon.key+key;

    public Payload(){
        super(name, material, quantity, lore, durability, stackable, shiny, cost, id, key);
    }

    public void registerRecipes(){}

    public void ability(PlayerInteractEntityEvent event) {
        com.klin.holoItems.dungeons.inaDungeon.Payload.add();
    }
}
