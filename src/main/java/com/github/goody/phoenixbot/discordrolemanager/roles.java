
package com.github.goody.phoenixbot.discordrolemanager;

import java.util.Collection;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.user.User;

/**
 *
 * @author Maron Möller
 * 
 */

public class roles {
    
    public static String[] getMembersList(DiscordApi api, String roleid) {
          
        Role member = api.getRoleById(roleid).get();
        
        Collection<User> usr = member.getUsers();
        
        int i = usr.size();
        String[] ids = new String[i];
        int j = 0;
        
        for(User nul : usr) {
            
            User n = nul;
            ids[j] = n.getIdAsString();
            j++;
            
        }
        
        return ids;
        
    }
    
}
