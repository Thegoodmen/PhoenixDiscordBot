
package com.github.goody.phoenixbot;

import com.github.goody.phoenixbot.discordrolemanager.roles;
import com.github.goody.phoenixbot.googlesheetwarpper.GetLists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.PrivateChannel;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.user.User;

/**
 *
 * @author Maron Möller
 * 
 */

public class Applicationcheck {
    
    public static String status = "on";


    
    public static void ini(DiscordApi api) {
        
        String MemRoleId = GetConfig.getMemberRoleID();
        String AppRoleId = GetConfig.getApplicantRoleID();
        String[][] GroupRolesAndId = Util.getGroupRoleIDs();

        CompletableFuture.runAsync(() -> {
            
            do {   
                
                String[] isMemberID = roles.getMembersList(api,MemRoleId);
                List<String> MemberIDs = new ArrayList<String> (Arrays.asList(isMemberID));
                List<List<Object>> database = GetLists.getDiscordWhitelist();

                for (List row : database) {
                    
                    String dataid = Util.getUserID(api, row.get(2).toString());

                    if(dataid != null){
                        
                        if (!MemberIDs.contains(dataid)) {
                            
                            if (row.get(9).toString().equals("Yes")) {
                                                                 
                                User t = api.getCachedUserById(dataid).get();
                                Role n = api.getRoleById(MemRoleId).get();
                                Role a = api.getRoleById(AppRoleId).get();
                                t.addRole(n);
                                t.removeRole(a);
                                
                                String st = row.get(3).toString().replaceAll("\\s+","");
                                String[] list = st.split(",");
                                
                                for (String role : list) {
                                                                       
                                    for (String[] b :  GroupRolesAndId) {
                                        
                                        if (b[0].equals(role)) {
                                        
                                            Role p = api.getRoleById(b[1]).get();
                                            
                                            t.addRole(p);
                                            
                                        }
                                        
                                    }
                                    
                                }
                                
                                try {
                                    
                                    PrivateChannel q;
                                    q = t.openPrivateChannel().get();
                                    q.sendMessage(GetConfig.getWelcomeMessage());
                                
                                } catch (InterruptedException | ExecutionException ex) {
                                    
                                    Logger.getLogger(Applicationcheck.class.getName()).log(Level.SEVERE, null, ex);
                                
                                }  
                                    
                            }
                            
                        } else {
                            
                            if (row.get(9).toString().equals("No")) {
                                
                                User t = api.getCachedUserById(dataid).get();
                                Role n = api.getRoleById(MemRoleId).get();
                                Role a = api.getRoleById(AppRoleId).get();
                                t.removeRole(n);
                                t.addRole(a);
                                
                            }
                            
                        }
                        
                    }
                    
                }
                
                try {
                        
                    TimeUnit.SECONDS.sleep(20);
                        
                } catch (InterruptedException ex) {
                        
                    Logger.getLogger(Applicationcheck.class.getName()).log(Level.SEVERE, null, ex);
                        
                }
                          
            } while(!status.equals("off"));
        
        });  
        
        CompletableFuture.runAsync(() -> {
            
            do {   
                
                GetLists.getNewApplications(api);
                
                try {
                        
                    TimeUnit.SECONDS.sleep(20);
                        
                } catch (InterruptedException ex) {
                        
                    Logger.getLogger(Applicationcheck.class.getName()).log(Level.SEVERE, null, ex);
                        
                }
            
            } while(!status.equals("off"));
        
        }); 

    }
    
    public static void stop() {
        
        status = "off";
        
    }
    
    
}
