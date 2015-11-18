package com.chen.dmandroid;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class DeviceActivity extends Activity{

	DeviceAdaptor da;
	List<Device> devices;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cdevice);
		
		ProgressDialog pd = new ProgressDialog(this);
		pd.setMessage("initilizing device data......");
		pd.show();
		
	    devices = new ArrayList<Device>();
		da = new DeviceAdaptor(this, devices);
		new DeviceData(pd, devices, this).start();
		
		ListView deviceListView = (ListView) findViewById(R.id.deviceListview);
		deviceListView.setAdapter(da);
		
		
		deviceListView.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
						
				    final Device device = devices.get(arg2);
				    
				    Log.i("Device SerialNum: ", device.getSerialNumber());
						
					Intent intent = new Intent(DeviceActivity.this, HistoryActivity.class);
					intent.putExtra("serialNum", device.getSerialNumber());
					startActivity(intent);
	        	
				}
		});
		
		
	}

	public void updateDeviceView() {
		runOnUiThread(new Runnable() {
            @Override
            public void run() {
            	da.notifyDataSetChanged();
            }
        });
	}

	

	

}
