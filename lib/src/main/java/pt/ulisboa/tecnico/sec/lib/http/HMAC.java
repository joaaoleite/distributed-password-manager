package pt.ulisboa.tecnico.sec.lib.http;

import io.jsonwebtoken.*;
import java.util.Base64;
import java.security.SecureRandom;
import io.jsonwebtoken.impl.crypto.MacProvider;
import java.security.Key;
import io.jsonwebtoken.impl.TextCodec;
import javax.crypto.KeyGenerator;
import org.json.JSONObject;
import java.util.Date;
import sun.nio.cs.ext.PCK;
import javax.crypto.spec.SecretKeySpec;
import pt.ulisboa.tecnico.sec.lib.exceptions.*;

public class HMAC {

	private Key key;
	private Date validity;
	
	public HMAC() throws Exception {
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		keyGen.init(128, random);
		this.key = keyGen.generateKey();
		this.validity = new Date(new Date().getTime() + (1000 * 60 * 60 * 24 * 30));
	}
	public HMAC(Key key) {
		this.key = key;
		this.validity = null;
	}
	public String getKey(){
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}
	public HMAC(String key) {
		byte[] keyBytes = Base64.getDecoder().decode(key);
		this.key = new SecretKeySpec(keyBytes, "AES");
	}
	public boolean checkValidity(){
		return validity!=null?validity.after(new Date()):false;
	}
	public String sign(JSONObject resObj) throws ExpiredDigitalSignatureException{
		if(checkValidity())
			throw new ExpiredDigitalSignatureException(validity);
		JwtBuilder builder = Jwts.builder();
		for(int i = 0; i<resObj.names().length(); i++){
			builder.claim(resObj.names().getString(i), resObj.get(resObj.names().getString(i)).toString());
		}
		String token = builder
		.signWith( SignatureAlgorithm.HS512, key)
		.compact();
		return token;
	}
	public boolean verify(String token,JSONObject reqObj) throws ExpiredDigitalSignatureException{
		if(checkValidity())
			throw new ExpiredDigitalSignatureException(validity);
		try{
			JwtParser parser=Jwts.parser();
			for(int i = 0; i<reqObj.names().length(); i++){
				parser.require(reqObj.names().getString(i), reqObj.get(reqObj.names().getString(i)).toString());
			}
			Jws<Claims> claims = parser
			.setSigningKey(key)
			.parseClaimsJws(token);
			return true;
		} catch (MissingClaimException e) {
			System.out.println("Missing key");
			return false;
		} catch (IncorrectClaimException e ) {
			System.out.println("Incorrect value");
			return false;
		}
	}
}
