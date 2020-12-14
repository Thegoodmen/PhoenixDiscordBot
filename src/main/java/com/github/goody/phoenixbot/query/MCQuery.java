package com.github.goody.phoenixbot.query;

import com.github.goody.phoenixbot.Util;
import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 *
 * @author Maron Möller
 * 
 */

public class MCQuery {
    
    final static byte HANDSHAKE = 9;
    final static byte STAT = 0;
	
    String serverAddress;
    int queryPort;
    int localPort = 25566;
	
    private DatagramSocket socket = null;
    private int token;
	
    public MCQuery(String address, int port) {
            
	serverAddress = address;
	queryPort = port;
        
    }
	
    private void handshake() {
		
        QueryRequest req = new QueryRequest();
	req.type = HANDSHAKE;
	req.sessionID = generateSessionID();
		
	int val = 11 - req.toBytes().length;
	byte[] input = ByteUtils.padArrayEnd(req.toBytes(), val);
	byte[] result = sendUDP(input);
		
	token = Integer.parseInt(new String(result).trim());
        
    }

    public QueryResponse basicStat() {
		
        handshake();

	QueryRequest req = new QueryRequest();
	req.type = STAT;
	req.sessionID = generateSessionID();
	req.setPayload(token);
        
	byte[] send = req.toBytes();
	byte[] result = sendUDP(send);
	QueryResponse res = new QueryResponse(result, false);
	return res;
        
    }
	
    public QueryResponse fullStat() {
	
	handshake();
		
	QueryRequest req = new QueryRequest();
	req.type = STAT;
	req.sessionID = generateSessionID();
	req.setPayload(token);
	req.payload = ByteUtils.padArrayEnd(req.payload, 4);
		
	byte[] send = req.toBytes();
        byte[] result = sendUDP(send);
	QueryResponse res = new QueryResponse(result, true);
	return res;
        
    }
	
    private byte[] sendUDP(byte[] input) {
		
        try {
			
            while(socket == null) {
				
                try {
					
                    socket = new DatagramSocket(localPort);
				
                } catch (BindException e) {
					
                    ++localPort;
                    Util.doDebug("Error 16! " + e);
				
                }
			
            }
			
            InetAddress address = InetAddress.getByName(serverAddress);
            DatagramPacket packet1 = new DatagramPacket(input, input.length, address, queryPort);
            socket.send(packet1);
            byte[] out = new byte[1024];
            DatagramPacket packet = new DatagramPacket(out, out.length);
            socket.setSoTimeout(500);
            socket.receive(packet);
            return packet.getData();
		
        } catch (SocketException e) {
            
            Util.doDebug("Error 17! " + e);
            
	} catch (SocketTimeoutException e) {
			
            Util.doDebug("Error 18! Socket Timeout! Is the server offline?");

	} catch (UnknownHostException e) {
			
            Util.doDebug("Error 19! Unknown host!");

	} catch (IOException e) {
            
            Util.doDebug("Error 20! Something went horribly wrong!");
            
        }
        
        return null;
        
    }
	
    private int generateSessionID() {

	return 1;
        
    }
	
}