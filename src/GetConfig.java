
package com.github.goody.heliossdiscordbot;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    public static Boolean debug;
    public static String networkstatstring;
 
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
            debug = Boolean.parseBoolean(prop.getProperty("debug"));
            networkstatstring = prop.getProperty("networkstat");
 
	} catch (Exception e) {
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
    
    public static String[] getCmdNetwork() {
        String[] a = networkstatstring.split(";");
        return a;
    }
    
    public static String[] getChannelList() {
        String[] a = standcha.split(";");
        return a;
    }
}
