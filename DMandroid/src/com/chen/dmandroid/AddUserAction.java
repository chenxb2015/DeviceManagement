package com.chen.dmandroid;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.chen.helper.JacksonUtil;
import com.chen.helper.UrlHelper;

public class AddUserAction extends Thread{

private final static String USER_AGENT = "Mozilla/5.0";
	

	String username;
	String email;
	String password;
	AddUserActivity aa;
	
	public AddUserAction(String username, String email, String password, AddUserActivity aa){
		this.username = username;
		this.email = email;
		this.password = password;
		this.aa = aa;
	}
	 
	
	public void run(){
		
		
		String url = UrlHelper.hostUrl + "/dm/addUser";
		try{
			
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			
			con.setRequestMethod("POST");

			//add request header
			con.setRequestProperty("User-Agent", USER_AGENT);
			String urlParameters = "email="+email+"&password="+password+"&name="+username;
			
			con.setDoOutput(true);
			
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();

			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			ResponseWithoutData responseWithoutData = JacksonUtil.convertToResponseWithoutData(response.toString());
			
			aa.updateAddUserView(responseWithoutData);
			
		}catch (Exception e){
			e.printStackTrace();
		}
		
		
		
	}
}
