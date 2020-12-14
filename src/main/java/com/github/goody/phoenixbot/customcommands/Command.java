
package com.github.goody.phoenixbot.customcommands;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Maron Möller
 * 
 */

@XmlRootElement(name = "command")
@XmlAccessorType (XmlAccessType.FIELD)

public class Command {
    
    public String commandtrigger;
    public String commandresponse;
    public String onlyadmins;
    
    public String getCommandtrigger() {
        
        return commandtrigger;
        
    }
    
    public String getCommandresponse() {
        
        return commandresponse;
        
    }
    
    public Boolean onlyAdmin() {
        
        return Boolean.parseBoolean(onlyadmins);
        
    }
    
}
