
package com.github.goody.heliossdiscordbot;

import com.github.goody.heliossdiscordbot.pterodactylwarpper.Servers;
import com.github.goody.heliossdiscordbot.pterodactylwarpper.Server;
import com.github.goody.heliossdiscordbot.minecraftquery.MinecraftServer;
import com.github.goody.heliossdiscordbot.pterodactylwarpper.GetData;
import com.github.goody.heliossdiscordbot.pterodactylwarpper.Serverstatus;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * 
 * @author Maron Möller
 * 
 */

public class GetServers {
    
    public static Servers unMarshalingServers() throws JAXBException {
        
        JAXBContext jaxbContext = JAXBContext.newInstance(Servers.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
     
        String path = "./servers.xml";
        Servers emps = (Servers) jaxbUnmarshaller.unmarshal( new File(path) );

        return emps;
    }
    
    public static Server[] getServerBYNick(String nick) {
        
        try {
            
            Server[] arr;
            Server p = new Server();
            Servers emps = unMarshalingServers();
            int i = 0;
            int j = 0;
            
            for(Server emp : emps.getServers())
            {
                
                if(emp.getNick().toLowerCase().equals(nick.toLowerCase())){
                    
                    i++;
                    
                }
                
            }
            
            arr = new Server[i];
            
            for(Server emp : emps.getServers())
            {
                
                if(emp.getNick().toLowerCase().equals(nick.toLowerCase())){
                    
                    arr[j] = emp;
                    j++;
                    
                }
                
            }
            
            return arr;
            
        } catch (JAXBException ex) {
            
            Logger.getLogger(GetServers.class.getName()).log(Level.SEVERE, null, ex);
            return null;
            
        }
        
    }
    
    public static Server[] getServerBYChannel(String channel) {
        
        try {
            
            Server[] arr;
            Server p = new Server();
            Servers emps = unMarshalingServers();
            int i = 0;
            int j = 0;
            
            for(Server emp : emps.getServers())
            {
                if(emp.getChannel().equals(channel)){
                    
                    i++;
                    
                }
            }
            
            arr = new Server[i];
            
            for(Server emp : emps.getServers())
            {
                
                if(emp.getChannel().equals(channel)){
                    
                    arr[j] = emp;
                    j++;
                    
                }
                
            }
            
            return arr;
            
        } catch (JAXBException ex) {
            
            Logger.getLogger(GetServers.class.getName()).log(Level.SEVERE, null, ex);
            return null;
            
        }
        
    }
    
    public static String[] getNickList() {   
        
        try {
            
            Servers emps = unMarshalingServers();
            String[] list = new String[emps.getCount()];
            int i = 0;
            
            for(Server emp : emps.getServers())
            {
                
                list[i] = emp.getNick();
                i++;
                
            }
            
            return list;
            
        } catch (JAXBException ex) {
            
            Logger.getLogger(GetServers.class.getName()).log(Level.SEVERE, null, ex);
            return null;
            
        }
    }
    
    public static String[] getNameList() {
        
        try {
            
            Servers emps = unMarshalingServers();
            String[] list = new String[emps.getCount()];
            int i = 0;
            
            for(Server emp : emps.getServers())
            {
                
                list[i] = emp.getName();
                i++;
                
            }
            
            return list;
            
        } catch (JAXBException ex) {
            
            Logger.getLogger(GetServers.class.getName()).log(Level.SEVERE, null, ex);
            return null;
            
        }
        
    }
    
    public static String[] getChannelList() {
        
        try {
            
            Servers emps = unMarshalingServers();
            String[] list = new String[emps.getCount()];
            int i = 0;
            
            for(Server emp : emps.getServers())
            {
                
                list[i] = emp.getChannel();
                i++;
                
            }
            
            return list;
            
        } catch (JAXBException ex) {
            
            Logger.getLogger(GetServers.class.getName()).log(Level.SEVERE, null, ex);
            return null;
            
        }
        
    }
    
    public static String[] getNetworkInfo() {
        
        Integer counton;
        Integer countmax;
        Integer count;
        count = 0;
        counton = 0;
        countmax = 0;
        String[] result = new String[2];
        
        try {
            
            Servers emps = unMarshalingServers();
            
            for(Server emp : emps.getServers())
            {

                try {
                    
                MinecraftServer qserver = new MinecraftServer(emp.getIP(),emp.getPort());
                qserver.connecttoServer();
                counton = counton + qserver.getOnlinePlayers();
                countmax = countmax + qserver.getMaxPlayers();
                Util.doDebug("Connected to " + emp.getName() + " (Node: " + emp.getRegion() + ")");
                
                count++;
                
                } catch (NumberFormatException ex) {
                
                    System.out.println("Error 10 - Connection to Queryport failed!");
                    
                } catch (Exception e) {
                    
                    System.out.println("Error 11 - Server not online or reachable!");
                    
                }
               
            }  
            
        } catch (JAXBException ex) {
            
            Logger.getLogger(GetServers.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        result[0] = counton.toString() + " / " + countmax.toString();
        result[1] = count.toString();
        
        return result;
        
    }
    
    public static Integer getSeverCount() {

        Integer count = 0;
        
        try {
            
            Servers emps = unMarshalingServers();
            
            count = emps.getServers().size();
            
        } catch (JAXBException ex) {
            
            Logger.getLogger(GetServers.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        return count;
        
    }
    
}
