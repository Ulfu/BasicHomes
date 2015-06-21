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
        User user = null;
        String homeName = "";
        boolean isPlayer = true;
        if(!(sender instanceof Player)){
            isPlayer = false;
        }
        if(args.length == 1 && (sender.hasPermission("basichomes.delhome") || !isPlayer)){
            if(args[0].contains(":") && (sender.hasPermission("basichomes.delhome.other") || !isPlayer)){
                String username = args[0].split(":", 2)[0];
                homeName = args[0].replaceFirst(username, "");
                OfflinePlayer i = Bukkit.getServer().getOfflinePlayer(username);
                if(i != null){
                    if(i.isOnline()){
                        user = BasicHomes.instance.userProfiles.get(i.getUniqueId());
                    }else{
                        user = new User(i.getUniqueId());
                    }
                }else{
                    sender.sendMessage(BasicHomes.instance.lang.getText("Player-Not-Found"));
                }
            }else{
                homeName = args[0];
                user = BasicHomes.instance.userProfiles.get(((Player)sender).getUniqueId());
            }
            if(user.getHome(homeName) != null){
                user.delHome(homeName);
                sender.sendMessage(BasicHomes.instance.lang.getText("home-deleted"));
            }else{
                sender.sendMessage(BasicHomes.instance.lang.getText("home-dont-exist"));
            }
        }else if(sender.hasPermission("basichomes.delhome.other") || !isPlayer){
            sender.sendMessage(BasicHomes.instance.lang.getText("delhome-invalid-usage-admin"));
        }else if(sender.hasPermission("basichomes.delhome")){
            sender.sendMessage(BasicHomes.instance.lang.getText("delhome-invalid-usage"));
        }else{
            sender.sendMessage(BasicHomes.instance.lang.getText("no-permission"));
        }
        return false;
    }
}
