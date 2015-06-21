package io.systemupdate.community.basichomes.utils;

import io.systemupdate.community.basichomes.BasicHomes;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

/**
 * Created by SystemUpdate (http://systemupdate.io) on 16/06/15.
 */
public class User{
    private UUID playerUUID;
    private HashMap<String, Location> homes = new HashMap<>();
    private File userFile;
    private YamlConfiguration userConfig;
    private int maxHomes = 0;

    public User(UUID uuid){
        this.playerUUID = uuid;
        userFile = new File(BasicHomes.instance.getDataFolder() + File.separator + "userdata" + File.separator + playerUUID.toString() + ".yml");
        if(!userFile.exists()){
            try{
                userFile.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        userConfig = YamlConfiguration.loadConfiguration(userFile);
        for(String i : userConfig.getKeys(false)){
            homes.put(i, new Location(
                    Bukkit.getServer().getWorld(userConfig.getString(i + ".World")),
                    userConfig.getDouble("Home." + i + ".X"),
                    userConfig.getDouble("Home." + i + ".Y"),
                    userConfig.getDouble("Home." + i + ".Z"),
                    userConfig.getLong("Home." + i + ".Yaw"),
                    userConfig.getLong("Home." + i + ".Pitch")));
        }
        Player player = Bukkit.getServer().getPlayer(uuid);
        if(player != null){
            for(PermissionAttachmentInfo i : player.getEffectivePermissions()){
                if(i.getPermission().startsWith("simplehomes.max.")){
                    int value = Integer.valueOf(i.getPermission().replace("simplehomes.max.", ""));
                    if(value > maxHomes){
                        maxHomes = value;
                    }
                }
            }
        }
    }

    public void addHome(String name, Location location){
        homes.put(name, location);
        userConfig.set("Home." + name + ".World", location.getWorld().getName());
        userConfig.set("Home." + name + ".X", location.getX());
        userConfig.set("Home." + name + ".Y", location.getY());
        userConfig.set("Home." + name + ".Z", location.getZ());
        userConfig.set("Home." + name + ".Yaw", location.getYaw());
        userConfig.set("Home." + name + ".Pitch", location.getPitch());
        try{
            userConfig.save(userFile);
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public void delHome(String name){
        homes.remove(name);
        userConfig.set("Home." + name, null);
        try{
            userConfig.save(userFile);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public Location getHome(String name){
        return homes.get(name);
    }

    public Set<String> getHomes(){
        return homes.keySet();
    }

    public int getMaxHomes(){
        return maxHomes;
    }
}