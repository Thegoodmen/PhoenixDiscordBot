package com.github.goody.phoenixbot.query;

import com.github.goody.phoenixbot.Util;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 * @author Maron Möller
 * 
 */

public class QueryRequest {
    
    private ByteArrayOutputStream byteStream;
    private DataOutputStream dataStream;
	
    static byte[] MAGIC = {(byte) 0xFE, (byte) 0xFD};
    byte type;
    int sessionID;
    byte[] payload;
	
    public QueryRequest() {
        
	int size = 1460;
	byteStream = new ByteArrayOutputStream(size);
	dataStream = new DataOutputStream(byteStream);
    }

    public QueryRequest(byte type) {
	
        this.type = type;
        
    }

    public byte[] toBytes() {
        
	byteStream.reset();
		
	try {
	
            dataStream.write(MAGIC);
            dataStream.write(type);
            dataStream.writeInt(sessionID);
            dataStream.write(payloadBytes());
	
        } catch (IOException e) {
			
            Util.doDebug("Error 12: " + e);
	
        }
	
        return byteStream.toByteArray();
    
    }
	
    private byte[] payloadBytes() {
        
	if(type == MCQuery.HANDSHAKE) {
			
            return new byte[]{};
	
        } else {
		
            return payload;
            
	}
	
    }
	
    protected void setPayload(int load) {
        
	this.payload = ByteUtils.intToBytes(load);

    }
    
}