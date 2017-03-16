package pt.ulisboa.tecnico.sec.lib.http;

//provides helper methods to print byte[]
import static javax.xml.bind.DatatypeConverter.printHexBinary;
import pt.ulisboa.tecnico.sec.lib.crypto.RSA;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;
import java.security.SignatureException;
import org.json.JSONObject;
import java.security.SecureRandom;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

/** Generate a digital signature */
public class DigitalSignature {
	private PublicKey publicKey;
	private PrivateKey privateKey;


	public DigitalSignature(PrivateKey privateKey) throws Exception{
		this.privateKey = privateKey;
	}
	public DigitalSignature(PrivateKey privateKey, String publicKey) throws Exception{
		this.privateKey = privateKey;
		this.publicKey = RSA.stringToPublicKey(publicKey);
	}
	public void setPublicKey(String publicKey) throws Exception{
		this.publicKey = RSA.stringToPublicKey(publicKey);
	}
	public void setPublicKey(PublicKey publicKey) throws Exception{
		this.publicKey = publicKey;
	}


	public String sign(JSONObject json) throws Exception {
		System.out.println(json.toString());
		byte[] body = json.toString().getBytes("UTF8");
		Signature sig = Signature.getInstance("SHA512withRSA");
		sig.initSign(privateKey);
		sig.update(body);
		byte[] signature = sig.sign();

		return Base64.getEncoder().encodeToString(signature);
	}

	public boolean verify(String token,JSONObject json) throws Exception {
		byte[] body = json.toString().getBytes("UTF8");
		byte[] cipherDigest = Base64.getDecoder().decode(token);
		Signature sig = Signature.getInstance("SHA512withRSA");
		sig.initVerify(publicKey);
		sig.update(body);
		try {
			return sig.verify(cipherDigest);
		} catch (SignatureException se) {
			return false;
		}
	}

}
