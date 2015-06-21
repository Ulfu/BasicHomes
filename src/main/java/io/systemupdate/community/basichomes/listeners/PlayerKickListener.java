package io.systemupdate.community.basichomes.listeners;

import io.systemupdate.community.basichomes.BasicHomes;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

/**
 * Created by SystemUpdate (http://systemupdate.io) on 16/06/15.
 */
public class PlayerKickListener implements Listener{

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event){
        BasicHomes.instance.userProfiles.remove(event.getPlayer().getUniqueId());
        BasicHomes.instance.teleporting.remove(event.getPlayer().getUniqueId());
    }
}
