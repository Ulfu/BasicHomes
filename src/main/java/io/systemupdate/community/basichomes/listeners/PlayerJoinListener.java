package io.systemupdate.community.basichomes.listeners;

import io.systemupdate.community.basichomes.BasicHomes;
import io.systemupdate.community.basichomes.utils.UpdateChecker;
import io.systemupdate.community.basichomes.utils.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by SystemUpdate (http://systemupdate.io) on 16/06/15.
 */
public class PlayerJoinListener implements Listener{

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        BasicHomes.instance.userProfiles.put(event.getPlayer().getUniqueId(), new User(event.getPlayer().getUniqueId()));
        if(event.getPlayer().hasPermission("simplehomes.admin") && BasicHomes.instance.updateChecker != null && BasicHomes.instance.updateChecker.getUpdateResult().equals(UpdateChecker.UpdateResult.AVAILABLE)){
            event.getPlayer().sendMessage(BasicHomes.instance.lang.getText("update-available"));
        }
    }
}
