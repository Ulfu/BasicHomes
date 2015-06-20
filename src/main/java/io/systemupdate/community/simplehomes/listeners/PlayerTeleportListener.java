package io.systemupdate.community.simplehomes.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 * Created by SystemUpdate (http://systemupdate.io) on 16/06/15.
 */
public class PlayerTeleportListener implements Listener{

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event){
        //TODO - Cancel if in teleportation, send message if online
    }
}
