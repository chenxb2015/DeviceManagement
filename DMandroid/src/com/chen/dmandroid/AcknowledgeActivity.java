package com.chen.dmandroid;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class AcknowledgeActivity extends Activity{

	public List<User> users = new ArrayList<User>();
	AcknowledgeAdaptor aa;
	Dialog dialog;
	
	public void showMsg(final ResponseWithoutData responseWithoutData){
		
		runOnUiThread(new Runnable() {
            @Override
            public void run() {
            
            	if(!responseWithoutData.isSuccess()){
        			dialog.show();
        		}
        		Toast.makeText(getApplicationContext(), responseWithoutData.getMsg(), Toast.LENGTH_LONG).show();
            }
        });
		
	}
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_acknowledge);
		ListView acknowledgeListView = (ListView) findViewById(R.id.borrowerListview);
		
		String serial = Build.SERIAL;
		Log.i("serialNum", serial);
		
		
		  ProgressDialog progress = new ProgressDialog(this);
	        
	      progress.setMessage("initilizing borrow data......");
	 	  progress.show();
	 	 aa = new AcknowledgeAdaptor(this, users);

	        new UserData(progress, users, this).start();
	        
	        acknowledgeListView.setAdapter(aa);
	        acknowledgeListView.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
						
				    final User u = users.get(arg2);
				    Log.i("Acknowledge", u.getEmail());
					AlertDialog.Builder builder = new AlertDialog.Builder(AcknowledgeActivity.this);	
					
					LayoutInflater inflater = AcknowledgeActivity.this.getLayoutInflater();
					
					final View  v = inflater.inflate(R.layout.dialog_acknowledge, null);
					
					builder.setView(v);
					
					final EditText pinCodeEditText = (EditText)  v.findViewById(R.id.pincode);
					
					builder.setTitle("Borrowed by "+ u.getName());	
					
					builder.setPositiveButton("Borrow", new DialogInterface.OnClickListener() {
		                   public void onClick(DialogInterface dialog, int id) {
		                        Log.i("Acknowledge", "borrow");
		                        
		                        String serialNum = Build.SERIAL;
		                        String email = u.getEmail();
		                        
		                        Log.i("serialNum",serialNum);
		                        
		                        if(serialNum.equals("unknown")){
		                        	String serviceName = Context.TELEPHONY_SERVICE;
		            				TelephonyManager m_telephonyManager = (TelephonyManager) getSystemService(serviceName);
		            				String imei  = m_telephonyManager.getDeviceId();;
		            				serialNum = imei +"9999";
		                        }
		                        Log.i("update serialNum to",serialNum);
		                        
		                        Log.i("email",email);
		                      
		                        String pinCode = pinCodeEditText.getText().toString();
		                        
		                      
		                        Log.i("pinCode",pinCode);
		                        
		                        AcknowledgeAction aa = new AcknowledgeAction(serialNum, email, pinCode, AcknowledgeActivity.this);
		                        aa.start();
		                        
	                   }
	               }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   Log.i("Acknowledge", "cancel!!");
	                   }
	               });
				   	
				   dialog = builder.create();
				   dialog.show();
				}
	        	
	        }); 
	        
	}

	public void updateUserView() {
		runOnUiThread(new Runnable() {
            @Override
            public void run() {
            	aa.notifyDataSetChanged();
            }
        });
	}
	
		

}
