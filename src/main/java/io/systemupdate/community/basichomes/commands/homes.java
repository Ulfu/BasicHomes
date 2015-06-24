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
        boolean isConsole = false;
        if(!(sender instanceof Player)){
            isConsole = true;
        }
        if(sender.hasPermission("basichomes.homes") || isConsole){
            User user = null;
            if(args.length >= 1){
                if(sender.hasPermission("basichomes.homes.other") || isConsole){
                    OfflinePlayer player = Bukkit.getServer().getOfflinePlayer(args[0]);
                    if(player != null){
                        if(player.isOnline()){
                            user = BasicHomes.instance.userProfiles.get(player.getUniqueId());
                        }else{
                            user = new User(player.getUniqueId());
                        }
                    }else{
                        sender.sendMessage(BasicHomes.instance.lang.getText("Player-Not-Found"));
                        return false;
                    }
                }else{
                    sender.sendMessage(BasicHomes.instance.lang.getText("no-permission"));
                    return false;
                }
            }else if(isConsole){
                sender.sendMessage(BasicHomes.instance.lang.getText("homes-invalid-usage-admin"));
                return false;
            }else{
                user = BasicHomes.instance.userProfiles.get(((Player)sender).getUniqueId());
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
        }else{
            Bukkit.getServer().broadcastMessage("1");
            sender.sendMessage(BasicHomes.instance.lang.getText("no-permission"));
        }
        return false;
    }
}
