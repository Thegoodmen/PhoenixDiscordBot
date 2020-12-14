
package com.github.goody.phoenixbot.minecraftquery;

import com.github.goody.phoenixbot.Util;
import com.github.goody.phoenixbot.query.MCQuery;
import com.github.goody.phoenixbot.query.QueryResponse;
import java.util.ArrayList;

/**
 *
 * @author Maron Möller
 * 
 */

public class MinecraftServer {
    
        private final String host;
        private final int port;
        private String motd;
        private int playerOnline;
        private int maxPlayer;
        private boolean isOnline;
        private String onlinePlayers;
        private ArrayList<String> player;
        
    public MinecraftServer(String host, int port) {
        
        this.host = host;
        this.port = port;
        
    }
    
    public void connecttoServer(){
        
        Util.doDebug("Try to connect to Server Queryport " + host + ":" + port + "!");
        MCQuery mcQuery = new MCQuery(host, port);
        QueryResponse response = mcQuery.fullStat();
        playerOnline = response.getOnlinePlayers();
        player = response.getPlayerList();
        
        if(playerOnline > 0){
            
            for (int i = 0; i < playerOnline ; i++){
                int u = 0;
                u++;
                
            }
            
        }
        
        onlinePlayers = player.toString();
        motd = response.getMOTD();
        maxPlayer = response.getMaxPlayers();
        isOnline = response.getMapName() != null;
        
    }
    
    public String getMOTD(){
        
        return motd;
        
    }
    
    public Integer getOnlinePlayers(){
        
        return playerOnline;
        
    }
    
    public Integer getMaxPlayers(){
        
        return maxPlayer;
        
    }
    
    public Boolean getServerStatus(){
        
        return isOnline;
        
    }
    
    public String getPlayers(){
        
        return onlinePlayers;
        
    }
    
    public ArrayList<String> getPlayerList() {
        
        return player;
        
    }
    
}