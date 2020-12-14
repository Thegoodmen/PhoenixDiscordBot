
package com.github.goody.phoenixbot.customcommands;

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

@XmlRootElement(name = "commands")
@XmlAccessorType (XmlAccessType.FIELD)

public class Commands {
    
    @XmlElement(name = "command")
    
    private List<Command> commands = null;
 
    public List<Command> getCommands() {
        
        return commands;
        
    }
 
    public void setCommands(List<Command> commands) {
        
        this.commands = commands;
        
    }
    
    public int getCount() {
        
        return commands.size();
        
    }
    
}
