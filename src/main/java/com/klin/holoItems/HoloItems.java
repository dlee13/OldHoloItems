package com.klin.holoItems;

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
        getCommand("worldname").setExecutor(collections);
        getCommand("custommodeldata").setExecutor(collections);
        getCommand("acquire").setExecutor(collections);
        getCommand("registry").setExecutor(collections);
        getCommand("disable").setExecutor(collections);
        getCommand("enable").setExecutor(collections);
        getCommand("collect").setExecutor(collections);
        getCommand("gethead").setExecutor(collections);
        getCommand("setcustommodeldata").setExecutor(collections);
        getCommand("settype").setExecutor(collections);
        getCommand("test").setExecutor(collections);

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
