package com.team.prj.user;

import java.util.List;

public class Users {
	
	private List<User> user;
	
	public Users() {
	}

	public Users(List<User> user) {
		super();
		this.user = user;
	}

	public List<User> getUser() {
		return user;
	}

	public void setUser(List<User> user) {
		this.user = user;
	}

}
