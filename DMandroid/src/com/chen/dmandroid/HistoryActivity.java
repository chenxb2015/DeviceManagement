package com.chen.dmandroid;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.ListView;

public class HistoryActivity extends Activity{

	
	HistoryAdaptor ha;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);
		
		ProgressDialog pd = new ProgressDialog(this);
		pd.setMessage("initilizing history data......");
		pd.show();
		
		String serialNumber = this.getIntent().getStringExtra("serialNum");
		
		Log.i("HistoryActi!!!", serialNumber);
		
		List<History> histories = new ArrayList<History>();
		ha = new HistoryAdaptor(this, histories);
		
		ListView historylistview = (ListView) findViewById(R.id.historylistview);
		historylistview.setAdapter(ha);
		new HistoryData(pd, histories, this, serialNumber).start();
	}

	public void updateDeviceView() {
		runOnUiThread(new Runnable() {
            @Override
            public void run() {
            	ha.notifyDataSetChanged();
            }
        });
	}

}
