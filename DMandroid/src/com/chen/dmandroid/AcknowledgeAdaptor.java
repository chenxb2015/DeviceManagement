package com.chen.dmandroid;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AcknowledgeAdaptor extends BaseAdapter{

	private Activity context;
	private List<User> users;
	
	public void setUsers(List<User> users){
		this.users = users;
	}
	
	public AcknowledgeAdaptor(Activity context, List<User> users){
		this.context = context;
		this.users = users;
		
	}
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
		
		convertView = inflater.inflate(R.layout.acknowledge, parent, false);

		TextView userNameTextView = (TextView) convertView.findViewById(R.id.acknowledgeUser);
		
		User u = users.get(position);

		userNameTextView.setText(u.getName());
		
		return convertView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return users.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return users.get(arg0);
	}


	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
}
