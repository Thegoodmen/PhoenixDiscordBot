
package com.github.goody.phoenixbot.pterodactylwarpper;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Maron Möller
 * 
 */

@XmlRootElement(name = "servers")
@XmlAccessorType (XmlAccessType.FIELD)

public class Servers {
    
    @XmlElement(name = "server")
    
    private List<Server> servers = null;
 
    public List<Server> getServers() {
        
        return servers;
        
    }
 
    public void setServers(List<Server> servers) {
        
        this.servers = servers;
        
    }
    
    public int getCount() {
        
        return servers.size();
        
    }
    
}
