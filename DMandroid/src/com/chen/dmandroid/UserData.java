package com.chen.dmandroid;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.chen.helper.JacksonUtil;
import com.chen.helper.UrlHelper;

import android.app.ProgressDialog;
import android.util.Log;

public class UserData extends Thread{
	
   private final String USER_AGENT = "Mozilla/5.0";
	
   private ProgressDialog progress;
   private  List<User> user;
   UserActivity ua;
   AcknowledgeActivity acknowledgeActivity;
   
   public UserData(ProgressDialog progress, List<User> user, AcknowledgeActivity acknowledgeActivity){
	   this.progress = progress;
	   this.user = user;
	   this.acknowledgeActivity = acknowledgeActivity;
   }
   
   public UserData(ProgressDialog progress, List<User> user, UserActivity userActivity){
	   this.progress = progress;
	   this.user = user;
	   this.ua = userActivity;
   }
   
   public void run(){
	 
	   
	   String url = UrlHelper.hostUrl + "/dm/users";
		try{
			
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			
			// optional default is GET
			con.setRequestMethod("GET");

			//add request header
			con.setRequestProperty("User-Agent", USER_AGENT);

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

		progress.cancel();
		UserResponse users = JacksonUtil.convertToUserResponse(response.toString());
		
		user.addAll(users.getData());
		
		boolean uaa = ua != null;
		boolean aaa = acknowledgeActivity != null;
		
		if(ua != null){
			ua.updateUserView();
		}
		if(acknowledgeActivity != null){
			acknowledgeActivity.updateUserView();
		}
			
		}catch(Exception e){
			Log.e("error", e.getMessage().toString());
		}
		
	   
   }
}
