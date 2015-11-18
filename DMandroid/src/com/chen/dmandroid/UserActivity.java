package com.chen.dmandroid;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class UserActivity extends Activity{

	public List<User> users = new ArrayList<User>();
	UserAdapter ua;
	ProgressDialog progress;
	ImageButton addUser;
	
	
	public void initUsers(){

	}

	public void updateUserView(){
		
		runOnUiThread(new Runnable() {
            @Override
            public void run() {
            	ua.notifyDataSetChanged();
            }
        });
		
	}
	
	public void showMsg(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.activity_user);
        
        final ListView listView = (ListView) findViewById(R.id.listview);
       
        
        progress = new ProgressDialog(this);
        
        progress.setMessage("initilizing user data......");
 	   	progress.show();
        
        ua = new UserAdapter(this, users);

        new UserData(progress, users, this).start();
        
        listView.setAdapter(ua);
        
        listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
					
				final User u = users.get(arg2);
				
				Toast.makeText(getApplicationContext(), u.getEmail(), Toast.LENGTH_SHORT).show();
				
				
				AlertDialog.Builder builder = new AlertDialog.Builder(UserActivity.this);
				builder.setTitle("Delete "+u.getName());
				builder.setMessage("Are you sure ?");
				builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface arg0, int arg1) {
						progress.show();
						new UserDeleteAction(progress, users, u, UserActivity.this).start();
					}
				}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface arg0, int arg1) {
						
					}
				});
				Dialog dialog = builder.create();
				dialog.show();
			}
        	
        });
        
        addUser = (ImageButton) findViewById(R.id.add_user_button);
        
        addUser.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				Intent intent = new Intent(UserActivity.this, AddUserActivity.class);
				startActivity(intent);
				UserActivity.this.finish();
			}
        });
        
    }
    
    public void sendMessage(View view) {
    	 Log.i("MainActivity","sendMessage!!!!!");
    
    	 Intent intent =  new Intent(UserActivity.this, SecondActivity.class);
         startActivity(intent);
    }
   

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

	

}
