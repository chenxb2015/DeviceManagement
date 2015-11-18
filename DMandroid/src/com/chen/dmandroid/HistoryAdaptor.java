package com.chen.dmandroid;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HistoryAdaptor extends BaseAdapter{
	
	private Activity context;
	List<History> histories;
	SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd HH:mm");
	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	
	public HistoryAdaptor(Activity context, List<History> histories){
		this.context = context;
		this.histories = histories;
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return histories.size();
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
		convertView = inflater.inflate(R.layout.history, parent, false);
		
		TextView historyusername = (TextView) convertView.findViewById(R.id.historyusername); 
		TextView historydate = (TextView) convertView.findViewById(R.id.historydate);
		
		History history = histories.get(position);
		historyusername.setText(history.getBorrowedBy());
		
		String d = "2015-08-26T08:45:48.000Z";

		Date date = null;
		try {
			date = formatter.parse(history.getBorrowedDate());
		} catch (ParseException e) {
			Log.i("HistoryAdaptor", "format date error");
		}
		historydate.setText(sd.format(date));
		
		return convertView;
	}
}
