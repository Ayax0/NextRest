package com.nextlvlup.nextrest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Base64;

import org.json.JSONObject;

public class RestRequest {
	
	private HttpURLConnection con;
	
	public RestRequest(String url, RequestType type) {
		try {
			con = (HttpURLConnection) new URL(url).openConnection();
			
			con.setRequestMethod(type.getName());
			con.setRequestProperty("Accept", "application/json");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public RestRequest basicAuth(String username, String password) {
		con.setRequestProperty("Authorization", "Basic " + Base64.getEncoder().encodeToString(new String(username + ":" + password).getBytes()));
		return this;
	}
	
	public int execute() {
		try {
			return con.getResponseCode();
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public void execute(JSONObject obj) {
		execute(obj.toString());
	}
	
	public void execute(String obj) {
		try {
			con.setDoOutput(true);
			con.setRequestProperty("Content-Type", "application/json");
			
			OutputStream os = con.getOutputStream();
			os.write(Charset.forName("UTF-8").encode(obj).array());
			os.flush();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getResponseCode() {
		try {
			return con.getResponseCode();
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public JSONObject getResponse() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			StringBuilder jsonString = new StringBuilder();
			String output;
			while((output = br.readLine()) != null) {
				jsonString.append(output);
			}
			
			return new JSONObject(jsonString.toString());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
