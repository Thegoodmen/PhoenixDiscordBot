
package com.github.goody.phoenixbot.pterodactylwarpper;

import com.github.goody.phoenixbot.Main;
import com.github.goody.phoenixbot.Util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maron Möller
 * 
 */

public class GetData {
    
    String panelurl;
    String panelkey;
    
    public GetData(String url, String key) {
        
        this.panelurl = url;
        this.panelkey = key;
        
    }
    
    public String getStatusString(String server) throws MalformedURLException, IOException {
        
        String line = null;
        URL myURL = new URL(panelurl + "/api/client/servers/" + server + "/resources");
        HttpURLConnection myURLConnection = (HttpURLConnection)myURL.openConnection();

        myURLConnection.setRequestProperty("Authorization", "Bearer " + panelkey);
        myURLConnection.setRequestProperty("Content-Type", "application/json");
        myURLConnection.setRequestProperty("Accept", "Application/vnd.pterodactyl.v1+json");
        myURLConnection.setUseCaches(false);
        myURLConnection.setDoInput(true);
        myURLConnection.setDoOutput(true);
        
        if (myURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            
            System.out.println("Error 44 : " + myURLConnection.getResponseMessage());
        
        } else {
            
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream(), "UTF-8"))) {
                
                line = reader.readLine();   
                
            } catch (IOException ex) {
                
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                
            }
            
        }
        
        return line;
    }
    
    public String getLimitString(String server) throws MalformedURLException, IOException{
    
        String line = null;
        URL myURL = new URL(panelurl + "/api/client/servers/" + server);
        HttpURLConnection myURLConnection = (HttpURLConnection)myURL.openConnection();

        myURLConnection.setRequestProperty("Authorization", "Bearer " + panelkey);
        myURLConnection.setRequestProperty("Content-Type", "application/json");
        myURLConnection.setRequestProperty("Accept", "Application/vnd.pterodactyl.v1+json");
        myURLConnection.setUseCaches(false);
        myURLConnection.setDoInput(true);
        myURLConnection.setDoOutput(true);
        
        if (myURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            
            System.out.println("Error 44 : " + myURLConnection.getResponseMessage());
        
        } else {
    
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream(), "UTF-8"))) {
                
                line = reader.readLine();
                
            } catch (IOException ex) {
                
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                
            }
            
        }
        
        return line;
        
    }
    
    public String getServerStatus(String server) {
        
        try {
            
            String Statustring = this.getStatusString(server);
            String Limitstring = this.getLimitString(server);
            Serverstatus p = new Serverstatus();
            p.ini(Statustring, Limitstring);
            return p.getState();
            
        } catch (IOException ex) {
            
            Logger.getLogger(GetData.class.getName()).log(Level.SEVERE, null, ex);
            return null;
            
        }
        
    }
       
    public Serverstatus getServer(String server) {
        
        try {
            
            String Statustring = this.getStatusString(server);
            String Limitstring = this.getLimitString(server);
            Serverstatus p = new Serverstatus();
            p.ini(Statustring, Limitstring);
            return p;
            
        } catch (IOException ex) {
            
            Util.doDebug("Error 70: " + ex);
            Logger.getLogger(GetData.class.getName()).log(Level.SEVERE, null, ex);
            return null;
            
        }
        
    }
    
}
