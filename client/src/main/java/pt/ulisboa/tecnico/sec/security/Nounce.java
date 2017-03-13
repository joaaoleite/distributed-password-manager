import java.util.Base64;
public class Nounce {
	private byte[] nounce;

	private byte[] epochBytes;
	private byte[] keyBytes;

	public Nounce(String key){
		long epoch = Instant.now().toEpochMilli();
    	ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
    	buffer.putLong(epoch);
    	epochBytes = buffer.array();
    	keyBytes = Base64.getDecoder().decode(key);
		generate();
	}

	public Nounce(PublicKey key){
    	long epoch = Instant.now().toEpochMilli();
    	ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
    	buffer.putLong(epoch);
    	epochBytes = buffer.array();
    	keyBytes = key.getEncoded();
		generate();
  	}

	private void generate(){
		nounce = new byte[2 * keyBytes.length];
    	for(int i = 0; i < keyBytes.length; i++){
      		nounce[2 * i] = keyBytes[i];
	      	nounce[(2 * i) + 1] = epochBytes[i % epochBytes.length];
    	}
	}

	public String toString(){
		return Base64.getEncoder().encodeToString(nounce);
	}
}
