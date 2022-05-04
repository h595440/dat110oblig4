package no.hvl.dat110.aciotdevice.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RestClient {

	public RestClient() {
		// TODO Auto-generated constructor stub
	}

	private static String logpath = "/accessdevice/log";

	public void doPostAccessEntry(String message) {

		// TODO: implement a HTTP POST on the service to post the message
		MediaType json = MediaType.parse("application/json; charset=utf-8");
		String urlP = "http://localhost:8080" + logpath;
		Gson gson = new Gson();
		AccessMessage msg = new AccessMessage(message);
		
		OkHttpClient client = new OkHttpClient();
		RequestBody body = RequestBody.create(json, gson.toJson(msg));
		Request request = new Request.Builder().url(urlP).post(body).build();
		
		try (Response res = client.newCall(request).execute()) {
			System.out.println(res.body().string());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String codepath = "/accessdevice/code";
	
	public AccessCode doGetAccessCode() {

		AccessCode code = null;
		
		// TODO: implement a HTTP GET on the service to get current access code
		String urlS = "http://" + Configuration.host + ":" + Configuration.port + codepath;
		URL url;
		HttpURLConnection connection;
		int responseCode;
		Gson gson;
		BufferedReader in;
		
		try {
			url = new URL(urlS);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			responseCode = connection.getResponseCode();
			
			if(responseCode == HttpURLConnection.HTTP_OK) {
				gson = new Gson();
				in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				code = gson.fromJson(in.readLine(), AccessCode.class);				
				
				in.close();
			}
			connection.disconnect();
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		return code;
	}
}
