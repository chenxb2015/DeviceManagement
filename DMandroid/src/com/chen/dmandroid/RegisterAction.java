package com.chen.dmandroid;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.chen.helper.JacksonUtil;
import com.chen.helper.UrlHelper;

import android.content.Context;
import android.telephony.TelephonyManager;

public class RegisterAction extends Thread{

	private final static String USER_AGENT = "Mozilla/5.0";
	
	String registerName = "";
	
	String deviceType = "";
	 String deviceMode = "";
	String os = "android";
	
	String imei  = "";
	
	 String serialNum = "";
	 String osVersion = "";
	 RegisterActivity ra;
	
	 
	 public RegisterAction(String registerName, String deviceType, String deviceMode, String imei, String serialNum,String os, String osVersion,  RegisterActivity ra){
		 this.registerName = registerName;
		 this.deviceType = deviceType;
		 this.deviceMode = deviceMode;
		
		 this.imei = imei;
		 this.serialNum = serialNum;
		 this.os = os;
		 this.osVersion = osVersion;
		 this.ra = ra;
	 }
	
	public void run(){
		String url = UrlHelper.hostUrl + "/dm/registerDevice";
		try{
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			
			con.setRequestMethod("POST");

			//add request header
			con.setRequestProperty("User-Agent", USER_AGENT);
			
			String urlParameters = "registerName="+registerName+"&deviceType="+deviceType+"&deviceMode="+deviceMode+"&imei="+imei+"&serialNumber="+serialNum+"&os="+os+"&osVersion="+osVersion;
			
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
			ra.showMsg(responseWithoutData);
			
		}catch (Exception e){
			e.printStackTrace();
		}
		
	}

}
