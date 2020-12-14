package com.github.goody.phoenixbot.query;

import java.util.ArrayList;

/**
 *
 * @author Maron Möller
 * 
 */

public class QueryResponse {
    
    static byte NULL = 00;
    static byte SPACE = 20;
	
    private final boolean fullstat;
    private final String motd;
    private final String gameMode;
    private final String mapName;
    private final int onlinePlayers;
    private final int maxPlayers;
    private final short port;
    private final String hostname;
    private String gameID;
    private String version;
    private String plugins;
    private ArrayList<String> playerList;
	
    public QueryResponse(byte[] data, boolean full) {

        this.fullstat = full;
		
        data = ByteUtils.trim(data);
        byte[][] temp = ByteUtils.split(data);
		
        if(!fullstat) {
                    
            motd = new String(ByteUtils.subarray(temp[0], 1, temp[0].length-1));
            gameMode = new String(temp[1]);
            mapName = new String(temp[2]);
            onlinePlayers = Integer.parseInt(new String(temp[3]));
            maxPlayers = Integer.parseInt(new String(temp[4]));
            port = ByteUtils.bytesToShort(temp[5]);
            hostname = new String(ByteUtils.subarray(temp[5], 2, temp[5].length-1));
		
        } else {
                 
            motd = new String(temp[3]);
            gameMode = new String(temp[5]);
            mapName = new String(temp[13]);
            onlinePlayers = Integer.parseInt(new String(temp[15]));
            maxPlayers = Integer.parseInt(new String(temp[17]));
            port = Short.parseShort(new String(temp[19]));
            hostname = new String(temp[21]);
            gameID = new String(temp[7]);
            version = new String(temp[9]);
            plugins = new String(temp[11]);
            playerList = new ArrayList<>();
		
            for(int i=25; i<temp.length; i++) {
                    
                playerList.add(new String(temp[i]));
                    
            }
                
        }
            
    }

    public String getMOTD() {
            
        return motd;
                
    }
	
    public String getGameMode() {
            
        return gameMode;
                
    }

    public String getMapName() {
            
        return mapName;
                
    }

    public int getOnlinePlayers() {
            
        return onlinePlayers;
                
    }

    public int getMaxPlayers() {
            
        return maxPlayers;
                
    }

    public ArrayList<String> getPlayerList() {
            
        return playerList;
                
    }
        
    public String getHostName() {
            
        return hostname;
            
    }
        
    public short getPort() {
            
        return port;
            
    }
        
    public String getGameID() {
            
        return gameID;
            
    }
        
    public String getVersion() {
            
        return version;
            
    }
        
    public String getPlugins() {
            
        return plugins;
            
    }

}