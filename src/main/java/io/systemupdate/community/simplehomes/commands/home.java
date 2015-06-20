package io.systemupdate.community.simplehomes.commands;

import io.systemupdate.community.simplehomes.SimpleHomes;
import io.systemupdate.community.simplehomes.utils.User;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by SystemUpdate (http://systemupdate.io) on 16/06/15.
 */
public class home implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
        if(!(sender instanceof Player)){
            sender.sendMessage(SimpleHomes.instance.lang.getText("console-sender"));
        }
        if(sender instanceof Player){
            if(args.length == 1){
                if(sender.hasPermission("simplehomes.home")){
                    String homeName = args[0];
                    Player player = (Player)sender;
                    if(homeName.contains(":") && sender.hasPermission("simplehomes.home.other")){
                        String user = homeName.split(":", 2)[0];
                        OfflinePlayer i = Bukkit.getServer().getOfflinePlayer(user);
                        if(i == null){
                            sender.sendMessage(SimpleHomes.instance.lang.getText("Player-Not-Found"));
                        }else{
                            User userProfile;
                            if (i.isOnline()) {
                                userProfile = SimpleHomes.instance.userProfiles.get(i.getUniqueId());
                            } else {
                                userProfile = new User(i.getUniqueId());
                            }
                            homeName = homeName.replaceFirst(user + ":", "");
                            if(userProfile.getHome(homeName) != null){
                                player.teleport(userProfile.getHome(homeName));
                                sender.sendMessage(SimpleHomes.instance.lang.getText("teleported-home"));
                            }else{
                                sender.sendMessage(SimpleHomes.instance.lang.getText("home-dont-exist"));
                            }
                        }
                    }else{
                        User user = SimpleHomes.instance.userProfiles.get(player.getUniqueId());
                        if(user.getHome(homeName) == null){
                            sender.sendMessage(SimpleHomes.instance.lang.getText("home-dont-exist"));
                        }else{
                            //TODO Cooldown + Permission to evade + countdown + permission to evade
                            player.teleport(user.getHome(homeName));
                            sender.sendMessage(SimpleHomes.instance.lang.getText("teleported-home"));
                        }
                    }
                }else{
                    sender.sendMessage(SimpleHomes.instance.lang.getText("no-permission"));
                }
            }else{
                if(sender.hasPermission("simplehomes.home.other")){
                    sender.sendMessage(SimpleHomes.instance.lang.getText("home-invalid-usage-admin"));
                }else{
                    sender.sendMessage(SimpleHomes.instance.lang.getText("home-invalid-usage"));
                }
            }
        }
        return false;
    }
}
