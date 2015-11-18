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

public class HistoryData extends Thread{
	 
	   private final String USER_AGENT = "Mozilla/5.0";
		
	   private ProgressDialog progress;
	   private  List<History> histories;
	   HistoryActivity ha;
	   String serialNum;
	   
	   public HistoryData(ProgressDialog progress, List<History> histories, HistoryActivity historyActivity, String serialNum){
		   this.progress = progress;
		   this.histories = histories;
		   this.ha = historyActivity;
		   this.serialNum = serialNum;
	   }
	   
	   public void run(){
		   String url = UrlHelper.hostUrl + "/dm/deviceHistory?serialNum="+serialNum;
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

				Log.i("history", response.toString());
				
			progress.cancel();
			HistoryResponse historyResponse = JacksonUtil.convertToHistoryResponse(response.toString());
			
			histories.addAll(historyResponse.getData());
			ha.updateDeviceView();
			}catch(Exception e){
				Log.e("error", e.getMessage());
			}
			
		   
	   }

}
