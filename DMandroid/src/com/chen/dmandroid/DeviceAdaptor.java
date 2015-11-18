package com.chen.dmandroid;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DeviceAdaptor extends BaseAdapter{

	private Activity context;
	List<Device> devices;
	
	public DeviceAdaptor(Activity context, List<Device> devices){
		this.context = context;
		this.devices = devices;
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return devices.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.cdevice, parent, false);

		TextView cddevicename = (TextView) convertView.findViewById(R.id.cddevicename); 
		TextView cdmode = (TextView) convertView.findViewById(R.id.cdmode);
		TextView cdusername = (TextView) convertView.findViewById(R.id.cdusername);
		
//		TextView cddeviceos = (TextView) convertView.findViewById(R.id.cddeviceos); 
		ImageView cddeviceosImage = (ImageView) convertView.findViewById(R.id.cddeviceosImage); 
		
		
		TextView cddeviceosversion = (TextView) convertView.findViewById(R.id.cddeviceosversion);
		
		Device device = devices.get(position);
		
		cddevicename.setText(device.getDeviceName());
		cdmode.setText(device.getDeviceMode());
		cdusername.setText(device.getBorrowerName());
//		cddeviceos.setText(device.getOs());
		String osVersion = device.getOs().trim().toLowerCase();
		
		if(osVersion.equalsIgnoreCase("android")){
			cddeviceosImage.setImageResource(R.drawable.ic_launcher);
		}else if(osVersion.equalsIgnoreCase("ios")){
			cddeviceosImage.setImageResource(R.drawable.ios);
		}else if(osVersion.equalsIgnoreCase("windows")){
			cddeviceosImage.setImageResource(R.drawable.windows);
		}
		cddeviceosversion.setText(device.getOsVersion());
		
		return convertView;
	}

	

}
