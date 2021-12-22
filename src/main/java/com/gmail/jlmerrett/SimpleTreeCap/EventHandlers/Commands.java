package com.gmail.jlmerrett.SimpleTreeCap.EventHandlers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

public class Commands implements Listener, CommandExecutor, TabCompleter {

    private final Plugin plugin;

    public Commands(Plugin plugin){
        this.plugin = plugin;
    }

    private static final String TREECAP_META = "treecap_meta";

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (command.getName().equalsIgnoreCase("treecap")){
            if(commandSender instanceof Player){
                if (args.length != 1) {
                    commandSender.sendMessage("Incorrect number of arguments!");
                    return false;
                }
                if (args[0].equalsIgnoreCase("enable")){
                    ((Player) commandSender).setMetadata(TREECAP_META, new FixedMetadataValue(plugin, "true"));

                    commandSender.sendMessage("SimpleTreeCap is enabled.");
                    return true;
                }
                if (args[0].equalsIgnoreCase("disable")) {
                    ((Player) commandSender).setMetadata(TREECAP_META, new FixedMetadataValue(plugin, "false"));
                    commandSender.sendMessage("SimpleTreeCap is disabled.");
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return Arrays.asList("enable", "disable");
    }
}
