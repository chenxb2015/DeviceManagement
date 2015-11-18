package com.chen.dmandroid;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class UserAdapter extends BaseAdapter {

	private Activity context;
	private List<User> users;
	
	public void setUsers(List<User> users){
		this.users = users;
	}
	
	public UserAdapter(Activity context, List<User> users){
		this.context = context;
		this.users = users;
		
	}
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
		
		convertView = inflater.inflate(R.layout.user, parent, false);
		
		TextView emailTextView = (TextView) convertView.findViewById(R.id.secondLine);
		TextView userNameTextView = (TextView) convertView.findViewById(R.id.firstLine);
		
		ImageView imageView = (ImageView) convertView.findViewById(R.id.icon);
		imageView.setImageResource(R.drawable.ic_launcher);
		
		User u = users.get(position);
		emailTextView.setText(u.getEmail());
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

//	@Override
//	public String toString() {
//		
//		return super.toString();
//	}

	
	
	
	
	
}
