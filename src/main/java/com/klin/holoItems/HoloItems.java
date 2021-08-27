package com.klin.holoItems;

import com.klin.holoItems.dungeons.Dungeons;
import com.klin.holoItems.dungeons.inaDungeon.InaDungeon;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class HoloItems extends JavaPlugin {
    private static HoloItems instance;

    @Override
    public void onEnable(){
        instance = this;

        Collections collections = new Collections();
        getServer().getPluginManager().registerEvents(collections, this);
        getServer().getPluginManager().registerEvents(new Events(), this);
        getServer().getPluginManager().registerEvents(new StatManager(), this);

        getCommand("collections").setExecutor(collections);
        getCommand("coordinates").setExecutor(collections);
        getCommand("custommodeldata").setExecutor(collections);
        getCommand("registry").setExecutor(collections);
        getCommand("acquire").setExecutor(collections);

        getCommand("accelerate").setExecutor(collections);
        getCommand("canceltasks").setExecutor(collections);
        getCommand("clearactivatables").setExecutor(collections);
        getCommand("collect").setExecutor(collections);
        getCommand("disable").setExecutor(collections);
        getCommand("enable").setExecutor(collections);
        getCommand("getactivatables").setExecutor(collections);
        getCommand("gethead").setExecutor(collections);
        getCommand("getmap").setExecutor(collections);
        getCommand("removechunktickets").setExecutor(collections);
        getCommand("setcustommodeldata").setExecutor(collections);
        getCommand("setenchantments").setExecutor(collections);
        getCommand("settype").setExecutor(collections);
        getCommand("test").setExecutor(collections);

        Dungeons dungeons = new Dungeons();
        getCommand("inadungeon").setExecutor(dungeons);

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "HoloItems [ON]");
    }

    @Override
    public void onDisable(){
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "HoloItems [OFF]");
    }

    public static HoloItems getInstance() {
        return instance;
    }
}
