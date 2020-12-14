
package com.github.goody.phoenixbot.pterodactylwarpper;

import com.github.goody.phoenixbot.GetConfig;
import com.github.goody.phoenixbot.Util;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maron Möller
 * 
 */

public class PowerFunctions {
    
    public static void startServer(String id) throws MalformedURLException, IOException {
        
        String panelurl = GetConfig.getURL();
        String panelkey = GetConfig.getKey();
        String cmd = "{ \"signal\": \"start\" }";
        
        URL myURL = new URL(panelurl + "/api/client/servers/" + id + "/power");
        HttpURLConnection myURLConnection = (HttpURLConnection)myURL.openConnection();
        
        myURLConnection.setRequestMethod("POST");
        myURLConnection.setRequestProperty ("Authorization", "Bearer " + panelkey);
        myURLConnection.setRequestProperty("Content-Type", "application/json");
        myURLConnection.setRequestProperty("Accept", "application/vnd.pterodactyl.v1+json");
        myURLConnection.setUseCaches(false);
        myURLConnection.setDoOutput(true);
        

        try (OutputStream os = myURLConnection.getOutputStream()) {
            
            os.write(cmd.getBytes());
            os.flush();
                
            if (myURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                
                Util.doDebug("Warning 46: " + myURLConnection.getResponseMessage());
                
            }   
        
        } catch (IOException ex) {
            
            Util.doDebug("POST failed !");
            
        }
        
    }
    
    public static void killServer(String id) throws MalformedURLException, IOException {
        
        String panelurl = GetConfig.getURL();
        String panelkey = GetConfig.getKey();
        String cmd = "{ \"signal\": \"kill\" }";
        
        URL myURL = new URL(panelurl + "/api/client/servers/" + id + "/power");
        HttpURLConnection myURLConnection = (HttpURLConnection)myURL.openConnection();
        
        myURLConnection.setRequestMethod("POST");
        myURLConnection.setRequestProperty ("Authorization", "Bearer " + panelkey);
        myURLConnection.setRequestProperty("Content-Type", "application/json");
        myURLConnection.setRequestProperty("Accept", "application/vnd.pterodactyl.v1+json");
        myURLConnection.setUseCaches(false);
        myURLConnection.setDoOutput(true);
        

        try (OutputStream os = myURLConnection.getOutputStream()) {
            
            os.write(cmd.getBytes());
            os.flush();
            
            if (myURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                
                Util.doDebug("Warning 45: " + myURLConnection.getResponseMessage());
                
            }  
                       
        } catch (IOException ex) {
            
            Util.doDebug("POST failed !");
            
        }
        
    }
    
    public static void startstallServer(String id) {
        
        String panelurl = GetConfig.getURL();
        String panelkey = GetConfig.getKey();
        
        CompletableFuture.runAsync(() -> {
            
            GetData q = new GetData(panelurl, panelkey);
            Serverstatus p = q.getServer(id);
            String status = p.getState();
            
            if ("stopping".equals(status)) {
                               
                try {
                    
                    TimeUnit.SECONDS.sleep(5);
                    
                } catch (InterruptedException ex) {
                    
                    Logger.getLogger(PowerFunctions.class.getName()).log(Level.SEVERE, null, ex);
                    
                }
                
                Serverstatus n = q.getServer(id);
                String status2 = n.getState();
                
                if ("stopping".equals(status2)) {
                    
                    try {
                        
                        killServer(id);
                                                
                    } catch (IOException ex) {
                        
                        Logger.getLogger(PowerFunctions.class.getName()).log(Level.SEVERE, null, ex);
                        
                    }
                    
                    try {
                    
                        TimeUnit.SECONDS.sleep(5);
                    
                    } catch (InterruptedException ex) {
                    
                        Logger.getLogger(PowerFunctions.class.getName()).log(Level.SEVERE, null, ex);
                    
                    }
                    
                    try {
                        
                        startServer(id);
                        
                    } catch (IOException ex) {
                        
                        Logger.getLogger(PowerFunctions.class.getName()).log(Level.SEVERE, null, ex);
                        
                    }
                    
                    Util.doDebug("Server started!");

                }
                
            } else if ("offline".equals(status)) {
                
                try {
                    
                    startServer(id);
                    
                } catch (IOException ex) {
                    
                    Logger.getLogger(PowerFunctions.class.getName()).log(Level.SEVERE, null, ex);
                    
                }
                
            }
            
        });   
                
    }
    
}
