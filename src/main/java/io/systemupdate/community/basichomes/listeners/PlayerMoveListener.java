package io.systemupdate.community.basichomes.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Created by SystemUpdate (http://systemupdate.io) on 16/06/15.
 */
public class PlayerMoveListener implements Listener{

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        //TODO - Cancel if in teleportation, send message if online
    }
}
