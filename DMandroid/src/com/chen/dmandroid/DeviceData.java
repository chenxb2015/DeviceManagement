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

public class DeviceData extends Thread{

	 private final String USER_AGENT = "Mozilla/5.0";
		
	   private ProgressDialog progress;
	   private  List<Device> devices;
	   DeviceActivity da;
	   
	   public DeviceData(ProgressDialog progress, List<Device> devices, DeviceActivity deviceActivity){
		   this.progress = progress;
		   this.devices = devices;
		   this.da = deviceActivity;
	   }
	   
	   public void run(){
		 
		   
		   String url = UrlHelper.hostUrl + "/dm/deviceStatus";
			try{
				
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				
				// optional default is GET
				con.setRequestMethod("GET");

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

			progress.cancel();
			DeviceResponse deviceResponse = JacksonUtil.convertToDeviceResponse(response.toString());
			
			devices.addAll(deviceResponse.getData());
			da.updateDeviceView();
			
			}catch(Exception e){
				Log.e("error", e.getMessage());
			}
	   }

}
