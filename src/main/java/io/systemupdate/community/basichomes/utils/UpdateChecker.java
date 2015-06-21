package io.systemupdate.community.basichomes.utils;

import io.systemupdate.community.basichomes.BasicHomes;
import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by SystemUpdate (http://systemupdate.io) on 17/06/15.
 */
public class UpdateChecker {

    private UpdateResult updateResult;

    private int schedulerId = 0;
    private int resourceId = 8450;

    private double version;
    private double resourceVersion = 0;

    private String apiKey = "98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4";

    public UpdateChecker(){
        version = Double.valueOf(BasicHomes.instance.getDescription().getVersion().split("-")[0]);
        schedulerId = Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(BasicHomes.instance, new Runnable(){

            @Override
            public void run(){
                checkForUpdate();
            }

        }, 0L, (20L * 60) * 120).getTaskId();
    }

    private void checkForUpdate(){
        if(version != resourceVersion){
            //I do not take credit for the following code, credit goes to Kyle/Maximvdw
            try{
                HttpURLConnection connection = (HttpURLConnection) new URL("http://www.spigotmc.org/api/general.php").openConnection();
                connection.setDoOutput(true);
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
                connection.setRequestMethod("POST");
                connection.getOutputStream().write(("key=" + apiKey + "&resource=" + resourceId).getBytes("UTF-8"));
                resourceVersion = Double.valueOf(new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine());
                if(resourceVersion != version){
                    updateResult = UpdateResult.AVAILABLE;
                    BasicHomes.instance.getLogger().warning("[Update] New update available - Current Version is " + version + " and new update version is " + resourceVersion);
                    BasicHomes.instance.getLogger().warning("[Update] You can download the update from http://www.spigotmc.org/resources/" + resourceId + "/");
                }else{
                    updateResult = UpdateResult.UNAVAILABLE;
                }
            }catch(IOException e){
                updateResult = UpdateResult.UNAVAILABLE;
            }
        }
    }

    public void endChecking(){
        Bukkit.getServer().getScheduler().cancelTask(schedulerId);
    }

    public enum UpdateResult{
        AVAILABLE,
        UNAVAILABLE
    }

    public UpdateResult getUpdateResult(){
        return updateResult;
    }

    public int getResourceId(){
        return resourceId;
    }

    public double getVersion(){
        return version;
    }

    public double getResourceVersion(){
        return resourceVersion;
    }
}
