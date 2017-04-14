package pt.ulisboa.tecnico.sec.client.http;

import org.json.JSONObject;

import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;

public class RequestTask {

	private RequestWork work;
	private JSONObject json;
	private FutureTask<JSONObject> task;

	public RequestTask(String url, int replica, JSONObject json, Executor executor) {
		this.work = new RequestWork(url, json, replica);
		this.json = json;
		this.task = new FutureTask<JSONObject>(work);
		executor.execute(this.task);
	}

	public boolean isComplete() {
		return this.task.isDone();
	}

	public JSONObject getRequest(){
		return this.json;
	}

	public JSONObject getResponse(){
		try {
			return this.task.get();
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}
