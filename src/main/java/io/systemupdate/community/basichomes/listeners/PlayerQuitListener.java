package io.systemupdate.community.basichomes.listeners;

import io.systemupdate.community.basichomes.BasicHomes;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by SystemUpdate (http://systemupdate.io) on 16/06/15.
 */
public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        BasicHomes.instance.userProfiles.remove(event.getPlayer().getUniqueId());
        BasicHomes.instance.teleporting.remove(event.getPlayer().getUniqueId());
    }
}
