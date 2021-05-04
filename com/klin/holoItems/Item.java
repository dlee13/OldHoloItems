package com.klin.holoItems;

import com.klin.holoItems.utility.Utility;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public abstract class Item {
    public ItemStack item;
    public String name;
    public Set<Enchantment> accepted;
    public int durability;
    public boolean stackable;
    public String id;
    public int cost;
    public char key;

    public Item(String name, Set<Enchantment> accepted, Material material, int quantity,
         String lore, int durability, boolean stackable, boolean shiny, int cost, String id, char key){
        item = Utility.process(name, material, quantity, lore, durability, shiny, id);
        this.name = name;
        this.accepted = accepted;
        this.durability = durability;
        this.stackable = stackable;

        this.cost = cost;
        if(cost!=-1)
            registerRecipes();
        this.id = id;
        this.key = key;
    }

    public abstract void registerRecipes();
}