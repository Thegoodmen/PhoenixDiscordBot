
package com.github.goody.phoenixbot.sqlquery;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maron Möller
 * 
 */

public class IOManager {
    
    Connection connection;
 
    public boolean connectToMysql(String host, String database, String user, String passwd){
    
        try{

            String connectionCommand = "jdbc:mysql://"+host+"/"+database+"?user="+user+"&password="+passwd;
            connection = DriverManager.getConnection(connectionCommand);
            return true;
 
        } catch (SQLException ex) {
            
            System.out.println(ex);
            return false;
            
        }
        
    }
    
    public ResultSet request(String statment) throws SQLException {
        
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(statment);
        return rs;
        
    }
    
    public void execute(String statment) throws SQLException {
        
        Statement st = connection.createStatement();
        st.executeUpdate(statment);
           
    }
    
    public void close() {
        
        try {
            
            connection.close();
        
        } catch (SQLException ex) {
            
            Logger.getLogger(IOManager.class.getName()).log(Level.SEVERE, null, ex);
        
        }
    
    }
    
}

