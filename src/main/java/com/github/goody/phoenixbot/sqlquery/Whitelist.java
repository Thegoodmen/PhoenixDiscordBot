
package com.github.goody.phoenixbot.sqlquery;

import com.github.goody.phoenixbot.GetConfig;
import com.github.goody.phoenixbot.Util;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maron Möller
 * 
 */

public class Whitelist {
    
    private static final String WHITELISTADRESS = GetConfig.getWhitelistAdress();
    private static final String WHITELISTTABLENAME = GetConfig.getWhitelistTableName();
    private static final String WHITELISTUSERNAME = GetConfig.getWhitelistUsername();
    private static final String WHITELISTPASSWORD = GetConfig.getWhitelistPassword();
    
    public static Boolean isWhitelisted(String usr) {
        
        IOManager q = new IOManager();
        Boolean result = false;
        
        if(q.connectToMysql(WHITELISTADRESS, WHITELISTTABLENAME, WHITELISTUSERNAME, WHITELISTPASSWORD)) {
            
            try {
            
                ResultSet rs = q.request("SELECT * FROM whitelist");
                
                
                while (rs.next()) {
                
                    if(usr.equals(rs.getString(1))) {
                        
                        result = true;
                        
                    }
                                
                }
                
                q.close();
            
            } catch (SQLException ex) {
                
                Logger.getLogger(Whitelist.class.getName()).log(Level.SEVERE, null, ex);
            
            }
            
        } else {
            
            Util.doDebug("Error 64: SQL Connection Faild");
        }
        
        return result;
        
    }
    
    public static void addWhitelist(String usr) {
        
        IOManager q = new IOManager();
        
        if(q.connectToMysql(WHITELISTADRESS, WHITELISTTABLENAME, WHITELISTUSERNAME, WHITELISTPASSWORD)) {
            
            try {
                
                q.execute("INSERT INTO whitelist VALUES ('" + usr + "', true)");
                q.close();
                
            } catch (SQLException ex) {
                
                Logger.getLogger(Whitelist.class.getName()).log(Level.SEVERE, null, ex);
                
            }
            
        } else {
            
            Util.doDebug("Error 64: SQL Connection Faild");
        }
   
    }
    
    public static void removeWhitelist(String usr) {
        
        IOManager q = new IOManager();
        
        if(q.connectToMysql(WHITELISTADRESS, WHITELISTTABLENAME, WHITELISTUSERNAME, WHITELISTPASSWORD)) {
            
            try {
                
                q.execute("DELETE FROM whitelist WHERE user='" + usr + "'");
                q.close();
                
            } catch (SQLException ex) {
                
                Logger.getLogger(Whitelist.class.getName()).log(Level.SEVERE, null, ex);
                
            }
            
        } else {
            
            Util.doDebug("Error 64: SQL Connection Faild");
            
        }
 
    }
    
}
