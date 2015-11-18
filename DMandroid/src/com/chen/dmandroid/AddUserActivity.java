package com.chen.dmandroid;

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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class AddUserActivity extends Activity{

	EditText username;
	EditText email;
	EditText password;
	Button addUserButton;
	ProgressDialog progress;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_user);
		
		username = (EditText) findViewById(R.id.addusername);
		email = (EditText) findViewById(R.id.adduseremail);
		password = (EditText) findViewById(R.id.adduserpassword);
		addUserButton = (Button) findViewById(R.id.submitadduserbutton);
		progress = new ProgressDialog(AddUserActivity.this);
		addUserButton.setOnClickListener(new OnClickListener(){
			
			public void onClick(View v) {
				String uname = username.getText().toString();
				String emails = email.getText().toString();
				String passwords = password.getText().toString();
			        
			    progress.setMessage("registering user, please wait....");
			 	progress.show();
			 	new AddUserAction(uname, emails, passwords, AddUserActivity.this).start();  
			}
		});
	        
	}

	public void updateAddUserView(final ResponseWithoutData responseWithoutData) {
		
		runOnUiThread(new Runnable() {
            @Override
            public void run() {
            	final String msg = responseWithoutData.getMsg();
        		if(responseWithoutData.isSuccess()){
        			username.setText("");
        			password.setText("");
        			email.setText("");
        		}else{
        			password.setText("");
        			
        		}
            	Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        });
		progress.cancel();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		
		Intent intent = new Intent(AddUserActivity.this, UserActivity.class);
		startActivity(intent);
	
	}
	
	
	
	
}
