
package com.github.goody.heliossdiscordbot.discordinterface;

import com.github.goody.heliossdiscordbot.GetConfig;
import com.github.goody.heliossdiscordbot.GetServers;
import com.github.goody.heliossdiscordbot.pterodactylwarpper.PanelMain;
import com.github.goody.heliossdiscordbot.Util;
import java.util.Arrays;
import java.util.List;
import static java.util.stream.Collectors.toList;
import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;


/**
 *
 * @author Maron Möller
 * 
 */

public class MyListeners implements MessageCreateListener {

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
                   
        String panelurl = GetConfig.getURL();
        String panelkey = GetConfig.getKey();
        
        Message message = event.getMessage();
        String channel = event.getChannel().getIdAsString();
        String holemessage = message.getContent();
        String[] messagepart = holemessage.split("\\s+");
        
        char first = 65;
        
        if(messagepart[0].length() > 0) {
        first = messagepart[0].charAt(0);
        }
        
        
        if(Util.isPrefix(first)) {
            
            if(Util.isServerCmd(messagepart[0].substring(1))) { 
                
                if(Util.isStandartChannel(channel)) {
                
                    if(messagepart.length > 1) {
                    
                        if(messagepart[1].equals("list")) {
                        
                            String[] s = GetServers.getNickList();
                            List<Object> listWithoutDuplicates = Arrays.asList(s)
                            .stream()
                            .distinct()
                            .collect(toList());
                        
                            event.getChannel().sendMessage("**Available Servers**: " + listWithoutDuplicates.toString().substring(1, listWithoutDuplicates.toString().length()-1));
                        
                        } else if (Util.isServerNick(messagepart[1])) {
                        
                            Util.BuildServerstatusNick(event, messagepart[1]);
                            
                        } else {
                            
                            event.getChannel().sendMessage("Server not found. Please use !server list to see all avavible servers.");
                            
                        }
                        
                    } else {
                        
                        event.getChannel().sendMessage("Please specify your request. Use !server list to see all avavible servers.");
                        
                    }
                    
                } else if (Util.isServerChannel(channel)) {
                
                    Util.BuildServerstatusChannel(event, channel);
                
                }
                
            } else if(Util.isNetworkCmd(messagepart[0].substring(1))) {
                
                if(Util.isStandartChannel(channel)) {
                    
                    Util.BuildNetworkstatus(event, channel);
                    
                }
                
            } else if (messagepart[0].substring(1).equalsIgnoreCase("List")) {
                
                if (Util.isServerChannel(channel)) {
                
                    Util.BuildPlayerList(event, channel);
                
                } else if(Util.isStandartChannel(channel)) {
                
                    if(messagepart.length > 1) {
                        
                        if(messagepart[1].equals("list")) {
                        
                            String[] s = GetServers.getNickList();
                            List<Object> listWithoutDuplicates = Arrays.asList(s)
                            .stream()
                            .distinct()
                            .collect(toList());
                        
                            event.getChannel().sendMessage("**Available Servers**: " + listWithoutDuplicates.toString().substring(1, listWithoutDuplicates.toString().length()-1));
                        
                        } else if (Util.isServerNick(messagepart[1])) {
                        
                            Util.BuildPlayerListByNick(event, messagepart[1]);
                            
                         } else {
                            
                            event.getChannel().sendMessage("Server not found. Please use !list list to see all avavible servers.");
                            
                        }
                        
                    } else {
                        
                        event.getChannel().sendMessage("Please specify your request. Use !list list to see all avavible servers.");
                        
                    }
                }
            } else if (messagepart[0].substring(1).equalsIgnoreCase("IP")) {
                
                if (Util.isServerChannel(channel)) {
                
                    Util.BuildServerIPByChannel(event, channel);
                
                } else if(Util.isStandartChannel(channel)) {
                
                    if(messagepart.length > 1) {
                        
                        if(messagepart[1].equals("list")) {
                        
                            String[] s = GetServers.getNickList();
                            List<Object> listWithoutDuplicates = Arrays.asList(s)
                            .stream()
                            .distinct()
                            .collect(toList());
                        
                            event.getChannel().sendMessage("**Available Servers**: " + listWithoutDuplicates.toString().substring(1, listWithoutDuplicates.toString().length()-1));
                        
                        } else if (Util.isServerNick(messagepart[1])) {
                        
                            Util.BuildServerIPByNick(event, messagepart[1]);
                            
                         } else {
                            
                            event.getChannel().sendMessage("Server not found. Please use !IP list to see all avavible servers.");
                            
                        }
                        
                    } else {
                        
                        event.getChannel().sendMessage("Please specify your request. Use !IP list to see all avavible servers.");
                        
                    }
                }
                
            }
              
        }
        
        if (message.getContent().equalsIgnoreCase("!ping")) {
            
            event.getChannel().sendMessage("Pong!");

            PanelMain.intPanel(panelurl, panelkey);
            PanelMain.sendPanelCmd("0e768091","Discord Pong");
            
        }

    }
      
}
