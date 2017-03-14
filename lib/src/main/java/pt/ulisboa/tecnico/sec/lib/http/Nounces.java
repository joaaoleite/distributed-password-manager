package pt.ulisboa.tecnico.sec.lib.http;

import java.util.Base64;
import java.util.ArrayList;
import java.security.PublicKey;
import java.nio.ByteBuffer;
import java.time.Instant;

import pt.ulisboa.tecnico.sec.lib.exceptions.*;

public class Nounces extends ArrayList<String> {

	private byte[] keyBytes;

	public Nounces(String key){
		super();
    	keyBytes = Base64.getDecoder().decode(key);
	}

	public Nounces(PublicKey key){
		super();
    	keyBytes = key.getEncoded();
  	}

	public String generate(){
		long epoch = Instant.now().toEpochMilli();
    	ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
    	buffer.putLong(epoch);
    	byte[] epochBytes = buffer.array();

		byte[] nounce = new byte[2 * keyBytes.length];
    	for(int i = 0; i < keyBytes.length; i++){
      		nounce[2 * i] = keyBytes[i];
	      	nounce[(2 * i) + 1] = epochBytes[i % epochBytes.length];
    	}
		String result = Base64.getEncoder().encodeToString(nounce);
		super.add(result);
		return result;
	}
	public boolean verify(String nounce){
		if(super.contains(nounce) || nounce==null)
			return false;
		super.add(nounce);
		return true;
	}
}