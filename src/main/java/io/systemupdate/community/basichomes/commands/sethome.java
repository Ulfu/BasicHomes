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
                if(sender.hasPermission("basichomes.sethome")){
                    String homeName = args[0];
                    Player player = (Player)sender;
                    if(homeName.contains(":") && sender.hasPermission("basichomes.sethome.other")){
                        String user = homeName.split(":", 2)[0];
                        OfflinePlayer i = Bukkit.getServer().getOfflinePlayer(user);
                        if(i == null){
                            sender.sendMessage(BasicHomes.instance.lang.getText("Player-Not-Found"));
                        }else{
                            User userProfile;
                            if(i.isOnline()){
                                userProfile = BasicHomes.instance.userProfiles.get(i.getUniqueId());
                            }else{
                                userProfile = new User(i.getUniqueId());
                            }
                            homeName = homeName.replaceFirst(user + ":", "");
                            for(String i2: homeName.split("home-contains-illegal-character")){
                                if(BasicHomes.instance.illegalCharacters.contains(i2.toLowerCase())){
                                    sender.sendMessage(BasicHomes.instance.lang.getText("sethome-successful"));
                                    return false;
                                }
                            }
                            if(userProfile.getHome(homeName) == null){
                                userProfile.addHome(homeName, player.getLocation());
                                sender.sendMessage(BasicHomes.instance.lang.getText("sethome-successful"));
                            }else{
                                sender.sendMessage(BasicHomes.instance.lang.getText("sethome-home-exists"));
                            }
                        }
                    }else{
                        User user = BasicHomes.instance.userProfiles.get(player.getUniqueId());
                        if(user.getHome(homeName) == null){
                            if(user.getHomes().size() >= user.getMaxHomes()){
                                sender.sendMessage(BasicHomes.instance.lang.getText("max-homes-reached"));
                            }else{
                                for(String i : homeName.split("home-contains-illegal-character")){
                                    if(BasicHomes.instance.illegalCharacters.contains(i.toLowerCase())){
                                        sender.sendMessage(BasicHomes.instance.lang.getText("home-contains-illegal-character"));
                                        return false;
                                    }
                                }
                                user.addHome(homeName, player.getLocation());
                                sender.sendMessage(BasicHomes.instance.lang.getText("sethome-successful"));
                            }
                        }else{
                            sender.sendMessage(BasicHomes.instance.lang.getText("sethome-home-exists"));
                        }
                    }
                }else{
                    sender.sendMessage(BasicHomes.instance.lang.getText("no-permission"));
                }
            }else{
                if(sender.hasPermission("simplehomes.sethome.other")){
                    sender.sendMessage(BasicHomes.instance.lang.getText("sethome-invalid-usage-admin"));
                }else{
                    sender.sendMessage(BasicHomes.instance.lang.getText("sethome-invalid-usage"));
                }
            }
        }
        return false;
    }
}
