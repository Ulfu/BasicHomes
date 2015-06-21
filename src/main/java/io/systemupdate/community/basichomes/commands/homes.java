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
 * Created by SystemUpdate (http://systemupdate.io) on 21/06/15.
 */
public class homes implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
        if(args.length != 1 && !(sender instanceof Player)){
            sender.sendMessage(BasicHomes.instance.lang.getText("console-sender"));
        }else{
            User user = null;
            if(args.length >= 1 && (sender.hasPermission("basichomes.homes.other"))){
                OfflinePlayer i = Bukkit.getServer().getOfflinePlayer(args[0]);
                if(i != null){
                    if(i.isOnline()){
                        user = BasicHomes.instance.userProfiles.get(i.getUniqueId());
                    }else{
                        user = new User(i.getUniqueId());
                        Bukkit.getServer().broadcastMessage("3");
                    }
                }
            }else if(sender.hasPermission("basichomes.homes")){
                user = BasicHomes.instance.userProfiles.get(((Player) sender).getUniqueId());
            }else{
                sender.sendMessage(BasicHomes.instance.lang.getText("no-permission"));
                return false;
            }
            int homes = user.getHomes().size();
            if(homes == 0){
                sender.sendMessage(BasicHomes.instance.lang.getText("homes-empty"));
            }else{
                String homesValue = BasicHomes.instance.lang.getText("homes-start");
                String comma = BasicHomes.instance.lang.getText("homes-comma");
                for(String i : user.getHomes()){
                    if(homes == 1){
                        homesValue = homesValue + i + BasicHomes.instance.lang.getText("homes-end");
                    }else{
                        homesValue = homesValue + i + comma + " ";
                    }
                    homes--;
                }
                sender.sendMessage(homesValue);
            }
        }
        return false;
    }
}
