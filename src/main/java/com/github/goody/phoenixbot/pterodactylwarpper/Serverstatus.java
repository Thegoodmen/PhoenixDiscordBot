
package com.github.goody.phoenixbot.pterodactylwarpper;

import org.json.JSONObject;

/**
 *
 * @author Maron Möller
 * 
 */

public class Serverstatus {
    
    public String state;
    public Boolean bostate;
    public String currentmemory;
    public Integer limitmemory;
    public Integer currentcpu;
    public Integer limitcpu;
    public String currentdisk;
    public Integer limitdisk;
    
    public void ini(String statusjsonstring, String limitjsonstring) {
        
        
            JSONObject json = new JSONObject(statusjsonstring);
            JSONObject json2 = new JSONObject(limitjsonstring);
            JSONObject attributes;
            JSONObject attributes2;
            JSONObject resources;
            JSONObject limits;
            
            attributes = json.getJSONObject("attributes");
            
            state = attributes.getString("current_state");
            
            resources = attributes.getJSONObject("resources");
            
            Integer tempmemo = resources.getInt("memory_bytes");
            currentmemory = tempmemo.toString() ;
            currentcpu = resources.getInt("cpu_absolute");
            Integer tempdisk = resources.getInt("disk_bytes");
            currentdisk = tempdisk.toString();
            
            attributes2 = json2.getJSONObject("attributes");
            
            limits = attributes2.getJSONObject("limits");
            
            limitmemory = limits.getInt("memory");
            limitcpu = limits.getInt("cpu");
            limitdisk = limits.getInt("disk");
            bostate = state.equals("running");
        
    }
    
    public String getState() {
        
        return state;
        
    }
    
    public String getStateFormat() {
        
        String output;
        
        switch (state) {
            
            case "running":

                output = "**Online** :green_circle:";
                break;

            case "starting":

                output = "**Starting** :yellow_circle:";
                break;

            default:
                
                output = "**Offline** :red_circle:";
                break;

        }
        
        return output;
        
    }
    
    public String getCurrentRAM() {
        
        return currentmemory;
        
    }
    
    public Integer getLimitRAM() {
        
        return limitmemory;
        
    }
    
    public String getCurrentDisk() {
        
        return currentdisk;
        
    }
    
    public Integer getLimitDisk() {
        
        return limitdisk;
        
    }
    
}
