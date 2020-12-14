
package com.github.goody.phoenixbot;

import com.github.goody.phoenixbot.customcommands.GetCommands;
import com.github.goody.phoenixbot.pterodactylwarpper.Server;
import com.github.goody.phoenixbot.minecraftquery.MinecraftServer;
import com.github.goody.phoenixbot.pterodactylwarpper.GetData;
import com.github.goody.phoenixbot.pterodactylwarpper.Serverstatus;
import java.awt.Color;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import static java.util.stream.Collectors.toList;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import javax.xml.ws.Holder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.permission.Role;

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
    
    public static String getUserID(DiscordApi api, String DiscordTag) {
        
        String[] s = DiscordTag.split("#");
        User usr = api.getCachedUserByNameAndDiscriminatorIgnoreCase(s[0],s[1]).get();
        String result = usr.getIdAsString();
        return result;
        
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
        
        for (String channel : GetConfig.getAdminChannelList()) {
                      
           if (channel.equals(channelid)) {
               
               r = true;
           
           }
           
        }
        
        return r;
        
    }
    
    public static boolean isAdminChannel(String channelid) {
        
        Boolean r = false;
        
        for (String channel : GetConfig.getAdminChannelList()) {
                      
           if (channel.equals(channelid)) {
               
               r = true;
           
           }
           
        }  
        
        return r;
        
    }
    
    public static boolean isAdmin(MessageCreateEvent event) {
        
        Boolean b;
        String AdminRoleID = GetConfig.getAdminRoleID();
        
        Role r = event.getApi().getRoleById(AdminRoleID).get();
        b = event.getMessageAuthor().asUser().get().getRoles(event.getServer().get()).contains(r);
         
        return b;
        
    }
    
    public static String formatInt(Integer i) {
        
        long integral = i;
        int x = 3;
        BigDecimal unscaled = new BigDecimal(integral);
        BigDecimal scaled = unscaled.scaleByPowerOfTen(-x);
        return scaled.toString();
        
    }
    
    public static String formatIntBig(String i) {
        
        String y = i.substring(0, i.length() - 6);
        
        long integral = Long.parseLong(y);
        int q = 3;
        BigDecimal unscaled = new BigDecimal(integral);
        BigDecimal scaled = unscaled.scaleByPowerOfTen(-q);
        String e = scaled.toString();
        return e;
        
    }
    
    public static void BuildServerstatusNick(MessageCreateEvent event, String nick) {
             
        String panelurl = GetConfig.getURL();
        String panelkey = GetConfig.getKey();
        String player = " ";
        String maxplayer = " ";
        
        GetData q = new GetData(panelurl, panelkey);
        
        Server[] p = GetServers.getServerBYNick(nick);
        
        for (Server p1 : p) {
            
            Serverstatus s = q.getServer(p1.getID());
            
            if ("running".equals(s.getState())) {
                
                try {
                    
                    MinecraftServer qserver = new MinecraftServer(p1.getNumIP(), p1.getPort());
                    qserver.connecttoServer();
                    player = qserver.getOnlinePlayers().toString();
                    maxplayer = qserver.getMaxPlayers().toString();
                    
                }catch (NumberFormatException ex) {
                    
                    System.out.println("Error 10 - Connection to Queryport failed!");
                    
                }
                
            }
            
            MessageBuilder n = new MessageBuilder();
            
            n.setEmbed(new EmbedBuilder()
                .setTitle(p1.getName() + " Server (Node " + p1.getRegion() + ")")
                .setDescription("Serverstatus: " + s.getStateFormat() + "\n Version: **" + p1.getVersion() + "** \n IP: **" + p1.getIP() + "** \n Player: **" + player + " / " + maxplayer + "**")
                .setColor(Color.BLUE)
                .addField("RAM", formatIntBig(s.getCurrentRAM()) + " GB of " + formatInt(s.getLimitRAM()) + " GB")
                .addField("Diskspace", formatIntBig(s.getCurrentDisk()) + " GB of " + formatInt(s.getLimitDisk()) + " GB")
                .setTimestampToNow()
                .setFooter("© Phoenix Bot by Goody"));
            
            n.send(event.getChannel());
            
            if (s.getState().equals("offline") || s.getState().equals("stopping")) {
                
                if (!Botstate.getStateIgnOffline()) {
                    
                    Util.BuildServerRestart(event, p1.getID());
                    
                }
                
            }
            
            player = " ";
            maxplayer = " ";
            
        }
        
    }
    
    public static void BuildServerstatusChannel(MessageCreateEvent event, String channel) {
             
        String panelurl = GetConfig.getURL();
        String panelkey = GetConfig.getKey();
        String player = " ";
        String maxplayer = " ";
        
        GetData q = new GetData(panelurl, panelkey);
        
        Server[] p = GetServers.getServerBYChannel(channel);
        
        for (Server p1 : p) {
            
            Serverstatus s = q.getServer(p1.getID());
            
            if ("running".equals(s.getState())) {
                
                try {
                    
                    MinecraftServer qserver = new MinecraftServer(p1.getNumIP(), p1.getPort());
                    qserver.connecttoServer();
                    player = qserver.getOnlinePlayers().toString();
                    maxplayer = qserver.getMaxPlayers().toString();
                    
                }catch (NumberFormatException ex) {
                    
                    System.out.println("Error 10 - Connection to Queryport failed!");
                    
                }
                
            }
            
            MessageBuilder n = new MessageBuilder();
            
            n.setEmbed(new EmbedBuilder()
                .setTitle(p1.getName() + " Server (Node " + p1.getRegion() + ")")
                .setDescription("Serverstatus: " + s.getStateFormat() + "\n Version: **" + p1.getVersion() + "** \n IP: **" + p1.getIP() + "** \n Player: **" + player + " / " + maxplayer + "**")
                .setColor(Color.BLUE)
                .addField("RAM", formatIntBig(s.getCurrentRAM()) + " GB of " + formatInt(s.getLimitRAM()) + " GB")
                .addField("Diskspace", formatIntBig(s.getCurrentDisk()) + " GB of " + formatInt(s.getLimitDisk()) + " GB")
                .setTimestampToNow()
                .setFooter("© Phoenix Bot by Goody"));
            
            n.send(event.getChannel());
            
            if (s.getState().equals("offline") || s.getState().equals("stopping")) {
                
                if (!Botstate.getStateIgnOffline()) {
                    
                    Util.BuildServerRestart(event, p1.getID());
                    
                }
                
            }
            
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
        
        CompletableFuture.runAsync(() -> {
        
            MessageBuilder n = new MessageBuilder();
        
            String[] info = GetServers.getNetworkInfo(event);
        
            n.setEmbed(new EmbedBuilder()
                .setTitle(GetConfig.getNetworkName())
                .setDescription("Networkstatus: **Online** :green_circle: \n Players: **" + info[0] + "** \n Servers: **" + info[1] + " of " + GetServers.getSeverCount() + " Servers are online!**")
                .setColor(Color.BLUE)
                .setTimestampToNow()
                .setFooter("© Phoenix Bot by Goody")
            );
            n.send(event.getChannel());
        
        }); 
              
    }
    
    public static void BuildPlayerList(MessageCreateEvent event, String channel) {
        
        String panelurl = GetConfig.getURL();
        String panelkey = GetConfig.getKey();


        GetData q = new GetData(panelurl, panelkey);
        
        Server[] p = GetServers.getServerBYChannel(channel);
 
        for(Server pl : p) {
            
            Serverstatus s = q.getServer(pl.getID());
        
            if("running".equals(s.getState())){
            
                try {
                    
                    String player;
                    String noun;
                    String verb;
                    ArrayList<String> playerlist;
                    
                    MinecraftServer qserver = new MinecraftServer(pl.getNumIP(),pl.getPort());
                    qserver.connecttoServer();
                    player = qserver.getOnlinePlayers().toString();
                    playerlist = qserver.getPlayerList();
                    
                    
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
                    String temb = "!";
            
                    m.setTitle(pl.getName() + " Server (Node " + pl.getRegion() + ")"); 
            
                    if (!player.contentEquals("0")) {
                        
                        temb = ":";
                        temp = "```" + playerlist.toString().substring(1, playerlist.toString().length() - 1) + "```";

                    }
            
                    mess = "There " + noun + " " + player + " " + verb + " online" + temb + temp;
            
                    m.setDescription(mess);        
                    m.setColor(Color.BLUE);
                    m.setTimestampToNow();
                    m.setFooter("© Phoenix Bot by Goody");
            
                    n.setEmbed(m);
                    n.send(event.getChannel());
                
                } catch (NumberFormatException ex) {
                
                    System.out.println("Error 10 - Connection to Queryport failed!");
                
                }   
            
            } else {
                
                event.getChannel().sendMessage("Sorry but the requested Server is not reachable. Please use the !Server Command to check the Serverstatus.");
                
            } 
            
        }
        
    }
    
    public static void BuildPlayerListByNick(MessageCreateEvent event, String nick) {
        
        String panelurl = GetConfig.getURL();
        String panelkey = GetConfig.getKey();

        GetData q = new GetData(panelurl, panelkey);
        
        Server[] p = GetServers.getServerBYNick(nick);
 
        for(Server pl : p) {
            
            Serverstatus s = q.getServer(pl.getID());
        
            if("running".equals(s.getState())){
            
                try {
                    
                    String player;
                    String noun;
                    String verb;
                    ArrayList<String> playerlist;
                    MinecraftServer qserver = new MinecraftServer(pl.getIP(),pl.getPort());
                    qserver.connecttoServer();
                    player = qserver.getOnlinePlayers().toString();
                    playerlist = qserver.getPlayerList();
                    
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
                    String temb = "!";
            
                    m.setTitle(pl.getName() + " Server (Node " + pl.getRegion() + ")"); 
            
                    if (!player.contentEquals("0")) {
                        
                        temb = ":";
                        temp = "```" + playerlist.toString().substring(1, playerlist.toString().length() - 1) + "```";

                    }
            
                    mess = "There " + noun + " " + player + " " + verb + " online" + temb + temp;
            
                    m.setDescription(mess);        
                    m.setColor(Color.BLUE);
                    m.setTimestampToNow();
                    m.setFooter("© Phoenix Bot by Goody");
            
                    n.setEmbed(m);
                    n.send(event.getChannel());
                
                } catch (NumberFormatException ex) {
                
                    System.out.println("Error 10 - Connection to Queryport failed!");
                
                }   
            
            } else {
                
                event.getChannel().sendMessage("Sorry but the requested Server is not reachable. Please use the !Server Command to check the Serverstatus.");
                
            }   
            
        }
        
    }
    
    public static void BuildServerIPByChannel(MessageCreateEvent event, String channel) {
                
        Server[] p = GetServers.getServerBYChannel(channel);
        
        for(Server pl : p) {
                           
            MessageBuilder n = new MessageBuilder();
            
            String message = "``` IP: " + pl.getIP() + "\n Nummerical IP: " + pl.getNumIP() + ":" + pl.getPort() + "```";
        
            n.setEmbed(new EmbedBuilder()
            .setTitle("ServerIP of " + pl.getName() + " (Node " + pl.getRegion() + ")")
            .setDescription(message)
            .setColor(Color.BLUE)
            .setTimestampToNow()
            .setFooter("© Phoenix Bot by Goody")
            );
        
            n.send(event.getChannel());
        
        }
        
    }
    
    public static void BuildServerIPByNick(MessageCreateEvent event, String nick) {
                
        Server[] p = GetServers.getServerBYNick(nick);
        
        for(Server pl : p) {
                           
            MessageBuilder n = new MessageBuilder();
            
            String message = "``` IP: " + pl.getIP() + "\n Nummerical IP: " + pl.getNumIP() + ":" + pl.getPort() + "```";
        
            n.setEmbed(new EmbedBuilder()
            .setTitle("ServerIP of " + pl.getName() + " (Node " + pl.getRegion() + ")")
            .setDescription(message)
            .setColor(Color.BLUE)
            .setTimestampToNow()
            .setFooter("© Phoenix Bot by Goody")
            );
        
            n.send(event.getChannel());
        
        }
        
    }
    
    public static void BuildServerRestart(MessageCreateEvent event, String id) {
        
        Server p = GetServers.getServerBYID(id);
        
        MessageBuilder n = new MessageBuilder();
        n.append("~||Errorcode=" + id + "||");
        EmbedBuilder m = new EmbedBuilder()
        .setTitle(p.getName() + " (Node " + p.getRegion() + ") seems to be Offline :exclamation:")
        .setDescription("We apologize for the inconvinience. \n If you wish to pending a Server start please react with :arrows_counterclockwise:")
        .setColor(Color.RED)
        .setTimestampToNow()
        .setFooter("© Phoenix Bot by Goody");
        n.setEmbed(m);
        n.send(event.getChannel());

    }
    
    public static void BuildApplicationAnnouce(DiscordApi api, String usr, Integer id, String age, String location, String intressed, String mcname, String heard) {
        
        Color colo;
        
        if (Integer.parseInt(age) >= 16) {
         
            colo = Color.GREEN;
            
        } else {
            
            colo = Color.ORANGE;
            
        }
        
        String logchaid = GetConfig.getApplicationLogChannelID();
        MessageBuilder n = new MessageBuilder();
        n.append("~New Application received ( #" + id + " )");
        EmbedBuilder m = new EmbedBuilder()
        .setTitle(usr + " applied for the Network !")
        .setDescription("**Age**: " + age + "\n **Location**: " + location + "\n **Interested in**: " + intressed + "\n **MC Name**: " + mcname + "\n **Heard from us at**: " + heard + "\n \n To accept the Application please react with :white_check_mark: !")
        .setColor(colo)
        .setTimestampToNow()
        .setFooter("© Phoenix Bot by Goody");
        n.setEmbed(m);
        n.send(api.getTextChannelById(logchaid).get());

    }
    
    public static String[][] getGroupRoleIDs() {
        
        String[][] output;
        String[] handler;
        String input = GetConfig.getRoleIDs();
        Integer i = 0;
        
        handler = input.split(";");
        output = new String[handler.length][2];
        
        for (String a : handler) {
            
            String[] b = a.split(",");
            output[i] = b;
            i++;
            
        }
            
        return output;    
        
    }
    
    public static String getMembersCount(DiscordApi api) {
        
        Holder<Integer> MemberCount = new Holder<>(0);
        Collection<User> usr =api.getServerById(GetConfig.getServerID()).get().getMembers();

        usr.forEach(name -> {
            
            if(!name.isBot()) {
                            
                MemberCount.value++;
                
            }
            
        });
        
        Integer i = MemberCount.value;
        return String.valueOf(i);
        
    }
    
    public static void sendServerList(TextChannel channel) {
        
        String[] s = GetServers.getNickList();
        
        List<Object> listWithoutDuplicates = Arrays.asList(s)
            .stream()
            .distinct()
            .collect(toList());
        
        String list = listWithoutDuplicates.toString().substring(1, listWithoutDuplicates.toString().length()-1);

        channel.sendMessage("**Available Servers**: " + list);
        
    }
    
    public static Boolean isCustomCommand(String command) {
        
        return Arrays.asList(GetCommands.getCommandTriggerList()).contains(command);
        
    }
    
    public static void doCustomCommand(String command, TextChannel channel) {
        
        String message;
        
        message = GetCommands.getResponse(command);
        
        if (message != null) {
            
            channel.sendMessage(message);
            
        }
        
    }
    
    public static Boolean isOnlyAdmin(String command) {
        
        return GetCommands.isRoleRestricted(command);
        
    }
    
}