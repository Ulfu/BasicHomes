package io.systemupdate.community.basichomes.commands;

import io.systemupdate.community.basichomes.BasicHomes;
import io.systemupdate.community.basichomes.utils.User;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by SystemUpdate (http://systemupdate.io) on 16/06/15.
 */
public class delhome implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
        if(!(sender instanceof Player)){
            sender.sendMessage(BasicHomes.instance.lang.getText("console-sender"));
        }
        if(sender instanceof Player){
            if (args.length == 1){
                if(sender.hasPermission("simplehomes.delhome")){
                    String homeName = args[0];
                    Player player = (Player) sender;
                    if (homeName.contains(":") && sender.hasPermission("simplehomes.delhome.other")){
                        String user = homeName.split(":", 2)[0];
                        OfflinePlayer i = Bukkit.getServer().getOfflinePlayer(user);
                        if (i == null) {
                            sender.sendMessage(BasicHomes.instance.lang.getText("Player-Not-Found"));
                        } else {
                            User userProfile;
                            if(i.isOnline()){
                                userProfile = BasicHomes.instance.userProfiles.get(i.getUniqueId());
                            }else{
                                userProfile = new User(i.getUniqueId());
                            }
                            homeName = homeName.replaceFirst(user + ":", "");
                            if(userProfile.getHome(homeName) == null){
                                sender.sendMessage(BasicHomes.instance.lang.getText("home-dont-exist"));
                            }else{
                                userProfile.delHome(homeName);
                                sender.sendMessage(BasicHomes.instance.lang.getText("home-deleted"));
                            }
                        }
                    }else{
                        User user = BasicHomes.instance.userProfiles.get(player.getUniqueId());
                        if(user.getHome(homeName) == null){
                            sender.sendMessage(BasicHomes.instance.lang.getText("home-dont-exist"));
                        }else{
                            user.delHome(homeName);
                            sender.sendMessage(BasicHomes.instance.lang.getText("home-deleted"));
                        }
                    }
                }else{
                    sender.sendMessage(BasicHomes.instance.lang.getText("no-permission"));
                }
            }else{
                if(sender.hasPermission("simplehomes.delhome.other")){
                    sender.sendMessage(BasicHomes.instance.lang.getText("delhome-invalid-usage-admin"));
                }else{
                    sender.sendMessage(BasicHomes.instance.lang.getText("delhome-invalid-usage"));
                }
            }
        }
        return false;
    }
}