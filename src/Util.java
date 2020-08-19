
package com.github.goody.heliossdiscordbot;

import com.github.goody.heliossdiscordbot.pterodactylwarpper.Server;
import com.github.goody.heliossdiscordbot.minecraftquery.MinecraftServer;
import com.github.goody.heliossdiscordbot.pterodactylwarpper.GetData;
import com.github.goody.heliossdiscordbot.pterodactylwarpper.Serverstatus;
import java.awt.Color;
import java.math.BigDecimal;
import java.util.ArrayList;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

/**
 *
 * @author Maron Möller
 * 
 */

public class Util {
    
    public static boolean isPrefix(char pre) {

        char possiblepre = GetConfig.getPrefix().charAt(0);
        
        return possiblepre == pre;  
        
    }
    
    public static boolean isServerCmd(String message) {
        
        Boolean r = false;
        
        for (String cmdServer : GetConfig.getCmdServer()) {
           String tempa = cmdServer.toLowerCase();
           String tempb = message.toLowerCase();
           
           if (tempa.equals(tempb)) {
               
               r = true;
           
           } 
        }  
        
        return r;
        
    }
    
    public static boolean isNetworkCmd(String message) {
        
        Boolean r = false;
        
        for (String cmdNetwork : GetConfig.getCmdNetwork()) {
           String tempa = cmdNetwork.toLowerCase();
           String tempb = message.toLowerCase();
           
           if (tempa.equals(tempb)) {
               
               r = true;
           
           } 
        }  
        
        return r;
        
    }
    
    public static boolean isServerNick(String message) {
        
        Boolean r = false;
        
        for (String cmdServer : GetServers.getNickList()) {
            String tempa = cmdServer.toLowerCase();
            String tempb = message.toLowerCase();
            
            if (tempa.equals(tempb)) {
                
               r = true;
               
           } 
        }
        
        return r;
        
    }
    
    public static boolean isServerChannel(String channelid) {
        
        Util.doDebug(" Debug: Is Channel ID in XML ?");
        Util.doDebug(channelid);
        
        Boolean r = false;
               
        for (String cmdServer : GetServers.getChannelList()) {
            
            Util.doDebug("Debug: Compare to " + cmdServer);
            
            if (channelid.equals(cmdServer)) {
                
               r = true;
               
               Util.doDebug("Yes");
               
           }
            
        }
        
        return r;
        
    }
    
    public static boolean isStandartChannel(String channelid) {
        
        Boolean r = false;
        
        for (String channel : GetConfig.getChannelList()) {
                      
           if (channel.equals(channelid)) {
               
               r = true;
           
           } 
        }  
        
        return r;
        
    }
    
    public static String formatInt(Integer i) {
        
        long integral = i;
        int x = 3;
        BigDecimal unscaled = new BigDecimal(integral);
        BigDecimal scaled = unscaled.scaleByPowerOfTen(-x);
        return scaled.toString();
        
    }
    
    public static void BuildServerstatusNick(MessageCreateEvent event, String nick) {
             
        String panelurl = GetConfig.getURL();
        String panelkey = GetConfig.getKey();
        String player = " ";
        String maxplayer = " ";
        
        GetData q = new GetData();
        q.ini(panelurl, panelkey);
        
        Server[] p = GetServers.getServerBYNick(nick);
        
        for(int i = 0; i < p.length; i++) {
        Serverstatus s = q.getServer(p[i].getID());
        
        if("on".equals(s.getState())){
            try {
                MinecraftServer qserver = new MinecraftServer(p[i].getIP(),p[i].getPort());
                qserver.connecttoServer();
                player = qserver.getOnlinePlayers().toString();
                maxplayer = qserver.getMaxPlayers().toString();
            } catch (NumberFormatException ex) {
                System.out.println("Error 10 - Connection to Queryport failed!");
            }
        }
        
        MessageBuilder n = new MessageBuilder();
        
        n.setEmbed(new EmbedBuilder()
            .setTitle(p[i].getName() + " Server (Node " + p[i].getRegion() + ")")
            .setDescription("Serverstatus: " + s.getStateFormat() + "\n Version: **" + p[i].getVersion() + "** \n IP: **" + p[i].getIP() +"** \n Player: **" + player + " / " + maxplayer + "**")
            .setColor(Color.BLUE)
            .addField("RAM", formatInt(s.getCurrentRAM()) + " GB of " + formatInt(s.getLimitRAM()) + " GB")
            .addField("Diskspace", formatInt(s.getCurrentDisk()) + " GB of " + formatInt(s.getLimitDisk()) + " GB")
            .setTimestampToNow()
            .setFooter("© Helioss Discordbot by Goody")
        );
        
        n.send(event.getChannel());
        
        player = " ";
        maxplayer = " ";
        
        }
        
    }
    
    public static void BuildServerstatusChannel(MessageCreateEvent event, String channel) {
             
        String panelurl = GetConfig.getURL();
        String panelkey = GetConfig.getKey();
        String player = " ";
        String maxplayer = " ";
        
        GetData q = new GetData();
        q.ini(panelurl, panelkey);
        
        Server[] p = GetServers.getServerBYChannel(channel);
        
        for(int i = 0; i < p.length; i++) {
            
            Serverstatus s = q.getServer(p[i].getID());
        
            if("on".equals(s.getState())){
            
                try {
                
                    MinecraftServer qserver = new MinecraftServer(p[i].getIP(),p[i].getPort());
                    qserver.connecttoServer();
                    player = qserver.getOnlinePlayers().toString();
                    maxplayer = qserver.getMaxPlayers().toString();
                
                } catch (NumberFormatException ex) {
                
                    System.out.println("Error 10 - Connection to Queryport failed!");
                
                }   
            
            }
        
            MessageBuilder n = new MessageBuilder();
        
            n.setEmbed(new EmbedBuilder()
            .setTitle(p[i].getName() + " Server (Node " + p[i].getRegion() + ")")
            .setDescription("Serverstatus: " + s.getStateFormat() + "\n Version: **" + p[i].getVersion() + "** \n IP: **" + p[i].getIP() +"** \n Player: **" + player + " / " + maxplayer + "**")
            .setColor(Color.BLUE)
            .addField("RAM", formatInt(s.getCurrentRAM()) + " GB of " + formatInt(s.getLimitRAM()) + " GB")
            .addField("Diskspace", formatInt(s.getCurrentDisk()) + " GB of " + formatInt(s.getLimitDisk()) + " GB")
            .setTimestampToNow()
            .setFooter("© Helioss Discordbot by Goody")
            );
        
            n.send(event.getChannel());
        
            player = " ";
            maxplayer = " ";
        
        }
        
    }
    
    public static void doDebug(String debug) {
               
        if(GetConfig.getDebugStatus()) {
            
            System.out.println(debug);
            
        }
        
    }
    
    public static void BuildNetworkstatus(MessageCreateEvent event, String channel) {
             
        MessageBuilder n = new MessageBuilder();
        
        String[] info = GetServers.getNetworkInfo();
        
        n.setEmbed(new EmbedBuilder()
            .setTitle("Helioss Network")
            .setDescription("Networkstatus: **Online** :green_circle: \n Players: **" + info[0] + "** \n Servers: **" + info[1] + " of " + GetServers.getSeverCount() + " Servers are online!**")
            .setColor(Color.BLUE)
            .setTimestampToNow()
            .setFooter("© Helioss Discordbot by Goody")
            );
        n.send(event.getChannel());
              
    }
    
    public static void BuildPlayerList(MessageCreateEvent event, String channel) {
        
        String panelurl = GetConfig.getURL();
        String panelkey = GetConfig.getKey();
        String player = " ";
        String noun = " ";
        String verb = " ";
        ArrayList<String> playerlist = null;

        GetData q = new GetData();
        q.ini(panelurl, panelkey);
        
        Server[] p = GetServers.getServerBYChannel(channel);
 
        for(int i = 0; i < p.length; i++) {
            
            Serverstatus s = q.getServer(p[i].getID());
        
            if("on".equals(s.getState())){
            
                try {
                    
                    MinecraftServer qserver = new MinecraftServer(p[i].getIP(),p[i].getPort());
                    qserver.connecttoServer();
                    player = qserver.getOnlinePlayers().toString();
                    playerlist = qserver.getPlayerList();
                
                } catch (NumberFormatException ex) {
                
                    System.out.println("Error 10 - Connection to Queryport failed!");
                
                }   
            
            }
            
            if (player.contentEquals("1")) {
                
                noun = "is";
                verb = "player";
                
            } else {
                
                noun = "are";
                verb = "players";
                
            }
            
            
            MessageBuilder n = new MessageBuilder();
            
            EmbedBuilder m = new EmbedBuilder();
            
            String mess;
            String temp = "";
            
            m.setTitle(p[i].getName() + " Server (Node " + p[i].getRegion() + ")"); 
            
            if (!player.contentEquals("0")) {
                
            temp = "```" + playerlist.toString().substring(1, playerlist.toString().length() - 1) + "```";

            }
            
            mess = "There " + noun + " " + player + " " + verb + " online:" + temp;
            
            m.setDescription(mess);        
            m.setColor(Color.BLUE);
            m.setTimestampToNow();
            m.setFooter("© Helioss Discordbot by Goody");
            
            n.setEmbed(m);
            
            n.send(event.getChannel());
            
        }
        
    }
    
    public static void BuildPlayerListByNick(MessageCreateEvent event, String nick) {
        
        String panelurl = GetConfig.getURL();
        String panelkey = GetConfig.getKey();
        String player = " ";
        String noun = " ";
        String verb = " ";
        ArrayList<String> playerlist = null;

        GetData q = new GetData();
        q.ini(panelurl, panelkey);
        
        Server[] p = GetServers.getServerBYNick(nick);
 
        for(int i = 0; i < p.length; i++) {
            
            Serverstatus s = q.getServer(p[i].getID());
        
            if("on".equals(s.getState())){
            
                try {
                    
                    MinecraftServer qserver = new MinecraftServer(p[i].getIP(),p[i].getPort());
                    qserver.connecttoServer();
                    player = qserver.getOnlinePlayers().toString();
                    playerlist = qserver.getPlayerList();
                
                } catch (NumberFormatException ex) {
                
                    System.out.println("Error 10 - Connection to Queryport failed!");
                
                }   
            
            }
            
            if (player.contentEquals("1")) {
                
                noun = "is";
                verb = "player";
                
            } else {
                
                noun = "are";
                verb = "players";
                
            }
            
            
            MessageBuilder n = new MessageBuilder();
            
            EmbedBuilder m = new EmbedBuilder();
            
            String mess;
            String temp = "";
            
            m.setTitle(p[i].getName() + " Server (Node " + p[i].getRegion() + ")"); 
            
            if (!player.contentEquals("0")) {
                
            temp = "```" + playerlist.toString().substring(1, playerlist.toString().length() - 1) + "```";

            }
            
            mess = "There " + noun + " " + player + " " + verb + " online:" + temp;
            
            m.setDescription(mess);        
            m.setColor(Color.BLUE);
            m.setTimestampToNow();
            m.setFooter("© Helioss Discordbot by Goody");
            
            n.setEmbed(m);
            
            n.send(event.getChannel());
            
        }
        
    }
    
    public static void BuildServerIPByChannel(MessageCreateEvent event, String channel) {
                
        Server[] p = GetServers.getServerBYChannel(channel);
        
        for(int i = 0; i < p.length; i++) {
                           
            MessageBuilder n = new MessageBuilder();
            
            String message = "``` IP: " + p[i].getIP() + "\n Nummerical IP: " + p[i].getNumIP() + ":" + p[i].getPort() + "```";
        
            n.setEmbed(new EmbedBuilder()
            .setTitle("ServerIP of " + p[i].getName() + " (Node " + p[i].getRegion() + ")")
            .setDescription(message)
            .setColor(Color.BLUE)
            .setTimestampToNow()
            .setFooter("© Helioss Discordbot by Goody")
            );
        
            n.send(event.getChannel());
        
        }
        
    }
    
    public static void BuildServerIPByNick(MessageCreateEvent event, String nick) {
                
        Server[] p = GetServers.getServerBYNick(nick);
        
        for(int i = 0; i < p.length; i++) {
                           
            MessageBuilder n = new MessageBuilder();
            
            String message = "``` IP: " + p[i].getIP() + "\n Nummerical IP: " + p[i].getNumIP() + ":" + p[i].getPort() + "```";
        
            n.setEmbed(new EmbedBuilder()
            .setTitle("ServerIP of " + p[i].getName() + " (Node " + p[i].getRegion() + ")")
            .setDescription(message)
            .setColor(Color.BLUE)
            .setTimestampToNow()
            .setFooter("© Helioss Discordbot by Goody")
            );
        
            n.send(event.getChannel());
        
        }
        
    }
    
    
    
}
