package com.feng.security.model;

import java.util.Set;


public class ApplicationUser {

	private Long userid;

	String username;

	String password;

	private boolean locked;

	private Set<ApplicationPrivilege> privileges;

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public Set<ApplicationPrivilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Set<ApplicationPrivilege> privileges) {
		this.privileges = privileges;
	}

}
