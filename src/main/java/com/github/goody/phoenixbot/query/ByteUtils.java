package com.github.goody.phoenixbot.query;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

/**
 * 
 * @author Ryan McCann 
 * 
 */

public class ByteUtils {
    
    public static byte[] subarray(byte[] in, int a, int b) {
            
        if (b - a > in.length) return in;

        byte[] out = new byte[(b - a) + 1];

        for (int i = a; i <= b; i++) {
                    
            out[i - a] = in[i];
                        
        }
                
        return out;
              
    }
	
    public static byte[] trim(byte[] arr)
	{
		if(arr[0]!=0 && arr[arr.length]!=0) return arr; //return the input if it has no leading/trailing null bytes
		
		int begin=0, end=arr.length;
		for(int i=0; i<arr.length; i++) // find the first non-null byte 
		{
			if(arr[i] != 0) {
				begin = i;
				break;
			}
		}
		for(int i=arr.length-1; i>=0; i--) //find the last non-null byte
		{
			if(arr[i] != 0) {
				end = i;
				break;
			}
		}
		
		return subarray(arr, begin, end);
	}
	
    public static byte[][] split(byte[] input) {
        
        ArrayList<byte[]> temp = new ArrayList<>();
	byte[][] output = new byte[input.length][input.length];
	int index_cache = 0;
	
        for(int i=0; i<input.length; i++) {
			
            if(input[i] == 0x00) {
				
                byte[] b = subarray(input, index_cache, i-1);
		temp.add(b);
		index_cache = i+1;
			
            }
		
        }

	if(index_cache != 0) {
			
            byte[] b = subarray(input, index_cache, input.length-1);
            temp.add(b);
	
        }
		
	output = new byte[temp.size()][input.length];
	
        for(int i=0; i<temp.size(); i++) {
			
            output[i] = temp.get(i);
		
        }
		
	return output;
        
    }
	
    public static byte[] padArrayEnd(byte[] arr, int amount) {
		
        byte[] arr2 = new byte[arr.length+amount];
	
        System.arraycopy(arr, 0, arr2, 0, arr.length);
		
        for(int i=arr.length; i<arr2.length; i++) {
			
            arr2[i] = 0;
		
        }
		
        return arr2;
        
    }
	
    public static short bytesToShort(byte[] b) {
		
        ByteBuffer buf = ByteBuffer.wrap(b, 0, 2);
	buf.order(ByteOrder.LITTLE_ENDIAN);
	return buf.getShort();
	
    }

    public static byte[] intToBytes(int in) {
		
        byte[] b;
        
	b = new byte[] {
            (byte) (in >>> 24	& 0xFF),
            (byte) (in >>> 16	& 0xFF),
            (byte) (in >>> 8	& 0xFF),
            (byte) (in >>> 0	& 0xFF)};
	return b;
        
    }
	
    public static int bytesToInt(byte[] in) {
		
        return ByteBuffer.wrap(in).getInt();
	
    }
	
}
