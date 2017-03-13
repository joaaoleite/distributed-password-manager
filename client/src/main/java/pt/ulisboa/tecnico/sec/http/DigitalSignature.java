package pt.ulisboa.tecnico.sec.security;

import static javax.xml.bind.DatatypeConverter.printHexBinary;

import io.jsonwebtoken.*;

import io.jsonwebtoken.impl.crypto.MacProvider;
import java.security.Key;
import io.jsonwebtoken.impl.TextCodec;
import javax.crypto.KeyGenerator;
import org.json.JSONObject;
import java.util.Date;
import sun.nio.cs.ext.PCK;
import javax.crypto.spec.SecretKeySpec;

import pt.ulisboa.tecnico.sec.exceptions.*;

public class DigitalSignature {

	private Key key;
	private Date validity;

	public DigitalSignature() throws Exception {
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(256); // for example
		this.key = keyGen.generateKey();
		this.validity = new Date(new Date().getTime() + (1000 * 60 * 60 * 24 * 30));
	}

	public DigitalSignature(Key key) {
		this.key = key;
		this.validity = null;
	}

	public String getKey(){
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}

	public DigitalSignature(String key) {
		byte[] keyBytes = Base64.getDecoder().decode(key);
		this.key = new SecretKeySpec(keyBytes, "AES");
	}

	public boolean checkValidity(){
		return validity.after(new Date());
	}

	private String sign(JSONObject resObj) throws DigitalSignatureExpiredException{
		if(checkValidity())
			throw new DigitalSignatureExpiredException(validity);
		JwtBuilder builder = Jwts.builder();
		for(int i = 0; i<resObj.names().length(); i++){
			builder.claim(resObj.names().getString(i), resObj.get(resObj.names().getString(i)).toString());
		}
		String token = builder
		.signWith( SignatureAlgorithm.HS512, key)
		.compact();
		System.out.println(token);
		return token;
	}

	private boolean verify(String token,JSONObject reqObj) throws DigitalSignatureExpiredException{
		if(checkValidity())
			throw new DigitalSignatureExpiredException(validity);
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
