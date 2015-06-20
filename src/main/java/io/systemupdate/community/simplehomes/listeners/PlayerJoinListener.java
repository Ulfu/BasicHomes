package io.systemupdate.community.simplehomes.listeners;

import io.systemupdate.community.simplehomes.SimpleHomes;
import io.systemupdate.community.simplehomes.utils.UpdateChecker;
import io.systemupdate.community.simplehomes.utils.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by SystemUpdate (http://systemupdate.io) on 16/06/15.
 */
public class PlayerJoinListener implements Listener{

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        SimpleHomes.instance.userProfiles.put(event.getPlayer().getUniqueId(), new User(event.getPlayer().getUniqueId()));
        if(event.getPlayer().hasPermission("simplehomes.admin") && SimpleHomes.instance.updateChecker != null && SimpleHomes.instance.updateChecker.getUpdateResult().equals(UpdateChecker.UpdateResult.AVAILABLE)){
            event.getPlayer().sendMessage(SimpleHomes.instance.lang.getText("update-available"));
        }
    }
}
