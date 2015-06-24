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
public class sethome implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
        if(!(sender instanceof Player)){
            sender.sendMessage(BasicHomes.instance.lang.getText("console-sender"));
        }
        if(sender instanceof Player){
            if(args.length == 1){
                String homeName = args[0];
                User user = null;
                Player player = (Player)sender;
                if(homeName.contains(":") && sender.hasPermission("basichomes.sethome.other")){
                    String username = args[0].split(":", 2)[0];
                    homeName = args[0].replaceFirst(username + ":", "");
                    OfflinePlayer i = Bukkit.getServer().getOfflinePlayer(username);
                    if(i != null){
                        if(i.isOnline()){
                            user = BasicHomes.instance.userProfiles.get(i.getUniqueId());
                        }else{
                            user = new User(i.getUniqueId());
                        }
                    }else{
                        sender.sendMessage(BasicHomes.instance.lang.getText("Player-Not-Found"));
                        return false;
                    }
                }else{
                    user = BasicHomes.instance.userProfiles.get(player.getUniqueId());
                }
                if(user.getHome(homeName) != null){
                    sender.sendMessage(BasicHomes.instance.lang.getText("sethome-home-exists"));
                    return false;
                }
                if(user.getHomes().size() >= user.getMaxHomes() && !player.hasPermission("basichomes.sethome.other")){
                    sender.sendMessage(BasicHomes.instance.lang.getText("max-homes-reached"));
                    return false;
                }
                for(String i : homeName.split("home-contains-illegal-character")){
                    if(BasicHomes.instance.illegalCharacters.contains(i.toLowerCase())){
                        sender.sendMessage(BasicHomes.instance.lang.getText("home-contains-illegal-character"));
                        return false;
                    }
                }
                user.addHome(homeName, player.getLocation());
                sender.sendMessage(BasicHomes.instance.lang.getText("sethome-successful"));
            }else if(sender.hasPermission("basichomes.sethome.other")){
                sender.sendMessage(BasicHomes.instance.lang.getText("sethome-invalid-usage-admin"));
            }else if(sender.hasPermission("basichomes.sethome")){
                sender.sendMessage(BasicHomes.instance.lang.getText("sethome-invalid-usage"));
            }else{
                sender.sendMessage(BasicHomes.instance.lang.getText("no-permission"));
            }
        }
        return false;
    }
}
