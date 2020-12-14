
package com.github.goody.phoenixbot;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Maron Möller
 * 
 */

public class GetConfig {

    public static String bottoken;
    public static String panelurl;
    public static String panelkey;
    public static String serverlist;
    public static String prefix;
    public static String serverstatstring;
    public static String standcha;
    public static String admincha;
    public static Boolean debug;
    public static String networkstatstring;
    public static String appsheet;
    public static String appchaid;
    public static Boolean appmodule;
    public static String approleid;
    public static String memroleid;
    public static String roleids;
    public static String adminroleid;
    public static String netname;
    public static String logchaid;
    public static String serverid;
    public static Boolean lblmodule;
    public static String catmemid;
    public static String catdonid;
    public static String dongoal;
    public static String welmessage;
    public static Boolean lblmembercount;
    public static Boolean lbldongoal;
    public static Boolean logapp;
    public static Boolean wlmodule;
    public static String wladress;
    public static String wltablename;
    public static String wlusername;
    public static String wlpassword;
    public static Boolean ccmodule;
     
    public static void getPropValues() {
 
	try {
            
            Properties prop = new Properties();
            String propFileName = "config.properties";
 
            FileInputStream fis = new FileInputStream("./" + propFileName);
 
            try {
                
		prop.load(fis);
                
            } catch (FileNotFoundException eg) {
                
		System.out.println("Exception: " + eg);
                Util.doDebug("Error 22 - Properties File not found");
                
            } finally {
                
                fis.close();
                
            }
            
            bottoken = prop.getProperty("tocken");
            panelurl = prop.getProperty("url");
            panelkey = prop.getProperty("key");
            serverlist = prop.getProperty("server");
            prefix = prop.getProperty("prefix");
            serverstatstring = prop.getProperty("serverstat");
            standcha = prop.getProperty("standcha");
            admincha = prop.getProperty("admincha");
            debug = Boolean.parseBoolean(prop.getProperty("debug"));
            networkstatstring = prop.getProperty("networkstat");
            appmodule = Boolean.parseBoolean(prop.getProperty("appmodule"));
            appsheet = prop.getProperty("appsheet");
            appchaid = prop.getProperty("appchaid");
            approleid = prop.getProperty("approleid");
            memroleid = prop.getProperty("memroleid");
            roleids = prop.getProperty("roleids");
            adminroleid = prop.getProperty("adminrole");
            netname = prop.getProperty("netname");
            logchaid = prop.getProperty("logchaid");
            serverid = prop.getProperty("serverid");
            lblmodule = Boolean.parseBoolean(prop.getProperty("lblmodule"));
            catmemid = prop.getProperty("catmemid");
            catdonid = prop.getProperty("catdonid");
            dongoal = prop.getProperty("dongoal");
            welmessage = prop.getProperty("welmessage");
            lblmembercount = Boolean.parseBoolean(prop.getProperty("lblmembercount"));
            lbldongoal = Boolean.parseBoolean(prop.getProperty("lbldongoal"));
            logapp = Boolean.parseBoolean(prop.getProperty("logapp"));
            wlmodule = Boolean.parseBoolean(prop.getProperty("wlmodule"));
            wladress = prop.getProperty("wladress");
            wltablename = prop.getProperty("wltablename");
            wlusername = prop.getProperty("wlusername");
            wlpassword = prop.getProperty("wlpassword");
            ccmodule = Boolean.parseBoolean(prop.getProperty("ccmodule"));
            
	} catch (IOException e) {
            
            System.out.println("Exception: " + e);
            
	} 
        
    }
    
    public static  String getKey() {
        
        return panelkey;
        
    }
    
    public static  String getURL() {
        
        return panelurl;
        
    }
    
    public static  String getToken() {
        
        return bottoken;
        
    }
    
    public static  String getServer() {
        
        return serverlist;
        
    }
    
    public static  String getPrefix() {
        
        return prefix;
        
    }
    
    public static  String[] getCmdServer() {
        
        String[] a = serverstatstring.split(";");
        return a;
        
    }
    
    public static Boolean getDebugStatus() {
        
        return debug;
        
    }
    
    public static void setDebugStatus(Boolean state) {
        
        debug = state;
        
    }
    
    public static String[] getCmdNetwork() {
        
        String[] a = networkstatstring.split(";");
        return a;
        
    }
    
    public static String[] getChannelList() {
        
        String[] a = standcha.split(";");
        return a;
        
    }
    
    public static String[] getAdminChannelList() {
        
        String[] a = admincha.split(";");
        return a;
        
    }
    
    public static Boolean getAppModuleStatus() {
        
        return appmodule;
        
    }
    
    public static String getAppSheetURL() {
        
        return appsheet;
        
    }
    
    public static String getApplicationLogChannelID() {
        
        return appchaid;
        
    }
    
    public static String getMemberRoleID() {
        
        return memroleid;
        
    }
    
    public static String getApplicantRoleID() {
        
        return approleid;
        
    }
    
    public static String getRoleIDs() {
        
        return roleids;
        
    }
    
    public static String getAdminRoleID() {
        
        return adminroleid;
        
    }
    
    public static String getNetworkName() {
        
        return netname;
        
    }
    
    public static String getGeneralLogChannel() {
        
        return logchaid;
        
    }
    
    public static String getServerID() {
        
        return serverid;
        
    }
    
    public static Boolean getLableModuleStatus() {
        
        return lblmodule;
        
    }
    
    public static String getCategorieMemberID() {
        
        return catmemid;
        
    }
    
    public static String getCategorieDonationID() {
        
        return catdonid;
        
    }
    
    public static String getDonationGoal() {
        
        return dongoal;
        
    }
    
    public static String getWelcomeMessage() {
        
        return welmessage;
        
    }
    
    public static Boolean getLableMemberCountActiv() {
        
        return lblmembercount;
    
    }
    
    public static Boolean getLableDonationGoalActiv() {
        
        return lbldongoal;
        
    }
    
    public static Boolean getAppLogActic() {
        
        return logapp;
        
    }
    
    public static Boolean getWhitelistModuleStatus() {
        
        return wlmodule;
        
    }
    
    public static String getWhitelistAdress() {
        
        return wladress;
        
    }
    
    public static String getWhitelistTableName() {
        
        return wltablename;
        
    }
    
    public static String getWhitelistUsername() {
        
        return wlusername;
        
    }
    
    public static String getWhitelistPassword() {
        
        return wlpassword;
        
    }
    
    public static Boolean getCustomCommandModuleStatus() {
        
        return ccmodule;
        
    }

}
