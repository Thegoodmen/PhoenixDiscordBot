
package com.github.goody.phoenixbot.pterodactylwarpper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Maron Möller
 * 
 */

@XmlRootElement(name = "server")
@XmlAccessorType (XmlAccessType.FIELD)

public class Server {
    
    public String id;
    public String name;
    public String nickname;
    public String IP;
    public Integer port;
    public String region;
    public String version;
    public String DCChannel;
    public String numIP;
    
   public String getID(){
       
       return id;
       
   }
   
   public String getName(){
       
       return name;
       
   }
   
   public String getNick() {
       
       return nickname;
       
   }
   
   public String getIP() {
       
       return IP;
       
   }
   
   public Integer getPort() {
       
       return port;
       
   }
   
   public String getRegion() {
       
       return region;
       
   }
   
   public String getVersion() {
       
       return version;
       
   }
   
   public String getChannel() {
       
       return DCChannel;
       
   }
   
   public String getNumIP() {
       
       return numIP;
       
   }
   
}
