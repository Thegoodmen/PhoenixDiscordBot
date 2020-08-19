/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.goody.heliossdiscordbot;



import com.github.goody.heliossdiscordbot.discordinterface.MyListeners;
import com.github.goody.heliossdiscordbot.pterodactylwarpper.GetData;
import com.github.goody.heliossdiscordbot.GetServers;
import com.github.goody.heliossdiscordbot.pterodactylwarpper.Serverstatus;
import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;



/**
 *
 * @author Maron
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        GetConfig.getPropValues();
        String token = GetConfig.getToken();

        DiscordApi discord;
        discord = new DiscordApiBuilder().setToken(token).login().join();
        
        System.out.println("Done ! - Bot started");

        discord.addListener(new MyListeners());
                  
        BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));   
        String str;   
  
        do {   
            str = obj.readLine();   
         }   while(!str.equals("stop"));

        System.out.println("Stopping Bot!");
        
        discord.disconnect();
        
        
}




    }