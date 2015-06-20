package io.systemupdate.community.simplehomes.listeners;

import io.systemupdate.community.simplehomes.SimpleHomes;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

/**
 * Created by SystemUpdate (http://systemupdate.io) on 16/06/15.
 */
public class PlayerKickListener implements Listener{

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event){
        SimpleHomes.instance.userProfiles.remove(event.getPlayer().getUniqueId());
        SimpleHomes.instance.teleporting.remove(event.getPlayer().getUniqueId());
    }
}
