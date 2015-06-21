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
public class basichomes implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', BasicHomes.instance.lang.getText("basichomes-cmd-title")));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', BasicHomes.instance.lang.getText("basichomes-cmd-authors").replace("%s", BasicHomes.instance.getDescription().getAuthors().toString().replace("[", "").replace("]", ""))));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', BasicHomes.instance.lang.getText("basichomes-cmd-resourselink").replace("%s", "http://www.spigotmc.org/resources/" + BasicHomes.instance.updateChecker.getResourceId() + "/")));
        if((sender.hasPermission("basichomes.admin") || !(sender instanceof Player)) && BasicHomes.instance.updateChecker != null && BasicHomes.instance.updateChecker.getUpdateResult().equals(UpdateChecker.UpdateResult.AVAILABLE)){
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', BasicHomes.instance.lang.getText("basichomes-cmd-version").replace("%s", String.valueOf(BasicHomes.instance.updateChecker.getVersion()))));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', BasicHomes.instance.lang.getText("basichomes-cmd-new-version").replace("%s", String.valueOf(BasicHomes.instance.updateChecker.getResourceVersion()))));
        }
        return false;
    }

}