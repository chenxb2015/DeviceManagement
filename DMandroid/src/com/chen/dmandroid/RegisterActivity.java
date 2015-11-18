package com.chen.dmandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity{

	 EditText registerNameET;
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        
        Button s = (Button) findViewById(R.id.register_button);
        
        registerNameET = (EditText) findViewById(R.id.register_device_name);
        
        s.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				String registerName = registerNameET.getText().toString();
				
				String deviceType = android.os.Build.DEVICE;
				 String deviceMode = android.os.Build.MODEL;
				String os = "android";
				
				String serviceName = Context.TELEPHONY_SERVICE;
				TelephonyManager m_telephonyManager = (TelephonyManager) getSystemService(serviceName);
				String imei  = m_telephonyManager.getDeviceId();;
				
				 String serialNum = android.os.Build.SERIAL;
				if(serialNum.equals("unknown")){
					serialNum = imei +"9999";
				}
				 String osVersion = android.os.Build.VERSION.RELEASE;
				 Log.i("registerName", registerName);
				 Log.i("deviceType", deviceType);
			     Log.i("deviceMode", deviceMode);
			     Log.i("imei", imei);
			     Log.i("serialNum", serialNum);
			     Log.i("os", os);
			     Log.i("osVersion", osVersion);
			     new RegisterAction(registerName,  deviceType,  deviceMode,  imei,  serialNum, os,  osVersion, RegisterActivity.this).start();
			}
        });
     
     
    }

	public void showMsg(final ResponseWithoutData responseWithoutData) {
		runOnUiThread(new Runnable() {
            @Override
            public void run() {
        		Toast.makeText(getApplicationContext(), responseWithoutData.getMsg(), Toast.LENGTH_LONG).show();
        		RegisterActivity.this.finish();
            }
        });
		
	}

}
