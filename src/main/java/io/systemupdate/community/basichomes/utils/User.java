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

    public User(final UUID uuid){
        this.playerUUID = uuid;
        Bukkit.getServer().getScheduler().runTaskAsynchronously(BasicHomes.instance, new Runnable() {
            @Override
            public void run() {
                userFile = new File(BasicHomes.instance.getDataFolder() + File.separator + "userdata" + File.separator + playerUUID.toString() + ".yml");
                if(!userFile.exists()){
                    try{
                        userFile.createNewFile();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
                userConfig = YamlConfiguration.loadConfiguration(userFile);
                if(userConfig.getConfigurationSection("Home") != null){
                    for(String i : userConfig.getConfigurationSection("Home").getKeys(false)){
                        homes.put(i, new Location(
                                Bukkit.getServer().getWorld(userConfig.getString("Home." + i + ".World")),
                                userConfig.getDouble("Home." + i + ".X"),
                                userConfig.getDouble("Home." + i + ".Y"),
                                userConfig.getDouble("Home." + i + ".Z"),
                                userConfig.getLong("Home." + i + ".Yaw"),
                                userConfig.getLong("Home." + i + ".Pitch")));
                    }
                }
            }
        });
        Player player = Bukkit.getServer().getPlayer(uuid);
        if(player != null){
            for(PermissionAttachmentInfo i : player.getEffectivePermissions()){
                if(i.getPermission().equalsIgnoreCase("basichomes.max.unlimited")){
                    maxHomes = Integer.MAX_VALUE;
                    break;
                }
                if(i.getPermission().startsWith("basichomes.max.")){
                    int value = Integer.valueOf(i.getPermission().replace("basichomes.max.", ""));
                    if(value > maxHomes){
                        maxHomes = value;
                    }
                }
            }
        }
    }

    public void addHome(final String name, final Location location){
        Bukkit.getServer().getScheduler().runTaskAsynchronously(BasicHomes.instance, new Runnable() {
            @Override
            public void run() {
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
        });
    }

    public void delHome(final String name){
        Bukkit.getServer().getScheduler().runTaskAsynchronously(BasicHomes.instance, new Runnable() {
            @Override
            public void run() {
                for(String i : homes.keySet()){
                    if(i.equalsIgnoreCase(name)){
                        homes.remove(i);
                        userConfig.set("Home." + i, null);
                        break;
                    }
                }
                try{
                    userConfig.save(userFile);
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public Location getHome(String name){
        for(String i : homes.keySet()){
            if(i.equalsIgnoreCase(name)){
                return homes.get(i);
            }
        }
        return null;
    }

    public Set<String> getHomes(){
        return homes.keySet();
    }

    public int getMaxHomes(){
        return maxHomes;
    }
}