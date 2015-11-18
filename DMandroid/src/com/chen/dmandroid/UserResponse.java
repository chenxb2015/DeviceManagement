package com.chen.dmandroid;

import java.util.List;

public class UserResponse extends Response{
	List<User> data;

	public List<User> getData() {
		return data;
	}

	public void setData(List<User> data) {
		this.data = data;
	}
	
	
	
}
