
package com.github.goody.heliossdiscordbot.pterodactylwarpper;

import com.github.goody.heliossdiscordbot.Main;
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
    
    public void ini(String url, String key) {
        
        this.panelurl = url;
        this.panelkey = key;
        
    }
    
    public void setKey(String key) {
        this.panelkey = key;
    }
    
    public void setURL(String url) {
        this.panelurl = url;
    }
    
    public String getStatusString(String server) throws MalformedURLException, IOException {
        
        String line = null;
        URL myURL = new URL(panelurl + "/api/client/servers/" + server + "/utilization");
        HttpURLConnection myURLConnection = (HttpURLConnection)myURL.openConnection();

        myURLConnection.setRequestProperty ("Authorization", "Bearer " + panelkey);
        myURLConnection.setRequestProperty("Content-Type", "application/json");
        myURLConnection.setRequestProperty("Accept", "application/vnd.pterodactyl.v1+json");
        myURLConnection.setUseCaches(false);
        myURLConnection.setDoInput(true);
        myURLConnection.setDoOutput(true);
        
        if (myURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            System.out.println("Error 11 :" + myURLConnection.getResponseMessage());
        }
        else {
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
            Serverstatus p = new Serverstatus();
            p.ini(Statustring);
            return p.getState();
        } catch (IOException ex) {
            Logger.getLogger(GetData.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public String toFormat(String input) {
        if (input.equals("on")) {
            String output = "Online";
            return output;
        } else {
            String output = "Offline";
            return output;
        }
    }
    
    public Serverstatus getServer(String server) {
        try {
            String Statustring = this.getStatusString(server);
            Serverstatus p = new Serverstatus();
            p.ini(Statustring);
            return p;
        } catch (IOException ex) {
            Logger.getLogger(GetData.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
