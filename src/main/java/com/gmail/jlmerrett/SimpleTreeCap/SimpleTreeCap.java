package com.gmail.jlmerrett.SimpleTreeCap;

import com.gmail.jlmerrett.SimpleTreeCap.EventHandlers.BreakBlockEventHandler;
import com.gmail.jlmerrett.SimpleTreeCap.EventHandlers.Commands;
import com.gmail.jlmerrett.SimpleTreeCap.EventHandlers.PlayerJoinEventHandler;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleTreeCap extends JavaPlugin {

    static BreakBlockEventHandler breakBlockEventHandler;
    static PlayerJoinEventHandler playerJoinEventHandler;
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
        playerJoinEventHandler = new PlayerJoinEventHandler(plugin);
        getServer().getPluginManager().registerEvents(breakBlockEventHandler, plugin);
        getServer().getPluginManager().registerEvents(playerJoinEventHandler, plugin);
        this.getCommand("treecap").setExecutor(new Commands(this));

    }
}
