package com.gmail.jlmerrett.SimpleTreeCap.EventHandlers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class PlayerJoinEventHandler implements Listener {

    private final Plugin plugin;
    private static final String TREECAP_META = "treecap_meta";

    public PlayerJoinEventHandler(Plugin plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent playerJoinEvent) {
        Player player = playerJoinEvent.getPlayer();
        List<MetadataValue> playerMetaData = player.getMetadata(TREECAP_META);
        if(playerMetaData.size() == 1) {
            if(playerMetaData.get(0).asBoolean()){
                player.sendMessage("SimpleTreeCap is enabled. \"/treecap disable\" to disable.");
            }
            else {
                player.sendMessage("SimpleTreeCap is disabled. \"/treecap enable\" to enable.");
            }
        }
        else{
            player.setMetadata(TREECAP_META, new FixedMetadataValue(plugin, "false"));
            player.sendMessage("SimpleTreeCap is disabled. \"/treecap enable\" to enable.");
        }
    }
}
