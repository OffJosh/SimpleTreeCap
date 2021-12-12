package com.gmail.jlmerrett.SimpleTreeCap;

import com.gmail.jlmerrett.SimpleTreeCap.EventHandlers.BreakBlockEventHandler;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleTreeCap extends JavaPlugin {

    static BreakBlockEventHandler breakBlockEventHandler;
    static Plugin plugin;

    @Override
    public void onEnable() {
       initPlugin();
    }

    @Override
    public void onDisable() {

    }

    private void initPlugin(){
        plugin = this;
        breakBlockEventHandler = new BreakBlockEventHandler();
        getServer().getPluginManager().registerEvents(breakBlockEventHandler, plugin);
    }
}
