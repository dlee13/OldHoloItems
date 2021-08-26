package com.klin.holoItems.dungeons;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.dungeons.inaDungeon.InaDungeon;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Dungeons implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        switch(cmd.getName().toLowerCase()) {
            case "inadungeon":
                HoloItems holoItems = HoloItems.getInstance();
                InaDungeon inaDungeon = new InaDungeon();
                holoItems.getCommand("build").setExecutor(inaDungeon);
                holoItems.getCommand("tostring").setExecutor(inaDungeon);
                //attack
                holoItems.getCommand("groundpound").setExecutor(inaDungeon);
                holoItems.getCommand("paintbomb").setExecutor(inaDungeon);
                holoItems.getCommand("spreadfire").setExecutor(inaDungeon);
                //classSelect
                holoItems.getCommand("freeze").setExecutor(inaDungeon);
                holoItems.getCommand("select").setExecutor(inaDungeon);
                holoItems.getCommand("resetselect").setExecutor(inaDungeon);
                //conduit
                holoItems.getCommand("conduit").setExecutor(inaDungeon);
                holoItems.getCommand("resetconduit").setExecutor(inaDungeon);
                //gettingWood
                holoItems.getCommand("plant").setExecutor(inaDungeon);
                holoItems.getCommand("resetgettingwood").setExecutor(inaDungeon);
                //minesweeper
                holoItems.getCommand("minesweeper").setExecutor(inaDungeon);
                holoItems.getCommand("resetminesweeper").setExecutor(inaDungeon);
                //maintenance
                holoItems.getCommand("class").setExecutor(inaDungeon);
                holoItems.getCommand("maintain").setExecutor(inaDungeon);
                holoItems.getCommand("resetmaintenance").setExecutor(inaDungeon);
                //payload
                holoItems.getCommand("payload").setExecutor(inaDungeon);
                holoItems.getCommand("spawn").setExecutor(inaDungeon);
                //waterfall
                holoItems.getCommand("waterfall").setExecutor(inaDungeon);
                holoItems.getCommand("resetwaterfall").setExecutor(inaDungeon);
                return true;
        }
        return true;
    }
}
