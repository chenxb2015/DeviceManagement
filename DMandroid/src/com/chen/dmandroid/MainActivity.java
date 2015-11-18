package com.chen.dmandroid;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity  {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        
        Button s = (Button) findViewById(R.id.user_link_button);
        s.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				Intent userIntent = new Intent(MainActivity.this, UserActivity.class);
				 startActivity(userIntent);
			}
        });
        
        
        Button deviceLink = (Button) findViewById(R.id.device_link_button);
        deviceLink.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				Intent deviceIntent = new Intent(MainActivity.this, DeviceActivity.class);
				 startActivity(deviceIntent);
			}
        	
        });
        
        Button acknowledgeLink = (Button) findViewById(R.id.acknowledge_link_button);
        acknowledgeLink.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				Intent deviceIntent = new Intent(MainActivity.this, AcknowledgeActivity.class);
				 startActivity(deviceIntent);
			}
        	
        });
     
        Button registerLink = (Button) findViewById(R.id.register_link_button);
        registerLink.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				Intent deviceIntent = new Intent(MainActivity.this, RegisterActivity.class);
				 startActivity(deviceIntent);
			}
        	
        });
     
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
