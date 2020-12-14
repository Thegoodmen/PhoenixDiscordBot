
package com.github.goody.phoenixbot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.javacord.api.DiscordApi;


/**
 *
 * @author Maron Möller
 * 
 */

public class LabelModul {
    
    public static String status = "on";

    public static void ini(DiscordApi api) {
        
        DiscordApi discord = Main.discord;

        CompletableFuture.runAsync(() -> {
            
            do {   
                
                if (GetConfig.getLableMemberCountActiv()) {
                    
                    String CatMemId = GetConfig.getCategorieMemberID();
                    String MemberCount = Util.getMembersCount(discord);
                    discord.getChannelById(CatMemId).get().asServerChannel().get().updateName("Members: " + MemberCount);
                    
                }
                
                if (GetConfig.getLableDonationGoalActiv()) {
                    
                    String CatDonId = GetConfig.getCategorieDonationID();
                    String DonationGoal = GetConfig.getDonationGoal();
                
                    Calendar cal = Calendar.getInstance();
                    String curMonth = new SimpleDateFormat("MMM").format(cal.getTime());

                    String curName = discord.getChannelCategoryById(CatDonId).get().getName();
                    String[] part = curName.split(":");
                
                    if (!part[0].equals(curMonth)) {
                    
                        discord.getChannelById(CatDonId).get().asServerChannel().get().updateName(curMonth + ": 0€ / " + DonationGoal + "€");
                        
                    }   
                    
                }

                try {
                        
                    TimeUnit.SECONDS.sleep(30);
                        
                } catch (InterruptedException ex) {
                        
                    Logger.getLogger(Applicationcheck.class.getName()).log(Level.SEVERE, null, ex);
                        
                }
                          
            } while(!status.equals("off"));
        
        });  

    }
    
    public static void stop() {
        
        status = "off";
        
    }
    
    public static void addDonation(Integer i) {
        
        DiscordApi discord = Main.discord;
        String CatDonId = GetConfig.getCategorieDonationID();
        String input = discord.getChannelCategoryById(CatDonId).get().getName();
        String DonGoal = GetConfig.getDonationGoal();
        
        String[] part = input.split(":");
        String temp = part[1].replaceAll("\\s+","");
        String[] n = temp.split("/");
        String current = n[0].replaceAll("€","");
        
        Integer j = Integer.parseInt(current);
        
        Integer k = j + i;
        
        String newStatus = part[0] + ": " + String.valueOf(k) + "€ / " + DonGoal + "€";
        
        discord.getChannelById(CatDonId).get().asServerChannel().get().updateName(newStatus);
        
    }
    
    public static void removeDonation(Integer i) {
        
        DiscordApi discord = Main.discord;
        String CatDonId = GetConfig.getCategorieDonationID();
        String input = discord.getChannelCategoryById(CatDonId).get().getName();
        String DonGoal = GetConfig.getDonationGoal();
        String[] part = input.split(":");
        String temp = part[1].replaceAll("\\s+","");
        String[] n = temp.split("/");
        String current = n[0].replaceAll("€","");
        Integer j = Integer.parseInt(current);
        
        Integer k = j - i;
        String newStatus = part[0] + ": " + String.valueOf(k) + "€ / " + DonGoal + "€";
        discord.getChannelById(CatDonId).get().asServerChannel().get().updateName(newStatus);
        
    }
    
}
