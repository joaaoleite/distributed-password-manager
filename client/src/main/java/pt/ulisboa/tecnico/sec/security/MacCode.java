package pt.ulisboa.tecnico.sec.security;

//provides helper methods to print byte[]
import static javax.xml.bind.DatatypeConverter.printHexBinary;

import io.jsonwebtoken.*;

import io.jsonwebtoken.impl.crypto.MacProvider;
import java.security.Key;
import io.jsonwebtoken.impl.TextCodec;
import javax.crypto.KeyGenerator;
import org.json.JSONObject;

/** Generate a Message Authentication Code */
public class MacCode {

	/*  Key key = generate();
	JSONObject resObj = new JSONObject();
	resObj.put("sexo","sdlals");
	resObj.put("cona","mmkmkm");
	String jwtStr=makeMAC(key,resObj);
	JSONObject reqObj = new JSONObject();
	reqObj.put("sexo","sdlals");
	reqObj.put("cona","mmkmkm");
	verifyMAC(jwtStr, key,reqObj);*/
	public static Key generate() throws Exception {
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(256); // for example
		return keyGen.generateKey();
	}

	private static String makeMAC(Key key, JSONObject resObj){
		JwtBuilder builder = Jwts.builder();
		for(int i = 0; i<resObj.names().length(); i++){
			builder.claim(resObj.names().getString(i), resObj.get(resObj.names().getString(i)).toString());
		}
		String jwtStr = builder
		.signWith( SignatureAlgorithm.HS512,key)
		.compact();
		System.out.println(jwtStr);
		return jwtStr;

	}

	private static boolean verifyMAC(String jwtStr,Key key,JSONObject reqObj){
		try{
			JwtParser parser=Jwts.parser();
			for(int i = 0; i<reqObj.names().length(); i++){
				parser.require(reqObj.names().getString(i), reqObj.get(reqObj.names().getString(i)).toString());
			}
			Jws<Claims> claims = parser
			.setSigningKey(key)
			.parseClaimsJws(jwtStr);
			return true;
		} catch (MissingClaimException e) {
			System.out.println("Missing Claim");
			return false;
		} catch (IncorrectClaimException e ) {
			System.out.println("Incorrect Claim");
			return false;
		}
	}




}
