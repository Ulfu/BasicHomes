package io.systemupdate.community.simplehomes.commands;

import io.systemupdate.community.simplehomes.SimpleHomes;
import io.systemupdate.community.simplehomes.utils.UpdateChecker;
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
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', SimpleHomes.instance.lang.getText("simplehomes-cmd-title")));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', SimpleHomes.instance.lang.getText("simplehomes-cmd-authors").replace("%s", SimpleHomes.instance.getDescription().getAuthors().toString())));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', SimpleHomes.instance.lang.getText("simplehomes-cmd-resourselink").replace("%s", "http://www.spigotmc.org/resources/" + SimpleHomes.instance.updateChecker.getResourceId() + "/")));
        if((sender.hasPermission("simplehomes.admin") || !(sender instanceof Player)) && SimpleHomes.instance.updateChecker != null && SimpleHomes.instance.updateChecker.getUpdateResult().equals(UpdateChecker.UpdateResult.AVAILABLE)){
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', SimpleHomes.instance.lang.getText("simplehomes-cmd-version").replace("%s", String.valueOf(SimpleHomes.instance.updateChecker.getVersion()))));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', SimpleHomes.instance.lang.getText("simplehomes-cmd-new-version").replace("%s", String.valueOf(SimpleHomes.instance.updateChecker.getResourceVersion()))));
        }
        return false;
    }

}