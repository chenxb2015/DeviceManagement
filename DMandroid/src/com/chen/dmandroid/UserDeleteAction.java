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

public class UserDeleteAction extends Thread{
	
	private ProgressDialog progress;
	 private  List<User> users;
	  UserActivity ua;
	  User u; 
	  
	public UserDeleteAction(ProgressDialog progress, List<User> users, User u, UserActivity ua){
		this.progress = progress;
		this.users = users;
		this.u =u;
		this.ua = ua;
	}
	  
	
	private final static String USER_AGENT = "Mozilla/5.0";
	
	public void run(){
		   
		   String url = UrlHelper.hostUrl + "/dm/deleteUser?email="+u.getEmail();
			try{
				
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				// optional default is GET
				con.setRequestMethod("DELETE");
				//add request header
				con.setRequestProperty("User-Agent", USER_AGENT);
				
				int responseCode = con.getResponseCode();
				
				BufferedReader in = new BufferedReader(
				        new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				Log.i("userdelete", response.toString());
			Thread.sleep(2000);
			progress.cancel();
			ResponseWithoutData responseWithoutData = JacksonUtil.convertToResponseWithoutData(response.toString());
			
			if(responseWithoutData.isSuccess()){
				users.remove(u);
				if(ua != null){
					ua.updateUserView();
				}
			}else{
				ua.showMsg(responseWithoutData.getMsg());
			}
				
			}catch(Exception e){
				Log.e("error", e.getMessage().toString());
			}
			
		   
	   }
	
}
