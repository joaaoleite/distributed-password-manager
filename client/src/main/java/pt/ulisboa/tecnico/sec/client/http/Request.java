package pt.ulisboa.tecnico.sec.client.http;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.lang.Math;

import org.json.JSONObject;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import pt.ulisboa.tecnico.sec.client.Session;

public class Request{

	private static String address;
	private static int basePort;
	private static int replicas;

	private List<RequestTask> requests;
	private Executor executor = Executors.newSingleThreadExecutor();

	private long higherTs;
	private String path;
	private JSONObject request;
	private JSONObject response;

	private static int consensus;
	private int complete;

	public static void settings(String a, int p, int r){
		address = a;
		basePort = p;
		replicas = r;

		int faults = (int) Math.floor( (replicas-1)/3 );
		consensus = (int) Math.floor( 2*faults + 1 );
	}

	public Request(String path){
		this.requests = new ArrayList<RequestTask>();
		this.higherTs = 0;
		this.path = path;
		this.complete = 0;
	}

	public void make(JSONObject req) throws Exception{

		try{
			String password = req.getString("password");
			String signature = Session.getInstance().DigitalSignature().sign(password);
			req.put("signature",signature);
		}
		catch(Exception e){ }

		this.request = req;

		// ----------------------------------------

		for(int i=0; i<replicas; i++){
			int port = basePort + i;
			String url = "http://"+address+":"+port+"/"+path;
			requests.add(new RequestTask(url, i, request, this.executor));
		}

		while(!requests.isEmpty() && complete<consensus){
			System.out.println("complete: "+complete+"<"+consensus+" :consensus");
			for(Iterator<RequestTask> it = requests.iterator(); it.hasNext();) {
				RequestTask request = it.next();
				if(request.isComplete()) {

					JSONObject res = null;
					try{
						res = request.getResponse();
					}
					catch(Exception e){
						it.remove();
						continue;
					}

					try{
						String password = res.getString("password");
						String token = res.getString("signature");
						if(!Session.getInstance().DigitalSignature().verify(token,password)){
							it.remove();
							continue;
						}
					}
					catch(Exception e){ }

					long ts;
					try{
						ts = res.getLong("timestamp");
					}
					catch(Exception e){
						System.out.println("No timestamp from server. Setting to 0.");
						e.printStackTrace();
						ts = 0;
					}

					if(ts >= higherTs){
						higherTs = ts;
						response = res;
					}
					complete++;
					it.remove();
				}
			}
			if(!requests.isEmpty()) Thread.sleep(100);
		}
	}

	public JSONObject getResponse(){
		return response;
	}
	public JSONObject getResquest(){
		return request;
	}
	public long getHigherTs(){
		return higherTs;
	}

}
