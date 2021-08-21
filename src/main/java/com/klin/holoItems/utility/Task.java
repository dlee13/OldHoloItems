package com.klin.holoItems.utility;

import com.klin.holoItems.Collections;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Task implements Runnable{
    private final int taskId;

    public Task(JavaPlugin plugin, int arg1, int arg2) {
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this, arg1, arg2);
        Collections.taskIds.add(taskId);
    }

    public void cancel(){
        Bukkit.getScheduler().cancelTask(taskId);
        Collections.taskIds.remove(taskId);
    }

    public int getTaskId(){
        return taskId;
    }
}
