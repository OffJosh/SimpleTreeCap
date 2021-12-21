package com.gmail.jlmerrett.SimpleTreeCap.EventHandlers;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

public class PlayerJoinEventHandler implements Listener {

    private final Plugin plugin;
    private static final String TREECAP_META = "treecap_meta";

    public PlayerJoinEventHandler(Plugin plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent playerJoinEvent) {
        Player player = playerJoinEvent.getPlayer();
        player.setMetadata(TREECAP_META, new FixedMetadataValue(plugin, "false"));
        player.sendMessage("SimpleTreeCap is disabled. \"/treecap enable\" to enable.");

    }
}
