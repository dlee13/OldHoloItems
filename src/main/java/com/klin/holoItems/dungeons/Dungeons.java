package com.klin.holoItems.dungeons;

import com.klin.holoItems.Collections;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.collections.dungeons.inaDungeonCollection.InaDungeonCollection;
import com.klin.holoItems.dungeons.inaDungeon.InaDungeon;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Dungeons implements CommandExecutor {
    InaDungeon inaDungeon;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        switch(cmd.getName().toLowerCase()) {
            case "inadungeon":
                if(inaDungeon!=null){
                    System.out.println("Ina Dungeon already ON");
                    return true;
                }
                Collections.collections.put(InaDungeonCollection.name, new InaDungeonCollection());
                HoloItems holoItems = HoloItems.getInstance();
                inaDungeon = new InaDungeon();
                holoItems.getCommand("build").setExecutor(inaDungeon);
                holoItems.getCommand("tostring").setExecutor(inaDungeon);
                holoItems.getCommand("reset").setExecutor(inaDungeon);
                holoItems.getCommand("shop").setExecutor(inaDungeon);
                holoItems.getCommand("groundpound").setExecutor(inaDungeon);
                holoItems.getCommand("paintbomb").setExecutor(inaDungeon);
                holoItems.getCommand("spreadfire").setExecutor(inaDungeon);
                holoItems.getCommand("freeze").setExecutor(inaDungeon);
                holoItems.getCommand("select").setExecutor(inaDungeon);
                holoItems.getCommand("conduit").setExecutor(inaDungeon);
                holoItems.getCommand("deadbeats").setExecutor(inaDungeon);
                holoItems.getCommand("cookie").setExecutor(inaDungeon);
                holoItems.getCommand("plant").setExecutor(inaDungeon);
                holoItems.getCommand("minesweeper").setExecutor(inaDungeon);
                holoItems.getCommand("class").setExecutor(inaDungeon);
                holoItems.getCommand("maintain").setExecutor(inaDungeon);
                holoItems.getCommand("payload").setExecutor(inaDungeon);
                holoItems.getCommand("guide").setExecutor(inaDungeon);
                holoItems.getCommand("waterfall").setExecutor(inaDungeon);
                System.out.println("Ina Dungeon [ON]");
                return true;
        }
        return true;
    }
}
