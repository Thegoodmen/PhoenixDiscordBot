
package com.github.goody.phoenixbot.discordinterface;

import com.github.goody.phoenixbot.GetConfig;
import com.github.goody.phoenixbot.googlesheetwarpper.GetLists;
import com.github.goody.phoenixbot.pterodactylwarpper.PowerFunctions;
import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.javacord.api.listener.message.reaction.ReactionAddListener;

/**
 *
 * @author Maron Möller
 * 
 */

public class MyReactionListeners implements ReactionAddListener {

    @Override
    
    public void onReactionAdd(ReactionAddEvent event) {
        
        String holemessage = event.getMessage().get().getContent();
        String[] messagepart = holemessage.split("\\s+");
        
        char first = 65;
        
        if(messagepart[0].length() > 0) {
            
            first = messagepart[0].charAt(0);
        
        }
        
        if(event.getMessageAuthor().get().isYourself()){
                       
            if("~".equals(Character.toString(first))) {
                
                if (event.getEmoji().equalsEmoji("🔄")) {
                    
                    if (!event.getUser().get().isYourself()) {
                                          
                        String[] code = event.getMessageContent().get().split("=");
                        String id = code[1].substring(0, code[1].length() - 2);
                        PowerFunctions.startstallServer(id);                     
                        event.deleteMessage();
                    
                    }
               
                }
                
            }
            
            if(event.getChannel().getIdAsString().equals(GetConfig.getApplicationLogChannelID())) {
            
                if("~".equals(Character.toString(first))) {
                    
                    if (!event.getUser().get().isYourself()) {
                        
                        if (event.getEmoji().equalsEmoji("✅")) {
                            
                            String[] str = event.getMessage().toString().split("#");
                            String id = str[1].substring(0,str[1].length() - 4);
                            GetLists.doApplicationAccept(id, event.getUser().get());
                            event.deleteMessage();
                                                                         
                        } else if (event.getEmoji().equalsEmoji("⛔")) {
                            
                            String[] str = event.getMessage().toString().split("#");
                            String id = str[1].substring(0,str[1].length() - 4);
                            GetLists.doApplicationDecline(id, event.getUser().get());
                            event.deleteMessage();
                            
                        }
                    
                    }
                    
                }
                
            }
                              
        }        
        
    }
    
}
