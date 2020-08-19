
package com.github.goody.heliossdiscordbot.pterodactylwarpper;

import org.json.JSONObject;

/**
 *
 * @author Maron Möller
 * 
 */

public class Serverstatus {
    
    public String state;
    public Boolean bostate;
    public Integer currentmemory;
    public Integer limitmemory;
    public Integer currentcpu;
    public Integer limitcpu;
    public Integer currentdisk;
    public Integer limitdisk;
    
    public void ini(String jsonstring) {
        
        
            JSONObject json = new JSONObject(jsonstring);
            JSONObject attributes;
            JSONObject memory;
            JSONObject cpu;
            JSONObject disk;
            
            attributes = json.getJSONObject("attributes");
            
            state = attributes.getString("state");
            
            memory = attributes.getJSONObject("memory");
            
            currentmemory = memory.getInt("current");
            limitmemory = memory.getInt("limit");
            
            cpu = attributes.getJSONObject("cpu");
            
            currentcpu = cpu.getInt("current");
            limitcpu = cpu.getInt("limit");
            
            disk = attributes.getJSONObject("disk");
            
            currentdisk = disk.getInt("current");
            limitdisk = disk.getInt("limit");
            
            if( state == "on") {
                bostate = true;
            } else {
                bostate = false;
            }
        
    }
    
    public String getState() {
        return state;
    }
    
    public String getStateFormat() {
        if (state.equals("on")) {
            String output = "**Online** :green_circle:";
            return output;
        } else {
            String output = "**Offline** :red_circle:";
            return output;
        }
    }
    
    public Integer getCurrentRAM() {
        return currentmemory;
    }
    
    public Integer getLimitRAM() {
        return limitmemory;
    }
    
    public Integer getCurrentDisk() {
        return currentdisk;
    }
    
    public Integer getLimitDisk() {
        return limitdisk;
    }
    
    
    
}
