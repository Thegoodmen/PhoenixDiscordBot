
package com.github.goody.phoenixbot;

import com.github.goody.phoenixbot.discordinterface.MyReactionListeners;
import com.github.goody.phoenixbot.discordinterface.MyListeners;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.util.logging.FallbackLoggerConfiguration;

/**
 *
 * @author Maron Möller
 * 
 */

public class Main {
    
    public static DiscordApi discord;

    public static void main(String[] args) throws IOException {
        
        FallbackLoggerConfiguration.setDebug(false);
        FallbackLoggerConfiguration.setTrace(false);
        
        GetConfig.getPropValues();
        String token = GetConfig.getToken();
        
        Botstate.setStateIgnOffline(false);

        discord = new DiscordApiBuilder().setToken(token).login().join();

        discord.addListener(new MyListeners());
        discord.addReactionAddListener(new MyReactionListeners());
        
        if(GetConfig.getAppModuleStatus()) {
        
            Applicationcheck.ini(discord);
        
        }
        
        if(GetConfig.getLableModuleStatus()) {
            
            LabelModul.ini(discord);
            
        } 
        
        System.out.println("Done ! - Bot started");
        
        BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));   
        String str;  

        do {   
            
            str = obj.readLine();
            
            if(str.equals("reload")) {
                
                GetConfig.getPropValues();
                System.out.println("Configs Reloaded!");
                
            }
            
            if(str.equals("debug")) {
                
                if (GetConfig.getDebugStatus()) {
                    
                    GetConfig.setDebugStatus(Boolean.FALSE);
                    System.out.println("Debug Mode disabled!");
                    
                } else {
                    
                    GetConfig.setDebugStatus(Boolean.TRUE);
                    System.out.println("Debug Mode enabled!");
                    
                }
                
            }
            
         }   while(!str.equals("stop"));

        System.out.println("Stopping Bot!");
                
        if(GetConfig.getAppModuleStatus()) {
            
            Applicationcheck.stop();
            
        }
        
        if(GetConfig.getLableModuleStatus()) {
            
            LabelModul.stop();
            
        } 
        
        discord.disconnect();
               
    }

}