
package com.github.goody.phoenixbot;

import java.awt.Color;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;

/**
 *
 * @author Maron Möller
 * 
 */

public class Log {
    
    public static void doMemberAcceptedLog(DiscordApi api, String id, User editor, String time) {
        
        if (GetConfig.getAppLogActic()) {
            
            String logchaid = GetConfig.getGeneralLogChannel();
            String usr = api.getCachedUserById(id).get().getDiscriminatedName();
            User member = api.getCachedUserById(id).get();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            String now = String.valueOf(sdf.format(timestamp));
        
            MessageBuilder n = new MessageBuilder();
            EmbedBuilder m = new EmbedBuilder()
                .setAuthor(member)
                .setTitle(usr + " Application was accepted!")
                .setDescription("Application processed by " + editor.getNicknameMentionTag() + "\n Applied: "  + time +  "\n Accepted: " + now)
                .setColor(Color.CYAN)
                .setTimestampToNow()
                .setFooter("© Phoenix Bot by Goody");
            n.setEmbed(m);
            n.send(api.getTextChannelById(logchaid).get());
            
        }
     
    }
    
    public static void doMemberDeclineLog(DiscordApi api, String id, User editor, String time) {
        
        if (GetConfig.getAppLogActic()) {
            
            String logchaid = GetConfig.getGeneralLogChannel();
            String usr = api.getCachedUserById(id).get().getDiscriminatedName();
            User member = api.getCachedUserById(id).get();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            String now = String.valueOf(sdf.format(timestamp));
        
            MessageBuilder n = new MessageBuilder();
            EmbedBuilder m = new EmbedBuilder()
                .setAuthor(member)
                .setTitle(usr + " Application was declined!")
                .setDescription("Application processed by " + editor.getNicknameMentionTag() + "\n Applied: "  + time +  "\n Declined: " + now)
                .setColor(Color.RED)
                .setTimestampToNow()
                .setFooter("© Phoenix Bot by Goody");
            n.setEmbed(m);
            n.send(api.getTextChannelById(logchaid).get());
       
        }
        
    }
    
}
