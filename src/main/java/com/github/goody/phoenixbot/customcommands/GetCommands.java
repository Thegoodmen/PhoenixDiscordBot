
package com.github.goody.phoenixbot.customcommands;

import com.github.goody.phoenixbot.Util;
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

public class GetCommands {
    
    public static Commands unMarshalingCommands() throws JAXBException {
        
        JAXBContext jaxbContext = JAXBContext.newInstance(Commands.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
     
        String path = "./commands.xml";
        Commands emps = (Commands) jaxbUnmarshaller.unmarshal( new File(path) );

        return emps;
        
    }
    
    public static String[] getCommandTriggerList() {
        
        try {
            
            Commands emps = unMarshalingCommands();
            String[] list = new String[emps.getCount()];
            int i = 0;
            
            for(Command emp : emps.getCommands())
            {
                
                list[i] = emp.getCommandtrigger();
                i++;
                
            }
            
            return list;
            
        } catch (JAXBException ex) {
            
            Logger.getLogger(GetCommands.class.getName()).log(Level.SEVERE, null, ex);
            return null;
            
        }
        
    }
    
    public static String getResponse(String command) {
        
        String output = null;
        
        try {

            Commands emps = unMarshalingCommands();
            
            for(Command emp : emps.getCommands()) {
                
                if(emp.getCommandtrigger().equals(command)){
                    
                    output = emp.getCommandresponse();
                    Util.doDebug("Found Command!");
                    
                }
                
            }
                        
        } catch (JAXBException ex) {
            
            Logger.getLogger(GetCommands.class.getName()).log(Level.SEVERE, null, ex);
            return null;
            
        }
        
        return output;
        
    }
    
    public static Boolean isRoleRestricted(String command) {
    
        Boolean output = false;
        
        try {

            Commands emps = unMarshalingCommands();
            
            for(Command emp : emps.getCommands()) {
                
                if(emp.getCommandtrigger().equals(command)){
                    
                    output = emp.onlyAdmin();
                    Util.doDebug("Found Command!");
                    
                }
                
            }
                        
        } catch (JAXBException ex) {
            
            Logger.getLogger(GetCommands.class.getName()).log(Level.SEVERE, null, ex);
            return null;
            
        }
        
        return output;
        
    }
    
}
