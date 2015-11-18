package com.chen.dmandroid;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.chen.helper.JacksonUtil;
import com.chen.helper.UrlHelper;



import android.app.Activity;

public class AcknowledgeAction extends Thread{
	private final static String USER_AGENT = "Mozilla/5.0";
	
	AcknowledgeActivity aa;
	String serialNum = "";
	String email = "";
	String pinCode ="";
	
	
	public AcknowledgeAction(String serialNum, String email, String pinCode, AcknowledgeActivity context){
		this.serialNum = serialNum;
		this.email = email;
		this.pinCode = pinCode;
		this.aa = context;
	}
	
	
	public void run(){
		
		
		String url = UrlHelper.hostUrl + "/dm/borrowDevice";
		try{
			
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			
			con.setRequestMethod("POST");

			//add request header
			con.setRequestProperty("User-Agent", USER_AGENT);
			
			String urlParameters = "serialNumber="+ serialNum +"&email="+email+"&pinCode="+pinCode;
			
			con.setDoOutput(true);
			
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			
			wr.flush();
			wr.close();
			

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'Post' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			
			ResponseWithoutData responseWithoutData = JacksonUtil.convertToResponseWithoutData(response.toString());
			aa.showMsg(responseWithoutData);
			
			
			//print result
			System.out.println(response.toString());
		}catch (Exception e){
			e.printStackTrace();
		}
		
		
		
	}
	
	
}
