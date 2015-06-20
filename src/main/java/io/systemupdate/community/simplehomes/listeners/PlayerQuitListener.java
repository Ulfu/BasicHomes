package io.systemupdate.community.simplehomes.listeners;

import io.systemupdate.community.simplehomes.SimpleHomes;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by SystemUpdate (http://systemupdate.io) on 16/06/15.
 */
public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        SimpleHomes.instance.userProfiles.remove(event.getPlayer().getUniqueId());
        SimpleHomes.instance.teleporting.remove(event.getPlayer().getUniqueId());
    }
}
