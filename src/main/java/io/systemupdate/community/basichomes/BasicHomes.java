package io.systemupdate.community.basichomes;

import io.systemupdate.community.basichomes.commands.*;
import io.systemupdate.community.basichomes.listeners.PlayerJoinListener;
import io.systemupdate.community.basichomes.listeners.PlayerKickListener;
import io.systemupdate.community.basichomes.listeners.PlayerQuitListener;
import io.systemupdate.community.basichomes.utils.Lang;
import io.systemupdate.community.basichomes.utils.UpdateChecker;
import io.systemupdate.community.basichomes.utils.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by SystemUpdate (http://systemupdate.io) on 16/06/15.
 */
public class BasicHomes extends JavaPlugin {
    public PluginManager pm = Bukkit.getServer().getPluginManager();
    public static BasicHomes instance;
    public UpdateChecker updateChecker;
    public Lang lang;

    public HashMap<UUID, User> userProfiles = new HashMap<>();
    public HashMap<UUID, Long> cooldown = new HashMap<>();
    public List<UUID> teleporting = new ArrayList<>();
    public List<String> illegalCharacters = new ArrayList<>();

    @Override
    public void onEnable(){
        this.registerEvents();
    }

    @Override
    public void onDisable(){
        userProfiles.clear();
        updateChecker.endChecking();
    }

    private void registerEvents(){
        BasicHomes.instance = this;
        //this.saveDefaultConfig();
        updateChecker = new UpdateChecker();
        lang = new Lang();
        lang.initilize();
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new PlayerQuitListener(), this);
        pm.registerEvents(new PlayerKickListener(), this);
       /* if(true){//TODO Change to config boolean && /homeinfo command - creation data/creator/location (x/y/z) - Need to rework /homes, /home
            pm.registerEvents(new PlayerMoveListener(), this);
            pm.registerEvents(new PlayerTeleportListener(), this);
        }*/
        this.getCommand("basichomes").setExecutor(new basichomes());
        this.getCommand("homes").setExecutor(new homes());
        this.getCommand("home").setExecutor(new home());
        this.getCommand("sethome").setExecutor(new sethome());
        this.getCommand("delhome").setExecutor(new delhome());
        if(this.getConfig().getConfigurationSection("illegalCharacters") != null){
            for(String i : this.getConfig().getStringList("illegalCharacters")){
                illegalCharacters.add(i.toLowerCase());
            }
        }
        illegalCharacters.add(".");
        File folder = new File(getDataFolder(), "userdata");
        if(!folder.exists()){
            folder.mkdirs();
        }
        for(Player i : Bukkit.getServer().getOnlinePlayers()){
            userProfiles.put(i.getUniqueId(), new User(i.getUniqueId()));
        }
    }
}
