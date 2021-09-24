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
    public int cost;

    public Item(String name, Set<Enchantment> accepted, Material material, int quantity,
         String lore, int durability, boolean stackable, boolean shiny, int cost){
        item = Utility.process(name, material, quantity, lore, durability, shiny);
        this.name = name;
        this.accepted = accepted;
        this.durability = durability;
        this.stackable = stackable;

        this.cost = cost;
        if(cost!=-1)
            registerRecipes();
    }

    public Item(String name, Material material, int quantity,
                String lore, int durability, boolean stackable, boolean shiny, int cost){
        item = Utility.process(name, material, quantity, lore, durability, shiny);
        this.name = name;
        this.accepted = null;
        this.durability = durability;
        this.stackable = stackable;

        this.cost = cost;
        if(cost!=-1)
            registerRecipes();
    }

    public Item(String name, Material material, int quantity, int durability, boolean stackable, boolean shiny, int cost){
        item = Utility.process(name, material, quantity, null, durability, shiny);
        this.name = name;
        this.accepted = null;
        this.durability = durability;
        this.stackable = stackable;

        this.cost = cost;
        if(cost!=-1)
            registerRecipes();
    }

    //temp
    public Item(String name, Set<Enchantment> accepted, Material material, int quantity,
                String lore, int durability, boolean stackable, boolean shiny, int cost, String id, char key){
        item = Utility.process(name, material, quantity, lore, durability, shiny);
        this.name = name;
        this.accepted = accepted;
        this.durability = durability;
        this.stackable = stackable;

        this.cost = cost;
        if(cost!=-1)
            registerRecipes();
    }

    public Item(String name, Material material, int quantity,
                String lore, int durability, boolean stackable, boolean shiny, int cost, String id, char key){
        item = Utility.process(name, material, quantity, lore, durability, shiny);
        this.name = name;
        this.accepted = null;
        this.durability = durability;
        this.stackable = stackable;

        this.cost = cost;
        if(cost!=-1)
            registerRecipes();
    }

    public Item(String name, Material material, int quantity, int durability, boolean stackable, boolean shiny, int cost, String id, char key){
        item = Utility.process(name, material, quantity, null, durability, shiny);
        this.name = name;
        this.accepted = null;
        this.durability = durability;
        this.stackable = stackable;

        this.cost = cost;
        if(cost!=-1)
            registerRecipes();
    }
    //

    public abstract void registerRecipes();
}