package io.systemupdate.community.basichomes.utils;

import io.systemupdate.community.basichomes.BasicHomes;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;

/**
 * Created by SystemUpdate (http://systemupdate.io) on 16/06/15.
 */
public class Lang{
    private File file;
    private YamlConfiguration config;
    private HashMap<String, String> messages = new HashMap<String, String>();

    public Lang(){

    }

    public void initilize(){
        file = new File(BasicHomes.instance.getDataFolder(), "lang.yml");
        if(!file.exists()){
            BasicHomes.instance.saveResource("lang.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(file);
        for(String i : config.getConfigurationSection("").getKeys(false)){
            messages.put(i, ChatColor.translateAlternateColorCodes('&', config.getString(i)));
        }
    }

    public String getText(String name){
        return messages.get(name);
    }
}
