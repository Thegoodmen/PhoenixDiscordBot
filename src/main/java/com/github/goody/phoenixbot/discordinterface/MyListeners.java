
package com.github.goody.phoenixbot.discordinterface;

import com.github.goody.phoenixbot.Botstate;
import com.github.goody.phoenixbot.GetConfig;
import com.github.goody.phoenixbot.LabelModul;
import com.github.goody.phoenixbot.Util;
import com.github.goody.phoenixbot.sqlquery.Whitelist;
import org.javacord.api.entity.channel.TextChannel;
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
        
        Message message = event.getMessage();
        TextChannel channel = event.getChannel();
        String channelID = event.getChannel().getIdAsString();
        String holemessage = message.getContent();
        String[] messagepart = holemessage.split("\\s+");   
        char first;
        
        if (!"".equals(holemessage)) {
            
            first = messagepart[0].charAt(0);
            String command = messagepart[0].substring(1).toLowerCase();

            if(Util.isPrefix(first)) {
            
                if(Util.isStandartChannel(channelID)) {
                
                    switch (command) {
                    
                        case "list":
                        
                            if(messagepart.length > 1) {
                        
                                if(messagepart[1].equals("list")) {
                        
                                    Util.sendServerList(channel);
                        
                                } else if (Util.isServerNick(messagepart[1])) {
                        
                                    Util.BuildPlayerListByNick(event, messagepart[1]);
                            
                                } else {
                            
                                    event.getChannel().sendMessage("Server not found. Please use !list list to see all available servers.");
                            
                                }
                        
                            } else {
                        
                                event.getChannel().sendMessage("Please specify your request. Use !list list to see all available servers.");
                        
                            }
                        
                            break;
                        
                        case "ip":
                        
                            if(messagepart.length > 1) {
                        
                                if(messagepart[1].equals("list")) {
                        
                                    Util.sendServerList(channel);
                        
                                } else if (Util.isServerNick(messagepart[1])) {
                        
                                    Util.BuildServerIPByNick(event, messagepart[1]);
                            
                                } else {
                            
                                    event.getChannel().sendMessage("Server not found. Please use !IP list to see all available servers.");
                            
                                }
                        
                            } else {
                        
                                event.getChannel().sendMessage("Please specify your request. Use !IP list to see all available servers.");
                        
                            }
                        
                            break;
                        
                        case "ignore":
                        
                            if (messagepart.length > 1) {
                            
                                switch (messagepart[1].toLowerCase()) {
                                
                                    case "offline":
                                    
                                        if (Util.isAdmin(event)) {
                                    
                                            if(Botstate.getStateIgnOffline()) {
                                
                                                Botstate.setStateIgnOffline(false);
                                                event.getChannel().sendMessage("Reenabled Server Restart Function!");
                                
                                            } else {
                                
                                                Botstate.setStateIgnOffline(true);
                                                event.getChannel().sendMessage("Restartoption of the Bot disabled! To reenabled it use !Ignore Offline again.");
                                            
                                            }
                                        
                                        } else {
                                        
                                            event.getChannel().sendMessage("This command is restricted to the Moderator Role.");
                                        
                                        }
                                    
                                        break;
                                    
                                    default:
                                    
                                        event.getChannel().sendMessage("Requested Ignore Function not found.");
                                        break;
                                    
                                }
                            
                            } else {
                            
                                event.getChannel().sendMessage("Please specify your Ignore-Request.");
                            
                            }

                            break;
                        
                        case "whitelist":
                            
                            if (GetConfig.getWhitelistModuleStatus()) {
                        
                                if (messagepart.length > 1) {

                                    switch (messagepart[1].toLowerCase()) {

                                        case "add":

                                            if(Util.isAdmin(event)) {

                                                Whitelist.addWhitelist(messagepart[2]);
                                                event.getChannel().sendMessage("The User " + messagepart[2] + " is now **added** to the Whitelist.");

                                            } else {

                                                event.getChannel().sendMessage("This command is restricted to the Moderator Role.");

                                            }

                                            break;

                                        case "remove":

                                            if(Util.isAdmin(event)) {

                                                Whitelist.removeWhitelist(messagepart[2]);
                                                event.getChannel().sendMessage("The User " + messagepart[2] + " is now **removed** from the Whitelist.");

                                            } else {

                                                event.getChannel().sendMessage("This command is restricted to the Moderator Role.");

                                            }

                                            break;

                                        case "info":
                                
                                            if (Whitelist.isWhitelisted(messagepart[2])) {

                                                event.getChannel().sendMessage("The User " + messagepart[2] + " **is** on the Whitelist.");

                                            } else {

                                                event.getChannel().sendMessage("The User " + messagepart[2] + " is **not** on the Whitelist.");

                                            }

                                            break;

                                        default:

                                            if (Util.isAdmin(event)) {

                                                event.getChannel().sendMessage("You can use !Whitelist add/remove/info [username].");

                                            } else {

                                                event.getChannel().sendMessage("You can use !Whitelist info [username].");
                                    
                                            }
                                
                                            break;
                                
                                    }
                            
                                } else {
                            
                                    if (Util.isAdmin(event)) {
                                    
                                        event.getChannel().sendMessage("You can use !Whitelist add/remove/info [username].");
                                    
                                    } else {
                                    
                                        event.getChannel().sendMessage("You can use !Whitelist info [username].");
                                    
                                    }
                            
                                }
                            
                            } else {
                                
                                event.getChannel().sendMessage("The Whitelist Module from Phoenix Bot is disabled. To use this feature please enable the Whitelist Module in the Config File.");
                                
                            }
                        
                            break;
                        
                        case "goal":
                        
                            if (Util.isAdmin(event))  {
                        
                                if (GetConfig.getLableModuleStatus()) {
                            
                                    if (messagepart.length > 2) {
                                
                                        Integer amount;
                        
                                        String a = messagepart[2].replaceAll("€","").replaceAll("$","").replaceAll("£","");
                            
                                        if (a.contains(".")) {
                                
                                            String[] b = a.split("\\.");
                                            a = b[0];
                                
                                        }
                            
                                        if (a.contains(",")) {
                                
                                            String[] b = a.split(",");
                                            a = b[0];
                                
                                        }
                        
                                        amount = Integer.parseInt(a);
                                
                                        switch (messagepart[1].toLowerCase()) {
                                    
                                            case "add":
                                        
                                                LabelModul.addDonation(amount);
                                                event.getChannel().sendMessage(amount + "€ got **added** to the monthly Donationgoal.");
                                                break;
                                        
                                            case "remove":
                                        
                                                LabelModul.removeDonation(amount);
                                                event.getChannel().sendMessage(amount + "€ got **removed** from the monthly Donationgoal.");
                                                break;
                                        
                                            default:
                                        
                                                event.getChannel().sendMessage("You can use !Goal add/remove [amount] to add or remove a certained amount from the Donationgoal.");
                                                break;
                                    
                                        }
                                
                                    } else {
                                
                                        event.getChannel().sendMessage("You can use !Goal add/remove [amount] to add or remove a certained amount from the Donationgoal.");
                                
                                    }                            
                            
                                } else {
                    
                                    event.getChannel().sendMessage("The Labelmodul from Phoenix Bot is disabled. To use this feature please enable the Labelmodul in the Config File.");
                            
                                }
                        
                            } else {
                                        
                                event.getChannel().sendMessage("This Command is restricted to the Moderator Role.");
                                        
                            }
                        
                            break;
     
                        default:     
                
                            if(Util.isServerCmd(command)) {
                    
                                if(messagepart.length > 1) {
                        
                                    if (messagepart[1].equalsIgnoreCase("list")) {
                            
                                        Util.sendServerList(channel);
                            
                                    } else if (Util.isServerNick(messagepart[1])) {
                        
                                        Util.BuildServerstatusNick(event, messagepart[1]);
                            
                                    } else {
                            
                                        event.getChannel().sendMessage("Server not found. Please use !server list to see all available servers.");
                            
                                    }
                        
                                } else {
                        
                                    event.getChannel().sendMessage("Please specify your request. Use !server list to see all available servers.");
                        
                                }
                            
                            } else if (Util.isNetworkCmd(command)) {
                
                                Util.BuildNetworkstatus(event, channelID);
                     
                            } else if (GetConfig.getCustomCommandModuleStatus()) {
                                
                                if (Util.isCustomCommand(command)) {
                                
                                    if(Util.isOnlyAdmin(command)) {
                                        
                                        if(Util.isAdmin(event)) {
                                            
                                            Util.doCustomCommand(command, channel);
                                            
                                        } else {
                                            
                                            event.getChannel().sendMessage("This Command is restricted to the Moderator Role.");
                                            
                                        }
                                    
                                    } else {
                                    
                                        Util.doCustomCommand(command,channel);
                                    
                                    }
                                    
                                }
                                    
                            }
                        
                            break;
                        
                    }
                
                } else if (Util.isServerChannel(channelID)) {
                
                    switch (command) {
                    
                        case "list":
                        
                            Util.BuildPlayerList(event, channelID);
                            break;
                        
                        case "IP":
                        
                            Util.BuildServerIPByChannel(event, channelID);
                            break;
                        
                        default:
                        
                            if(Util.isServerCmd(command)) {
                    
                                Util.BuildServerstatusChannel(event, channelID);
                    
                            }
                        
                            break;
                        
                    }
                                     
                }
            
            }
        
        }
                
        if (message.getContent().equalsIgnoreCase("!ping")) {
            
            event.getChannel().sendMessage("Pong!");
            
        }
        
        if(event.getMessageAuthor().isYourself()){
            
            if (Util.isServerChannel(event.getChannel().getIdAsString())) {  
                
                if (!"".equals(holemessage)) {
            
                    first = messagepart[0].charAt(0);
                
                    if ("~".equals(Character.toString(first))) {
                
                        event.addReactionToMessage("🔄");
                
                    }
                    
                }
            
            }
        
            String AppLogChaId = GetConfig.getApplicationLogChannelID();
            
            if (event.getChannel().getIdAsString().equals(AppLogChaId)) {
                
                if (!"".equals(holemessage)) {
            
                    first = messagepart[0].charAt(0);
                    
                    if("~".equals(Character.toString(first))) {
                    
                        event.addReactionToMessage("✅");
                        event.addReactionToMessage("⛔");  
                    
                    }
                    
                }
  
            }
                              
        }
        
    }
                  
}
