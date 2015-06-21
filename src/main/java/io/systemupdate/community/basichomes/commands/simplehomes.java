package io.systemupdate.community.basichomes.commands;

import io.systemupdate.community.basichomes.BasicHomes;
import io.systemupdate.community.basichomes.utils.UpdateChecker;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by SystemUpdate (http://systemupdate.io) on 16/06/15.
 */
public class simplehomes implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', BasicHomes.instance.lang.getText("simplehomes-cmd-title")));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', BasicHomes.instance.lang.getText("simplehomes-cmd-authors").replace("%s", BasicHomes.instance.getDescription().getAuthors().toString())));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', BasicHomes.instance.lang.getText("simplehomes-cmd-resourselink").replace("%s", "http://www.spigotmc.org/resources/" + BasicHomes.instance.updateChecker.getResourceId() + "/")));
        if((sender.hasPermission("simplehomes.admin") || !(sender instanceof Player)) && BasicHomes.instance.updateChecker != null && BasicHomes.instance.updateChecker.getUpdateResult().equals(UpdateChecker.UpdateResult.AVAILABLE)){
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', BasicHomes.instance.lang.getText("simplehomes-cmd-version").replace("%s", String.valueOf(BasicHomes.instance.updateChecker.getVersion()))));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', BasicHomes.instance.lang.getText("simplehomes-cmd-new-version").replace("%s", String.valueOf(BasicHomes.instance.updateChecker.getResourceVersion()))));
        }
        return false;
    }

}