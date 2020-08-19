
package com.github.goody.heliossdiscordbot.pterodactylwarpper;

import com.stanjg.ptero4j.PteroAdminAPI;
import com.stanjg.ptero4j.PteroUserAPI;
import com.stanjg.ptero4j.entities.panel.admin.Server;
import com.stanjg.ptero4j.entities.panel.user.UserServer;

/**
 *
 * @author Maron Möller
 * 
 */

public class PanelMain {
    
    public static PteroUserAPI panel;
    
    public static void intPanel(String panelurl, String panelkey) {
        panel = new PteroUserAPI(panelurl, panelkey);
        
        UserServer server = panel.getServersController().getServer("0e768091");
        server.sendCommand("Helioss Discord Bot is connected!");
    }
    
    public static void sendPanelCmd(String serverid, String cmd) {
        
        UserServer server = panel.getServersController().getServer(serverid);
        server.sendCommand(cmd);
        
    }
    
}
