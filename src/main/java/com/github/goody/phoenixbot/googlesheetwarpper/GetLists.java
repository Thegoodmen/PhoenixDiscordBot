
package com.github.goody.phoenixbot.googlesheetwarpper;

import com.github.goody.phoenixbot.GetConfig;
import com.github.goody.phoenixbot.Log;
import com.github.goody.phoenixbot.Main;
import com.github.goody.phoenixbot.Util;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.user.User;

/**
 *
 * @author Maron Möller
 * 
 */

public class GetLists {
    
    private static final String BOTNAME = "Phoenix Bot";
    private static final JsonFactory JsonFactory = JacksonFactory.getDefaultInstance();
    private static final String TOCKENPATH = "tokens";
   
    private static final List<String> Scope = Collections.singletonList(SheetsScopes.SPREADSHEETS);
    private static final String CREDENTIALSPATH = "/credentials.json";
    
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {

        InputStream in = GetLists.class.getResourceAsStream(CREDENTIALSPATH);
        
        if (in == null) {
            
            throw new FileNotFoundException("Resource not found: " + CREDENTIALSPATH);
            
        }
        
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JsonFactory, new InputStreamReader(in));
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JsonFactory, clientSecrets, Scope)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOCKENPATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        
    }
    
    public static List<List<Object>> getDiscordWhitelist() {

        try {
            
            final java.util.logging.Logger buggyLogger = java.util.logging.Logger.getLogger(FileDataStoreFactory.class.getName()); 
            buggyLogger.setLevel(java.util.logging.Level.SEVERE);
            
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            final String spreadsheetId = GetConfig.getAppSheetURL();
            final String range = "Input!A2:J";
            
            Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JsonFactory, getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(BOTNAME)
                    .build();
            
            ValueRange response = service.spreadsheets().values()
                    .get(spreadsheetId, range)
                    .execute();
            
            List<List<Object>> values = response.getValues();
            
            if (values == null || values.isEmpty()) {
                
                Util.doDebug("Error 66: No data found on Google.");
                return null;
                
            } else {
                
                Util.doDebug("INFO: Recived Discord Whitelist Data from Google!");
                return values;
                
            }
            
        } catch (GeneralSecurityException | IOException ex) {
            
            Logger.getLogger(GetLists.class.getName()).log(Level.SEVERE, null, ex);
            return null;
            
        }
        
    }
    
    public static void getNewApplications(DiscordApi api) {
        
        try {
            
            final java.util.logging.Logger buggyLogger = java.util.logging.Logger.getLogger(FileDataStoreFactory.class.getName()); 
            buggyLogger.setLevel(java.util.logging.Level.SEVERE);
            
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            final String spreadsheetId = GetConfig.getAppSheetURL();
            final String range = "Input!A2:K";
            
            Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JsonFactory, getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(BOTNAME)
                    .build();
            
            ValueRange response = service.spreadsheets().values()
                    .get(spreadsheetId, range)
                    .execute();
            
            List<List<Object>> values = response.getValues();
            
            if (values == null || values.isEmpty()) {
                
                Util.doDebug("Error 66: No data found on Google.");
                
            } else {
                
                Util.doDebug("INFO: Recived Discord Applicaton Data from Google!");
                
                int i = 2;
                
                for (List row : values) {
                     
                    if (row.get(0).toString().equals("")) {

                        Util.doDebug("New Application found: " + row.get(2));
                        String mcname = row.get(4).toString();
                        
                        if (mcname.equals("")) {
                        
                            mcname = "*none given*";
                            
                        }
                        
                        Util.BuildApplicationAnnouce(api, row.get(2).toString(), i, row.get(5).toString(), row.get(6).toString(), row.get(3).toString(), mcname, row.get(7).toString());
                        
                        String send = "Input!A" + i;
                        
                        ValueRange body = new ValueRange()
                            .setValues(Arrays.asList(      
                            Arrays.asList(i)));
                                    
                        UpdateValuesResponse result = service.spreadsheets().values()
                            .update(spreadsheetId, send, body)
                            .setValueInputOption("RAW")
                            .execute();
                        
                    }
                    
                    i++;
                     
                 }
                
            }
            
        } catch (GeneralSecurityException | IOException ex) {
            
            Logger.getLogger(GetLists.class.getName()).log(Level.SEVERE, null, ex);

            
        }
        
    }
    
    public static void doApplicationAccept(String id, User editor) {
        
        try {
            
            DiscordApi api = Main.discord;
            String spreadsheetId = GetConfig.getAppSheetURL();
            String tow = "Input!J" + id;
            NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
              
            ValueRange brain = new ValueRange()
                    .setValues(Arrays.asList(
                    Arrays.asList("Yes","Yes")));
                    
            Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JsonFactory, getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(BOTNAME)
                    .build();
            
            UpdateValuesResponse end = service.spreadsheets().values()
                    .update(spreadsheetId, tow, brain)
                    .setValueInputOption("RAW")
                    .execute();
            
            final java.util.logging.Logger buggyLogger = java.util.logging.Logger.getLogger(FileDataStoreFactory.class.getName()); 
            buggyLogger.setLevel(java.util.logging.Level.SEVERE);
            
            final String range = "Input!A" + id + ":K" + id;
            
            ValueRange response = service.spreadsheets().values()
                    .get(spreadsheetId, range)
                    .execute();
            
            List<List<Object>> values = response.getValues();
            
            for (List row : values) {
                
                if(row.get(0).equals(id)) {
                    
                    User member = api.getCachedUserByDiscriminatedName(row.get(2).toString()).get();
                 
                    Log.doMemberAcceptedLog(api, member.getIdAsString(), editor, row.get(1).toString());
                    
                }
                
            }
            
        } catch (GeneralSecurityException | IOException ex) {
            
            Logger.getLogger(GetLists.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    
    }
    
    public static void doApplicationDecline(String id, User editor) {
        
        try {
            
            DiscordApi api = Main.discord;
            String spreadsheetId = GetConfig.getAppSheetURL();
            String tow = "Input!J" + id;
            NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
              
            ValueRange brain = new ValueRange()
                    .setValues(Arrays.asList(
                    Arrays.asList("No","No")));
                    
            Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JsonFactory, getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(BOTNAME)
                    .build();
            
            UpdateValuesResponse end = service.spreadsheets().values()
                    .update(spreadsheetId, tow, brain)
                    .setValueInputOption("RAW")
                    .execute();
            
            final java.util.logging.Logger buggyLogger = java.util.logging.Logger.getLogger(FileDataStoreFactory.class.getName()); 
            buggyLogger.setLevel(java.util.logging.Level.SEVERE);
            
            final String range = "Input!A" + id + ":K" + id;
            
            ValueRange response = service.spreadsheets().values()
                    .get(spreadsheetId, range)
                    .execute();
            
            List<List<Object>> values = response.getValues();
            
            for (List row : values) {
                
                if(row.get(0).equals(id)) {
                    
                    User member = api.getCachedUserByDiscriminatedName(row.get(2).toString()).get();
                 
                    Log.doMemberDeclineLog(api, member.getIdAsString(), editor, row.get(1).toString());
                    
                }
                
            }
            
        } catch (GeneralSecurityException | IOException ex) {
            
            Logger.getLogger(GetLists.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    
    }
    
}
